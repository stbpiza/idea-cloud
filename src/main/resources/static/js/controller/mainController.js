import { get, post } from './controller.js';





function meetingNoteListRequest(page) {
    console.log(page);
    return get('/api/meeting-notes', `?page=${page}&size=10`)
        .then(response => {
            console.log(response.json());
        })
        .catch(error => {
            console.log(error);
        })
}

meetingNoteListRequest(1);