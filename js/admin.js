// 관리자 정보 (실제 구현 시 API 연동 가능)
const sessionInfo = { adminName: "장성주" };

// 초기 데이터 리스트 (안서동 기반)

let currentFilter = "전체";
let spotList = [
    { id: 1, name: '안라커피', score: 85, type: 'cafe', category: '카페', lat: 36.833450, lng: 127.173136, wifi: '보통', socket: '적음', free: 30 },
    { id: 2, name: '카페고메', score: 90, type: 'cafe', category: '카페', lat: 36.834127, lng: 127.172958, wifi: '좋음', socket: '많음', free: 60 },
    { id: 3, name: '사람 그리고 이야기', score: 88, type: 'cafe', category: '카페', lat: 36.834812, lng: 127.172922, wifi: '좋음', socket: '보통', free: 55 },
    { id: 4, name: 'Eso', score: 80, type: 'cafe', category: '카페', lat: 36.8141891, lng: 127.1787319, wifi: '보통', socket: '적음', free: 40 },
    { id: 5, name: '예뫼골', score: 78, type: 'cafe', category: '카페', lat: 36.8314512, lng: 127.186215, wifi: '보통', socket: '없음', free: 35 },
    { id: 6, name: '소소하며 달달한 카페', score: 82, type: 'cafe', category: '카페', lat: 36.8316793, lng: 127.1775288, wifi: '보통', socket: '적음', free: 45 },
    { id: 7, name: '루나틱스', score: 75, type: 'cafe', category: '카페', lat: 36.8376232, lng: 127.1745275, wifi: '보통', socket: '적음', free: 30 },
    { id: 8, name: '블랙컨테이너', score: 70, type: 'cafe', category: '카페', lat: 36.8411054, lng: 127.1807979, wifi: '보통', socket: '미확인', free: 25 },
    { id: 9, name: '마리스 커피', score: 87, type: 'cafe', category: '카페', lat: 36.833900, lng: 127.172500, wifi: '좋음', socket: '적음', free: 50 },
    { id: 10, name: '카페 미소레', score: 78, type: 'cafe', category: '카페', lat: 36.833500, lng: 127.173000, wifi: '보통', socket: '적음', free: 35 },
    { id: 11, name: '카페라이크', score: 80, type: 'cafe', category: '카페', lat: 36.834300, lng: 127.173400, wifi: '보통', socket: '적음', free: 40 },
    { id: 12, name: '슬로우커피', score: 83, type: 'cafe', category: '카페', lat: 36.833700, lng: 127.174200, wifi: '좋음', socket: '보통', free: 45 },
    { id: 13, name: '아비시니아 커피 로스터스', score: 88, type: 'cafe', category: '카페', lat: 36.835200, lng: 127.176800, wifi: '좋음', socket: '보통', free: 55 },
    { id: 14, name: '해화', score: 86, type: 'cafe', category: '카페', lat: 36.832800, lng: 127.175900, wifi: '보통', socket: '적음', free: 50 },
    { id: 15, name: '오월의 숲', score: 84, type: 'cafe', category: '카페', lat: 36.830900, lng: 127.182300, wifi: '보통', socket: '없음', free: 45 },
    { id: 16, name: '언덕위의 커피나무', score: 82, type: 'cafe', category: '카페', lat: 36.836000, lng: 127.179000, wifi: '보통', socket: '적음', free: 40 },
    { id: 17, name: 'cafe M', score: 85, type: 'cafe', category: '카페', lat: 36.834600, lng: 127.175200, wifi: '좋음', socket: '보통', free: 50 },
    { id: 18, name: '이레', score: 77, type: 'cafe', category: '카페', lat: 36.835000, lng: 127.173800, wifi: '보통', socket: '적음', free: 35 },
    { id: 19, name: '호다방', score: 77, type: 'cafe', category: '카페', lat: 36.8410495533003, lng: 127.1797073538, wifi: '보통', socket: '적음', free: 35 },
    { id: 20, name: '지로스터 안서점', score: 89, type: 'cafe', category: '카페', lat: 36.835761, lng: 127.173041, wifi: '좋음', socket: '많음', free: 55 },
    { id: 21, name: '스타벅스 천안안서점', score: 91, type: 'cafe', category: '카페', lat: 36.830185, lng: 127.179896, wifi: '우수', socket: '많음', free: 40 },
    { id: 22, name: '컴포즈커피 천안단대호수점', score: 75, type: 'cafe', category: '카페', lat: 36.836022, lng: 127.173371, wifi: '있음', socket: '적음', free: 30 },
    { id: 23, name: '이디야커피 백석대점', score: 80, type: 'cafe', category: '카페', lat: 36.84037, lng: 127.180583, wifi: '있음', socket: '많음', free: 45 },
    { id: 24, name: '파이커피', score: 89, type: 'cafe', category: '카페', lat: 36.8341, lng: 127.1729, wifi: '좋음', socket: '보통', free: 50 },
    { id: 25, name: '피플앤스토리', score: 86, type: 'cafe', category: '카페', lat: 36.8350, lng: 127.1729, wifi: '좋음', socket: '보통', free: 55 },
    { id: 26, name: '지로스터 안서점', score: 88, type: 'cafe', category: '카페', lat: 36.8357, lng: 127.1730, wifi: '보통', socket: '많음', free: 40 },
    { id: 27, name: '힐사이드', score: 85, type: 'cafe', category: '브런치카페', lat: 36.8360, lng: 127.1735, wifi: '보통', socket: '적음', free: 45 },
    { id: 28, name: '버터라운지', score: 83, type: 'cafe', category: '카페', lat: 36.8309, lng: 127.1824, wifi: '보통', socket: '적음', free: 35 },
    { id: 29, name: '구름정원', score: 84, type: 'cafe', category: '전통찻집/카페', lat: 36.8315, lng: 127.1865, wifi: '보통', socket: '적음', free: 60 },
    { id: 30, name: '카페ING (상명대 한누리관)', score: 75, type: 'cafe', category: '교내카페', lat: 36.8330, lng: 127.1781, wifi: '교내공용', socket: '보통', free: 70 },
    { id: 31, name: '파인트리카페 (상명대 기숙사)', score: 72, type: 'cafe', category: '교내카페', lat: 36.8335, lng: 127.1785, wifi: '교내공용', socket: '적음', free: 65 },
    { id: 32, name: '카페뷰리 (백석대 조형관)', score: 78, type: 'cafe', category: '교내카페', lat: 36.8395, lng: 127.1850, wifi: '교내공용', socket: '보통', free: 60 },
    { id: 33, name: '일루카페', score: 80, type: 'cafe', category: '카페', lat: 36.8415, lng: 127.1815, wifi: '보통', socket: '적음', free: 40 },
    { id: 34, name: '카페센트 (단대병원점)', score: 79, type: 'cafe', category: '카페', lat: 36.8405, lng: 127.1725, wifi: '있음', socket: '적음', free: 30 },
    { id: 35, name: '디카페', score: 76, type: 'cafe', category: '카페', lat: 36.8400, lng: 127.1720, wifi: '있음', socket: '적음', free: 35 },
    { id: 36, name: '카페 이륜당 (단대로 인근)', score: 82, type: 'cafe', category: '카페', lat: 36.8380, lng: 127.1710, wifi: '보통', socket: '적음', free: 40 },
    { id: 37, name: '카페 엠 (리각미술관 내)', score: 87, type: 'cafe', category: '갤러리카페', lat: 36.8205, lng: 127.1885, wifi: '좋음', socket: '적음', free: 50 },
    { id: 38, name: '서단', score: 88, type: 'cafe', category: '대형카페', lat: 36.8126, lng: 127.1812, wifi: '좋음', socket: '보통', free: 45 },

    { id: 39, name: '백석대학교 도서관', score: 99, type: 'library', category: '도서관', lat: 36.839350, lng: 127.185960, wifi: '암호', socket: '많음', free: 80 },
    { id: 40, name: '상명대학교 도서관', score: 87, type: 'library', category: '도서관', lat: 36.6021911, lng: 126.955345, wifi: '암호', socket: '많음', free: 75 },
    { id: 41, name: '단국대학교 도서관', score: 80, type: 'library', category: '도서관', lat: 36.835777, lng: 127.166544, wifi: '암호', socket: '많음', free: 66 },
    { id: 73, name: '백석대학교 백석학술정보관', score: 98, type: 'library', category: '도서관', lat: 36.837746, lng: 127.184050, wifi: '암호', socket: '많음', free: 90  },
    { id: 74, name: '백석문화대학교 도서관', score: 98, type: 'library', category: '도서관', lat: 36.838530, lng: 127.182691, wifi: '암호', socket: '많음', free: 88  },

    { id: 42, name: 'CU 안서중앙점', score: 40, type: 'store', category: '편의점', lat: 36.8345, lng: 127.1740 },
    { id: 43, name: 'GS25 안서사거리점', type: 'store', category: '편의점', lat: 36.8352, lng: 127.1735 },
    { id: 44, name: '세븐일레븐 안서점', type: 'store', category: '편의점', lat: 36.8338, lng: 127.1750 },
    { id: 45, name: '이마트24 안서점', type: 'store', category: '편의점', lat: 36.8360, lng: 127.1760 },
    { id: 46, name: 'CU 백석대점', type: 'store', category: '편의점', lat: 36.8388, lng: 127.1780 },
    { id: 47, name: 'GS25 백석대점', type: 'store', category: '편의점', lat: 36.8392, lng: 127.1787 },
    { id: 48, name: '세븐일레븐 백석문화대점', type: 'store', category: '편의점', lat: 36.8400, lng: 127.1775 },
    { id: 49, name: '이마트24 백석대점', type: 'store', category: '편의점', lat: 36.8395, lng: 127.1782 },
    { id: 50, name: 'CU 상명대점', type: 'store', category: '편의점', lat: 36.8336, lng: 127.1783 },
    { id: 51, name: 'GS25 상명대점', type: 'store', category: '편의점', lat: 36.8333, lng: 127.1779 },
    { id: 52, name: '이마트24 상명대점', type: 'store', category: '편의점', lat: 36.8335, lng: 127.1782 },
    { id: 53, name: 'CU 단국대점', type: 'store', category: '편의점', lat: 36.8365, lng: 127.1675 },
    { id: 54, name: 'GS25 단국대정문점', type: 'store', category: '편의점', lat: 36.8360, lng: 127.1668 },
    { id: 55, name: '세븐일레븐 단국대점', type: 'store', category: '편의점', lat: 36.8355, lng: 127.1670 },
    { id: 56, name: '이마트24 단국대점', type: 'store', category: '편의점', lat: 36.8362, lng: 127.1672 },
    { id: 57, name: 'CU 호서대점', type: 'store', category: '편의점', lat: 36.8420, lng: 127.1800 },
    { id: 58, name: 'GS25 호서대점', type: 'store', category: '편의점', lat: 36.8415, lng: 127.1795 },
    { id: 59, name: '세븐일레븐 안서북부점', type: 'store', category: '편의점', lat: 36.8415, lng: 127.1805 },
    { id: 60, name: 'CU 안서북부점', type: 'store', category: '편의점', lat: 36.8408, lng: 127.1798 },
    { id: 61, name: 'GS25 안서남부점', type: 'store', category: '편의점', lat: 36.8325, lng: 127.1745 },
    { id: 62, name: 'CU 안서남부점', type: 'store', category: '편의점', lat: 36.8328, lng: 127.1750 },
    { id: 63, name: '이마트24 안서중앙점', type: 'store', category: '편의점', lat: 36.8348, lng: 127.1742 },

    { id: 64, name: '정문복사 (백석대 정문)', score: 88, type: 'print', category: '복사/인쇄', lat: 36.8411786, lng: 127.1813488, desc: '대량 인쇄 및 제본 전문' },
    { id: 65, name: '파랑 인쇄소 (백석대 인근)', score: 82, type: 'print', category: '복사/인쇄', lat: 36.8396891, lng: 127.1770605, desc: '과제물 출력 및 복사' },
    { id: 66, name: '상명대 복사실 (한누리관)', score: 85, type: 'print', category: '복사/인쇄', lat: 36.8330, lng: 127.1781, desc: '교내 전용 복사실' },
    { id: 67, name: '단국대 복사실 (혜당관)', score: 84, type: 'print', category: '복사/인쇄', lat: 36.8359, lng: 127.1665, desc: '학생회관 내 위치' },
    { id: 68, name: '프린트카페 천안안서점', score: 90, type: 'print', category: '복사/인쇄', lat: 36.8345, lng: 127.1742, desc: '24시간 무인 인쇄 가능' },

    { id: 69, name: '다이소 천안안서점', score: 92, type: 'stationery', category: '문구/잡화', lat: 36.8306313, lng: 127.1770219, desc: '안서동에서 가장 큰 문구 잡화점' },
    { id: 70, name: '알파와오메가문구 (백석문화대)', score: 85, type: 'stationery', category: '문구점', lat: 36.8385795, lng: 127.183288, desc: '전문 제도용품 및 문구' },
    { id: 71, name: '상명대 구내매점/문구', score: 80, type: 'stationery', category: '교내문구', lat: 36.8328, lng: 127.1775, desc: '기본 필기구 및 소모품' },
    { id: 72, name: '단국대 구내매점/문구', score: 82, type: 'ststationeryore', category: '교내문구', lat: 36.8362, lng: 127.1670, desc: '교내 문구 및 사무용품' }
];


let userList = [
    { id: "user01", name: "김철수", email: "chulsoo@bu.ac.kr", date: "2026-03-01", role: "일반" },
    { id: "admin_test", name: "박관리", email: "admin@bu.ac.kr", date: "2026-01-10", role: "관리자" }
];

// 초기화 이벤트
document.addEventListener("DOMContentLoaded", () => {
    const adminNameEl = document.getElementById("adminName");
    if(adminNameEl) adminNameEl.innerText = sessionInfo.adminName;
    updateDashboard();
    renderSpotTable(); 
    renderUserTable();
});

// 모드 전환 (장소 관리 vs 회원 관리)
function switchMode(mode) {
    const spotSec = document.getElementById('spotSection');
    const userSec = document.getElementById('userSection');
    const btnSpot = document.getElementById('btnSpotMode');
    const btnUser = document.getElementById('btnUserMode');

    if (mode === 'spot') {
        spotSec.style.display = 'block';
        userSec.style.display = 'none';
        btnSpot.classList.add('active');
        btnUser.classList.remove('active');
        updateDashboard(); 
    } else {
        spotSec.style.display = 'none';
        userSec.style.display = 'block';
        btnSpot.classList.remove('active');
        btnUser.classList.add('active');
    }
}

// 대시보드 카운트 업데이트
function updateDashboard() {
    const counts = { "전체": spotList.length, "카페": 0, "도서관": 0, "편의점": 0, "문구점": 0, "프린트": 0 };
    
    spotList.forEach(item => {
        let cat = item.category;
        if(cat === "복사/인쇄") cat = "프린트";
        if(cat === "문구/잡화" || cat === "교내문구") cat = "문구점";

        if (counts[cat] !== undefined) {
            counts[cat]++;
        }
    });

    if(document.getElementById("countAll")) document.getElementById("countAll").innerText = counts["전체"];
    if(document.getElementById("countCafe")) document.getElementById("countCafe").innerText = counts["카페"];
    if(document.getElementById("countLibrary")) document.getElementById("countLibrary").innerText = counts["도서관"];
    if(document.getElementById("countStore")) document.getElementById("countStore").innerText = counts["편의점"];
    if(document.getElementById("countStationery")) document.getElementById("countStationery").innerText = counts["문구점"];
    if(document.getElementById("countPrint")) document.getElementById("countPrint").innerText = counts["프린트"];
}

// 카테고리 필터링
function filterCategory(category) {
    currentFilter = category;
    const titleEl = document.getElementById("currentCategoryTitle");
    if(titleEl) titleEl.innerText = category;
    
    const cards = document.querySelectorAll('.category-card');
    cards.forEach(card => {
        const title = card.querySelector('h4').innerText;
        if(title === category) card.classList.add('active');
        else card.classList.remove('active');
    });

    renderSpotTable();
}

// 장소 테이블 렌더링
function renderSpotTable() {
    const tableBody = document.getElementById("spotTableBody");
    if(!tableBody) return;

    const filteredData = currentFilter === "전체" 
        ? spotList 
        : spotList.filter(item => {
            let cat = item.category;
            if(cat === "복사/인쇄") cat = "프린트";
            if(cat === "문구/잡화" || cat === "교내문구") cat = "문구점";
            return cat === currentFilter;
        });

    tableBody.innerHTML = filteredData.map(item => `
        <tr>
            <td><strong>${item.name}</strong></td>
            <td>${item.category}</td>
            <td>${item.score}점</td>
            <td>
                <button onclick="deleteSpot(${item.id})" style="color:#ff4d4d; border:none; background:none; cursor:pointer; font-weight:600;">삭제</button>
            </td>
        </tr>
    `).join("");
}

// 회원 테이블 렌더링
function renderUserTable() {
    const tbody = document.getElementById("userTableBody");
    if(!tbody) return;
    tbody.innerHTML = userList.map(user => `
        <tr>
            <td>${user.id}</td>
            <td><strong>${user.name}</strong></td>
            <td>${user.email}</td>
            <td>${user.date}</td>
            <td>${user.role}</td>
            <td>
                <button onclick="deleteUser('${user.id}')" style="color:#ff4d4d; border:none; background:none; cursor:pointer; font-weight:600;">삭제</button>
            </td>
        </tr>
    `).join("");
}

// 장소 삭제 함수
function deleteSpot(id) {
    const target = spotList.find(item => item.id === id);
    if (!target) return;

    if (confirm(`[${target.name}] 장소를 목록에서 영구히 삭제하시겠습니까?`)) {
        spotList = spotList.filter(item => item.id !== id);
        updateDashboard();
        renderSpotTable();
        alert("삭제되었습니다.");
    }
}

// 회원 삭제 함수
function deleteUser(userId) {
    const target = userList.find(user => user.id === userId);
    if (!target) return;

    if (confirm(`회원 [${target.name}]님을 정말로 삭제하시겠습니까?`)) {
        userList = userList.filter(user => user.id !== userId);
        renderUserTable();
        alert("회원 정보가 삭제되었습니다.");
    }
}