package hkarabakla.service.impl;


import hkarabakla.model.Attack;
import hkarabakla.model.Pokemon;
import hkarabakla.model.User;
import hkarabakla.service.Printer;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class ConsolePrinter implements Printer {

    private static ConsolePrinter consolePrinter = null;

    private ConsolePrinter() {
    }

    public static ConsolePrinter getInstance() {

        if(!Optional.ofNullable(consolePrinter).isPresent()) {
            consolePrinter = new ConsolePrinter();
        }
        return consolePrinter;
    }

    /**
     * Prints the str to the console
     * @param str String
     */
    @Override
    public void print(String str) {
        System.out.print(str);
    }

    /**
     * Prints the str to the console
     * @param str String
     */
    @Override
    public void println(String str) {
        System.out.println(str);
    }

    /**
     * Prints the strings to the console in the given format
     * @param format String
     * @param strings Object[]
     */
    @Override
    public void format(String format, Object... strings) {
        System.out.format(format, strings);
        System.out.println();
    }

    /**
     * Prints the str to the console with a delay before exit
     * @param str String
     * @param delay long
     * @param e Exception
     */
    @Override
    public void println(String str, long delay, Exception e) {
        println(str);
        println(e.getMessage());
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            println(ex.getMessage());
        }
    }

    /**
     * Prints the pokemon to the console
     * @param pokemon Pokemon
     */
    @Override
    public void println(Pokemon pokemon) {
        System.out.println(pokemon);
    }

    /**
     * Prints the pokemons to the console
     * @param pokemons List<Pokemon>
     */
    @Override
    public void println(List<Pokemon> pokemons) {
        IntStream.range(0, pokemons.size()).forEach(value -> {
            System.out.print(" " + value + ". ");
            println(pokemons.get(value));
        });
        println("");
    }

    /**
     * Prints the current fight status to the console
     * @param user1 User
     * @param pokemon1 Pokemon
     * @param user2 User
     * @param pokemon2 Pokemon
     */
    @Override
    public void printCurrentStat(User user1, Pokemon pokemon1, User user2, Pokemon pokemon2) {
        println("");
        String format = "|%1$-10s|%2$-10s|%3$-10s|";
        String formatHeader = "+%1$-10s+%2$-10s+%3$-10s+";
        String formatOuterBorder = "+%1$-30s+";

        format(formatOuterBorder, (char)27 + "[32m" + "--------------------------------");
        format(format, "Owner", "Pokemon", "Energy");
        format(formatHeader, "----------", "----------", "----------");
        format(format, user1.getName(), pokemon1.getName(), pokemon1.getEnergy());
        format(format, user2.getName(), pokemon2.getName(), pokemon2.getEnergy());
        format(formatOuterBorder, "--------------------------------");
        println("");
    }

    /**
     * Prints the fight result to the console
     * @param winner User
     * @param winnerScore int
     * @param looser User
     * @param looserScore int
     */
    @Override
    public void printCurrentStat(User winner, int winnerScore, User looser, int looserScore) {
        println("");
        String format = "|%1$-10s|%2$-10s|%3$-10s|";
        String formatHeader = "+%1$-10s+%2$-10s+%3$-10s+";
        String formatOuterBorder = "+%1$-30s+";

        format(formatOuterBorder, (char)27 + "[32m" + "--------------------------------");
        format(format, "Owner", "Score", "Result");
        format(formatHeader, "----------", "----------", "----------");
        format(format, winner.getName(), winnerScore, "**Winner**");
        format(format, looser.getName(), looserScore, "");
        println("");
    }

    /**
     * Prints the battle starting message to the console
     * @param user1 User
     * @param pokemon1 Pokemon
     * @param user2 User
     * @param pokemon2 Pokemon
     * @param message String
     */
    @Override
    public void printBattleStarting(User user1, Pokemon pokemon1, User user2, Pokemon pokemon2, String message) {
        println("");
        String format = "|%1$-27s  VS  %2$-27s|";
        String formatHeader = "|%1$-60s|";
        String formatOuterBorder = "+%1$-60s+";

        format(formatOuterBorder, (char)27 + "[32m" + "------------------------------------------------------------");
        format(formatHeader, message);
        format(formatOuterBorder, "------------------------------------------------------------");
        format(format, user1.getName() + "'s " + pokemon1.getName(), user2.getName() + "'s " + pokemon2.getName());
        format(formatOuterBorder, "------------------------------------------------------------");
        println("");
    }

    /**
     * Clears the console
     */
    @Override
    public void clearScreen() {
        // Clear differently based on operating system using appropriate command
        try {

            // Invoking console clearing properly thanks to StackOverflow
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }

        } catch (Exception e) {

            // If all else fails, println some blank lines!
            for (int i = 0; i < 40; i++) {
                System.out.println();
            }
        }
    }

    /**
     * Prints the attacks to the console
     * @param attacks List<Attack>
     */
    @Override
    public void print(List<Attack> attacks) {
        IntStream.range(0, attacks.size()).forEach(value -> {
            System.out.print(" " + value + ". ");
            println(attacks.get(value).toString());
        });
    }

    /**
     * Prints the user to the console
     * @param user User
     */
    @Override
    public void println(User user) {
        println(user.toString());
        println(user.getPokemons());

    }
}
