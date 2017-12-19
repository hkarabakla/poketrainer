package hkarabakla.constants;

public abstract class Messages {

    public static final String CHOOSE_POKEMON_FOR_BATTLE = (char)27 + "[33m" + "Select your pokemon for battle";
    public static final String CHOOSE_POKEMON_TEAM = (char)27 + "[33m" + "Select your pokemons for team";
    public static final String TOURNAMENT_INTRO = (char)27 + "[32m" + "*********** Tournament is starting ***********";
    public static final String ENTER_YOUR_CHOICE = (char)27 + "[33m" + "Enter your choice : ";
    public static final String CURRENT_BATTLE = (char)27 + "[32m" + "********************** BATTLE STARTS ***********************";
    public static final String SPLASH_SCREEN_MESSAGE = (char)27 + "[32m" + "Welcome to the Pokemon League";
    public static final String ENTER_YOUR_NAME = (char)27 + "[33m" + "Enter your name : ";
    public static final String SELECT_YOUR_ATTACK = (char)27 + "[33m" + "Select your next attack :";
    public static final String MENU = (char)27 + "[33m" + "Make your choose for an action : ";
    public static final String MENU_TOURNAMENT = (char)27 + "[33m" + "0. Start a tournament";
    public static final String MENU_TOURNAMENT_UNAVAILABLE = (char)27 + "[31m" + "0. Start a tournament (Unavailable now)";
    public static final String MENU_TRAINEE = (char)27 + "[33m" + "1. Train your pokemons";
    public static final String MENU_CURRENT_SESSION = (char)27 + "[33m" + "2. Display current stats";
    public static final String MENU_SAVE_AND_EXIT = (char)27 + "[33m" + "3. Save session and exit";
    public static final String MENU_EXIT = (char)27 + "[31m" + "4. Exit";
    public static final String SAVE_AND_EXIT = (char)27 + "[32m" + "Your session saved and you will be able to resume your game. See you later :)";
    public static final String LOAD_LAST_SESSION_OR_START_NEW_ONE = (char)27 + "[33m" + "Do you one resume last session ? y/n";
    public static final String NOT_ENOUGH_POKEMON_FOR_TOURNAMENT = (char)27 + "[31m" + "You don't have enough number(" + SystemConstants.TOURNAMENT_TEAM_SIZE + ") of pokemons with at least 20 energy point to attend a tournament. You should train your pokemons.";
    public static final String X_MAKES_AN_ATTACK_ON_Y = (char)27 + "[32m" + "%s's %s makes an %s attack on %s's %s, current status :";
    public static final String FINISHING_FIGHT_MESSAGE = (char)27 + "[32m" + "%s's %s wins the battle against %s's %s and becomes entitled to get +1 more point !";
    public static final String USER_WINS_TOURNAMENT_MESSAGE = (char)27 + "[32m" + "%s wins the tournament :)";
    public static final String COMPETITOR_WINS_TOURNAMENT_MESSAGE = (char)27 + "[31m" + "%s wins the tournament :(";
    public static final String USER_WINS_TRAINING_MESSAGE = (char)27 + "[32m" + "%s wins the training :)";
    public static final String COMPETITOR_WINS_TRAINING_MESSAGE = (char)27 + "[31m" + "%s wins the training :(";
    public static final String POKEMON_LEVEL_UP_MESSAGE = (char)27 + "[32m" + "Congrats ! Your pokemon %s level ups : %s";
    public static final String TRAINING_INTRO = (char)27 + "[32m" + "*********** Training is starting ***********";;
    public static final String INVALID_CHOICE = (char)27 + "[33m" + "Please make a valid choice";
}
