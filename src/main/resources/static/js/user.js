import {
    signup,
    checkEmail,
    debounce,
    updateValidPassword,
    updateValidName,
} from './controller/userController.js'

document.querySelector("#signup").addEventListener("click", signup);
document.querySelector("#email").addEventListener("input", debounce(checkEmail, 1000));
document.querySelector("#name").addEventListener("input", updateValidName);
document.querySelector("#password").addEventListener("input", updateValidPassword);