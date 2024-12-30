package kseoni.ch.pkmn.shared.models.requests;

import kseoni.ch.pkmn.shared.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO for {@link UserEntity}
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    private String username;
    private String password;
}