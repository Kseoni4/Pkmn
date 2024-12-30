package kseoni.ch.pkmn.shared.security.filters;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kseoni.ch.pkmn.shared.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @PostConstruct
    public void init() {
        log.info("Initializing JWT authentication filter");
        String token = jwtService.createDummyToken();
        log.info("Generated JWT token: {}", token);

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if ((token == null || !token.startsWith("Bearer ")) && !Objects.isNull(request.getCookies())) {
            token = parseFromCookies(request);
        }

        log.debug("token is {}", token);

        if (Objects.isNull(token) || token.isEmpty() || token.startsWith("Basic ")) {
            filterChain.doFilter(request, response);
            return;
        }
        if(!token.startsWith("Bearer ")) {
            token = new String(Base64.getDecoder().decode(token));
        }

        String jwt = token.substring(7);

        DecodedJWT decodedJWT = jwtService.verify(jwt);

        if (Objects.isNull(decodedJWT)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = decodedJWT.getSubject();

        Claim authorityList = decodedJWT.getClaim("authority");

        Collection<? extends GrantedAuthority> authorities = authorityList.asList(SimpleGrantedAuthority.class);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                authorities
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Authenticated with JWT");

        filterChain.doFilter(request, response);
    }

    private String parseFromCookies(HttpServletRequest request){
        String token = "";
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("Authorization")) {
                token = cookie.getValue();
                break;
            }
        }
        return token;
    }
}
