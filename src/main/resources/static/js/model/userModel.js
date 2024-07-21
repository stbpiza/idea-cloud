// document.write('<script src="/static/js/controller/userController.js"></script>')
import {
    signUpRequestBody,
    emailCheckRequestBody,
    signInRequestBody
} from '../dto/user.js'

// 회원가입

export function getEmailCheckBody() {
    const inputEmail = document.querySelector("#email").value;
    emailCheckRequestBody.email = inputEmail;
    return emailCheckRequestBody

}

export function getSignUpBody() {

    const inputEmail = document.querySelector("#email").value;
    const inputName = document.querySelector("#name").value;
    const inputPassword = document.querySelector("#password").value;
    const inputPasswordCheck = document.querySelector("#password-check").value;

    if (inputPassword !== inputPasswordCheck) {
        return "passwordNotMatched";
    }
    signUpRequestBody.email = inputEmail;
    signUpRequestBody.name = inputName;
    signUpRequestBody.password = inputPassword;

    return signUpRequestBody
}

export function checkNameInput() {
    const inputName = document.querySelector("#name").value;
    return inputName !== "";
}

export function checkPasswordInput() {
    const inputPassword = document.querySelector("#password").value;
    return inputPassword !== "";
}

export function checkPasswordSame() {
    const inputPassword = document.querySelector("#password").value;
    const inputPasswordCheck = document.querySelector("#password-check").value;
    return inputPassword === inputPasswordCheck;
}



// 로그인

export function getSignInBody() {
    const inputEmail = document.querySelector("#email").value;
    const inputPassword = document.querySelector("#password").value;
    signInRequestBody.email = inputEmail;
    signInRequestBody.password = inputPassword;
    return signInRequestBody

}





export function saveToken(token) {
    window.localStorage.setItem('token', token);
}
