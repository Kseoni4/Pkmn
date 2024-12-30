package kseoni.ch.pkmn.shared.dto;

import kseoni.ch.pkmn.shared.entities.StudentEntity;

/**
 * DTO for {@link StudentEntity}
 */
public record StudentDto(String firstName, String surName, String familyName, String group) {
}