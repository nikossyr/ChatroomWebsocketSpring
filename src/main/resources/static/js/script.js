"use strict";

// Gets the app base url from browser
function getContextPath() {
    return window.location.href.substring(7);
}

let ws;
let wsonline;
$(document).ready(function () {

    $(document).bind('keypress', function (e) {
        if (e.key === "Enter") {
            $('#message_send').trigger('click');
        }
    });

    let url = "ws://" + getContextPath() + "/messaging";
    ws = new WebSocket(url);


    $("#message_send").on("click", function () {
        sendMessage();
        $("#message_input").val('');
    });

    $("#closeconnection").click(function () {
        ws.close();
    });

    // alert(ws);
    ws.onmessage = function (data) {
        //alert(data.data);
        let delimiterIndex = data.data.indexOf(":");
        console.log("Index", delimiterIndex);
        let nickname = data.data.substring(0, delimiterIndex);
        console.log(nickname);
        let message = data.data.substring(delimiterIndex + 1);
        console.log(message);
        let html = "<div class=\"incoming_msg\">\n" +
            " <div class=\"incoming_msg_img\"><img src=\"https://ptetutorials.com/images/user-profile.png\"\n" +
            "  alt=\"sunil\"></div>\n" +
            "<div class=\"received_msg\">\n" +
            "<p>" + nickname + "</p>" +
            " <div class=\"received_withd_msg\">\n" +
            " <p>" + message + "</p>\n" +
            "<span class=\"time_date\">" +
            new Date().toLocaleString() +
            "</span></div>\n" +
            " </div>\n" +
            " </div>";
        $("#message_tree").append(html);
        $('#message_tree').animate({scrollTop: $('#message_tree')[0].scrollHeight}, 500);
    };


    let url1 = "ws://" + getContextPath() + "/online";
    wsonline = new WebSocket(url1);
    wsonline.onmessage = function (users) {
        if (users.data === "HARD_RESET") {
            $("#chatlist").empty();
        } else {
            let html = "<div class=\"chat_list\">" +
                "<div class=\"chat_people\">\n" +
                "<div class=\"chat_img\"><img src=\"https://ptetutorials.com/images/user-profile.png\"\n" +
                "alt=\"sunil\"></div>\n" +
                "<div class=\"chat_ib\">\n" +
                "<h5>" + users.data + "</h5>\n" +
                "</div>\n" +
                "</div>" +
                " </div>";
            $("#chatlist").append(html);
        }
    };

    setTimeout(function () {
        wsonline.send("Hi")
    }, 2000);
});

function appendOutgoingMessage(message) {
    let html = "<div class=\"outgoing_msg\">\n" +
        "<div class=\"sent_msg msg\">\n" +
        "<p>" + message + "</p>\n" +
        "<span class=\"time_date\">" +
        "<span class=\"time_date\">" +
        new Date().toLocaleString() +
        "</span></div>\n" +
        "</div>";
    $("#message_tree").append(html);
    $('#message_tree').animate({scrollTop: $('#message_tree')[0].scrollHeight}, 500);
}

function sendMessage() {
    let message = $("#message_input").val();
    appendOutgoingMessage(message);
    ws.send(message);
}


