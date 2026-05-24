const BACKEND_URL = `${location.protocol}//${location.hostname}:8080`;
const API_BASE_URL = location.port === '5500' ? BACKEND_URL : '';

function routeUrl(path) {
    return location.port === '5500' ? `${BACKEND_URL}${path}` : path;
}

function bindAccountTypeCards(name) {
    document.querySelectorAll(`input[name="${name}"]`).forEach(input => {
        input.addEventListener('change', () => {
            document.querySelectorAll(`input[name="${name}"]`).forEach(item => {
                item.closest('.account-type-card')?.classList.toggle('active', item.checked);
            });
            toggleOwnerFields();
        });
    });
}

async function readErrorMessage(response) {
    try {
        const data = await response.json();
        return data.errors?.[0]?.message || data.message || data.error || '요청을 처리하지 못했습니다.';
    } catch (error) {
        return '요청을 처리하지 못했습니다.';
    }
}

let cafeOptions = [];

async function loadCafeOptions() {
    const cafeList = document.getElementById('ownerCafeList');
    if (!cafeList) return;

    try {
        const response = await fetch(`${API_BASE_URL}/api/places?type=cafe`);
        if (!response.ok) throw new Error();

        cafeOptions = await response.json();
        cafeList.innerHTML = '';
        cafeOptions.forEach(cafe => {
            const option = document.createElement('option');
            option.value = cafe.name;
            option.label = cafe.placeId;
            cafeList.appendChild(option);
        });
    } catch (error) {
        cafeOptions = [];
    }
}

function toggleOwnerFields() {
    const accountType = document.querySelector('input[name="accountType"]:checked')?.value || 'user';
    const ownerFields = document.getElementById('ownerFields');
    if (!ownerFields) return;

    const isOwner = accountType === 'owner';
    ownerFields.hidden = !isOwner;
    ['ownerPlaceId', 'businessNumber', 'documentUrl'].forEach(id => {
        const field = document.getElementById(id);
        if (field) field.required = isOwner;
    });
}

async function loginAfterSignup(userId, password) {
    const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId, password })
    });

    if (!response.ok) {
        throw new Error(await readErrorMessage(response));
    }

    const data = await response.json();
    localStorage.setItem('authToken', data.token);
    localStorage.setItem('user', JSON.stringify(data.user));
    return data;
}

async function requestOwnerVerification() {
    const token = localStorage.getItem('authToken');
    const placeId = resolveOwnerPlaceId();
    const body = {
        placeId,
        businessNumber: document.getElementById('businessNumber').value.trim(),
        documentUrl: document.getElementById('documentUrl').value.trim()
    };

    const response = await fetch(`${API_BASE_URL}/api/owner/verifications`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(body)
    });

    if (!response.ok) {
        throw new Error(await readErrorMessage(response));
    }

    return response.json();
}

async function rollbackCreatedSignup() {
    const token = localStorage.getItem('authToken');
    if (!token) return false;

    try {
        const response = await fetch(`${API_BASE_URL}/api/users/me`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${token}` }
        });
        return response.ok;
    } catch (error) {
        return false;
    } finally {
        localStorage.removeItem('authToken');
        localStorage.removeItem('user');
    }
}

function resolveOwnerPlaceId() {
    const value = document.getElementById('ownerPlaceId').value.trim();
    const matchedCafe = cafeOptions.find(cafe => cafe.placeId === value || cafe.name === value);
    if (matchedCafe) return matchedCafe.placeId;
    return value;
}

async function checkUserIdAvailability() {
    const userId = document.getElementById('userId')?.value.trim();
    if (!userId) {
        alert('아이디를 입력해주세요.');
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/api/auth/user-ids/${encodeURIComponent(userId)}/availability`);
        if (!response.ok) {
            alert(await readErrorMessage(response));
            return;
        }

        const data = await response.json();
        alert(data.available ? '사용 가능한 아이디입니다.' : '이미 사용 중인 아이디입니다.');
    } catch (error) {
        alert('서버에 연결할 수 없습니다. 백엔드가 실행 중인지 확인해주세요.');
    }
}

bindAccountTypeCards('accountType');
toggleOwnerFields();
loadCafeOptions();
document.querySelector('.duplicate-btn')?.addEventListener('click', checkUserIdAvailability);

document.getElementById('joinForm')?.addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const name = document.getElementById('userName').value;
    const userId = document.getElementById('userId').value;
    const pw = document.getElementById('userPw').value;
    const pwConfirm = document.getElementById('userPwConfirm').value;
    const accountType = document.querySelector('input[name="accountType"]:checked')?.value || 'user';

    if (pw !== pwConfirm) {
        alert('비밀번호가 일치하지 않습니다. 다시 확인해주세요.');
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/api/auth/signup`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userId, password: pw, name })
        });

        if (!response.ok) {
            alert(await readErrorMessage(response));
            return;
        }

        if (accountType === 'owner') {
            await loginAfterSignup(userId, pw);
            try {
                await requestOwnerVerification();
            } catch (error) {
                const rolledBack = await rollbackCreatedSignup();
                const retryMessage = rolledBack
                    ? '입력한 정보를 확인한 뒤 같은 아이디로 다시 가입해주세요.'
                    : '이미 생성된 계정이 남아 있을 수 있습니다. 관리자에게 계정 삭제를 요청한 뒤 다시 시도해주세요.';
                alert(`${error.message || '사업자 인증 요청에 실패했습니다.'}\n${retryMessage}`);
                return;
            }
            alert('사업자 회원가입과 인증 요청이 완료되었습니다. 관리자 승인 후 사업자 로그인을 이용할 수 있습니다.');
            location.href = routeUrl('/login');
            return;
        }

        alert('회원가입이 완료되었습니다! 로그인 페이지로 이동합니다.');
        location.href = routeUrl('/login');
    } catch (error) {
        alert(error.message || '서버에 연결할 수 없습니다. 백엔드가 실행 중인지 확인해주세요.');
    }
});
