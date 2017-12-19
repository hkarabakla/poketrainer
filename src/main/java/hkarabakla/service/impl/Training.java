package hkarabakla.service.impl;


import hkarabakla.constants.Messages;
import hkarabakla.model.Attack;
import hkarabakla.model.User;
import hkarabakla.service.Battle;
import hkarabakla.service.Printer;
import hkarabakla.service.SystemInitializer;

import java.util.Random;
import java.util.Scanner;

public class Training extends Battle {

    private int pokemonsEnergyBeforeTraining;

    public Training(User user, Printer printer, Scanner scanner, SystemInitializer systemInitializer, int trainingTeamSize) {
        this.user = user;
        this.printer = printer;
        this.systemInitializer = systemInitializer;
        this.scanner = scanner;
        this.random = new Random();
        this.attackChart = systemInitializer.getAttackChart();
        this.teamSize = trainingTeamSize;
    }

    /**
     * Prints battle result for training
     * @param usersScore int
     * @param enemysScore int
     */
    @Override
    protected void printBattleResults(int usersScore, int enemysScore) {
        printer.clearScreen();
        if (usersScore > enemysScore) {
            printer.println(String.format(Messages.USER_WINS_TRAINING_MESSAGE, user.getName()));
            printer.printCurrentStat(user, usersScore, competitor, enemysScore);
        } else {
            printer.println(String.format(Messages.COMPETITOR_WINS_TRAINING_MESSAGE, competitor.getName()));
            printer.printCurrentStat(competitor, enemysScore, user, usersScore);
        }
    }

    /**
     * Starts battle for training
     */
    @Override
    protected void fight() {

        while (!user.getBattleTeam().isEmpty() && !competitor.getBattleTeam().isEmpty()) {

            selectPokemonForCurrentBattle();
            selectPokemonForEnemyForCurrentBattle();
            printer.printBattleStarting(user, userPokemon, competitor, competitorPokemon, Messages.CURRENT_BATTLE);

            pokemonsEnergyBeforeTraining = userPokemon.getEnergy();

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
                                competitor.getBattleTeam().remove(competitorPokemon);
                                user.getBattleTeam().remove(userPokemon);
                                userScore++;
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

            // Increment pokemons energy after training +5 points
            userPokemon.setEnergy(pokemonsEnergyBeforeTraining + 5);
        }

        printBattleResults(userScore, competitorScore);
    }

    /**
     * Decides to finish battle for training
     * @return boolean
     */
    @Override
    protected boolean shouldFinishFightAfterUserAttack() {
        boolean shouldFinishFight = false;
        printer.println("");
        printer.println(Messages.SELECT_YOUR_ATTACK);
        printer.print(userPokemon.getAttacks());

        int attackInt = scanner.nextInt();
        Attack attack = userPokemon.getAttacks().get(attackInt);

        printer.println(String.format(Messages.X_MAKES_AN_ATTACK_ON_Y,
                user.getName(), userPokemon.getName(), attack.getName(),
                competitor.getName(), competitorPokemon.getName()));

        calculateEnergyPointsAfterAttack(userPokemon, competitorPokemon, attack);

        printer.printCurrentStat(user, userPokemon, competitor, competitorPokemon);

        if (competitorPokemon.getEnergy() <= 1) {
            competitor.getBattleTeam().remove(competitorPokemon);
            user.getBattleTeam().remove(userPokemon);
            userScore++;

            // Increment pokemon's energy after training extra +5 points for glory
            pokemonsEnergyBeforeTraining = pokemonsEnergyBeforeTraining + 5;
            userPokemon.setEnergy(pokemonsEnergyBeforeTraining);
            shouldFinishFight = true;
        }

        return shouldFinishFight;
    }
}
