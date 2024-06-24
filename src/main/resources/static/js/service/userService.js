// document.write('<script src="/static/js/controller/userController.js"></script>')


import { signup } from '../controller/userController'
import { signupRequest } from '../dto/user'




export function signup() {

    const inputEmail = document.querySelector(".email").value;
    const inputName = document.querySelector(".name").value;
    const inputPassword = document.querySelector(".password").value;

    signupRequest.email = inputEmail;
    signupRequest.name = inputName;
    signupRequest.password = inputPassword;

    const response = signup(signupRequest)
}


