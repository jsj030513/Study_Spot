document.getElementById('joinForm')?.addEventListener('submit', function(e) {
    e.preventDefault();
    
    const pw = document.getElementById('userPw').value;
    const pwConfirm = document.getElementById('userPwConfirm').value;

    if (pw !== pwConfirm) {
        alert('비밀번호가 일치하지 않습니다. 다시 확인해주세요.');
        return;
    }

    alert('회원가입이 완료되었습니다! 로그인 페이지로 이동합니다.');
    location.href = 'login.html';
});