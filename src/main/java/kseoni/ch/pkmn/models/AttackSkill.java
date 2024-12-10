package kseoni.ch.pkmn.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import kseoni.ch.pkmn.converters.SkillDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = SkillDeserializer.class)
public class AttackSkill implements Serializable {

    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private String cost;

    @JsonProperty
    private int damage;

}
