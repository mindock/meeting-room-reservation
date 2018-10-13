class Reservation {
    constructor(today) {
        $("#selectDate").value = today;
        getData("/api/meetingRoom", this.initTimetable.bind(this));
        $("#selectDate").addEventListener("change", this.dateChangeHandler.bind(this));
    }
    setReservationsAreaPosition() {
        $(".reservation-area").style.top = $(".timetable-area .timetable").offsetTop + 2 + "px";
        $(".reservation-area").style.left = $(".timetable-area .timetable").offsetLeft + 2 + "px";
    }
    getTimeFormat(time) {
        const timeStr = time.toString();
        const dotIndex = timeStr.indexOf(".");
        if(dotIndex === -1) {
            return timeStr + ":00";
        }
        return timeStr.substr(0, dotIndex) + ":30";
    }
    fillTimePartContent(meetingRoomNum) {
        const trHtml = `<tr id={id}>`;
        const tdHtml = `<td></td>`;
        let html = ``;
        for(let time = 9; time < 21; time += 0.5) {
            html += `<tr id='time-`+time+`'>`;
            html += `<th> ` + this.getTimeFormat(time) + `</th>`;
            for(let i = 0; i < meetingRoomNum; i++)
                html += tdHtml;
            html += `</tr>`;
        }
        return html;
    }
    fillMeetingRoomContent(meetingRoom) {
        let html = `<th id={id}> {name} </th>`;
        return html.replace(/{id}/g, "meetingRoom-"+meetingRoom.id)
            .replace(/{name}/g, meetingRoom.name);
    }
    initTimetable(response) {
        let html = `
            <tr class="meetingRoom">
                <td id="blank"></td>`;
        response.json().then(meetingRooms => {
            meetingRooms.forEach(meetingRoom => {
                html += this.fillMeetingRoomContent(meetingRoom);
            });
            html += `</tr>`;
            html += this.fillTimePartContent(meetingRooms.length);
            $(".timetable").insertAdjacentHTML("beforeend", html);
            $(".timetable").style.width = 102 * (meetingRooms.length + 1) + "px";
            this.setReservationsAreaPosition();
        });
    }
    convertStringToTime(time) {
        const dotIndex = time.indexOf(":");
        let id = time.substr(0, dotIndex);
        if(time.substr(dotIndex + 1) === "30")
            return parseInt(time.substr(0, dotIndex)) + 0.5;
        return parseInt(time.substr(0, dotIndex));
    }
    getReservationLength(reservation) {
        let diff = this.convertStringToTime(reservation.endTime) - this.convertStringToTime(reservation.startTime);
        diff *= 2;
        return 52 * diff + diff - 3 + "px";
    }
    getReservationDivLeft(reservation) {
        return $("#meetingRoom-"+reservation.meetingRoom.id).offsetLeft + "px";
    }
    getReservationDivTop(reservation) {
        const timeStr = reservation.startTime;
        const dotIndex = timeStr.indexOf(":");
        let id = timeStr.substr(0, dotIndex);
        if(timeStr.substr(dotIndex + 1) === "30")
            id += "\\.5";
        return $("#time-"+id).offsetTop + "px";
    }
    fillReservationContent(reservation) {
        const html = `
                <dl id={id} style="top:{top}; left:{left}; height:{height};">
                    <dt>{bookerName}</dt>
                    <dd>{startTime} ~ {endTime}</dd>
                </dl>`;
        return html.replace(/{id}/g, "reservation-"+reservation.id)
            .replace(/{top}/g, this.getReservationDivTop(reservation))
            .replace(/{left}/g, this.getReservationDivLeft(reservation))
            .replace(/{height}/g, this.getReservationLength(reservation))
            .replace(/{bookerName}/g, reservation.bookerName)
            .replace(/{startTime}/g, reservation.startTime)
            .replace(/{endTime}/g, reservation.endTime);
    }
    removeReservations() {
        [...$(".reservation-area").children].forEach(reservation => {
            reservation.remove();
        });
    }
    changeReservations(response) {
        let html = ``;
        if(response.status === 200) {
            this.removeReservations();
            response.json().then(reservations => {
                reservations.forEach(reservation => {
                    html += this.fillReservationContent(reservation);
                });
                $(".reservation-area").insertAdjacentHTML("beforeend", html);
            });
        }
    }
    dateChangeHandler(evt) {
        const date = evt.target.value;
        getData("/api/reservation/"+date, this.changeReservations.bind(this));
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const date = new Date();
    new Reservation(date.getFullYear()+"-"+ (date.getMonth() + 1).toString().padStart(2, '0')+"-"+date.getDate().toString().padStart(2, '0'));
});