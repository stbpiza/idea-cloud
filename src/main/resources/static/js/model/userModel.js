// document.write('<script src="/static/js/controller/userController.js"></script>')
import { signupRequest } from '../controller/userController.js'
import { signupRequestBody } from '../dto/user.js'


export default class UserModel {
    constructor() {
    }
}



export function getSignupBody() {

    const inputEmail = document.querySelector("#email").value;
    const inputName = document.querySelector("#name").value;
    const inputPassword = document.querySelector("#password").value;

    signupRequestBody.email = inputEmail;
    signupRequestBody.name = inputName;
    signupRequestBody.password = inputPassword;

    return signupRequestBody
}


