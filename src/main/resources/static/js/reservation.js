class Reservation {
    constructor() {
        $("#repeat-check").addEventListener("change", this.repeatCheckHandler);
        $("#reservation-btn").addEventListener("click", this.reservationBtnClickHandler.bind(this));
    }
    validBooker() {
        const nameLength = $("#bookerName").value.length;
        if(nameLength < 2 || nameLength > 10) {
            $("#error-booker").style.display = "";
            return false;
        }
        $("#error-booker").style.display = "none";
        return true;
    }
    validStartTime() {
        const startTime = $("#startTime").value.split(":");
        const hour = parseInt(startTime[0]);
        const minute = parseInt(startTime[1]);

        if(isNaN(hour) || isNaN(minute))
            return false;
        if(hour < 9 || hour > 20 || (minute != 0 && minute != 30))
            return false;
        return true;
    }
    validEndTime() {
        const endTime = $("#endTime").value.split(":");
        const hour = parseInt(endTime[0]);
        const minute = parseInt(endTime[1]);

        if(isNaN(hour) || isNaN(minute))
            return false;
        if(hour < 9 || hour > 21 || (minute != 0 && minute != 30))
            return false;
        if(hour === 9 && minute === 0 || hour === 21 && minute === 30)
            return false;
        return true;
    }
    validTimeFormat() {
        if(this.validStartTime() && this.validEndTime()) {
            $("#error-time-format").style.display = "none";
            return true;
        }
        $("#error-time-format").style.display = "";
        return false;
    }
    validTime() {
        const startTime = $("#startTime").value.split(":");
        const endTime = $("#endTime").value.split(":");
        if (parseInt(endTime[0]) > parseInt(startTime[0])) {
            $("#error-time").style.display = "none";
            return true;
        }
        if (parseInt(endTime[0]) === parseInt(startTime[0]) && parseInt(endTime[1]) > parseInt(startTime[1])) {
            $("#error-time").style.display = "none";
            return true;
        }
        $("#error-time").style.display = "";
        return false;
    }
    validRepeatNum() {
        const repeatNum = $("#repeatNum").value;
        if(repeatNum < 0 || repeatNum > 10) {
            $("error-repeat").style.display = "";
            return false;
        }
        $("#error-repeat").style.display = "none";
        return true;
    }
    reservateCallback(response) {
        if(response.status == 201) {
            document.location.href = "/";
        } else {
            response.text().then(result => {
                alert(result);
            })
        }
    }
    reservationBtnClickHandler(evt) {
        const validations = [ this.validBooker, this.validTimeFormat.bind(this), this.validTime, this.validRepeatNum];
        if(validations.filter(valid => !valid()).length != 0) {
            return;
        }
        this.reservationForm = {
            "meetingRoom": {
                "id": $("#meetingRoom-select").options[$("#meetingRoom-select").selectedIndex].value,
            },
            "bookerName": $("#bookerName").value,
            "startDate": $("#startDate").value,
            "startTime": $("#startTime").value,
            "endTime": $("#endTime").value,
            "repeatNum": $("#repeatNum").value
        }
        fetchManger({
            url: "/api/reservation",
            method: "POST",
            headers: {"content-type":"application/json"},
            body: JSON.stringify(this.reservationForm),
            callback: this.reservateCallback
        });
    }
    repeatCheckHandler(evt) {
        const target = evt.target;
        if(target.checked) {
            $("#repeat-yes").style.display = "inline-block";
        } else {
            $("#repeatNum").value = 0;
            $("#repeat-yes").style.display = "none";
        }
    }
}

document.addEventListener("DOMContentLoaded", () => {
    new Reservation();
})