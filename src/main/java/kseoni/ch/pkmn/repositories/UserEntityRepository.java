package kseoni.ch.pkmn.repositories;

import kseoni.ch.pkmn.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {
}