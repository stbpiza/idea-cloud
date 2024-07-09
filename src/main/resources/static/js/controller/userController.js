import { get, post } from './controller.js';
import {
    getSignUpBody,
    saveToken,
    getEmailCheckBody,
    checkNameInput,
    checkPasswordInput,
    checkPasswordSame,
    getSignInBody,
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

// 회원가입

const signUpInputValid = {
     validEmail : false,
     validName : false,
     validPassword : false,
}

export function updateValidEmail(valid) {
    signUpInputValid.validEmail = valid;
    buttonUpdate();
}

export function updateValidName() {
    signUpInputValid.validName = checkNameInput();
    buttonUpdate();
}

export function updateValidPassword(valid) {
    signUpInputValid.validPassword = checkPasswordInput();
    if (checkPasswordSame()) {
        messageClear();
    } else {
        passwordCheckFail();
    }
    buttonUpdate();
}

function buttonUpdate() {
    if (signUpInputIsValid()) {
        btnActive();
    } else {
        btnDisabled();
    }
}

function signUpInputIsValid() {
    return signUpInputValid.validEmail && signUpInputValid.validName && signUpInputValid.validPassword;
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

export function signUp() {

    messageClear();

    let signupBody = getSignUpBody();
    console.log("signupBody : ", signupBody)
    if (signupBody === "passwordNotMatched") {
        passwordCheckFail();
        updateValidPassword(false);
    } else {

        signUpRequest(signupBody).then(response => {
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


// 로그인

export function signIn() {

    let signInBody = getSignInBody();

    signInRequest(signInBody).then(response => {
        console.log(response);

        if (response.status === 200) {
            saveToken(response.data.token);
            location.href = "/";
        } else if (response.status === 400) {
            alert("입력값이 잘못되었습니다.");
        } else {
            alert("로그인에 실패하였습니다.");
        }
    });


}




function signUpRequest(body) {
    return  post('/api/users', '', body);
}
function emailCheckRequest(body) {
    return post('/api/users/check-email', '', body);
}
function signInRequest(body) {
    return post('/api/session', '', body);
}