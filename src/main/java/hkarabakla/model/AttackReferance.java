package hkarabakla.model;

import hkarabakla.enums.PokemonType;

public class AttackReferance {

    private PokemonType defensive;
    private PokemonType offensive;
    private double advantage;
    private double disadvantage;

    public AttackReferance() {
    }

    public PokemonType getDefensive() {
        return defensive;
    }

    public void setDefensive(PokemonType defensive) {
        this.defensive = defensive;
    }

    public PokemonType getOffensive() {
        return offensive;
    }

    public void setOffensive(PokemonType offensive) {
        this.offensive = offensive;
    }

    public double getAdvantage() {
        return advantage;
    }

    public void setAdvantage(double advantage) {
        this.advantage = advantage;
    }

    public double getDisadvantage() {
        return disadvantage;
    }

    public void setDisadvantage(double disadvantage) {
        this.disadvantage = disadvantage;
    }
}
