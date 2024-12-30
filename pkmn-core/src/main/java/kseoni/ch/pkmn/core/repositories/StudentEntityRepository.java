package kseoni.ch.pkmn.core.repositories;

import kseoni.ch.pkmn.shared.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentEntityRepository extends JpaRepository<StudentEntity, UUID> {
}