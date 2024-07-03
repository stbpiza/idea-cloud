import { get, post } from './controller.js';
import { getSignupBody, saveToken, getEmailCheckBody } from '../model/userModel.js';
import {
    signUpSuccess,
    signUpFail,
    passwordCheckFail,
    messageClear,
    emailTypeError,
    emailCheckFail,
    emailCheckSuccess,
    serverError
} from '../view/userView.js';

let timer;

export const debounce = (callback, delay) => {
    return (event) => {
        // 실행 중인 타이머가 있다면, 제거 후 재생성
        if (timer) clearTimeout(timer);
        timer = setTimeout(callback, delay, event);
    };
};

export function checkEmail() {

    messageClear()

    let emailCheckBody = getEmailCheckBody();

    emailCheckRequest(emailCheckBody).then(response => {
        console.log(response);
        console.log(response.status);
        if (response.status === 200) {
            console.log("emailCheckSuccess")
            emailCheckSuccess();
        } else if (response.status === 409) {
            console.log("emailCheckFail")
            emailCheckFail();
        } else if (response.status === 400) {
            console.log("emailTypeError")
            emailTypeError();
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

    } else {

        signupRequest(signupBody).then(response => {
            console.log(response);

            if (response.status === 201) {
                saveToken();
                signUpSuccess();
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