import api.Joke;

/**
 * The MainGame class represents the main entry point for the Dad Jokes Guessing Game.
 * It initializes the UserInterface, creates a Joke object, and runs the game.
 */
public class MainGame {

    private static Joke joke;

    /**
     * The main method is the entry point for the Dad Jokes Guessing Game.
     * It creates a UserInterface, prints the game title, initializes a Joke object, and runs the game.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        UserInterface UI = new UserInterface();

        System.out.println("Are you a Dad?: The Ultimate Dad Jokes Guessing Game");
        System.out.println("Find out if you qualify to be a 'dad' by guessing the punchlines!");

        joke = new Joke();
        runGame(joke, UI);
    }

    /**
     * The runGame method runs the Dad Jokes Guessing Game.
     * It displays the current joke's question and answer, sets up the user interface,
     * and allows the user to guess the punchline.
     *
     * @param joke the current joke
     * @param ui   the user interface
     */
    public static void runGame(Joke joke, UserInterface ui) {
        System.out.println("Question:" + joke.getQuestion().strip());
        System.out.println("Answer:" + joke.getAnswer());
        ui.setGuesses();
        ui.nextRound();
        ui.setJokeText(joke.getQuestion());
        ui.setJokeOutline(joke.getAnswer());
        ui.userTyping(joke);
    }

    /**
     * The getCurrentJoke method returns the current joke.
     *
     * @return the current joke
     */
    public static Joke getCurrentJoke() {
        return joke;
    }

    /**
     * The newJoke method creates a new joke.
     */
    public static void newJoke() {
        joke = new Joke();
    }
}
