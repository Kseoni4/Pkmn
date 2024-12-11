package kseoni.ch.pkmn.dao;

import kseoni.ch.pkmn.dto.UserEntityDto;
import kseoni.ch.pkmn.entities.AuthorityEntity;
import kseoni.ch.pkmn.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
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
