package hkarabakla;


import hkarabakla.constants.Messages;
import hkarabakla.constants.SystemConstants;
import hkarabakla.model.LastSession;
import hkarabakla.model.User;
import hkarabakla.service.Battle;
import hkarabakla.service.Printer;
import hkarabakla.service.SystemInitializer;
import hkarabakla.service.impl.ConsolePrinter;
import hkarabakla.service.impl.FileBasedSystemInitializer;
import hkarabakla.service.impl.Tournament;
import hkarabakla.service.impl.Training;

import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Pokemon Trainer App
 */
public class App {

    static private SystemInitializer systemInitializer;
    static private Printer printer;
    static private User user;
    static private Scanner scanner;

    public static void main(String[] args) {

        scanner = new Scanner(System.in);
        printer = ConsolePrinter.getInstance();
        systemInitializer = FileBasedSystemInitializer.getInstance(printer);

        printer.println(Messages.SPLASH_SCREEN_MESSAGE);

        loadUsersLastSessionToResume();

        while (true) {

            int pokemonsSizeReadyToTournament = printMenuAndGetPokemonsNumberReadyToTournament();

            Battle battle;

            int usersMenuChoice = scanner.nextInt();
            if (usersMenuChoice == 0 && pokemonsSizeReadyToTournament >= SystemConstants.TOURNAMENT_TEAM_SIZE) {
                battle = new Tournament(user, printer, scanner, systemInitializer, SystemConstants.TOURNAMENT_TEAM_SIZE);
                battle.initializeAndStart(Messages.TOURNAMENT_INTRO);
            } else if (usersMenuChoice == 0) {
                printer.println(Messages.NOT_ENOUGH_POKEMON_FOR_TOURNAMENT);
            } else if (usersMenuChoice == 1) {
                battle = new Training(user, printer, scanner, systemInitializer, SystemConstants.TRAINING_TEAM_SIZE);
                battle.initializeAndStart(Messages.TRAINING_INTRO);
            } else if (usersMenuChoice == 2) {
                printer.println(user);
            } else if (usersMenuChoice == 3) {
                LastSession currentSession = new LastSession(user);
                systemInitializer.saveCurrentSession(currentSession);
                printer.println(Messages.SAVE_AND_EXIT);
                break;
            } else if (usersMenuChoice == 4) {
                break;
            } else {
                printer.println(Messages.INVALID_CHOICE);
            }
        }
    }

    /**
     * Prints the main menu for the user to select main action
     * 0 -> Allows user to start a new tournament, each tournament consist of three fights.
     * Tournaments require at least 3 pokemons with 20 energy point to attend
     * 1 -> Allows user to start training to improve pokemon's energy point
     * 2 -> Prints user's current status with energy status of pokemons
     * 3 -> Allows user to save its current status to resume later and exits
     * 4 -> Allows user to exit without saving
     *
     * @return Returns pokemon number of the user which has at least 20 energy point
     */
    private static int printMenuAndGetPokemonsNumberReadyToTournament() {

        int pokemonsSizeReadyToTournament = user
                .getPokemons()
                .stream()
                .filter(pokemon -> pokemon.getEnergy() >= SystemConstants.MIN_ENERGY_FOR_TOURNAMENT)
                .collect(Collectors.toList()).size();


        printer.println("");
        printer.println(Messages.MENU);
        if (pokemonsSizeReadyToTournament >= SystemConstants.TOURNAMENT_TEAM_SIZE) {
            printer.println(Messages.MENU_TOURNAMENT);
        } else {
            printer.println(Messages.MENU_TOURNAMENT_UNAVAILABLE);
        }
        printer.println(Messages.MENU_TRAINEE);
        printer.println(Messages.MENU_CURRENT_SESSION);
        printer.println(Messages.MENU_SAVE_AND_EXIT);
        printer.println(Messages.MENU_EXIT);
        return pokemonsSizeReadyToTournament;
    }

    /**
     * Loads user's last saved session from a json file
     */
    private static void loadUsersLastSessionToResume() {
        LastSession lastSession = systemInitializer.loadLastSession();
        Optional.ofNullable(lastSession).ifPresent(lastSession1 -> {
            printer.println(Messages.LOAD_LAST_SESSION_OR_START_NEW_ONE);
            String option = scanner.next();
            if (SystemConstants.YES.equals(option)) {
                user = lastSession1.getUser();
            } else {
                printer.print(Messages.ENTER_YOUR_NAME);
                String username = scanner.next();
                user = new User(username, systemInitializer.getAllPokemons());
            }
        });
    }
}
