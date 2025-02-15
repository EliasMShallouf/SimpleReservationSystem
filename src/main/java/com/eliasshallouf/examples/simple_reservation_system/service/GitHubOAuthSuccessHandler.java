package com.eliasshallouf.examples.simple_reservation_system.service;

import com.eliasshallouf.examples.simple_reservation_system.domain.model.User;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.helpers.GitHubUser;
import com.eliasshallouf.examples.simple_reservation_system.service.db.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GitHubOAuthSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserService userService;

    private final DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException, ServletException {
        GitHubUser gitHubUser = (GitHubUser) authentication.getPrincipal();
        User user = userService.getUserByEmail(gitHubUser.getGithubEmail());

        String redirectUrl;
        if(user == null) {
            String query =
                UriUtils.encodeQueryParams(
                    new MultiValueMapAdapter<>(
                        Map.of(
                            "email", List.of(gitHubUser.getGithubEmail()),
                            "name", List.of(gitHubUser.getName())
                        )
                    )
                )
                .toSingleValueMap()
                .entrySet()
                .stream()
                .map(ent -> ent.getKey() + "=" + ent.getValue())
                .collect(Collectors.joining("&"));
            redirectUrl = "/pre-register?" + query;
        } else {
            request.getSession().setAttribute(GitHubUser.SESSION_TAG, user);
            redirectUrl = "/home";
        }

        redirectStrategy.setContextRelative(true);
        redirectStrategy.sendRedirect(request, response, redirectUrl);
    }
}
