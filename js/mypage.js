document.addEventListener('DOMContentLoaded', () => {
    loadMyFavorites();
});

/* ================= 1. 즐겨찾기 관리 로직 ================= */

function loadMyFavorites() {
    const favList = document.getElementById('favList');
    
    // 메인 페이지와 동일한 키값으로 가져오기
    const savedData = localStorage.getItem('favorites'); 
    const favorites = savedData ? JSON.parse(savedData) : [];

    if (favorites.length === 0) {
        favList.innerHTML = `
            <div class="state-view" style="grid-column: 1/-1;">
                <div class="empty-anim">Empty</div>
                <p>아직 찜한 장소가 없습니다.<br>메인에서 마음에 드는 공부명당을 찾아보세요!</p>
            </div>`;
        return;
    }

    favList.innerHTML = favorites.map(item => `
        <div class="fav-card">
            <div class="fav-top">
                <span class="fav-name">${item.name || '장소명 없음'}</span>
                <button class="btn-del" onclick="deleteFav('${item.name}')">삭제</button>
            </div>
            <div class="fav-bottom">
                <span class="badge-mini">
                    ${item.score ? item.score + '점' : '점수 정보 없음'}
                </span>
                <a href="main.html" class="view-link" style="margin-left: 10px;">
                    지도에서 보기 >
                </a>
            </div>
        </div>
    `).join('');
}

function deleteFav(name) {
    if(!confirm("즐겨찾기에서 삭제하시겠습니까?")) return;

    let favorites = JSON.parse(localStorage.getItem('favorites')) || [];
    favorites = favorites.filter(f => f.name !== name);
    
    localStorage.setItem('favorites', JSON.stringify(favorites));
    loadMyFavorites(); 
}

/* ================= 2. 정보 수정(비밀번호 변경) 로직 ================= */

// 모달 열기
function openModal() {
    const modal = document.getElementById('editModal');
    if(modal) modal.style.display = 'flex';
}

// 모달 닫기
function closeModal() {
    const modal = document.getElementById('editModal');
    if(modal) modal.style.display = 'none';
    
    // 입력창 초기화
    document.getElementById('currentPw').value = "";
    document.getElementById('newPw').value = "";
    document.getElementById('confirmPw').value = "";
}

// 비밀번호 변경 실행
function updatePassword() {
    const currentPw = document.getElementById('currentPw').value;
    const newPw = document.getElementById('newPw').value;
    const confirmPw = document.getElementById('confirmPw').value;

    // 로컬스토리지에서 유저 정보 가져오기 (가입 시 저장한 키값 확인 필요)
    const userData = JSON.parse(localStorage.getItem('user'));

    if (!currentPw || !newPw || !confirmPw) {
        alert("모든 필드를 입력해주세요.");
        return;
    }

    // 현재 비밀번호 체크
    if (userData && userData.password !== currentPw) {
        alert("현재 비밀번호가 일치하지 않습니다.");
        return;
    }

    // 새 비밀번호 일치 체크
    if (newPw !== confirmPw) {
        alert("새 비밀번호 확인이 일치하지 않습니다.");
        return;
    }

    // 데이터 업데이트
    userData.password = newPw;
    localStorage.setItem('user', JSON.stringify(userData));
    
    alert("비밀번호가 안전하게 변경되었습니다.");
    closeModal();
}

/* ================= 3. 기타 유틸리티 ================= */

function handleLogout() {
    if (confirm("로그아웃 하시겠습니까?")) {
        location.href = 'main.html';
    }
}