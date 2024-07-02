
export function signUpSuccess() {
    console.log("회원가입 성공");
    alert("회원가입이 완료되었습니다.");
    location.href = "/login";
}

export function signUpFail() {
    console.log("회원가입 실패");
    alert("회원가입에 실패하였습니다.");
}

export function messageClear() {
    const password = document.querySelector("#password-error");
    password.classList.add("hide")
    password.innerText= ""
}

export function passwordCheckFail() {
    const password = document.querySelector("#password-error");
    password.innerText= "비밀번호가 일치하지 않습니다."
    password.classList.remove("hide")
}

export function emailTypeError() {
    const email = document.querySelector("#email-error");
    email.innerText= "이메일 형식이 아닙니다."
    email.classList.remove("hide")
}

