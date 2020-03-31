package com.example.chatroom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String getWelcomePage() {
        return "index";
    }

    @GetMapping("/chatroom")
    public String getChatRoot() {

        return "chatroom";
    }

    @PostMapping("/addNickname")
    public String addNote(@RequestParam("nicknameInput") String nickname, HttpServletRequest request) {
        request.getSession().setAttribute("NICKNAME", nickname);
        return "redirect:/chatroom";
    }
}
