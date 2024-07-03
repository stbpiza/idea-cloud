import { signup, checkEmail, debounce } from './controller/userController.js'

document.querySelector("#signup").addEventListener("click", signup);
document.querySelector("#email").addEventListener("input", debounce(checkEmail, 1000));