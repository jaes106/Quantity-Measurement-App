package com.bridgelabz.auth;

import com.bridgelabz.user.entity.User;
import com.bridgelabz.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository repository;
    private final JwtService jwtService;


    public OAuth2LoginSuccessHandler(
            JwtService jwtService,
            UserRepository repository) {

        this.jwtService = jwtService;
        this.repository = repository;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oauthUser =
                (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        User user = repository.findByEmail(email)
                .orElseGet(() ->
                        repository.save(
                                new User(
                                        name,
                                        email,
                                        "GOOGLE"
                                )
                        )
                );

        String token = jwtService.generateToken(user.getEmail());

        response.setContentType("application/json");

        response.getWriter().write(
                "{ \"token\": \"" + token + "\" }"
        );
    }
}