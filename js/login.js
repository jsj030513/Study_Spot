document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const userId = document.getElementById('userId').value;
    const userPw = document.getElementById('userPw').value;

    // 간단한 테스트 로직 (실제로는 서버 API 호출)
    if(userId && userPw) {
        alert(`${userId}님, 공부명당에 오신 것을 환영합니다!`);
        location.href = 'main.html';
    } else {
        alert('아이디와 비밀번호를 모두 입력해주세요.');
    }
});