package org.ideacloud.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping("/")
    public String index() {
        return "home";
    }


    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }
}
