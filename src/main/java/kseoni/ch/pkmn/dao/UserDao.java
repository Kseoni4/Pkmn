package kseoni.ch.pkmn.dao;

import kseoni.ch.pkmn.dto.UserEntityDto;
import kseoni.ch.pkmn.entities.AuthorityEntity;
import kseoni.ch.pkmn.entities.UserEntity;
import kseoni.ch.pkmn.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserDao {

    private final UserEntityRepository userEntityRepository;

    public void saveUser(UserEntity userEntity) {
        userEntityRepository.save(userEntity);
    }

    public UserEntityDto getUserByUsername(String username) {
        UserEntity user = userEntityRepository.findById(username).orElse(null);
        return UserEntityDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorityAuthorities(user.getAuthorities()
                        .stream()
                        .map(AuthorityEntity::getAuthority)
                        .collect(Collectors.toSet())
                )
                .build();
    }

    public boolean existsUser(String username) {
        return userEntityRepository.existsById(username);
    }
}
