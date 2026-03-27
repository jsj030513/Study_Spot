/**
 * 마이페이지 데이터 로드 로직
 */

document.addEventListener('DOMContentLoaded', () => {
    loadMyFavorites();
});

function loadMyFavorites() {
    const favList = document.getElementById('favList');
    
    // 1. 메인 페이지와 동일한 키값('favorites')으로 가져오기
    const savedData = localStorage.getItem('favorites'); 
    const favorites = savedData ? JSON.parse(savedData) : [];

    // 데이터가 없을 때 처리
    if (favorites.length === 0) {
        favList.innerHTML = `
            <div style="grid-column: 1/-1; text-align: center; padding: 60px 0; color: var(--text-sub);">
                <p style="font-size: 3rem; margin-bottom: 20px;">Empty</p>
                <p>아직 찜한 장소가 없습니다.<br>메인에서 마음에 드는 공부명당을 찾아보세요!</p>
            </div>`;
        return;
    }

    // 2. 데이터가 있다면 카드 생성
    favList.innerHTML = favorites.map(item => `
        <div class="fav-card">
            <div class="fav-top">
                <span class="fav-name">${item.name || '장소명 없음'}</span>
                <button class="btn-del" onclick="deleteFav('${item.name}')">삭제</button>
            </div>
            <div class="fav-bottom">
                <span style="font-size: 0.9rem; color: var(--primary); font-weight: bold;">
                    ${item.score ? item.score + '점' : '점수 정보 없음'}
                </span>
                <a href="main.html" style="margin-left: 10px; font-size: 0.8rem; color: var(--text-sub); text-decoration: none;">
                    지도에서 보기 >
                </a>
            </div>
        </div>
    `).join('');
}

// 삭제 기능
function deleteFav(name) {
    if(!confirm("즐겨찾기에서 삭제하시겠습니까?")) return;

    let favorites = JSON.parse(localStorage.getItem('favorites')) || [];
    favorites = favorites.filter(f => f.name !== name);
    
    localStorage.setItem('favorites', JSON.stringify(favorites));
    loadMyFavorites(); // 새로고침 없이 리스트 업데이트
}

function handleLogout() {
    if (confirm("로그아웃 하시겠습니까?")) {
        location.href = 'main.html';
    }
}