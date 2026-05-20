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

document.getElementById('joinForm')?.addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const name = document.getElementById('userName').value;
    const userId = document.getElementById('userId').value;
    const pw = document.getElementById('userPw').value;
    const pwConfirm = document.getElementById('userPwConfirm').value;

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

        alert('회원가입이 완료되었습니다! 로그인 페이지로 이동합니다.');
        location.href = routeUrl('/login');
    } catch (error) {
        alert('서버에 연결할 수 없습니다. 백엔드가 실행 중인지 확인해주세요.');
    }
});
