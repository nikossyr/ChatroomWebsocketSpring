"use strict";

// Gets the app base url from browser
function getContextPath() {
    return window.location.href.substring(7);
}

let ws;
$(document).ready(function () {

    $(document).bind('keypress', function (e) {
        if (e.key === "Enter") {
            $('#message_send').trigger('click');
        }
    });

    let url = "ws://" + getContextPath() + "messaging";
    console.log(url);
    ws = new WebSocket(url);

    $("#message_send").on("click", function () {
        sendMessage();
    });

    $("#submitbtn").click(function () {
        sendForm();
        $("#question").val('');
    });

    $("#closeconnection").click(function () {
        ws.close();

    });

    // alert(ws);
    ws.onmessage = function (message) {
        //alert(data.data);
        let html = "<div class=\"outgoing_msg\">\n" +
            "<div class=\"sent_msg msg\">\n" +
            "<p>" + message.data + "</p>\n" +
            "<span class=\"time_date\"> 11:01 AM    |    Today</span> </div>\n" +
            "</div>";
        $("#message_tree").append(html);
        $('#message_tree').animate({scrollTop: $('#message_tree')[0].scrollHeight}, 500);
    };


});

function sendMessage() {
    ws.send($("#message_input").val());
}


function sendForm() {
    ws.send($("#question").val());
}

