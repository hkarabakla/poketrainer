package hkarabakla.service;


import hkarabakla.constants.Messages;
import hkarabakla.enums.PokemonType;
import hkarabakla.model.Attack;
import hkarabakla.model.AttackReferance;
import hkarabakla.model.Pokemon;
import hkarabakla.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public abstract class Battle {

    protected Printer printer;
    protected Scanner scanner;
    protected Random random;
    protected SystemInitializer systemInitializer;
    protected User user;
    protected User competitor;
    protected Pokemon userPokemon;
    protected Pokemon competitorPokemon;
    protected int userScore = 0;
    protected int competitorScore = 0;
    protected Map<PokemonType, Map<PokemonType, AttackReferance>> attackChart;
    protected int teamSize;

    protected abstract void printBattleResults(int usersScore, int enemysScore);

    protected abstract void fight();

    protected abstract boolean shouldFinishFightAfterUserAttack();

    /**
     * Initializes the environment for fight
     * @param message String
     */
    public void initializeAndStart(String message) {
        printer.println(message);
        createCompetitorRandomlyForTheBattle();
        selectPokemonsForYourTeam(teamSize);
        fight();
    }

    /**
     * Removes attacks from the pokemons attack list which pokemon cannot afford
     * @param pokemon Pokemon
     */
    protected void removeAttacksWhichPokemonCannotAfford(Pokemon pokemon) {
        pokemon.getAttacks().removeIf(attack -> pokemon.getEnergy() < attack.getCost());
    }

    /**
     * Calculates the result of the fight and returns if there is a winner after competitor's attack
     * @return boolean
     */
    protected boolean shouldFinishFightAfterCompetitorsAttack() {

        boolean shouldFinishFight = false;
        int attackInt = random.nextInt(competitorPokemon.getAttacks().size());
        Attack attack = competitorPokemon.getAttacks().get(attackInt);

        printer.println(String.format(Messages.X_MAKES_AN_ATTACK_ON_Y,
                competitor.getName(), competitorPokemon.getName(), attack.getName(), user.getName(), userPokemon.getName()));

        calculateEnergyPointsAfterAttack(competitorPokemon, userPokemon, attack);

        printer.printCurrentStat(user, userPokemon, competitor, competitorPokemon);

        if (userPokemon.getEnergy() <= 1) {
            printer.println(String.format(Messages.FINISHING_FIGHT_MESSAGE,
                    competitor.getName(), competitorPokemon.getName(), user.getName(), userPokemon.getName()));

            competitor.getBattleTeam().remove(competitorPokemon);
            user.getBattleTeam().remove(userPokemon);
            competitorScore++;
            shouldFinishFight = true;
        }

        return shouldFinishFight;
    }

    /**
     * Increases the energy points of the fighter pokemons' due to applied attack's cost and damage
     * @param offensive Pokemon
     * @param defensive Pokemon
     * @param attack Attack
     */
    protected void calculateEnergyPointsAfterAttack(Pokemon offensive, Pokemon defensive, Attack attack) {
        double decreaseRatioOfLooser = attackChart.get(offensive.getType()).get(defensive.getType()).getAdvantage();
        offensive.setEnergy(offensive.getEnergy() - attack.getCost());
        defensive.setEnergy((int) (defensive.getEnergy() - attack.getDamage() * decreaseRatioOfLooser));
    }

    /**
     * Creates a competitor and select its pokemons randomly
     */
    private void createCompetitorRandomlyForTheBattle() {
        competitor = new User("Competitor", systemInitializer.getAllPokemons());
        selectPokemonsForCompetitorsTeam(teamSize);
    }

    /**
     * Selects pokemons randomly for the competitor
     * @param pokemonNumber int
     */
    private void selectPokemonsForCompetitorsTeam(int pokemonNumber) {
        for (int i = 0; i < pokemonNumber; i++) {
            Pokemon selected = competitor.getPokemons().get(random.nextInt(competitor.getPokemons().size()));
            competitor.getBattleTeam().add(selected);
        }
    }

    /**
     * Allows user to select pokemons for fight
     * @param pokemonNumber int
     */
    private void selectPokemonsForYourTeam(int pokemonNumber) {

        printer.println(Messages.CHOOSE_POKEMON_TEAM);
        List<Pokemon> tempList = new ArrayList<>(user.getPokemons());

        for (int i = 0; i < pokemonNumber; i++) {
            printer.println(tempList);
            printer.println(Messages.ENTER_YOUR_CHOICE);
            Pokemon selected = tempList.get(scanner.nextInt(tempList.size()));
            user.getBattleTeam().add(selected);
            tempList.remove(selected);
        }
    }

    /**
     * Allows user to select a pokemon for the next fight
     */
    protected void selectPokemonForCurrentBattle() {

        if (user.getBattleTeam().size() > 1) {
            printer.clearScreen();
            printer.println(Messages.CHOOSE_POKEMON_FOR_BATTLE);
            printer.println(user.getBattleTeam());
            this.userPokemon = user.getBattleTeam().get(scanner.nextInt());
        } else if (user.getBattleTeam().size() == 1) {
            this.userPokemon = user.getBattleTeam().get(0);
        }

    }

    /**
     * Selects a pokemon for the next fight randomly
     */
    protected void selectPokemonForEnemyForCurrentBattle() {
        this.competitorPokemon = competitor.getBattleTeam().get(random.nextInt(competitor.getBattleTeam().size()));
    }
}
