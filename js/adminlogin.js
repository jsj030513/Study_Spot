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

document.getElementById('adminLoginForm').addEventListener('submit', async event => {
    event.preventDefault();

    const userId = document.getElementById('adminId').value.trim();
    const password = document.getElementById('adminPw').value;

    if (!userId || !password) {
        alert('관리자 아이디와 비밀번호를 모두 입력해주세요.');
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userId, password })
        });

        if (!response.ok) {
            alert(await readErrorMessage(response));
            return;
        }

        const data = await response.json();
        if (data.user.role !== 'A') {
            localStorage.removeItem('authToken');
            localStorage.removeItem('user');
            alert('관리자 계정만 관리자 페이지에 접근할 수 있습니다.');
            return;
        }

        localStorage.setItem('authToken', data.token);
        localStorage.setItem('user', JSON.stringify(data.user));
        location.href = routeUrl('/admin');
    } catch (error) {
        alert('서버에 연결할 수 없습니다. 백엔드가 실행 중인지 확인해주세요.');
    }
});
