import {
    debounce,
    signIn,
} from './controller/userController.js'

// 로그인
document.querySelector("#signin").addEventListener("click", signIn);