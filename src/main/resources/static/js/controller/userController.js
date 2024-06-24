import { get, post } from './controller';




export function signup(body) {
    const response = post('/users', '', body);

    return response.then(response => {
        return response.json();
    });
}