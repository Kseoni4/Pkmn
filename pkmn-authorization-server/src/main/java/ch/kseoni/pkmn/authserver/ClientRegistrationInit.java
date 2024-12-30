package ch.kseoni.pkmn.authserver;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ClientRegistrationInit {

    private final RegisteredClientRepository jdbcRegisteredClientRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${vaadin-client.redirect-url}")
    private String vaadinClientRedirectUrl;

    @PostConstruct
    public void init() {
        if (Objects.nonNull(jdbcRegisteredClientRepository.findByClientId("pkmn-vaadin-client"))) {
            return;
        }

        jdbcRegisteredClientRepository.save(
                RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId("pkmn-vaadin-client")
                        .clientSecret(passwordEncoder.encode("secret"))
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantTypes(a ->
                                {
                                    a.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                                    a.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
                                })
                        .redirectUris(o -> o.add("http://"+vaadinClientRedirectUrl+":8080/login/oauth2/code/pkmn-vaadin-client")
                        )
                        .postLogoutRedirectUri("http://"+vaadinClientRedirectUrl+":8080")
                        .scopes(o -> {
                            o.add("openid");
                            o.add("profile");
                        })
                        .tokenSettings(TokenSettings.builder().reuseRefreshTokens(true).build())
                        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                        .build());
    }
}