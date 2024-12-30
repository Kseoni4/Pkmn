package kseoni.ch.pkmn.shared.dto;

import kseoni.ch.pkmn.shared.entities.CardEntity;
import kseoni.ch.pkmn.shared.entities.StudentEntity;
import kseoni.ch.pkmn.shared.models.AttackSkill;
import kseoni.ch.pkmn.shared.models.EnergyType;
import kseoni.ch.pkmn.shared.models.PokemonStage;

import java.util.List;

/**
 * DTO for {@link CardEntity}
 */
public record CardEntityDto(String cardNumber, PokemonStage pokemonStage, String name, int hp, String evolvesFromName,
                            List<AttackSkill> skills, EnergyType weaknessType, EnergyType resistanceType,
                            String retreatCost, String gameSet, EnergyType pokemonType, char regulationMark, StudentEntity pokemonOwner) {
    @Override
    public String evolvesFromName() {
        return evolvesFromName == null ? "Nothing" : evolvesFromName;
    }

    public static CardEntityDto fromEntity(CardEntity entity){
        return new CardEntityDto(
                entity.getCardNumber(),
                entity.getPokemonStage(),
                entity.getName(),
                entity.getHp(),
                entity.getEvolvesFrom() == null ? "Nothing" : entity.getEvolvesFrom().getName(),
                entity.getSkills(),
                entity.getWeaknessType(),
                entity.getResistanceType(),
                entity.getRetreatCost(),
                entity.getGameSet(),
                entity.getPokemonType(),
                entity.getRegulationMark(),
                entity.getPokemonOwner()
        );
    }
}