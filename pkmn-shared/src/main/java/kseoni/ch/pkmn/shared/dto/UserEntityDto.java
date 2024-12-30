package kseoni.ch.pkmn.shared.dto;

import kseoni.ch.pkmn.shared.entities.UserEntity;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * DTO for {@link UserEntity}
 */
@Builder
public record UserEntityDto(String username, String password, Set<String> authorityAuthorities) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("get authorities");
        return authorityAuthorities.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}