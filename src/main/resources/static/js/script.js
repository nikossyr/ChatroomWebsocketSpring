"use strict";

let token = $("meta[name='_csrf']").attr("content");
let headerName = $("meta[name='_csrf_header']").attr("content");

// Gets the app base url from browser
function getContextPath() {
    return window.location.href.substring(7);
}

// let ws;
// let wsonline;
// $(document).ready(function () {
//
//     $(document).bind('keypress', function (e) {
//         if (e.key === "Enter") {
//             $('#message_send').trigger('click');
//         }
//     });
//
//     let url = "ws://" + getContextPath() + "/messaging";
//     ws = new WebSocket(url);
//
//
//     $("#message_send").on("click", function () {
//         sendMessage();
//         $("#message_input").val('');
//     });
//
//     $("#closeconnection").click(function () {
//         ws.close();
//     });
//
//     // alert(ws);
//     ws.onmessage = function (response) {
//         console.log(response);
//         let responseJson = JSON.parse(response.data);
//         let html = "<div class=\"incoming_msg\">\n" +
//             " <div class=\"incoming_msg_img\"><img src=\"https://ptetutorials.com/images/user-profile.png\"\n" +
//             "  alt=\"sunil\"></div>\n" +
//             "<div class=\"received_msg\">\n" +
//             "<p>" + responseJson.username + "</p>" +
//             " <div class=\"received_withd_msg\">\n" +
//             " <p>" + responseJson.message + "</p>\n" +
//             "<span class=\"time_date\">" +
//             new Date().toLocaleString() +
//             "</span></div>\n" +
//             " </div>\n" +
//             " </div>";
//         $("#message_tree").append(html);
//         $('#message_tree').animate({scrollTop: $('#message_tree')[0].scrollHeight}, 500);
//     };
//
//
//     let url1 = "ws://" + getContextPath() + "/online";
//     wsonline = new WebSocket(url1);
//     wsonline.onmessage = function (usersOnline) {
//         console.log(usersOnline);
//         let users = JSON.parse(usersOnline.data);
//         console.log(users);
//         $("#chatlist").empty();
//         for (let i = 0; i < users.length; i++) {
//             let html = "<div id='" + users[i] + "' class=\"chat_list\">" +
//                 "<div class=\"chat_people\">\n" +
//                 "<div class=\"chat_img\"><img src=\"https://ptetutorials.com/images/user-profile.png\"\n" +
//                 "alt=\"sunil\"></div>\n" +
//                 "<div class=\"chat_ib\">\n" +
//                 "<h5>" + users[i] + "</h5>\n" +
//                 "</div>\n" +
//                 "</div>" +
//                 " </div>";
//             $("#chatlist").append(html);
//         }
//     };
// });
let url = "ws://localhost:8080/chat";
let socket = new WebSocket(url);
let stompClient = Stomp.over(socket);

let headers = {};
headers[headerName] = token;

stompClient.connect(headers, function (frame) {
    console.log("********RECEIVED FRAME************");
    console.log(frame);
    stompClient.subscribe('/topic/greetings', function (response) {
        console.log("********RECEIVED GREETING************");
        console.log(response);
        let messageDetails = JSON.parse(response.body);
        let html = "<div class=\"incoming_msg\">\n" +
            " <div class=\"incoming_msg_img\"><img src=\"https://ptetutorials.com/images/user-profile.png\"\n" +
            "  alt=\"sunil\"></div>\n" +
            "<div class=\"received_msg\">\n" +
            "<p>" + messageDetails.username + "</p>" +
            " <div class=\"received_withd_msg\">\n" +
            " <p>" + messageDetails.message + "</p>\n" +
            "<span class=\"time_date\">" +
            new Date().toLocaleString() +
            "</span></div>\n" +
            " </div>\n" +
            " </div>";
        $("#message_tree").append(html);
        $('#message_tree').animate({scrollTop: $('#message_tree')[0].scrollHeight}, 500);
    });
});
setTimeout(() => stompClient.send("/app/greetings", {}, "Hi peaches!"), 2000);

$(document).ready(function () {

    $(document).bind('keypress', function (e) {
        if (e.key === "Enter") {
            $('#message_send').trigger('click');
        }
    });

    $("#message_send").on("click", function () {
        sendMessage();
        $("#message_input").val('');
    });

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
    stompClient.send("/app/greetings", {}, message);
}


