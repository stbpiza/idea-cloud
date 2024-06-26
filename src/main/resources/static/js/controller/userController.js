import { get, post } from './controller';
import { getSignupBody } from '../model/userModel';

export default class UserController {
    constructor(view, model) {
        this.view = view;
        this.model = model;
    }
}


export function signup() {

    let signupBody = getSignupBody();

    signupRequest(signupBody).then(response => {
        console.log(response);
    });
}




function signupRequest(body) {
    const response = post('/users', '', body);

    return response.then(response => {
        return response.json();
    });
}