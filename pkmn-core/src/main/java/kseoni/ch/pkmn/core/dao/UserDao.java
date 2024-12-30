package kseoni.ch.pkmn.core.dao;

import kseoni.ch.pkmn.core.CoreConfiguration;
import kseoni.ch.pkmn.shared.dto.UserEntityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Import(CoreConfiguration.class)
@Component
@RequiredArgsConstructor
public class UserDao {

    public final PasswordEncoder passwordEncoder;

    public final JdbcUserDetailsManager jdbcUserDetailsManager;

    public UserDetails saveUser(String username, String password, String authority) {

        password = passwordEncoder.encode(password);

        jdbcUserDetailsManager.createUser(
                UserEntityDto.builder()
                        .username(username)
                        .password(password)
                        .authorityAuthorities(Collections.singleton(authority))
                        .build());

        return jdbcUserDetailsManager.loadUserByUsername(username);
    }

    public UserEntityDto getUserByUsername(String username) {
        UserDetails user = jdbcUserDetailsManager.loadUserByUsername(username);
        return UserEntityDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorityAuthorities(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }

    public boolean existsUser(String username) {
        return jdbcUserDetailsManager.userExists(username);
    }
}
