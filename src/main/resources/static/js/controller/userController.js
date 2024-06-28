import { get, post } from './controller.js';
import { getSignupBody, saveToken } from '../model/userModel.js';
import { signUpSuccess, signUpFail } from '../view/userView.js';

export { signupRequest }


export function signup() {

    let signupBody = getSignupBody();

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




function signupRequest(body) {
    return  post('/api/users', '', body);
}