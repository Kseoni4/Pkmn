package kseoni.ch.pkmn.shared.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class AuthorityEntity {
    @Id
    @Column(name = "authority", nullable = false, length = 50)
    private String authority;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "username", nullable = false)
    private UserEntity username;

}