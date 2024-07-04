import { get, post } from './controller.js';
import {
    getSignupBody,
    saveToken,
    getEmailCheckBody,
    checkNameInput,
    checkPasswordInput,
} from '../model/userModel.js';
import {
    signUpSuccess,
    signUpFail,
    signUpBadRequest,
    passwordCheckFail,
    messageClear,
    emailTypeError,
    emailCheckFail,
    emailCheckSuccess,
    serverError,
    btnActive,
    btnDisabled,
} from '../view/userView.js';

let timer;

export const debounce = (callback, delay) => {
    return (event) => {
        // 실행 중인 타이머가 있다면, 제거 후 재생성
        if (timer) clearTimeout(timer);
        timer = setTimeout(callback, delay, event);
    };
};

const inputValid = {
     validEmail : false,
     validName : false,
     validPassword : false,
}

export function updateValidEmail(valid) {
    inputValid.validEmail = valid;
    buttonUpdate();
}

export function updateValidName() {
    inputValid.validName = checkNameInput();
    buttonUpdate();
}

export function updateValidPassword(valid) {
    inputValid.validPassword = checkPasswordInput();
    buttonUpdate();
}

function buttonUpdate() {
    if (isValid()) {
        btnActive();
    } else {
        btnDisabled();
    }
}

function isValid() {
    return inputValid.validEmail && inputValid.validName && inputValid.validPassword;
}

export function checkEmail() {

    messageClear()

    let emailCheckBody = getEmailCheckBody();

    emailCheckRequest(emailCheckBody).then(response => {
        console.log(response);
        console.log(response.status);
        if (response.status === 200) {
            console.log("emailCheckSuccess")
            emailCheckSuccess();
            updateValidEmail(true);
        } else if (response.status === 409) {
            console.log("emailCheckFail")
            emailCheckFail();
            updateValidEmail(false);
        } else if (response.status === 400) {
            console.log("emailTypeError")
            emailTypeError();
            updateValidEmail(false);
        } else {
            console.log("serverError")
            serverError();
        }

    });

}

export function signup() {

    messageClear();

    let signupBody = getSignupBody();
    console.log("signupBody : ", signupBody)
    if (signupBody === "passwordNotMatched") {
        passwordCheckFail();
        updateValidPassword(false);
    } else {

        signupRequest(signupBody).then(response => {
            console.log(response);

            if (response.status === 201) {
                saveToken();
                signUpSuccess();
            } else if (response.status === 400) {
                signUpBadRequest();
            } else {
                signUpFail();
            }
        });
    }
}




function signupRequest(body) {
    return  post('/api/users', '', body);
}
function emailCheckRequest(body) {
    return post('/api/users/check-email', '', body);
}