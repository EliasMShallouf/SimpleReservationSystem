package com.eliasshallouf.examples.simple_reservation_system;

import com.eliasshallouf.examples.simple_reservation_system.service.GitHubOAuthSuccessHandler;
import com.eliasshallouf.examples.simple_reservation_system.service.GitHubOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class AppSecurity {
    @Autowired
    private GitHubOAuthService githubOAuthService;

    @Autowired
    private GitHubOAuthSuccessHandler gitHubOAuthSuccessHandler;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )
            .exceptionHandling(e -> e
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .maximumSessions(1)
            )
            .oauth2Login(httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
                .loginPage("/")
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(githubOAuthService))
                .successHandler(gitHubOAuthSuccessHandler)
            )
            .build();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}