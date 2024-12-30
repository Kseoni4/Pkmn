package kseoni.ch.pkmn.apiclient;

import lombok.Getter;

@Getter
public class PokemonTcgQuery {

    private String name;

    private String set;

    private String number;

    private String hp;

    private PokemonTcgQuery(){}

    public static PokemonTcgQuery builder(){
        return new PokemonTcgQuery();
    }

    public PokemonTcgQuery setName(String name) {
        this.name = name;
        return this;
    }

    public PokemonTcgQuery setSet(String set) {
        this.set = set;
        return this;
    }

    public PokemonTcgQuery setNumber(String number) {
        this.number = number;
        return this;
    }

    public PokemonTcgQuery setHp(String hp) {
        this.hp = hp;
        return this;
    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        sb.append("?q=");
        sb.append("name:").append("\"").append(name).append("\"");
        if (set != null) sb.append(" set:").append(set);
        if (number != null) sb.append(" number:").append(number);
        if (hp != null) sb.append(" hp:").append(hp);
        return sb.toString();
    }
}
