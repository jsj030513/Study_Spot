function initMyPage() {
    const userData = JSON.parse(localStorage.getItem('user'));
    
    if (!userData) {
        alert("로그인이 필요한 페이지입니다.");
        location.href = 'login.html';
        return;
    }

    const nameEl = document.getElementById('userName');
    const deptEl = document.getElementById('userDept');

    if (nameEl) nameEl.innerText = `${userData.name}님`;
    // 학과 정보가 있다면 표시 (데이터 구조에 따라 조절)
    if (deptEl && userData.dept) deptEl.innerText = userData.dept;

    renderFavorites();
}

// 즐겨찾기 목록 렌더링
function renderFavorites() {
    const favListContainer = document.getElementById('favoriteList');
    if (!favListContainer) return;

    const favorites = JSON.parse(localStorage.getItem('myFavorites')) || [];

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
        card.innerHTML = `
            <div class="fav-card-top">
                <span class="fav-name" title="${item.name}">${item.name}</span>
                <button class="btn-del" onclick="removeFavorite(${item.id})">삭제</button>
            </div>
            <button class="btn-view-map" onclick="goToMap(${item.id})">지도에서 확인</button>
        `;
        favListContainer.appendChild(card);
    });
}

// 모달 열기
function openModal() {
    const modal = document.getElementById('editModal');
    const userData = JSON.parse(localStorage.getItem('user'));
    
    if (modal && userData) {
        document.getElementById('editName').value = userData.name;
        modal.style.display = 'flex';
    }
}

// 모달 닫기
function closeModal() {
    const modal = document.getElementById('editModal');
    if (modal) modal.style.display = 'none';
}

// 정보 저장
function saveUserInfo() {
    const newName = document.getElementById('editName').value;
    
    if (!newName.trim()) {
        alert("이름을 입력해주세요.");
        return;
    }

    let userData = JSON.parse(localStorage.getItem('user'));
    userData.name = newName;
    localStorage.setItem('user', JSON.stringify(userData));

    alert("성공적으로 수정되었습니다!");
    closeModal();
    initMyPage(); // 화면 정보 갱신
}

function goToMap(id) {
    location.href = `main.html?id=${id}`;
}

function removeFavorite(id) {
    if (confirm("즐겨찾기에서 삭제하시겠습니까?")) {
        let favorites = JSON.parse(localStorage.getItem('myFavorites')) || [];
        favorites = favorites.filter(f => f.id !== id);
        localStorage.setItem('myFavorites', JSON.stringify(favorites));
        renderFavorites();
    }
}

function handleLogout() {
    if (confirm("로그아웃 하시겠습니까?")) {
        localStorage.removeItem('user');
        location.href = 'main.html';
    }
}

// 바깥 영역 클릭 시 모달 닫기
window.onclick = function(event) {
    const modal = document.getElementById('editModal');
    if (event.target == modal) closeModal();
}

window.addEventListener('DOMContentLoaded', initMyPage);