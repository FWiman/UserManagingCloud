package com.Fredrik.oauth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    @RequestMapping("/")
    public String home() {
        return "home-page";
    }

    @RequestMapping("/admin")
        public Principal admin(Principal admin) {
            return admin;
        }
    }
