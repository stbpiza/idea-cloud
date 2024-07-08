
export function signUpSuccess() {
    console.log("회원가입 성공");
    alert("회원가입이 완료되었습니다.");
    location.href = "/signin";
}

export function signUpBadRequest() {
    console.log("회원가입 실패 400");
    alert("입력값이 잘못되었습니다.");

}

export function signUpFail() {
    console.log("회원가입 실패");
    alert("회원가입에 실패하였습니다.");
}

export function messageClear() {
    const password = document.querySelector("#password-error");
    password.classList.add("hide")
    password.innerText= ""
    const email = document.querySelector("#email-error");
    email.classList.add("hide")
    email.classList.remove("text-green-400")
    email.classList.add("text-red-400")
    email.innerText= ""
}

export function passwordCheckFail() {
    const password = document.querySelector("#password-error");
    password.innerText= "비밀번호가 일치하지 않습니다."
    password.classList.remove("hide")
    btnDisabled();
}

export function emailTypeError() {
    const email = document.querySelector("#email-error");
    email.innerText= "이메일 형식이 아닙니다."
    email.classList.remove("hide")
    btnDisabled();
}

export function emailCheckSuccess() {
    const email = document.querySelector("#email-error");
    email.classList.remove("hide")
    email.classList.remove("text-red-400")
    email.classList.add("text-green-600")
    email.innerText= "사용 가능한 이메일입니다."
    btnActive();
}

export function emailCheckFail() {
    const email = document.querySelector("#email-error");
    email.classList.remove("hide")
    email.innerText= "이미 사용중인 이메일입니다."
    btnDisabled();
}

export function serverError() {
    console.log("서버 에러");
    alert("서버 에러가 발생했습니다.");
}

export function btnActive() {
    console.log("btnActive")
    const button = document.querySelector("#signup");
    button.disabled = false;
    button.classList.remove("bg-indigo-300");
    button.classList.add("bg-indigo-600");
}

export function btnDisabled() {
    console.log("btnDisabled")
    const button = document.querySelector("#signup");
    button.disabled = true;
    button.classList.remove("bg-indigo-600");
    button.classList.add("bg-indigo-300");
}