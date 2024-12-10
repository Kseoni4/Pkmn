package kseoni.ch.pkmn.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.annotation.PostConstruct;
import kseoni.ch.pkmn.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtService {

    @Value("${token.secret}")
    private String SECRET_KEY;

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
                .withExpiresAt(LocalDateTime.now().plusMinutes(5).toInstant(ZoneOffset.UTC))
                .sign(algorithm);
    }

    public String createToken(String username, Collection<? extends GrantedAuthority> authority){
        return JWT.create()
                .withIssuer("pkmn")
                .withSubject(username)
                .withClaim("authority", authority.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .withExpiresAt(LocalDateTime.now().plusMinutes(5).toInstant(ZoneOffset.UTC))
                .sign(algorithm);
    }

    public DecodedJWT verify(String jwt){
        try {
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .withIssuer("pkmn")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(jwt);

            if(!userDao.existsUser(decodedJWT.getSubject())){
                throw new UsernameNotFoundException("Subject " +decodedJWT.getSubject() + " not found");
            }

            return decodedJWT;
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(e.getMessage());
        }
    }
}
