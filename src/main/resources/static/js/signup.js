import {
    signUp,
    checkEmail,
    debounce,
    updateValidPassword,
    updateValidName,
    signIn,
} from './controller/userController.js'

// 회원가입
document.querySelector("#signup").addEventListener("click", signUp);
document.querySelector("#email").addEventListener("input", debounce(checkEmail, 1000));
document.querySelector("#name").addEventListener("input", updateValidName);
document.querySelector("#password").addEventListener("input", updateValidPassword);
document.querySelector("#password-check").addEventListener("input", updateValidPassword);