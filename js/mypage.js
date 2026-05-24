const BACKEND_URL = `${location.protocol}//${location.hostname}:8080`;
const API_BASE_URL = location.port === '5500' ? BACKEND_URL : '';

function routeUrl(path) {
    return location.port === '5500' ? `${BACKEND_URL}${path}` : path;
}

async function readErrorMessage(response) {
    try {
        const data = await response.json();
        return data.errors?.[0]?.message || data.message || data.error || '요청을 처리하지 못했습니다.';
    } catch (error) {
        return '요청을 처리하지 못했습니다.';
    }
}

function initMyPage() {
    const userData = getStoredUser();
    
    if (!userData) {
        alert("로그인이 필요한 페이지입니다.");
        location.href = routeUrl('/login');
        return;
    }

    const nameEl = document.getElementById('userName');

    if (nameEl) nameEl.innerText = `${userData.name}님`;

    renderFavorites();
}

function getStoredUser() {
    try {
        return JSON.parse(localStorage.getItem('user'));
    } catch (error) {
        localStorage.removeItem('user');
        return null;
    }
}

function getStoredFavorites() {
    try {
        return JSON.parse(localStorage.getItem('myFavorites')) || [];
    } catch (error) {
        localStorage.removeItem('myFavorites');
        return [];
    }
}

// 즐겨찾기 목록 렌더링
function renderFavorites() {
    const favListContainer = document.getElementById('favoriteList');
    if (!favListContainer) return;

    const favorites = getStoredFavorites();

    if (favorites.length === 0) {
        favListContainer.innerHTML = `
            <div style="grid-column:1/-1; text-align:center; padding:80px 0; color:#94a3b8;">
                <span style="font-size:4rem; display:block; margin-bottom:20px;">📭</span>
                <h3>찜한 목록이 비어있습니다.</h3>
            </div>
        `;
        return;
    }

    favListContainer.innerHTML = '';
    favorites.forEach(item => {
        const card = document.createElement('div');
        card.className = 'fav-card';

        const top = document.createElement('div');
        top.className = 'fav-card-top';

        const name = document.createElement('span');
        name.className = 'fav-name';
        name.title = item.name;
        name.textContent = item.name;

        const deleteButton = document.createElement('button');
        deleteButton.className = 'btn-del';
        deleteButton.type = 'button';
        deleteButton.textContent = '삭제';
        deleteButton.onclick = () => removeFavorite(item.id);

        const viewButton = document.createElement('button');
        viewButton.className = 'btn-view-map';
        viewButton.type = 'button';
        viewButton.textContent = '지도에서 확인';
        viewButton.onclick = () => goToMap(item.id);

        top.append(name, deleteButton);
        card.append(top, viewButton);
        favListContainer.appendChild(card);
    });
}

// 모달 열기
function openModal() {
    const modal = document.getElementById('editModal');
    const userData = getStoredUser();
    
    if (modal && userData) {
        document.getElementById('editName').value = userData.name;
        document.getElementById('editPassword').value = '';
        document.getElementById('editPasswordConfirm').value = '';
        modal.style.display = 'flex';
    }
}

// 모달 닫기
function closeModal() {
    const modal = document.getElementById('editModal');
    if (modal) modal.style.display = 'none';
}

// 정보 저장
async function saveUserInfo() {
    const newName = document.getElementById('editName').value;
    const newPassword = document.getElementById('editPassword').value;
    const passwordConfirm = document.getElementById('editPasswordConfirm').value;
    
    if (!newName.trim()) {
        alert("이름을 입력해주세요.");
        return;
    }

    if (newPassword || passwordConfirm) {
        if (newPassword.length < 8) {
            alert("비밀번호는 8자 이상 입력해주세요.");
            return;
        }
        if (newPassword !== passwordConfirm) {
            alert("새 비밀번호가 일치하지 않습니다.");
            return;
        }
    }

    const token = localStorage.getItem('authToken');
    if (!token) {
        alert("로그인이 필요한 페이지입니다.");
        location.href = routeUrl('/login');
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/api/users/me`, {
            method: 'PATCH',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: newName.trim(),
                password: newPassword ? newPassword : null
            })
        });

        if (!response.ok) {
            alert(await readErrorMessage(response));
            return;
        }

        const updatedUser = await response.json();
        localStorage.setItem('user', JSON.stringify(updatedUser));

        alert("성공적으로 수정되었습니다!");
        closeModal();
        initMyPage(); // 화면 정보 갱신
    } catch (error) {
        alert('서버에 연결할 수 없습니다. 백엔드가 실행 중인지 확인해주세요.');
    }
}

function goToMap(id) {
    location.href = `${routeUrl('/main')}?id=${encodeURIComponent(id)}`;
}

function removeFavorite(id) {
    if (confirm("즐겨찾기에서 삭제하시겠습니까?")) {
        let favorites = getStoredFavorites();
        favorites = favorites.filter(f => String(f.id) !== String(id));
        localStorage.setItem('myFavorites', JSON.stringify(favorites));
        renderFavorites();
    }
}

function handleLogout() {
    if (confirm("로그아웃 하시겠습니까?")) {
        localStorage.removeItem('user');
        localStorage.removeItem('authToken');
        location.href = routeUrl('/main');
    }
}

// 바깥 영역 클릭 시 모달 닫기
window.onclick = function(event) {
    const modal = document.getElementById('editModal');
    if (event.target == modal) closeModal();
}

window.addEventListener('DOMContentLoaded', initMyPage);
