package kseoni.ch.pkmn.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kseoni.ch.pkmn.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/success")
@RequiredArgsConstructor
public class SuccessController {

    private final JwtService jwtService;

    @PostMapping
    public RedirectView successLogin(HttpServletResponse response, Authentication authentication){
        System.out.println("Name: "+authentication.getName());
        System.out.println("Authorities: "+authentication.getAuthorities());
        System.out.println("Credentials: "+authentication.getCredentials());
        System.out.println("Details: "+authentication.getDetails());

        String cookieValue = "Bearer " + jwtService.createToken(authentication.getName(), authentication.getAuthorities());

        System.out.println("Cookie: "+cookieValue);

        String encodedValue = Base64.getEncoder().encodeToString(cookieValue.getBytes(StandardCharsets.UTF_8));

        System.out.println("Encoded value: "+encodedValue);

        response.addCookie(new Cookie("Authorization", encodedValue));

        response.addHeader("jwt", cookieValue);

        return new RedirectView("/hello");
    }
}
