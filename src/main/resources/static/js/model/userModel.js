// document.write('<script src="/static/js/controller/userController.js"></script>')
import { signupRequestBody, emailCheckRequestBody } from '../dto/user.js'



export function getEmailCheckBody() {
    const inputEmail = document.querySelector("#email").value;
    emailCheckRequestBody.email = inputEmail;
    return emailCheckRequestBody

}


export function getSignupBody() {

    const inputEmail = document.querySelector("#email").value;
    const inputName = document.querySelector("#name").value;
    const inputPassword = document.querySelector("#password").value;
    const inputPasswordCheck = document.querySelector("#password-check").value;

    if (inputPassword !== inputPasswordCheck) {
        return "passwordNotMatched";
    }
    signupRequestBody.email = inputEmail;
    signupRequestBody.name = inputName;
    signupRequestBody.password = inputPassword;

    return signupRequestBody
}

export function saveToken(token) {
    window.localStorage.setItem('token', token);
}

export function checkNameInput() {
    const inputName = document.querySelector("#name").value;
    return inputName !== "";
}

export function checkPasswordInput() {
    const inputEmail = document.querySelector("#password").value;
    return inputEmail !== "";
}