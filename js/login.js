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

bindAccountTypeCards('loginType');

document.getElementById('loginForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const userId = document.getElementById('userId').value;
    const userPw = document.getElementById('userPw').value;
    const loginType = document.querySelector('input[name="loginType"]:checked')?.value || 'user';

    if (!userId || !userPw) {
        alert('아이디와 비밀번호를 모두 입력해주세요.');
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userId, password: userPw })
        });

        if (!response.ok) {
            alert(await readErrorMessage(response));
            return;
        }

        const data = await response.json();
        localStorage.setItem('authToken', data.token);
        localStorage.setItem('user', JSON.stringify(data.user));

        if (loginType === 'owner') {
            if (data.user.role === 'O' || data.user.role === 'A') {
                alert(`${data.user.name} 사장님, 관리 페이지로 이동합니다.`);
                location.href = routeUrl('/owner');
                return;
            }

            alert('아직 승인된 사업자 계정이 아닙니다. 사업자 회원가입 또는 관리자 승인을 확인해주세요.');
            return;
        }

        alert(`${data.user.name}님, 공부명당에 오신 것을 환영합니다!`);
        location.href = routeUrl('/main');
    } catch (error) {
        alert('서버에 연결할 수 없습니다. 백엔드가 실행 중인지 확인해주세요.');
    }
});
