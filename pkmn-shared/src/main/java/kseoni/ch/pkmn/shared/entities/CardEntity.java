package kseoni.ch.pkmn.shared.entities;

import kseoni.ch.pkmn.shared.converters.SkillDeserializer;
import kseoni.ch.pkmn.shared.models.AttackSkill;
import kseoni.ch.pkmn.shared.models.Card;
import kseoni.ch.pkmn.shared.models.EnergyType;
import kseoni.ch.pkmn.shared.models.PokemonStage;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "CardEntity")
@Table(name = "cards")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String cardNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "stage")
    private PokemonStage pokemonStage;

    private String name;

    private int hp;

    @OneToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn
    private CardEntity evolvesFrom;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attack_skills")
    @JsonDeserialize(using = SkillDeserializer.class)
    private List<AttackSkill> skills;

    @Enumerated(EnumType.STRING)
    private EnergyType weaknessType;

    @Enumerated(EnumType.STRING)
    private EnergyType resistanceType;

    private String retreatCost;

    private String gameSet;

    @Enumerated(EnumType.STRING)
    private EnergyType pokemonType;

    private char regulationMark;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pokemon_owner_id", referencedColumnName = "id")
    private StudentEntity pokemonOwner;

    public static CardEntity fromModel(Card card) {
        return CardEntity.builder()
                .cardNumber(card.getCardNumber())
                .pokemonStage(card.getPokemonStage())
                .name(card.getName())
                .hp(card.getHp())
                .evolvesFrom(card.getEvolvesFrom() == null ? null : CardEntity.fromModel(card.getEvolvesFrom()))
                .skills(card.getSkills())
                .weaknessType(card.getWeaknessType())
                .resistanceType(card.getResistanceType())
                .retreatCost(card.getRetreatCost())
                .gameSet(card.getGameSet())
                .pokemonType(card.getEnergyType())
                .regulationMark(card.getRegulationMark())
                .pokemonOwner(card.getPokemonOwner() == null ? null : StudentEntity.fromModel(card.getPokemonOwner()))
                .build();
    }
}