package com.Fredrik.oauth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "home-page";
    }


    @GetMapping("/profile")
    public String profile(OAuth2AuthenticationToken token, Model model) {
        String provider = token.getAuthorizedClientRegistrationId();
        model.addAttribute("provider", provider);
        model.addAttribute("name", token.getPrincipal().getAttribute("name"));
        model.addAttribute("email", token.getPrincipal().getAttribute("email"));
        model.addAttribute("picture", token.getPrincipal().getAttribute("picture"));

        return "user-profile";
    }

    @GetMapping("/login")
    public String loginPage(Authentication authentication) {
        return "custom-login";
    }

}
