package hkarabakla.model;


import hkarabakla.enums.PokemonType;

import java.util.List;

public class Pokemon {

    private String name;
    private PokemonType type;
    private PokemonLevel level;
    private int power;
    private int energy;
    private List<Attack> attacks;

    public Pokemon() {
    }

    public Pokemon(PokemonType type, String name, PokemonLevel level, int power, int energy, List<Attack> attacks) {
        this.type = type;
        this.name = name;
        this.level = level;
        this.power = power;
        this.energy = energy;
        this.attacks = attacks;
    }

    public PokemonType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public PokemonLevel getLevel() {
        return level;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    @Override
    public String toString() {
        return name +
                ", type=" + type +
                ", level=" + level +
                ", power=" + power +
                ", energy=" + energy;
    }
}
