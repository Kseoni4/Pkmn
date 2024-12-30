package ch.kseoni.pkmn.authserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.Objects;

@Slf4j
public class CustomOAuth2AuthorizationService extends JdbcOAuth2AuthorizationService {
    public CustomOAuth2AuthorizationService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
        super(jdbcOperations, registeredClientRepository);
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        log.info("Saving OAuth2Authorization");
        log.info("OAuth2Authorization attr {}",authorization.getAttributes());
        if(Objects.nonNull(authorization.getAccessToken())){
            log.info("OAuth2Authorization access tokem claims {}",authorization.getAccessToken().getClaims());
        }
        log.info("OAuth2Authorization scopes {}", authorization.getAuthorizedScopes());;

        super.save(authorization);
    }
}
