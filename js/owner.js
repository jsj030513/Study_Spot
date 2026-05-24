const BACKEND_URL = `${location.protocol}//${location.hostname}:8080`;
const API_BASE_URL = location.port === '5500' ? BACKEND_URL : '';

let myCafes = [];
let selectedCafe = null;
let profileExists = false;
let statusExists = false;

function routeUrl(path) {
    return location.port === '5500' ? `${BACKEND_URL}${path}` : path;
}

function getStoredUser() {
    try {
        return JSON.parse(localStorage.getItem('user'));
    } catch (error) {
        localStorage.removeItem('user');
        return null;
    }
}

function getAuthHeaders() {
    const token = localStorage.getItem('authToken');
    return {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
    };
}

async function requestJson(path, options = {}) {
    const response = await fetch(`${API_BASE_URL}${path}`, {
        ...options,
        headers: {
            ...getAuthHeaders(),
            ...(options.headers || {})
        }
    });

    if (!response.ok) {
        throw new Error(await readErrorMessage(response));
    }

    if (response.status === 204) return null;
    return response.json();
}

async function readErrorMessage(response) {
    try {
        const data = await response.json();
        return data.errors?.[0]?.message || data.message || data.error || '요청을 처리하지 못했습니다.';
    } catch (error) {
        return '요청을 처리하지 못했습니다.';
    }
}

async function initOwnerPage() {
    const user = getStoredUser();
    if (!user || !localStorage.getItem('authToken')) {
        location.href = routeUrl('/login');
        return;
    }

    if (user.role !== 'O' && user.role !== 'A') {
        showEmpty('사장님 권한이 필요합니다.', '관리자 승인 후 사장님 페이지를 이용할 수 있습니다.');
        return;
    }

    bindForms();
    await loadMyCafes();
}

async function loadMyCafes() {
    try {
        myCafes = await requestJson('/api/owner/cafes');
        renderCafeList();

        if (myCafes.length === 0) {
            showEmpty('아직 연결된 카페가 없습니다.', '관리자 승인을 받은 내 장소만 이곳에 표시됩니다.');
            return;
        }

        await selectCafe(myCafes[0].placeId);
    } catch (error) {
        showEmpty('내 카페를 불러오지 못했습니다.', error.message);
    }
}

function renderCafeList() {
    const cafeList = document.getElementById('cafeList');
    cafeList.innerHTML = '';

    myCafes.forEach(cafe => {
        const button = document.createElement('button');
        button.type = 'button';
        button.className = `cafe-button ${selectedCafe?.placeId === cafe.placeId ? 'active' : ''}`;
        button.onclick = () => selectCafe(cafe.placeId);

        const name = document.createElement('strong');
        name.textContent = cafe.name;

        const address = document.createElement('span');
        address.textContent = cafe.address || '주소 정보 없음';

        button.append(name, address);
        cafeList.appendChild(button);
    });
}

async function selectCafe(placeId) {
    selectedCafe = myCafes.find(cafe => cafe.placeId === placeId);
    if (!selectedCafe) return;

    renderCafeList();
    document.getElementById('emptyState').hidden = true;
    document.getElementById('ownerDashboard').hidden = false;
    document.getElementById('selectedCafeName').textContent = selectedCafe.name;
    document.getElementById('selectedCafeAddress').textContent = selectedCafe.address || '주소 정보 없음';
    fillBasicForm(selectedCafe);

    await Promise.all([
        loadOpenStatus(),
        loadOccupancyStatus(),
        loadProfile(),
        loadPhotos()
    ]);
}

function fillBasicForm(cafe) {
    document.getElementById('cafeNameInput').value = cafe.name || '';
    document.getElementById('telNoInput').value = cafe.telNo || '';
    document.getElementById('addressInput').value = cafe.address || '';
    document.getElementById('wifiStatusInput').value = cafe.wifiStatus || '';
    document.getElementById('outletStatusInput').value = cafe.outletStatus || '';
    document.getElementById('noiseLevelInput').value = cafe.noiseLevel || '';
    document.getElementById('seatTypeInput').value = cafe.seatType || '';
    document.getElementById('descriptionInput').value = cafe.description || '';
}

async function loadOpenStatus() {
    document.getElementById('openStatus').value = 'false';
    document.getElementById('openStatusMessage').value = '';
    document.getElementById('openStatusUpdatedAt').textContent = '미등록';

    try {
        const status = await requestJson(`/api/owner/cafes/${selectedCafe.placeId}/open-status`);
        document.getElementById('openStatus').value = String(Boolean(status.open));
        document.getElementById('openStatusMessage').value = status.message || '';
        document.getElementById('openStatusUpdatedAt').textContent = status.updatedAt
            ? formatDateTime(status.updatedAt)
            : '미등록';
    } catch (error) {
        document.getElementById('openStatusUpdatedAt').textContent = '미등록';
    }
}

async function loadOccupancyStatus() {
    statusExists = false;
    resetOccupancyStatus();

    try {
        const status = await requestJson(`/api/owner/cafes/${selectedCafe.placeId}/status`);
        statusExists = true;
        renderOccupancyStatus(status);
    } catch (error) {
        document.getElementById('statusUpdatedAt').textContent = '미등록';
    }
}

function resetOccupancyStatus() {
    document.getElementById('currentCount').value = 0;
    document.getElementById('capacity').value = 1;
    document.getElementById('congestionLevel').value = '';
    document.getElementById('occupancySummary').textContent = '0 / 0명';
    document.getElementById('congestionBadge').textContent = '미등록';
    document.getElementById('congestionBadge').className = '';
    document.getElementById('occupancyFill').style.width = '0%';
}

function renderOccupancyStatus(status) {
    document.getElementById('currentCount').value = status.currentCount;
    document.getElementById('capacity').value = status.capacity;
    document.getElementById('congestionLevel').value = status.congestionLevel || '';
    document.getElementById('occupancySummary').textContent = `${status.currentCount} / ${status.capacity}명`;
    document.getElementById('statusUpdatedAt').textContent = formatDateTime(status.updatedAt);

    const badge = document.getElementById('congestionBadge');
    badge.textContent = congestionLabel(status.congestionLevel);
    badge.className = String(status.congestionLevel || '').toLowerCase();

    const rate = Math.min(Number(status.occupancyRate) || 0, 100);
    document.getElementById('occupancyFill').style.width = `${rate}%`;
}

async function loadProfile() {
    profileExists = false;
    fillProfileForm({});

    try {
        const profile = await requestJson(`/api/owner/cafes/${selectedCafe.placeId}/profile`);
        profileExists = true;
        fillProfileForm(profile);
    } catch (error) {
        profileExists = false;
    }
}

function fillProfileForm(profile) {
    document.getElementById('introText').value = profile.introText || '';
    document.getElementById('noticeText').value = profile.noticeText || '';
    document.getElementById('openingHours').value = profile.openingHours || '';
    document.getElementById('menuText').value = profile.menuText || '';
    document.getElementById('snsUrl').value = profile.snsUrl || '';
}

async function loadPhotos() {
    const photoGrid = document.getElementById('photoGrid');
    photoGrid.innerHTML = '<div class="empty-state"><strong>사진을 불러오는 중입니다.</strong></div>';

    try {
        const photos = await requestJson(`/api/owner/cafes/${selectedCafe.placeId}/photos`);
        renderPhotos(photos);
    } catch (error) {
        photoGrid.innerHTML = `<div class="empty-state"><strong>사진을 불러오지 못했습니다.</strong><span>${escapeHtml(error.message)}</span></div>`;
    }
}

function renderPhotos(photos) {
    const photoGrid = document.getElementById('photoGrid');
    photoGrid.innerHTML = '';

    if (!photos.length) {
        photoGrid.innerHTML = '<div class="empty-state"><strong>등록된 사진이 없습니다.</strong><span>사진 URL을 추가해 매장 분위기를 보여주세요.</span></div>';
        return;
    }

    photos
        .slice()
        .sort((a, b) => a.displayOrder - b.displayOrder)
        .forEach(photo => {
            const card = document.createElement('div');
            card.className = 'photo-card';

            const image = document.createElement('img');
            image.src = photo.photoUrl;
            image.alt = '카페 등록 사진';

            const footer = document.createElement('div');
            footer.className = 'photo-card-footer';

            const order = document.createElement('span');
            order.textContent = `${photo.displayOrder}번째 사진`;

            const deleteButton = document.createElement('button');
            deleteButton.type = 'button';
            deleteButton.textContent = '삭제';
            deleteButton.onclick = () => deletePhoto(photo.photoId);

            footer.append(order, deleteButton);
            card.append(image, footer);
            photoGrid.appendChild(card);
        });
}

function bindForms() {
    document.getElementById('basicForm').addEventListener('submit', saveBasicInfo);
    document.getElementById('openStatusForm').addEventListener('submit', saveOpenStatus);
    document.getElementById('occupancyForm').addEventListener('submit', saveOccupancyStatus);
    document.getElementById('profileForm').addEventListener('submit', saveProfile);
    document.getElementById('photoForm').addEventListener('submit', addPhoto);
}

async function saveBasicInfo(event) {
    event.preventDefault();
    if (!selectedCafe) return;

    const address = document.getElementById('addressInput').value.trim();
    const body = {
        name: document.getElementById('cafeNameInput').value.trim(),
        address,
        telNo: document.getElementById('telNoInput').value.trim(),
        wifiStatus: document.getElementById('wifiStatusInput').value.trim(),
        outletStatus: document.getElementById('outletStatusInput').value.trim(),
        noiseLevel: document.getElementById('noiseLevelInput').value.trim(),
        seatType: document.getElementById('seatTypeInput').value.trim(),
        description: document.getElementById('descriptionInput').value.trim()
    };

    try {
        if (address && address !== (selectedCafe.address || '')) {
            const coordinates = await geocodeAddress(address);
            body.latitude = coordinates.latitude;
            body.longitude = coordinates.longitude;
        }

        const updatedCafe = await requestJson(`/api/owner/cafes/${selectedCafe.placeId}`, {
            method: 'PATCH',
            body: JSON.stringify(body)
        });
        selectedCafe = updatedCafe;
        myCafes = myCafes.map(cafe => cafe.placeId === updatedCafe.placeId ? updatedCafe : cafe);
        document.getElementById('selectedCafeName').textContent = updatedCafe.name;
        document.getElementById('selectedCafeAddress').textContent = updatedCafe.address || '주소 정보 없음';
        renderCafeList();
        alert('카페 기본정보가 저장되었습니다.');
    } catch (error) {
        alert(error.message);
    }
}

function geocodeAddress(address) {
    return new Promise((resolve, reject) => {
        if (window.kakaoMapSdkFailed || !window.kakao?.maps?.services) {
            reject(new Error('주소 좌표 변환을 사용할 수 없습니다. 카카오맵 SDK 로딩을 확인해주세요.'));
            return;
        }

        const geocoder = new kakao.maps.services.Geocoder();
        geocoder.addressSearch(address, (result, status) => {
            if (status !== kakao.maps.services.Status.OK || !result.length) {
                reject(new Error('주소를 찾을 수 없습니다. 도로명/지번 주소를 정확히 입력해주세요.'));
                return;
            }

            resolve({
                latitude: Number(result[0].y),
                longitude: Number(result[0].x)
            });
        });
    });
}

async function saveOpenStatus(event) {
    event.preventDefault();
    if (!selectedCafe) return;

    const body = {
        open: document.getElementById('openStatus').value === 'true',
        message: document.getElementById('openStatusMessage').value.trim()
    };

    try {
        const status = await requestJson(`/api/owner/cafes/${selectedCafe.placeId}/open-status`, {
            method: 'PATCH',
            body: JSON.stringify(body)
        });
        document.getElementById('openStatus').value = String(Boolean(status.open));
        document.getElementById('openStatusMessage').value = status.message || '';
        document.getElementById('openStatusUpdatedAt').textContent = status.updatedAt
            ? formatDateTime(status.updatedAt)
            : '미등록';
        alert('영업 상태가 저장되었습니다.');
    } catch (error) {
        alert(error.message);
    }
}

async function saveOccupancyStatus(event) {
    event.preventDefault();
    if (!selectedCafe) return;

    const congestionLevel = document.getElementById('congestionLevel').value;
    const body = {
        currentCount: Number(document.getElementById('currentCount').value),
        capacity: Number(document.getElementById('capacity').value),
        congestionLevel: congestionLevel || null
    };

    try {
        const status = await requestJson(`/api/owner/cafes/${selectedCafe.placeId}/status`, {
            method: statusExists ? 'PATCH' : 'POST',
            body: JSON.stringify(body)
        });
        statusExists = true;
        renderOccupancyStatus(status);
        alert('혼잡도 정보가 저장되었습니다.');
    } catch (error) {
        alert(error.message);
    }
}

async function saveProfile(event) {
    event.preventDefault();
    if (!selectedCafe) return;

    const body = {
        introText: document.getElementById('introText').value.trim(),
        noticeText: document.getElementById('noticeText').value.trim(),
        openingHours: document.getElementById('openingHours').value.trim(),
        menuText: document.getElementById('menuText').value.trim(),
        snsUrl: document.getElementById('snsUrl').value.trim()
    };

    try {
        await requestJson(`/api/owner/cafes/${selectedCafe.placeId}/profile`, {
            method: profileExists ? 'PATCH' : 'POST',
            body: JSON.stringify(body)
        });
        profileExists = true;
        alert('카페 프로필이 저장되었습니다.');
    } catch (error) {
        alert(error.message);
    }
}

async function addPhoto(event) {
    event.preventDefault();
    if (!selectedCafe) return;

    const body = {
        photoUrl: document.getElementById('photoUrl').value.trim(),
        displayOrder: Number(document.getElementById('displayOrder').value)
    };

    try {
        await requestJson(`/api/owner/cafes/${selectedCafe.placeId}/photos`, {
            method: 'POST',
            body: JSON.stringify(body)
        });
        document.getElementById('photoUrl').value = '';
        await loadPhotos();
        alert('카페 사진이 등록되었습니다.');
    } catch (error) {
        alert(error.message);
    }
}

async function deletePhoto(photoId) {
    if (!selectedCafe || !confirm('이 사진을 삭제하시겠습니까?')) return;

    try {
        await requestJson(`/api/owner/cafes/${selectedCafe.placeId}/photos/${photoId}`, {
            method: 'DELETE'
        });
        await loadPhotos();
    } catch (error) {
        alert(error.message);
    }
}

function showEmpty(title, description) {
    const emptyState = document.getElementById('emptyState');
    emptyState.hidden = false;
    document.getElementById('ownerDashboard').hidden = true;
    emptyState.innerHTML = `<strong>${escapeHtml(title)}</strong><span>${escapeHtml(description)}</span>`;
}

function congestionLabel(level) {
    if (level === 'LOW') return '여유';
    if (level === 'MEDIUM') return '보통';
    if (level === 'HIGH') return '혼잡';
    return '미등록';
}

function formatDateTime(value) {
    if (!value) return '미등록';
    const date = new Date(value);
    if (Number.isNaN(date.getTime())) return '미등록';
    return date.toLocaleString('ko-KR', { dateStyle: 'short', timeStyle: 'short' });
}

function escapeHtml(value) {
    return String(value ?? '')
        .replaceAll('&', '&amp;')
        .replaceAll('<', '&lt;')
        .replaceAll('>', '&gt;')
        .replaceAll('"', '&quot;')
        .replaceAll("'", '&#039;');
}

window.refreshSelectedCafe = async () => {
    if (selectedCafe) await selectCafe(selectedCafe.placeId);
};

window.handleLogout = () => {
    localStorage.removeItem('user');
    localStorage.removeItem('authToken');
    location.href = routeUrl('/login');
};

window.addEventListener('DOMContentLoaded', initOwnerPage);
