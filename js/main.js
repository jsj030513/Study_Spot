// wifi: 빵빵함 / 보통 / 없음, socket: 많음 / 보통 / 적음 / 없음
const spotData = [
    { id: 1, name: '안라커피', score: 85, type: 'cafe', typeName: '카페', lat: 36.8334791, lng: 127.1731506, wifi: '보통', socket: '적음', free: 30 },
    { id: 2, name: '카페고메', score: 90, type: 'cafe', typeName: '카페', lat: 36.8341021, lng: 127.1729208, wifi: '좋음', socket: '많음', free: 60 },
    { id: 3, name: '사람 그리고 이야기', score: 88, type: 'cafe', typeName: '카페', lat: 36.8348171, lng: 127.1728410, wifi: '좋음', socket: '보통', free: 55 },
    { id: 4, name: 'Eso', score: 80, type: 'cafe', typeName: '카페', lat: 36.8141891, lng: 127.1787319, wifi: '보통', socket: '적음', free: 40 },
    { id: 5, name: '예뫼골', score: 78, type: 'cafe', typeName: '카페', lat: 36.8314512, lng: 127.1862976, wifi: '보통', socket: '없음', free: 35 },
    { id: 6, name: '소소하며 달달한 카페', score: 82, type: 'cafe', typeName: '카페', lat: 36.8316793, lng: 127.1775288, wifi: '보통', socket: '적음', free: 45 },
    { id: 7, name: '루나틱스', score: 75, type: 'cafe', typeName: '카페', lat: 36.8376232, lng: 127.1745275, wifi: '보통', socket: '적음', free: 30 },
    { id: 8, name: '블랙컨테이너', score: 70, type: 'cafe', typeName: '카페', lat: 36.8411054, lng: 127.1807979, wifi: '보통', socket: '미확인', free: 25 },
    { id: 9, name: '마리스 커피', score: 87, type: 'cafe', typeName: '카페', lat: 36.833900, lng: 127.172500, wifi: '좋음', socket: '적음', free: 50 },
    { id: 10, name: '카페 미소레', score: 78, type: 'cafe', typeName: '카페', lat: 36.833500, lng: 127.173000, wifi: '보통', socket: '적음', free: 35 },
    { id: 11, name: '카페라이크', score: 80, type: 'cafe', typeName: '카페', lat: 36.834300, lng: 127.173400, wifi: '보통', socket: '적음', free: 40 },
    { id: 12, name: '슬로우커피', score: 83, type: 'cafe', typeName: '카페', lat: 36.833700, lng: 127.174200, wifi: '좋음', socket: '보통', free: 45 },
    { id: 13, name: '아비시니아 커피 로스터스', score: 88, type: 'cafe', typeName: '카페', lat: 36.835200, lng: 127.176800, wifi: '좋음', socket: '보통', free: 55 },
    { id: 14, name: '해화', score: 86, type: 'cafe', typeName: '카페', lat: 36.832800, lng: 127.175900, wifi: '보통', socket: '적음', free: 50 },
    { id: 15, name: '오월의 숲', score: 84, type: 'cafe', typeName: '카페', lat: 36.830900, lng: 127.182300, wifi: '보통', socket: '없음', free: 45 },
    { id: 16, name: '언덕위의 커피나무', score: 82, type: 'cafe', typeName: '카페', lat: 36.836000, lng: 127.179000, wifi: '보통', socket: '적음', free: 40 },
    { id: 17, name: 'cafe M', score: 85, type: 'cafe', typeName: '카페', lat: 36.834600, lng: 127.175200, wifi: '좋음', socket: '보통', free: 50 },
    { id: 18, name: '이레', score: 77, type: 'cafe', typeName: '카페', lat: 36.835000, lng: 127.173800, wifi: '보통', socket: '적음', free: 35 },
    { id: 19, name: '호다방', score: 77, type: 'cafe', typeName: '카페', lat: 36.8410495533003, lng: 127.1797073538, wifi: '보통', socket: '적음', free: 35 },
    { id: 20, name: '지로스터 안서점', score: 89, type: 'cafe', typeName: '카페', lat: 36.835761, lng: 127.173041, wifi: '좋음', socket: '많음', free: 55 },
    { id: 21, name: '스타벅스 천안안서점', score: 91, type: 'cafe', typeName: '카페', lat: 36.830185, lng: 127.179896, wifi: '우수', socket: '많음', free: 40 },
    { id: 22, name: '컴포즈커피 천안단대호수점', score: 75, type: 'cafe', typeName: '카페', lat: 36.836022, lng: 127.173371, wifi: '있음', socket: '적음', free: 30 },
    { id: 23, name: '이디야커피 백석대점', score: 80, type: 'cafe', typeName: '카페', lat: 36.84037, lng: 127.180583, wifi: '있음', socket: '많음', free: 45 },
    { id: 24, name: '파이커피', score: 89, type: 'cafe', typeName: '카페', lat: 36.8341, lng: 127.1729, wifi: '좋음', socket: '보통', free: 50 },
    { id: 25, name: '피플앤스토리', score: 86, type: 'cafe', typeName: '카페', lat: 36.8350, lng: 127.1729, wifi: '좋음', socket: '보통', free: 55 },
    { id: 26, name: '지로스터 안서점', score: 88, type: 'cafe', typeName: '카페', lat: 36.8357, lng: 127.1730, wifi: '보통', socket: '많음', free: 40 },
    { id: 27, name: '힐사이드', score: 85, type: 'cafe', typeName: '브런치카페', lat: 36.8360, lng: 127.1735, wifi: '보통', socket: '적음', free: 45 },
    { id: 28, name: '버터라운지', score: 83, type: 'cafe', typeName: '카페', lat: 36.8309, lng: 127.1824, wifi: '보통', socket: '적음', free: 35 },
    { id: 29, name: '구름정원', score: 84, type: 'cafe', typeName: '전통찻집/카페', lat: 36.8315, lng: 127.1865, wifi: '보통', socket: '적음', free: 60 },
    { id: 30, name: '카페ING (상명대 한누리관)', score: 75, type: 'cafe', typeName: '교내카페', lat: 36.8330, lng: 127.1781, wifi: '교내공용', socket: '보통', free: 70 },
    { id: 31, name: '파인트리카페 (상명대 기숙사)', score: 72, type: 'cafe', typeName: '교내카페', lat: 36.8335, lng: 127.1785, wifi: '교내공용', socket: '적음', free: 65 },
    { id: 32, name: '카페뷰리 (백석대 조형관)', score: 78, type: 'cafe', typeName: '교내카페', lat: 36.8395, lng: 127.1850, wifi: '교내공용', socket: '보통', free: 60 },
    { id: 33, name: '일루카페', score: 80, type: 'cafe', typeName: '카페', lat: 36.8415, lng: 127.1815, wifi: '보통', socket: '적음', free: 40 },
    { id: 34, name: '카페센트 (단대병원점)', score: 79, type: 'cafe', typeName: '카페', lat: 36.8405, lng: 127.1725, wifi: '있음', socket: '적음', free: 30 },
    { id: 35, name: '디카페', score: 76, type: 'cafe', typeName: '카페', lat: 36.8400, lng: 127.1720, wifi: '있음', socket: '적음', free: 35 },
    { id: 36, name: '카페 이륜당 (단대로 인근)', score: 82, type: 'cafe', typeName: '카페', lat: 36.8380, lng: 127.1710, wifi: '보통', socket: '적음', free: 40 },
    { id: 37, name: '카페 엠 (리각미술관 내)', score: 87, type: 'cafe', typeName: '갤러리카페', lat: 36.8205, lng: 127.1885, wifi: '좋음', socket: '적음', free: 50 },
    { id: 38, name: '서단', score: 88, type: 'cafe', typeName: '대형카페', lat: 36.8126, lng: 127.1812, wifi: '좋음', socket: '보통', free: 45 },

    { id: 39, name: '백석대학교 도서관', score: 99, type: 'library', typeName: '도서관', lat: 36.8390, lng: 127.1785, wifi: '암호', socket: '많음', free: 80 },
    { id: 40, name: '상명대학교 도서관', score: 87, type: 'library', typeName: '도서관', lat: 36.8330721, lng: 127.1781372, wifi: '암호', socket: '많음', free: 75 },
    { id: 41, name: '단국대학교 도서관', score: 80, type: 'library', typeName: '도서관', lat: 36.8359131, lng: 127.1665654, wifi: '암호', socket: '많음', free: 66 },

    { id: 42, name: 'CU 안서중앙점', score: 40, type: 'store', typeName: '편의점', lat: 36.8345, lng: 127.1740 },
    { id: 43, name: 'GS25 안서사거리점', type: 'store', typeName: '편의점', lat: 36.8352, lng: 127.1735 },
    { id: 44, name: '세븐일레븐 안서점', type: 'store', typeName: '편의점', lat: 36.8338, lng: 127.1750 },
    { id: 45, name: '이마트24 안서점', type: 'store', typeName: '편의점', lat: 36.8360, lng: 127.1760 },
    { id: 46, name: 'CU 백석대점', type: 'store', typeName: '편의점', lat: 36.8388, lng: 127.1780 },
    { id: 47, name: 'GS25 백석대점', type: 'store', typeName: '편의점', lat: 36.8392, lng: 127.1787 },
    { id: 48, name: '세븐일레븐 백석문화대점', type: 'store', typeName: '편의점', lat: 36.8400, lng: 127.1775 },
    { id: 49, name: '이마트24 백석대점', type: 'store', typeName: '편의점', lat: 36.8395, lng: 127.1782 },
    { id: 50, name: 'CU 상명대점', type: 'store', typeName: '편의점', lat: 36.8336, lng: 127.1783 },
    { id: 51, name: 'GS25 상명대점', type: 'store', typeName: '편의점', lat: 36.8333, lng: 127.1779 },
    { id: 52, name: '이마트24 상명대점', type: 'store', typeName: '편의점', lat: 36.8335, lng: 127.1782 },
    { id: 53, name: 'CU 단국대점', type: 'store', typeName: '편의점', lat: 36.8365, lng: 127.1675 },
    { id: 54, name: 'GS25 단국대정문점', type: 'store', typeName: '편의점', lat: 36.8360, lng: 127.1668 },
    { id: 55, name: '세븐일레븐 단국대점', type: 'store', typeName: '편의점', lat: 36.8355, lng: 127.1670 },
    { id: 56, name: '이마트24 단국대점', type: 'store', typeName: '편의점', lat: 36.8362, lng: 127.1672 },
    { id: 57, name: 'CU 호서대점', type: 'store', typeName: '편의점', lat: 36.8420, lng: 127.1800 },
    { id: 58, name: 'GS25 호서대점', type: 'store', typeName: '편의점', lat: 36.8415, lng: 127.1795 },
    { id: 59, name: '세븐일레븐 안서북부점', type: 'store', typeName: '편의점', lat: 36.8415, lng: 127.1805 },
    { id: 60, name: 'CU 안서북부점', type: 'store', typeName: '편의점', lat: 36.8408, lng: 127.1798 },
    { id: 61, name: 'GS25 안서남부점', type: 'store', typeName: '편의점', lat: 36.8325, lng: 127.1745 },
    { id: 62, name: 'CU 안서남부점', type: 'store', typeName: '편의점', lat: 36.8328, lng: 127.1750 },
    { id: 63, name: '이마트24 안서중앙점', type: 'store', typeName: '편의점', lat: 36.8348, lng: 127.1742 },

    { id: 64, name: '정문복사 (백석대 정문)', score: 88, type: 'print', typeName: '복사/인쇄', lat: 36.8411786, lng: 127.1813488, desc: '대량 인쇄 및 제본 전문' },
    { id: 65, name: '파랑 인쇄소 (백석대 인근)', score: 82, type: 'print', typeName: '복사/인쇄', lat: 36.8396891, lng: 127.1770605, desc: '과제물 출력 및 복사' },
    { id: 66, name: '상명대 복사실 (한누리관)', score: 85, type: 'print', typeName: '복사/인쇄', lat: 36.8330, lng: 127.1781, desc: '교내 전용 복사실' },
    { id: 67, name: '단국대 복사실 (혜당관)', score: 84, type: 'print', typeName: '복사/인쇄', lat: 36.8359, lng: 127.1665, desc: '학생회관 내 위치' },
    { id: 68, name: '프린트카페 천안안서점', score: 90, type: 'print', typeName: '24시무인복사', lat: 36.8345, lng: 127.1742, desc: '24시간 무인 인쇄 가능' },

    { id: 69, name: '다이소 천안안서점', score: 92, type: 'stationery', typeName: '문구/잡화', lat: 36.8306313, lng: 127.1770219, desc: '안서동에서 가장 큰 문구 잡화점' },
    { id: 70, name: '알파와오메가문구 (백석문화대)', score: 85, type: 'stationery', typeName: '문구점', lat: 36.8385795, lng: 127.183288, desc: '전문 제도용품 및 문구' },
    { id: 71, name: '상명대 구내매점/문구', score: 80, type: 'stationery', typeName: '교내문구', lat: 36.8328, lng: 127.1775, desc: '기본 필기구 및 소모품' },
    { id: 72, name: '단국대 구내매점/문구', score: 82, type: 'ststationeryore', typeName: '교내문구', lat: 36.8362, lng: 127.1670, desc: '교내 문구 및 사무용품' }
];

let map;
let overlays = [];
let selectedSpotForNav = null; // 현재 선택된 장소 정보 저장용
let isLoggedIn = true;       // 로그인 상태 (테스트 시 true로 변경)
let userName = "장성주";

// 2. 초기화 함수
function init() {
    initMap();
    renderSpots('all');
    setupEvents();
    updateHeader();

}

// 3. 카카오 지도 생성 (중심점: 안서동 카페거리/백석대 중간)
function initMap() {
    const container = document.getElementById('kakaoMap');
    const options = {
        center: new kakao.maps.LatLng(36.8360, 127.1750), 
        level: 4 
    };
    map = new kakao.maps.Map(container, options);
}

// 4. 리스트 및 마커 렌더링
function renderSpots(filterType, keyword = '') {
    const spotList = document.getElementById('spotList');
    spotList.innerHTML = '';

    if (overlays) overlays.forEach(o => o.setMap(null));
    overlays = [];

    spotData.forEach(spot => {
        const isTypeMatch = filterType === 'all' || spot.type === filterType;
        const isNameMatch = spot.name.includes(keyword);

        if (isTypeMatch && isNameMatch) {
            // [A] 리스트 카드 생성
            const card = document.createElement('div');
            card.className = 'spot-card';
            card.innerHTML = `
                <div class="card-top">
                    <span class="card-name">${spot.name}</span>
                    <span class="score-badge">${spot.score || 0}점</span>
                </div>
                <div class="card-info-row" style="display:flex; gap:8px; margin-bottom:8px;">
                    <span class="info-tag" style="font-size:0.75rem; background:#f1f3f5; padding:2px 8px; border-radius:6px; color:#636e72;">📶 와이파이: ${spot.wifi || '미확인'}</span>
                    <span class="info-tag" style="font-size:0.75rem; background:#f1f3f5; padding:2px 8px; border-radius:6px; color:#636e72;">🔌 콘센트: ${spot.socket || '미확인'}</span>
                </div>
                <div class="card-type" style="font-size:0.8rem; color:#2b5a9e; font-weight:700;">${spot.typeName}</div>
            `;
            card.onclick = () => selectSpot(spot);
            spotList.appendChild(card);

            // [B] 점수별 마커 생성
            const score = spot.score || 0;
            let scoreClass = 'high';
            if (score < 80) scoreClass = 'low';
            else if (score < 90) scoreClass = 'mid';

            const content = `
                <div class="map-marker-wrapper" onclick="selectSpotById(${spot.id})">
                    <div class="score-pin ${scoreClass}"><span>${score}</span></div>
                    <div class="marker-title">${spot.name}</div>
                </div>`;

            const overlay = new kakao.maps.CustomOverlay({
                position: new kakao.maps.LatLng(spot.lat, spot.lng),
                content: content,
                yAnchor: 1.2
            });
            overlay.setMap(map);
            overlays.push(overlay);
        }
    });
}

// 5. 장소 선택 및 상세창 제어
function selectSpot(spot) {
    map.panTo(new kakao.maps.LatLng(spot.lat, spot.lng));
    openDetail(spot);
}

window.selectSpotById = function (id) {
    const spot = spotData.find(s => s.id === id);
    if (spot) selectSpot(spot);
};

function openDetail(spot) {
    selectedSpotForNav = spot; // 길찾기/즐겨찾기용 데이터 저장
    document.getElementById('detailName').innerText = spot.name;
    document.getElementById('detailScore').innerText = spot.score || 0;

    // 상세 지표 게이지바 업데이트
    const scoreBars = document.getElementById('scoreBars');
    const wifiPower = (spot.wifi === '좋음' || spot.wifi === '우수' || spot.wifi === '빵빵함') ? 100 : 50;
    
    scoreBars.innerHTML = `
        <div class="bar-row" style="display:flex; align-items:center; gap:10px; margin-bottom:12px;">
            <span style="font-size:0.8rem; color:#636e72; width:60px;">와이파이</span>
            <div style="flex:1; height:8px; background:#edf2f7; border-radius:4px; overflow:hidden;">
                <div style="width:${wifiPower}%; height:100%; background:#2b5a9e; transition:0.5s;"></div>
            </div>
        </div>
        <div class="bar-row" style="display:flex; align-items:center; gap:10px;">
            <span style="font-size:0.8rem; color:#636e72; width:60px;">쾌적도</span>
            <div style="flex:1; height:8px; background:#edf2f7; border-radius:4px; overflow:hidden;">
                <div style="width:${spot.free || 50}%; height:100%; background:#51cf66; transition:0.5s;"></div>
            </div>
        </div>
    `;
    document.getElementById('detailPanel').classList.add('open');
}

function closeDetail() {
    document.getElementById('detailPanel').classList.remove('open');
}

// 6. 외부 연동 기능 (길찾기 & 즐겨찾기)
function startNavigation() {
    if (!selectedSpotForNav) return;
    const navUrl = `https://map.kakao.com/link/to/${selectedSpotForNav.name},${selectedSpotForNav.lat},${selectedSpotForNav.lng}`;
    window.open(navUrl, '_blank');
}

function toggleFavorite() {
    if (!isLoggedIn) {
        const goToLogin = confirm("로그인 후 이용 가능한 서비스입니다.\n로그인 페이지로 이동하시겠습니까?");
        if (goToLogin) location.href = 'login.html';
        return;
    }

    let favorites = JSON.parse(localStorage.getItem('myFavorites')) || [];
    const isAlreadyFav = favorites.some(f => f.id === selectedSpotForNav.id);

    if (isAlreadyFav) {
        favorites = favorites.filter(f => f.id !== selectedSpotForNav.id);
        alert(`⭐ ${selectedSpotForNav.name} 즐겨찾기가 해제되었습니다.`);
    } else {
        favorites.push({ id: selectedSpotForNav.id, name: selectedSpotForNav.name });
        alert(`⭐ ${selectedSpotForNav.name} 즐겨찾기에 추가되었습니다!`);
    }
    localStorage.setItem('myFavorites', JSON.stringify(favorites));
}

// 7. 이벤트 설정
function setupEvents() {
    const chips = document.querySelectorAll('.category-chip');
    chips.forEach(chip => {
        chip.onclick = function () {
            chips.forEach(c => c.classList.remove('active'));
            this.classList.add('active');
            renderSpots(this.dataset.type, document.getElementById('searchInput').value);
        };
    });

    document.getElementById('searchInput').oninput = function () {
        const activeType = document.querySelector('.category-chip.active').dataset.type;
        renderSpots(activeType, this.value);
    };
}

function updateHeader() {
    const navLinks = document.querySelector('.nav-links');
    
    if (isLoggedIn) {
        // 로그인 상태일 때: 마이페이지, 로그아웃 표시
        navLinks.innerHTML = `
            <li><a href="main.html" style="color:var(--accent);">탐색하기</a></li>
            <li><a href="#">장소추가</a></li>
            <li><a href="mypage.html">마이페이지</a></li>
            <li><a href="#" onclick="handleLogout()">로그아웃</a></li>
            <li class="user-welcome" style="margin-left:10px; font-size:0.85rem; color:#636e72;">
                <b>${userName}</b>님 환영합니다!
            </li>
        `;
    } else {
        // 비로그인 상태일 때 (기존 유지)
        navLinks.innerHTML = `
            <li><a href="main.html" style="color:var(--accent);">탐색하기</a></li>
            <li><a href="#">장소추가</a></li>
            <li><a href="login.html">로그인</a></li>
            <li><a href="join.html">회원가입</a></li>
        `;
    }
}

// 4. 로그아웃 처리 함수
function handleLogout() {
    if (confirm("로그아웃 하시겠습니까?")) {
        isLoggedIn = false;
        alert("로그아웃 되었습니다.");
        updateHeader(); // 헤더 다시 그리기
        location.reload(); // 페이지 새로고침 (선택 사항)
    }
}

window.onload = init;