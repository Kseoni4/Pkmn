package kseoni.ch.pkmn.repositories;

import kseoni.ch.pkmn.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentEntityRepository extends JpaRepository<StudentEntity, UUID> {
}