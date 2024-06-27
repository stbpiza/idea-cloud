import { get, post } from './controller.js';
import { getSignupBody } from '../model/userModel.js';

export { signupRequest }


export function signup() {

    let signupBody = getSignupBody();

    signupRequest(signupBody).then(response => {
        console.log(response);
    });
}




function signupRequest(body) {
    const response = post('/api/users', '', body);

    return response.then(response => {
        return response.json();
    });
}