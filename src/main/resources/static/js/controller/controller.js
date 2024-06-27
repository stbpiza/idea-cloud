export { get, post }

export default class Controller {
    constructor(view, model) {
        this.view = view;
        this.model = model;
    }
}





function sendRequest(method, path, query, body) {

    const token = window.localStorage.getItem('token');

    return fetch(path + query, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token,
        },
        body: JSON.stringify(body)
    })
        .then(response => {
            if (response.status === 401) {
                window.location.href = '/login';
            } else {
                return response;
            }
        })

}



function get(path, query) {
    return sendRequest('GET', path, query);
}

function post(path, query, body) {
    return sendRequest('POST', path, query, body);
}




