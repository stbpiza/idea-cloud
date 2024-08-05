import { get, post } from './controller.js';
import { drawMeetingNoteList } from "../view/homeView.js";

let page = 0;

async function meetingNoteList() {

    let body = await meetingNoteListRequest(page);

    console.log(body);

    drawMeetingNoteList(body);

}




function meetingNoteListRequest(page) {
    console.log(page);
    return get('/api/meeting-notes', `?page=${page}&size=10`)
        .then(response => {
            console.log(response);
            return response.json();
        })
        .then(data => {
            console.log(data);
            return data;
        })
}

meetingNoteList();