export { signUpSuccess, signUpFail, passwordCheckFail }


function signUpSuccess() {
    console.log("회원가입 성공");
    alert("회원가입이 완료되었습니다.");
    location.href = "/login";
}

function signUpFail() {
    console.log("회원가입 실패");
    alert("회원가입에 실패하였습니다.");
}

function passwordCheckFail() {
    const password = document.querySelector("#password-error");
    password.classList.remove("hide")
    password.value= "비밀번호가 일치하지 않습니다."
}