package com.nnk.springboot.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LogoutController {

    private static final Logger logger = LogManager.getLogger(LogoutController.class.getName());

    @PostMapping("app-logout")
    public RedirectView logout(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Requête de LogOut");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        RedirectView mav = new RedirectView("/login");
        return mav;
    }
    
}