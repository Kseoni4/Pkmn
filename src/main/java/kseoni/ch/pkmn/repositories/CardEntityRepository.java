package kseoni.ch.pkmn.repositories;

import kseoni.ch.pkmn.entities.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardEntityRepository extends JpaRepository<CardEntity, UUID> {
    @Query("""
        SELECT c
        FROM CardEntity c
        WHERE c.pokemonOwner.firstName = :firstName
              AND c.pokemonOwner.surName = :surName
              AND c.pokemonOwner.familyName = :familyName
    """)
    Optional<CardEntity> findByPokemonOwner(String firstName, String surName, String familyName);

    <T> List<T> findAllByName(String name, Class<T> type);
}