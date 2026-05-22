const BACKEND_URL = `${location.protocol}//${location.hostname}:8080`;
const API_BASE_URL = location.port === '5500' ? BACKEND_URL : '';

let places = [];
let users = [];
let ownerStatusFilter = '';
let currentFilter = '전체';

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

function authHeaders() {
    return {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('authToken') || ''}`
    };
}

async function requestJson(path, options = {}) {
    const response = await fetch(`${API_BASE_URL}${path}`, {
        ...options,
        headers: {
            ...authHeaders(),
            ...(options.headers || {})
        }
    });

    if (!response.ok) {
        const message = await readErrorMessage(response);
        if (response.status === 401 || response.status === 403) {
            clearAdminSession();
            alert(message);
            location.href = routeUrl('/adminlogin');
            throw new Error(message);
        }
        throw new Error(message);
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

document.addEventListener('DOMContentLoaded', async () => {
    const user = getStoredUser();
    if (!user || !localStorage.getItem('authToken')) {
        location.href = routeUrl('/adminlogin');
        return;
    }
    if (user.role !== 'A') {
        clearAdminSession();
        alert('관리자 권한이 필요합니다.');
        location.href = routeUrl('/adminlogin');
        return;
    }

    document.getElementById('adminName').textContent = user.name;
    await Promise.all([loadPlaces(), loadUsers(), loadOwnerVerifications('PENDING')]);
});

function switchMode(mode) {
    const sections = {
        spot: document.getElementById('spotSection'),
        user: document.getElementById('userSection'),
        review: document.getElementById('reviewSection'),
        owner: document.getElementById('ownerSection')
    };
    Object.entries(sections).forEach(([key, section]) => {
        if (section) section.style.display = key === mode ? 'block' : 'none';
    });

    document.querySelectorAll('.menu-btn').forEach(button => button.classList.remove('active'));
    const activeButton = document.getElementById(`btn${capitalize(mode)}Mode`);
    activeButton?.classList.add('active');

    if (mode === 'review') loadReviews();
    if (mode === 'owner') loadOwnerVerifications(ownerStatusFilter);
}

function capitalize(value) {
    return value.charAt(0).toUpperCase() + value.slice(1);
}

async function loadPlaces() {
    try {
        places = await fetchPublicJson('/api/places');
        updateDashboard();
        renderSpotTable();
    } catch (error) {
        renderEmpty('spotTableBody', error.message);
    }
}

async function fetchPublicJson(path) {
    const response = await fetch(`${API_BASE_URL}${path}`);
    if (!response.ok) throw new Error(await readErrorMessage(response));
    return response.json();
}

function updateDashboard() {
    const counts = { '전체': places.length, '카페': 0, '도서관': 0, '편의점': 0, '문구점': 0, '프린트': 0 };
    places.forEach(place => {
        const label = normalizeTypeName(place.typeName);
        if (counts[label] !== undefined) counts[label] += 1;
    });

    setText('countAll', counts['전체']);
    setText('countCafe', counts['카페']);
    setText('countLibrary', counts['도서관']);
    setText('countStore', counts['편의점']);
    setText('countStationery', counts['문구점']);
    setText('countPrint', counts['프린트']);
}

function normalizeTypeName(typeName) {
    if (typeName === '복사/인쇄') return '프린트';
    if (typeName === '문구/잡화' || typeName === '교내문구') return '문구점';
    return typeName || '기타';
}

function setText(id, value) {
    const element = document.getElementById(id);
    if (element) element.textContent = value;
}

function filterCategory(category) {
    currentFilter = category;
    setText('currentCategoryTitle', category);
    document.querySelectorAll('.category-card').forEach(card => {
        card.classList.toggle('active', card.querySelector('h4')?.textContent === category);
    });
    renderSpotTable();
}

function renderSpotTable() {
    const filtered = currentFilter === '전체'
        ? places
        : places.filter(place => normalizeTypeName(place.typeName) === currentFilter);

    const rows = filtered.map(place => `
        <tr>
            <td><strong>${escapeHtml(place.name)}</strong><div class="muted">${escapeHtml(place.address || '')}</div></td>
            <td>${escapeHtml(place.typeName)}</td>
            <td>${place.recommendScore}점</td>
            <td><span class="status-badge status-user">${escapeHtml(place.placeId)}</span></td>
        </tr>
    `).join('');

    document.getElementById('spotTableBody').innerHTML = rows || emptyRow(4, '장소가 없습니다.');
}

async function loadUsers() {
    const keyword = document.getElementById('userSearch')?.value.trim();
    const query = keyword ? `?keyword=${encodeURIComponent(keyword)}` : '';

    try {
        users = await requestJson(`/api/admin/users${query}`);
        renderUserTable();
    } catch (error) {
        renderEmpty('userTableBody', error.message, 6);
    }
}

function renderUserTable() {
    const rows = users.map(user => `
        <tr>
            <td>${escapeHtml(user.userId)}</td>
            <td><strong>${escapeHtml(user.name)}</strong></td>
            <td>-</td>
            <td>${escapeHtml(user.registeredDate || '-')}</td>
            <td>${roleBadge(user.role)}</td>
            <td>
                <button class="text-danger-btn" onclick="deleteUser('${escapeJs(user.userId)}')">삭제</button>
            </td>
        </tr>
    `).join('');

    document.getElementById('userTableBody').innerHTML = rows || emptyRow(6, '회원이 없습니다.');
}

function roleBadge(role) {
    const label = role === 'A' ? '관리자' : role === 'O' ? '사장님' : '이용자';
    const className = role === 'A' ? 'status-admin' : role === 'O' ? 'status-owner' : 'status-user';
    return `<span class="status-badge ${className}">${label}</span>`;
}

async function deleteUser(userId) {
    if (!confirm(`${userId} 회원을 삭제하시겠습니까?`)) return;

    try {
        await requestJson(`/api/admin/users/${encodeURIComponent(userId)}`, { method: 'DELETE' });
        await loadUsers();
    } catch (error) {
        alert(error.message);
    }
}

async function loadReviews() {
    const tbody = document.getElementById('reviewTableBody');
    tbody.innerHTML = emptyRow(6, '한줄평을 불러오는 중입니다.');

    try {
        if (!places.length) await loadPlaces();
        const cafes = places.filter(place => place.type === 'cafe');
        const reviewGroups = await Promise.all(cafes.map(async cafe => {
            const reviews = await fetchPublicJson(`/api/places/${cafe.placeId}/reviews`);
            return reviews.map(review => ({ ...review, cafe }));
        }));
        renderReviewTable(reviewGroups.flat());
    } catch (error) {
        renderEmpty('reviewTableBody', error.message, 6);
    }
}

function renderReviewTable(reviews) {
    const rows = reviews.map(review => `
        <tr class="${review.clean ? '' : 'flagged-row'}">
            <td><strong>${escapeHtml(review.cafe.name)}</strong><div class="muted">${escapeHtml(review.placeId)}</div></td>
            <td>${escapeHtml(review.userId)}</td>
            <td>${escapeHtml(review.content)}</td>
            <td>${sentimentBadge(review.sentiment)}</td>
            <td>${review.clean ? '<span class="status-badge status-clean">통과</span>' : '<span class="status-badge status-blocked">마스킹됨</span>'}</td>
            <td>${escapeHtml(review.registeredDate || '-')}</td>
        </tr>
    `).join('');

    document.getElementById('reviewTableBody').innerHTML = rows || emptyRow(6, '등록된 한줄평이 없습니다.');
}

function sentimentBadge(sentiment) {
    return sentiment === 'POSITIVE'
        ? '<span class="status-badge status-clean">긍정</span>'
        : '<span class="status-badge status-blocked">부정</span>';
}

async function loadOwnerVerifications(status = '') {
    ownerStatusFilter = status;
    const query = status ? `?status=${encodeURIComponent(status)}` : '';

    try {
        if (!places.length) await loadPlaces();
        const verifications = await requestJson(`/api/admin/owner-verifications${query}`);
        renderOwnerVerificationTable(verifications);
    } catch (error) {
        renderEmpty('ownerVerificationTableBody', error.message, 6);
    }
}

function renderOwnerVerificationTable(verifications) {
    const rows = verifications.map(item => {
        const cafe = places.find(place => place.placeId === item.placeId);
        return `
            <tr>
                <td><strong>${escapeHtml(item.userId)}</strong><div class="muted">${escapeHtml(item.requestedAt || '')}</div></td>
                <td>
                    <strong>${escapeHtml(cafe?.name || item.placeId)}</strong>
                    <div class="muted">${escapeHtml(cafe?.address || '카페 정보를 확인할 수 없습니다.')}</div>
                </td>
                <td>${escapeHtml(item.businessNumber)}</td>
                <td>
                    <a class="download-link" href="${escapeAttribute(item.documentUrl)}" download target="_blank" rel="noopener">다운로드</a>
                    <div class="muted">서류의 상호와 신청 카페명을 대조하세요.</div>
                </td>
                <td>${verificationBadge(item.status)}</td>
                <td>${verificationActions(item)}</td>
            </tr>
        `;
    }).join('');

    document.getElementById('ownerVerificationTableBody').innerHTML = rows || emptyRow(6, '사장님 신청이 없습니다.');
}

function verificationBadge(status) {
    const label = status === 'APPROVED' ? '승인' : status === 'REJECTED' ? '반려' : '대기';
    const className = status === 'APPROVED' ? 'status-clean' : status === 'REJECTED' ? 'status-blocked' : 'status-pending';
    return `<span class="status-badge ${className}">${label}</span>`;
}

function verificationActions(item) {
    if (item.status !== 'PENDING') {
        return `<span class="muted">${escapeHtml(item.rejectReason || '처리 완료')}</span>`;
    }

    return `
        <div class="row-actions">
            <button class="approve-btn" onclick="reviewOwnerVerification('${escapeJs(item.verificationId)}', 'APPROVED')">승인</button>
            <button class="reject-btn" onclick="reviewOwnerVerification('${escapeJs(item.verificationId)}', 'REJECTED')">반려</button>
        </div>
    `;
}

async function reviewOwnerVerification(verificationId, status) {
    let rejectReason = null;
    if (status === 'REJECTED') {
        rejectReason = prompt('반려 사유를 입력해주세요.');
        if (!rejectReason) return;
    }

    try {
        await requestJson(`/api/admin/owner-verifications/${verificationId}`, {
            method: 'PATCH',
            body: JSON.stringify({ status, rejectReason })
        });
        await loadOwnerVerifications(ownerStatusFilter);
    } catch (error) {
        alert(error.message);
    }
}

function handleLogout() {
    clearAdminSession();
    location.href = routeUrl('/adminlogin');
}

function clearAdminSession() {
    localStorage.removeItem('user');
    localStorage.removeItem('authToken');
}

function renderEmpty(tbodyId, message, colSpan = 4) {
    const tbody = document.getElementById(tbodyId);
    if (tbody) tbody.innerHTML = emptyRow(colSpan, message);
}

function emptyRow(colSpan, message) {
    return `<tr><td colspan="${colSpan}" class="empty-cell">${escapeHtml(message)}</td></tr>`;
}

function escapeHtml(value) {
    return String(value ?? '')
        .replaceAll('&', '&amp;')
        .replaceAll('<', '&lt;')
        .replaceAll('>', '&gt;')
        .replaceAll('"', '&quot;')
        .replaceAll("'", '&#039;');
}

function escapeAttribute(value) {
    return escapeHtml(value).replaceAll('`', '&#096;');
}

function escapeJs(value) {
    return String(value ?? '').replaceAll('\\', '\\\\').replaceAll("'", "\\'");
}
