"use strict";

let token = $("meta[name='_csrf']").attr("content");
let headerName = $("meta[name='_csrf_header']").attr("content");
let headers = {};
headers[headerName] = token;
let socket = new SockJS('/chat');
let stompClient = Stomp.over(socket);
let currentUser = "";

stompClient.connect(headers, function (frame) {
    currentUser = frame["headers"]["user-name"];
    stompClient.subscribe('/user/queue/online', function (usersOnline) {
        let usernames = JSON.parse(usersOnline.body);
        for (let i = 0; i < usernames.length; i++) {
            let username = usernames[i];
            let usernameId = "#" + username;
            if ($(usernameId).length) {
                if (!$(usernameId).hasClass("online")) {
                    $(usernameId).addClass("online");
                }
            } else {
                if (username === currentUser) {
                    let html = "<div id='" + username + "' class=\"chat_list online\">" +
                        "<div class=\"chat_people\">\n" +
                        "<div class=\"chat_img\"><img src=\"https://ptetutorials.com/images/user-profile.png\"\n" +
                        "alt=\"sunil\"></div>\n" +
                        "<div class=\"chat_ib\">\n" +
                        "<h5>Me</h5>\n" +
                        "</div>\n" +
                        "</div>" +
                        " </div>";
                    $("#chatlist").append(html);
                } else {
                    let html = "<div id='" + username + "' class=\"chat_list online\">" +
                        "<div class=\"chat_people\">\n" +
                        "<div class=\"chat_img\"><img src=\"https://ptetutorials.com/images/user-profile.png\"\n" +
                        "alt=\"sunil\"></div>\n" +
                        "<div class=\"chat_ib\">\n" +
                        "<h5>" + username + "</h5>\n" +
                        "</div>\n" +
                        "</div>" +
                        " </div>";
                    $("#chatlist").append(html);
                }
            }
        }

    });

    stompClient.subscribe('/user/queue/offline', function (userOffline) {
        let username = userOffline.body;
        let usernameId = "#" + username;
        if ($(usernameId).length) {
            if (!$(usernameId).hasClass("online")) {
                $(usernameId).removeClass("online");
            }
        }

    })
    stompClient.subscribe('/user/queue/specific-user', function (response) {
        let responseJson = JSON.parse(response.body);
        let html = "<div class=\"incoming_msg\">\n" +
            " <div class=\"incoming_msg_img\"><img src=\"https://ptetutorials.com/images/user-profile.png\"\n" +
            "  alt=\"sunil\"></div>\n" +
            "<div class=\"received_msg\">\n" +
            "<p>" + responseJson.from + "</p>" +
            " <div class=\"received_withd_msg\">\n" +
            " <p>" + responseJson.text + "</p>\n" +
            "<span class=\"time_date\">" +
            responseJson.time +
            "</span></div>\n" +
            " </div>\n" +
            " </div>";
        $("#message_tree").append(html);
        $('#message_tree').animate({scrollTop: $('#message_tree')[0].scrollHeight}, 500);
    })
});


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

function sendMessage() {
    let message = $("#message_input").val();
    appendOutgoingMessage(message);
    let outgoingMsg = {
        // 'from': "Me",
        'to': "ALL",
        'text': message
    };
    stompClient.send("/app/chat", {}, JSON.stringify(outgoingMsg));
}

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

// let msg = {
//     // 'from': "Me",
//     'to': "pablo_esc",
//     'text': "Hi Pablo!"
// };



