import { get, post } from './controller';

export default class UserController {
    constructor(view, model) {
        this.view = view;
        this.model = model;
    }
}


export function signup(body) {
    const response = post('/users', '', body);

    return response.then(response => {
        return response.json();
    });
}