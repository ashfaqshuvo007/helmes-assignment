package com.helmes.assignment.unit;

import com.helmes.assignment.config.CustomAuthenticationHandler;
import com.helmes.assignment.entity.models.MyUserDetails;
import com.helmes.assignment.enums.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.*;

class AuthenticationHandlerTests {

    private CustomAuthenticationHandler authenticationHandler;

    @Mock
    private Authentication authentication;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticationHandler = new CustomAuthenticationHandler();// Initialize mocks
    }

    @Test
    void testRedirectToAdminHome() throws IOException, ServletException {
        MyUserDetails userDetails = new MyUserDetails("John", "123456", Role.ADMIN);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        // Mock the authorities
        when(authentication.getAuthorities())
            .thenReturn((Collection) Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        // Mock the response behavior
        doNothing().when(response).sendRedirect("/admin/home");

        // Call the handler
        authenticationHandler.onAuthenticationSuccess(request, response, authentication);

        // Verify redirection
        verify(response, times(1)).sendRedirect("/admin/home");
    }

    @Test
    void testRedirectToUserHome() throws IOException, ServletException {
        MyUserDetails userDetails = new MyUserDetails("John", "123456", Role.USER);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Mock the authentication with USER role
        when(authentication.getAuthorities())
            .thenReturn((Collection) Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        // Mock the response behavior
        doNothing().when(response).sendRedirect("/user/home");

        // Call the handler
        authenticationHandler.onAuthenticationSuccess(request, response, authentication);

        // Verify redirection
        verify(response, times(1)).sendRedirect("/user/home");
    }

}
