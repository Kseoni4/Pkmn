package kseoni.ch.pkmn.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.annotation.PostConstruct;
import kseoni.ch.pkmn.dao.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtService {

    @Value("${token.secret}")
    private String SECRET_KEY;

    @Value("${token.expiration}")
    private long TOKEN_EXPIRATION_MINUTES;

    private Algorithm algorithm;

    private final UserDao userDao;

    @PostConstruct
    public void init(){
        algorithm = Algorithm.HMAC512(SECRET_KEY);
    }
    
    public String createDummyToken(){
        return JWT.create()
                .withIssuer("pkmn")
                .withSubject("admin")
                .withClaim("authority", "[ROLE_ADMIN]")
                .withExpiresAt(Instant.now().plus(TOKEN_EXPIRATION_MINUTES, ChronoUnit.MINUTES))
                .sign(algorithm);
    }

    public String createToken(String username, GrantedAuthority authority){
        return JWT.create()
                .withIssuer("pkmn")
                .withSubject(username)
                .withClaim("authority", authority.getAuthority())
                .withExpiresAt(LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_MINUTES).toInstant(ZoneOffset.UTC))
                .sign(algorithm);
    }

    public DecodedJWT verify(String jwt){
        try {
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .withIssuer("pkmn")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(jwt);

            log.info("JWT expires at {}", decodedJWT.getExpiresAt());

            if(!userDao.existsUser(decodedJWT.getSubject())){
                throw new UsernameNotFoundException("Subject " +decodedJWT.getSubject() + " not found");
            }

            return decodedJWT;
        } catch (JWTVerificationException e) {
            log.error("Error while verifying {}", e.getMessage());
            return null;
        }
    }
}
