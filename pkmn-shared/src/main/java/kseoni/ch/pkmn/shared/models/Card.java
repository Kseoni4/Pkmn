package kseoni.ch.pkmn.shared.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import kseoni.ch.pkmn.shared.converters.SkillDeserializer;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card implements Serializable {

    private PokemonStage pokemonStage;

    private String name;

    private int hp;

    private EnergyType energyType;

    private Card evolvesFrom;

    @JsonDeserialize(using = SkillDeserializer.class)
    private List<AttackSkill> skills;

    private EnergyType weaknessType;

    private EnergyType resistanceType;

    private String retreatCost;

    private String gameSet;

    private char regulationMark;

    @JsonProperty("owner")
    private Student pokemonOwner;

    private String cardNumber;

}
