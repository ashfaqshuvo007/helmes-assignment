package com.helmes.assignment.config;


import com.helmes.assignment.entity.models.MyUserDetails;
import com.helmes.assignment.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication) throws IOException {
        String redirectUrl = determineTargetUrl(authentication);

        response.sendRedirect(redirectUrl);
    }

    private String determineTargetUrl(Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Role role = userDetails.getRole();

        if (role == Role.ADMIN) {
            return "/admin/home";
        } else {
            return "/user/home";
        }
    }
}
