package hkarabakla.model;


import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private List<Pokemon> pokemons;
    private List<Pokemon> battleTeam;

    public User() {
    }

    public User(String name, List<Pokemon> pokemons) {
        this.name = name;
        this.pokemons = pokemons;
        this.battleTeam = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public List<Pokemon> getBattleTeam() {
        return battleTeam;
    }

    @Override
    public String toString() {
        return "name=" + name;
    }
}
