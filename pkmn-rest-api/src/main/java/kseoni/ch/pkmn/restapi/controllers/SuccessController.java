package kseoni.ch.pkmn.restapi.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kseoni.ch.pkmn.shared.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/success")
@RequiredArgsConstructor
public class SuccessController {

    private final JwtService jwtService;

    @PostMapping
    public RedirectView successLogin(HttpServletResponse response, Authentication authentication){

        log.info("Success login {}", authentication.getPrincipal());

        List<? extends GrantedAuthority> authorityList = authentication.getAuthorities().stream().toList();

        String cookieValue = "Bearer " + jwtService.createToken(authentication.getName(), authorityList.get(0));

        log.info("Cookie: {}",cookieValue);

        String encodedValue = Base64.getEncoder().encodeToString(cookieValue.getBytes(StandardCharsets.UTF_8));

        log.info("Encoded value: {}",encodedValue);

        response.addCookie(new Cookie("jwt", encodedValue));

        response.addHeader("Authorization", cookieValue);

        return new RedirectView("/hello");
    }
}
