package hkarabakla.service;



import hkarabakla.model.Attack;
import hkarabakla.model.Pokemon;
import hkarabakla.model.User;

import java.util.List;

public interface Printer {

    /**
     * Prints the given string str
     * @param str String
     */
    void print(String str);

    /**
     * Prints the given string str with new line character at the end
     * @param str String
     */
    void println(String str);

    /**
     * Prints the given string str and exception message of e, then make a thread sleep by given delay before exit
     * @param str String
     * @param delay long
     * @param e Exception
     */
    void println(String str, long delay, Exception e);

    /**
     * Prints the given pokemon
     * @param pokemon Pokemon
     */
    void println(Pokemon pokemon);

    /**
     * Prints the given pokemon list
     * @param pokemons List<Pokemon>
     */
    void println(List<Pokemon> pokemons);

    /**
     * Prints the current status of the fight after each attack
     * @param user1 User
     * @param pokemon1 Pokemon
     * @param user2 User
     * @param pokemon2 Pokemon
     */
    void printCurrentStat(User user1, Pokemon pokemon1, User user2, Pokemon pokemon2);

    /**
     * Prints the winning status of the finished fight
     * @param winner User
     * @param winnerScore int
     * @param looser User
     * @param looserScore int
     */
    void printCurrentStat(User winner, int winnerScore, User looser, int looserScore);

    /**
     * Prints the fight starting message
     * @param user1 User
     * @param pokemon1 Pokemon
     * @param user2 User
     * @param pokemon2 Pokemon
     * @param message String
     */
    void printBattleStarting(User user1, Pokemon pokemon1, User user2, Pokemon pokemon2, String message);

    /**
     * Clears the screen
     */
    void clearScreen();

    /**
     * Prints the given attack list before choosing attack by the user
     * @param attacks List<Attack>
     */
    void print(List<Attack> attacks);

    /**
     * Prints the current status of the given user
     * @param user User
     */
    void println(User user);

    /**
     * Prints the given strings in the given format
     * @param format String
     * @param strings Object[]
     */
    void format(String format, Object... strings);
}
