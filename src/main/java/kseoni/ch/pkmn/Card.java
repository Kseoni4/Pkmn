package kseoni.ch.pkmn;

import java.io.Serializable;
import java.util.List;

public class Card implements Serializable {

    private PokemonStage pokemonStage;

    private String name;

    private int hp;

    private EnergyType energyType;

    private Card evolvesFrom;

    private List<AttackSkill> skills;

    private EnergyType weaknessType;

    private EnergyType resistanceType;

    private String retreatCost;

    private String gameSet;

    private char regulationMark;

    private Student pokemonOwner;

}
