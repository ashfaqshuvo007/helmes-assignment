package com.helmes.assignment.config;

import com.helmes.assignment.services.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
public class SecurityConfiguration {

    private final MyUserDetailService myUserDetailService;


    public SecurityConfiguration(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(
            authorize-> {
                authorize.requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**" ).permitAll();
                authorize.requestMatchers("/login","/logout","/error/**").permitAll();
                // Restrict access to admin and user pages based on roles
                authorize.requestMatchers("/admin/**").hasRole("ADMIN");
                authorize.requestMatchers("/user/**").hasRole("USER");
                authorize.anyRequest().authenticated();
            }
        ).formLogin(login -> login.loginPage("/login").successHandler(customAuthenticationSuccessHandler()).permitAll())
            .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailService() {
        return myUserDetailService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationHandler();
    }
}
