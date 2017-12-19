package hkarabakla.service.impl;


import hkarabakla.constants.Messages;
import hkarabakla.constants.SystemConstants;
import hkarabakla.model.Attack;
import hkarabakla.model.Pokemon;
import hkarabakla.model.User;
import hkarabakla.service.Battle;
import hkarabakla.service.Printer;
import hkarabakla.service.SystemInitializer;

import java.util.Random;
import java.util.Scanner;

public class Tournament extends Battle {

    public Tournament(User user, Printer printer, Scanner scanner, SystemInitializer systemInitializer, int pokemonSize) {
        this.user = user;
        this.printer = printer;
        this.systemInitializer = systemInitializer;
        this.scanner = scanner;
        this.random = new Random();
        this.attackChart = systemInitializer.getAttackChart();
        this.teamSize = pokemonSize;
    }

    @Override
    protected void printBattleResults(int usersScore, int enemysScore) {

        printer.clearScreen();
        if (usersScore > enemysScore) {
            printer.println(String.format(Messages.USER_WINS_TOURNAMENT_MESSAGE, user.getName()));
            printer.printCurrentStat(user, usersScore, competitor, enemysScore);
        } else {
            printer.println(String.format(Messages.COMPETITOR_WINS_TOURNAMENT_MESSAGE, competitor.getName()));
            printer.printCurrentStat(competitor, enemysScore, user, usersScore);
        }
    }

    @Override
    protected void fight() {

        while (!user.getBattleTeam().isEmpty() && !competitor.getBattleTeam().isEmpty()) {

            selectPokemonForCurrentBattle();
            selectPokemonForEnemyForCurrentBattle();
            printer.printBattleStarting(user, userPokemon, competitor, competitorPokemon, Messages.CURRENT_BATTLE);

            while (userPokemon.getEnergy() > 0 || competitorPokemon.getEnergy() > 0) {

                //Decide who will make an attack randomly
                boolean willUserAttackFirst = random.nextBoolean();
                if (willUserAttackFirst) {
                    removeAttacksWhichPokemonCannotAfford(userPokemon);

                    if (!userPokemon.getAttacks().isEmpty()) {
                        if (shouldFinishFightAfterUserAttack()) {
                            break;
                        }
                    } else {
                        removeAttacksWhichPokemonCannotAfford(competitorPokemon);

                        if (!competitorPokemon.getAttacks().isEmpty()) {
                            if (shouldFinishFightAfterCompetitorsAttack()) {
                                break;
                            }
                        }
                    }
                } else {
                    removeAttacksWhichPokemonCannotAfford(competitorPokemon);

                    if (!competitorPokemon.getAttacks().isEmpty()) {
                        if (shouldFinishFightAfterCompetitorsAttack()) {
                            break;
                        }
                    } else {
                        removeAttacksWhichPokemonCannotAfford(userPokemon);

                        if (!userPokemon.getAttacks().isEmpty()) {
                            if (shouldFinishFightAfterUserAttack()) {
                                break;
                            }
                        } else {
                            if (userPokemon.getEnergy() < competitorPokemon.getEnergy()) {
                                printer.println(String.format(Messages.FINISHING_FIGHT_MESSAGE,
                                        user.getName(), userPokemon.getName(), competitor.getName(),
                                        competitorPokemon.getName()));
                                competitor.getBattleTeam().remove(competitorPokemon);
                                user.getBattleTeam().remove(userPokemon);
                                userScore++;
                                incrementPokemonsLevelScore(userPokemon);
                                break;
                            } else {
                                competitor.getBattleTeam().remove(competitorPokemon);
                                user.getBattleTeam().remove(userPokemon);
                                competitorScore++;
                                break;
                            }
                        }
                    }
                }
            }
        }

        printBattleResults(userScore, competitorScore);
    }

    @Override
    protected boolean shouldFinishFightAfterUserAttack() {

        boolean shouldFinishFight = false;
        printer.println("");
        printer.println(Messages.SELECT_YOUR_ATTACK);
        printer.print(userPokemon.getAttacks());

        int attackInt = scanner.nextInt();
        Attack attack = userPokemon.getAttacks().get(attackInt);

        printer.println(String.format(Messages.X_MAKES_AN_ATTACK_ON_Y,
                user.getName(), userPokemon.getName(), attack.getName(), competitor.getName(),
                competitorPokemon.getName()));

        calculateEnergyPointsAfterAttack(userPokemon, competitorPokemon, attack);

        printer.printCurrentStat(user, userPokemon, competitor, competitorPokemon);

        if (competitorPokemon.getEnergy() <= 1) {
            printer.println(String.format(Messages.FINISHING_FIGHT_MESSAGE,
                    user.getName(), userPokemon.getName(), competitor.getName(), competitorPokemon.getName()));

            competitor.getBattleTeam().remove(competitorPokemon);
            user.getBattleTeam().remove(userPokemon);
            userScore++;
            incrementPokemonsLevelScore(userPokemon);
            shouldFinishFight = true;
        }

        return shouldFinishFight;
    }

    /**
     * Incremets the pokemons level after winning a tournament
     * @param pokemon Pokemon
     */
    private void incrementPokemonsLevelScore(Pokemon pokemon) {
        pokemon.getLevel().setScore(pokemon.getLevel().getScore() + SystemConstants.LEVEL_SCORE_INCREMENTER);

        if (pokemon.getLevel().getScore() >= pokemon.getLevel().getMaxScore()) {
            pokemon.getLevel().setLevel(pokemon.getLevel().getLevel() + SystemConstants.LEVEL_INCREMENTER);
            pokemon.setPower(pokemon.getPower() + SystemConstants.POWER_INCREMENTER);
            pokemon.setEnergy(pokemon.getPower());
            printer.println(String.format(Messages.POKEMON_LEVEL_UP_MESSAGE, pokemon.getName(), pokemon.getLevel()));
        }
    }

}
