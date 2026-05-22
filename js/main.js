/**
 * Study-Spot 메인 로직
 * 작성자: 장성주 (Baekseok Univ. Software '22)
 */

// 1. 장소 데이터 (WIFI: 빵빵함/보통/없음, Socket: 많음/보통/적/음/없음)
/**const spotData = [
    { id: 1, name: '안라커피', score: 85, type: 'cafe', typeName: '카페', lat: 36.833450, lng: 127.173136, wifi: '보통', socket: '적음', free: 30 },
    { id: 2, name: '카페고메', score: 90, type: 'cafe', typeName: '카페', lat: 36.834127, lng: 127.172958, wifi: '좋음', socket: '많음', free: 60 },
    { id: 3, name: '사람 그리고 이야기', score: 88, type: 'cafe', typeName: '카페', lat: 36.834812, lng: 127.172922, wifi: '좋음', socket: '보통', free: 55 },
    { id: 4, name: 'Eso', score: 80, type: 'cafe', typeName: '카페', lat: 36.8141891, lng: 127.1787319, wifi: '보통', socket: '적음', free: 40 },
    { id: 5, name: '예뫼골', score: 78, type: 'cafe', typeName: '카페', lat: 36.8314512, lng: 127.186215, wifi: '보통', socket: '없음', free: 35 },
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

    { id: 39, name: '백석대학교 도서관', score: 99, type: 'library', typeName: '도서관', lat: 36.839350, lng: 127.185960, wifi: '암호', socket: '많음', free: 80 },
    { id: 40, name: '상명대학교 도서관', score: 87, type: 'library', typeName: '도서관', lat: 36.6021911, lng: 126.955345, wifi: '암호', socket: '많음', free: 75 },
    { id: 41, name: '단국대학교 도서관', score: 80, type: 'library', typeName: '도서관', lat: 36.835777, lng: 127.166544, wifi: '암호', socket: '많음', free: 66 },
    { id: 73, name: '백석대학교 백석학술정보관', score: 98, type: 'library', typeName: '도서관', lat: 36.837746, lng: 127.184050, wifi: '암호', socket: '많음', free: 90  },
    { id: 74, name: '백석대학교 백석학술정보관', score: 98, type: 'library', typeName: '도서관', lat: 36.838530, lng: 127.182691, wifi: '암호', socket: '많음', free: 88  },

    { id: 42, name: 'CU 안서중앙점', score: 40, type: 'store', typeName: '편의점', lat:37.315570, lng: 127.1740 },
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
*/
let map;
let overlays = [];
let spotData = [];
let selectedSpotForNav = null;
let isLoggedIn = false;
let userName = "";
let isMapReady = false;
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

async function init() {
    const userData = getStoredUser();
    if (userData) {
        isLoggedIn = true;
        userName = userData.name;
    }

    initMap();
    await loadSpots();

    const urlParams = new URLSearchParams(window.location.search);
    const targetId = urlParams.get('id');

    if (targetId) {
        renderSpots('all', '', targetId);
        setTimeout(() => selectSpotById(targetId), 300);
    } else {
        renderSpots('all');
    }

    setupEvents();
    updateHeader();
}

function getStoredUser() {
    try {
        return JSON.parse(localStorage.getItem('user'));
    } catch (error) {
        localStorage.removeItem('user');
        return null;
    }
}

function initMap() {
    const container = document.getElementById('kakaoMap');
    if (!container) return;

    if (!window.kakao?.maps) {
        const message = window.kakaoMapSdkFailed
            ? '카카오맵 SDK 로딩에 실패했습니다.'
            : '카카오맵을 불러오지 못했습니다.';
        showMapFallback(message);
        return;
    }

    const options = { center: new kakao.maps.LatLng(36.8360, 127.1750), level: 4 };
    try {
        map = new kakao.maps.Map(container, options);
        isMapReady = true;
    } catch (error) {
        showMapFallback('지도를 초기화하지 못했습니다.');
    }
}

function showMapFallback(message) {
    const container = document.getElementById('kakaoMap');
    if (!container) return;

    container.style.display = 'grid';
    container.style.placeItems = 'center';
    container.style.color = '#64748b';
    container.style.fontWeight = '700';
    container.textContent = message;
}

async function loadSpots() {
    const spotList = document.getElementById('spotList');

    try {
        const response = await fetch(`${API_BASE_URL}/api/places`);
        if (!response.ok) throw new Error('장소 목록을 불러오지 못했습니다.');

        const places = await response.json();
        spotData = places
            .map(place => ({
                id: place.placeId,
                name: place.name,
                score: place.recommendScore,
                type: place.type,
                typeName: place.typeName,
                lat: Number(place.latitude),
                lng: Number(place.longitude),
                wifi: place.wifiStatus,
                socket: place.outletStatus,
                noise: place.noiseLevel,
                address: place.address,
                telNo: place.telNo,
                description: place.description,
                openStatus: null,
                profile: null,
                recentReviews: [],
                sentiment: createDefaultSentiment(place.recommendScore),
                oneLineReview: place.description || createDefaultOneLine(place)
            }))
            .filter(spot => Number.isFinite(spot.lat) && Number.isFinite(spot.lng));

        await hydrateCafeReviews();
    } catch (error) {
        spotData = [];
        if (spotList) {
            spotList.innerHTML = `
                <div style="padding:24px; color:#ef4444; text-align:center;">
                    장소 데이터를 불러오지 못했습니다. 백엔드와 DB 연결을 확인해주세요.
                </div>
            `;
        }
    }
}

async function hydrateCafeReviews() {
    const cafes = spotData.filter(spot => spot.type === 'cafe');
    await Promise.all(cafes.map(async (spot) => {
        try {
            const response = await fetch(`${API_BASE_URL}/api/places/${spot.id}/reviews`);
            if (!response.ok) return;

            const reviews = await response.json();
            applyReviewSummary(spot, reviews);
        } catch (error) {
            // 리뷰가 없어도 장소 목록은 보여야 합니다.
        }

        try {
            const response = await fetch(`${API_BASE_URL}/api/places/${spot.id}/open-status`);
            if (!response.ok) return;
            spot.openStatus = await response.json();
        } catch (error) {
            // 영업 상태가 없어도 장소 목록은 보여야 합니다.
        }

        try {
            const response = await fetch(`${API_BASE_URL}/api/places/${spot.id}/profile`);
            if (!response.ok) return;
            spot.profile = await response.json();
        } catch (error) {
            // 프로필이 없어도 장소 목록은 보여야 합니다.
        }
    }));
}

function applyReviewSummary(spot, reviews) {
    if (!Array.isArray(reviews) || reviews.length === 0) return;

    const positiveCount = reviews.filter(review => review.sentiment === 'POSITIVE' || review.faceType === 'positive').length;
    const negativeCount = reviews.filter(review => review.sentiment === 'NEGATIVE' || review.faceType === 'negative').length;
    const visibleReviews = reviews.filter(review => review.content);
    const latestReview = visibleReviews[0];

    spot.sentiment = {
        type: positiveCount >= negativeCount ? 'positive' : 'negative',
        label: positiveCount >= negativeCount ? '긍정 평가' : '부정 평가',
        positiveCount,
        negativeCount
    };
    spot.recentReviews = visibleReviews.slice(0, 3);
    spot.oneLineReview = latestReview?.content || createDefaultOneLine(spot);
}

function createDefaultSentiment(score = 0) {
    return {
        type: score >= 75 ? 'positive' : 'negative',
        label: score >= 75 ? '긍정 평가' : '부정 평가',
        positiveCount: 0,
        negativeCount: 0
    };
}

function createDefaultOneLine(place) {
    const typeName = place.typeName || '장소';
    const score = place.recommendScore ?? place.score ?? 0;
    if (score >= 80) {
        return `${typeName} 이용자에게 무난하게 추천할 수 있는 장소입니다.`;
    }
    return `${typeName} 이용 전 시설과 분위기를 한 번 더 확인해보세요.`;
}

// 필터링 및 검색 로직 통합
function renderSpots(filterType, keyword = '', targetId = null) {
    const spotList = document.getElementById('spotList');
    if (!spotList) return;
    spotList.innerHTML = '';

    if (overlays) overlays.forEach(o => o.setMap(null));
    overlays = [];

    const filtered = spotData.filter(spot => {
        const isTypeMatch = filterType === 'all' || spot.type === filterType;
        const isNameMatch = spot.name.toLowerCase().includes(keyword.toLowerCase());
        const isTargetMatch = targetId ? String(spot.id) === String(targetId) : true;
        return isTypeMatch && isNameMatch && isTargetMatch;
    });

    if (targetId && filtered.length > 0) {
        const notice = document.createElement('div');
        notice.style = "padding:10px; background:#eef2ff; font-size:0.8rem; text-align:center; cursor:pointer; color:#3182f6;";
        notice.innerHTML = "<b>[공부명당]</b> 전체 목록 보기 ↺";
        notice.onclick = () => location.href = routeUrl('/main');
        spotList.appendChild(notice);
    }

    filtered.forEach(spot => {
        const card = document.createElement('div');
        card.className = 'spot-card';
        card.append(createSpotCardTop(spot), createSpotMeta(spot));
        card.onclick = () => selectSpot(spot);
        spotList.appendChild(card);

        if (isMapReady) {
            const marker = createMarker(spot);
            const overlay = new kakao.maps.CustomOverlay({
                position: new kakao.maps.LatLng(spot.lat, spot.lng),
                content: marker,
                yAnchor: 1.2
            });
            overlay.setMap(map);
            overlays.push(overlay);
        }
    });
}

function createSpotCardTop(spot) {
    const top = document.createElement('div');
    top.className = 'card-top';

    const name = document.createElement('span');
    name.className = 'card-name';
    name.textContent = spot.name;

    const score = document.createElement('span');
    const sentiment = getSentimentView(spot);
    score.className = `face-badge ${sentiment.type}`;
    score.textContent = sentiment.face;
    score.title = sentiment.label;
    score.setAttribute('aria-label', sentiment.label);

    top.append(name, score);
    return top;
}

function createSpotMeta(spot) {
    const meta = document.createElement('div');
    meta.style.fontSize = '0.75rem';
    meta.style.color = '#636e72';
    meta.textContent = `${spot.typeName || '장소'} | ${usageTimeLabel(spot)}`;
    return meta;
}

function createMarker(spot) {
    const wrapper = document.createElement('div');
    wrapper.className = 'map-marker-wrapper';
    wrapper.onclick = () => selectSpotById(spot.id);

    const pin = document.createElement('div');
    const sentiment = getSentimentView(spot);
    pin.className = `face-pin ${sentiment.type}`;

    const scoreText = document.createElement('span');
    scoreText.textContent = sentiment.face;

    const title = document.createElement('div');
    title.className = 'marker-title';
    title.textContent = spot.name;

    pin.appendChild(scoreText);
    wrapper.append(pin, title);
    return wrapper;
}

function getSentimentView(spot) {
    const sentiment = spot.sentiment || createDefaultSentiment(spot.score);
    const isPositive = sentiment.type === 'positive';
    return {
        type: isPositive ? 'positive' : 'negative',
        face: isPositive ? '😊' : '😟',
        label: sentiment.label || (isPositive ? '긍정 평가' : '부정 평가')
    };
}

function selectSpot(spot) {
    if (isMapReady) {
        map.panTo(new kakao.maps.LatLng(spot.lat, spot.lng));
    }
    openDetail(spot);
}

window.selectSpotById = function (id) {
    const spot = spotData.find(s => String(s.id) === String(id));
    if (spot) selectSpot(spot);
};

function openDetail(spot) {
    selectedSpotForNav = spot;
    const sentiment = getSentimentView(spot);
    document.getElementById('detailName').innerText = spot.name;
    document.getElementById('detailScore').innerText = sentiment.face;
    document.getElementById('detailScore').className = `big-score face-detail ${sentiment.type}`;
    const scoreBars = document.getElementById('scoreBars');
    if (scoreBars) {
        const positiveCount = spot.sentiment?.positiveCount || 0;
        const negativeCount = spot.sentiment?.negativeCount || 0;
        scoreBars.innerHTML = `
            <div class="detail-review-counts">
                <span>😊 ${positiveCount}개</span>
                <span>😟 ${negativeCount}개</span>
                <span>${escapeHtml(spot.typeName || '장소')} | ${escapeHtml(usageTimeLabel(spot))}</span>
            </div>
            ${spot.openStatus?.message ? `<p class="detail-open-message">${escapeHtml(spot.openStatus.message)}</p>` : ''}
            ${createRecentReviewMarkup(spot)}
            ${createReviewComposer(spot)}
        `;
    }
    document.getElementById('detailPanel').classList.add('open');
}

function createRecentReviewMarkup(spot) {
    const reviews = spot.recentReviews || [];
    if (!reviews.length) {
        return `<p class="detail-one-line">${escapeHtml(spot.oneLineReview || createDefaultOneLine(spot))}</p>`;
    }

    return `
        <div class="recent-review-panel">
            <strong>최근 한줄평</strong>
            <ul class="recent-review-list">
                ${reviews.map(review => `<li>${escapeHtml(review.content)}</li>`).join('')}
            </ul>
        </div>
    `;
}

function createReviewComposer(spot) {
    if (spot.type !== 'cafe') {
        return '<p class="review-guide">한줄평은 카페에만 등록할 수 있습니다.</p>';
    }

    const user = getStoredUser();
    if (!user) {
        return `
            <div class="review-composer locked">
                <p>일반 사용자로 로그인하면 이 카페에 한줄평을 남길 수 있습니다.</p>
                <button type="button" onclick="location.href='${routeUrl('/login')}'">로그인하기</button>
            </div>
        `;
    }

    if (user.role !== 'U') {
        return '<p class="review-guide">한줄평 등록은 일반 사용자 계정에서만 가능합니다.</p>';
    }

    return `
        <div class="review-composer">
            <label for="reviewContent">한줄평 남기기</label>
            <div class="review-input-row">
                <input id="reviewContent" type="text" maxlength="100" placeholder="이 카페의 공부 분위기를 한 줄로 남겨주세요.">
                <button type="button" onclick="submitCafeReview()">등록</button>
            </div>
        </div>
    `;
}

window.submitCafeReview = async () => {
    if (!selectedSpotForNav || selectedSpotForNav.type !== 'cafe') return;

    const input = document.getElementById('reviewContent');
    const content = input?.value.trim() || '';
    const token = localStorage.getItem('authToken');
    const user = getStoredUser();

    if (!user || !token) {
        alert('로그인이 필요합니다.');
        location.href = routeUrl('/login');
        return;
    }

    if (user.role !== 'U') {
        alert('한줄평은 일반 사용자 계정에서만 등록할 수 있습니다.');
        return;
    }

    if (!content) {
        alert('한줄평을 입력해주세요.');
        input?.focus();
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/api/places/${selectedSpotForNav.id}/reviews`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ content })
        });

        if (!response.ok) {
            alert(await readErrorMessage(response));
            return;
        }

        const reviewsResponse = await fetch(`${API_BASE_URL}/api/places/${selectedSpotForNav.id}/reviews`);
        if (reviewsResponse.ok) {
            applyReviewSummary(selectedSpotForNav, await reviewsResponse.json());
        }

        const searchInput = document.getElementById('searchInput');
        const activeChip = document.querySelector('.category-chip.active');
        renderSpots(activeChip?.dataset.type || 'all', searchInput?.value || '');
        openDetail(selectedSpotForNav);
        alert('한줄평이 등록되었습니다.');
    } catch (error) {
        alert('서버에 연결할 수 없습니다. 백엔드가 실행 중인지 확인해주세요.');
    }
};

function openStatusLabel(status) {
    if (!status) return '영업상태 미등록';
    return status.open ? '영업중' : '마감';
}

function usageTimeLabel(spot) {
    const openingHours = spot.profile?.openingHours?.trim();
    if (openingHours) return openingHours;
    return '이용시간 미등록';
}

function escapeHtml(value) {
    return String(value ?? '')
        .replaceAll('&', '&amp;')
        .replaceAll('<', '&lt;')
        .replaceAll('>', '&gt;')
        .replaceAll('"', '&quot;')
        .replaceAll("'", '&#039;');
}

window.closeDetail = () => document.getElementById('detailPanel').classList.remove('open');

window.startNavigation = () => {
    if (!selectedSpotForNav) return;
    const navUrl = `https://map.kakao.com/link/to/${selectedSpotForNav.name},${selectedSpotForNav.lat},${selectedSpotForNav.lng}`;
    window.open(navUrl, '_blank');
};

window.toggleFavorite = () => {
    if (!selectedSpotForNav) return;
    if (!isLoggedIn) { alert("로그인이 필요합니다."); return; }
    let favorites = getStoredFavorites();
    if (!favorites.some(f => String(f.id) === String(selectedSpotForNav.id))) {
        favorites.push({ id: selectedSpotForNav.id, name: selectedSpotForNav.name });
        localStorage.setItem('myFavorites', JSON.stringify(favorites));
        alert("즐겨찾기에 추가되었습니다.");
    }
};

function getStoredFavorites() {
    try {
        return JSON.parse(localStorage.getItem('myFavorites')) || [];
    } catch (error) {
        localStorage.removeItem('myFavorites');
        return [];
    }
}

// [수정됨] 이벤트 설정 함수 - 검색창 실시간 연동 포함
function setupEvents() {
    const chips = document.querySelectorAll('.category-chip');
    const searchInput = document.getElementById('searchInput'); // HTML에서 id="searchInput" 확인 필수

    // 카테고리 칩 클릭
    chips.forEach(chip => {
        chip.onclick = function() {
            chips.forEach(c => c.classList.remove('active'));
            this.classList.add('active');
            const keyword = searchInput ? searchInput.value : '';
            renderSpots(this.dataset.type, keyword);
        };
    });

    // 검색창 실시간 입력 (onkeyup) 연동
    if (searchInput) {
        searchInput.onkeyup = function() {
            const activeChip = document.querySelector('.category-chip.active');
            const type = activeChip ? activeChip.dataset.type : 'all';
            renderSpots(type, this.value);
        };
    }
}

function updateHeader() {
    const navLinks = document.querySelector('.nav-links');
    if (isLoggedIn && navLinks) {
        const user = getStoredUser();
        const items = [
            createNavItem('탐색하기', routeUrl('/main'), true)
        ];
        if (user?.role === 'O' || user?.role === 'A') {
            items.push(createNavItem('사장님 페이지', routeUrl('/owner')));
        }
        items.push(
            createNavItem('마이페이지', routeUrl('/mypage')),
            createLogoutNavItem(),
            createUserNameNavItem(userName)
        );
        navLinks.replaceChildren(
            ...items
        );
    }
}

function createNavItem(text, href, active = false) {
    const item = document.createElement('li');
    const link = document.createElement('a');
    link.href = href;
    link.textContent = text;
    if (active) link.style.color = 'var(--accent)';
    item.appendChild(link);
    return item;
}

function createLogoutNavItem() {
    const item = document.createElement('li');
    const link = document.createElement('a');
    link.href = '#';
    link.textContent = '로그아웃';
    link.onclick = window.handleLogout;
    item.appendChild(link);
    return item;
}

function createUserNameNavItem(name) {
    const item = document.createElement('li');
    item.style.marginLeft = '15px';
    item.style.fontSize = '0.9rem';

    const strong = document.createElement('b');
    strong.textContent = name;

    item.append(strong, '님');
    return item;
}

window.handleLogout = (e) => {
    if (e) e.preventDefault();
    localStorage.removeItem('user');
    localStorage.removeItem('authToken');
    location.reload();
};

window.onload = init;
