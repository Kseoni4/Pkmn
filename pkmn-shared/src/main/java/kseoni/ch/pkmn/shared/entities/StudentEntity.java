package kseoni.ch.pkmn.shared.entities;

import jakarta.persistence.*;
import kseoni.ch.pkmn.shared.models.Student;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String firstName;

    private String surName;

    private String familyName;

    @Column(name = "\"group\"")
    private String group;

    public static StudentEntity fromModel(Student student){
        return StudentEntity.builder()
                .id(UUID.randomUUID())
                .firstName(student.getFirstName())
                .surName(student.getSurName())
                .familyName(student.getFamilyName())
                .group(student.getGroup())
                .build();
    }
}
