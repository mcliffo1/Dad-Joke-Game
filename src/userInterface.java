import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import api.Joke;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.ui.Button;


/**
 * The UserInterface class represents the graphical user interface of the dad joke game. It handles
 * user input, displays jokes, tracks rounds and guesses, and manages UI assets.
 */
public class UserInterface {

    private final int CANVAS_WIDTH = 600;
    private final int CANVAS_HEIGHT = 800;

    private CanvasWindow canvas;
    private Button startButton;
    private GraphicsText round;
    private GraphicsText guesses;
    private GraphicsText jokeGraphicsText;
    private GraphicsText jokeOutlineText;
    private GraphicsText userInputText;
    private GraphicsText correctIncorrect;
    private GraphicsText score;
    private int roundNumber = 0;
    private int theInt = 0;
    private int guessesRemaining;
    private String jokeText;
    private String jokeOutline;
    private static String userText = "";
    private boolean running;
    private Image peterGriffin;
    private Image homeScreen;
    private Image lossScreen;
    private HintLetters hint;
    private Blinker userInputBlinker;

    /**
     * The UserInterface class represents the graphical user interface of the application. It provides
     * methods for initializing the home screen and creating a blinker for user input.
     */
    public UserInterface() {
        canvas = new CanvasWindow("dadjoke", CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setBackground(Color.GRAY);
        initHomeScreen();

        userInputBlinker = new Blinker(this);
        userInputBlinker.createBlinker();
    }

    /**
     * Handles user typing input for the joke game.
     * 
     * @param joke The joke object representing the current joke being played.
     */
    public void userTyping(Joke joke) {
        ArrayList<Key> acceptableList = new ArrayList<Key>(
            Arrays.asList(
                Key.A, Key.B, Key.C, Key.D, Key.E, Key.F, Key.G,
                Key.H, Key.I, Key.J, Key.K, Key.L, Key.M, Key.N,
                Key.O, Key.P, Key.Q, Key.R, Key.S, Key.T, Key.U,
                Key.V, Key.W, Key.X, Key.Y, Key.Z));
        canvas.onKeyDown(n -> {
            if (n.getKey() == Key.DELETE_OR_BACKSPACE) {
                removeUserInput();
            } else if (n.getKey() == Key.RETURN_OR_ENTER) {
                roundWinLoss(joke);
            } else if (userText.length() >= jokeOutline.length() - 1) {
            } else if (acceptableList.contains(n.getKey()) && userText.length() > 1
                && jokeOutline.charAt(userText.length()) == ' ' && theInt % 2 == 0) {
                addUserInput(" " + n.getKey().toString());
            } else if (acceptableList.contains(n.getKey()) && userText.length() > 1
                && jokeOutline.charAt(userText.length()) == ' ') {
                addUserInput(" " + n.getKey().toString());
                plusOne();
            } else if (acceptableList.contains(n.getKey())) {
                addUserInput(n.getKey().toString());
            }
            System.out.println(userText);
            System.out.println(jokeOutline);
            System.out.println(jokeOutlineText.getText());
            userInputBlinker.updateBlinkerPosition();
        });
    }

    /**
     * Checks if the user's answer matches the answer of a given joke.
     * 
     * @param joke The joke object to compare the user's answer against.
     * @return true if the user's answer matches the joke's answer, false otherwise.
     */
    public boolean answerCheck(Joke joke) {
        System.out.println(joke.getAnswer());
        System.out.println(userText);
        String cleanUserText = userText.replaceAll("\\s+", "");
        String cleanJokeAnswer = joke.getAnswer().replaceAll("[\\s'!?.,;-]", "");
        return cleanUserText.equalsIgnoreCase(cleanJokeAnswer); // placeholder
    }

    /**
     * Resets the user interface to its initial state. Clears the user text, generates a new joke, and
     * updates the display with the new joke. Also resets the guesses, clears the hint maps, and
     * initializes the hint letters.
     */
    public void reset() {
        userText = "";
        plusOne();
        MainGame.newJoke();
        Joke joke = MainGame.getCurrentJoke();
        setJokeText(joke.getQuestion());
        setJokeOutline(joke.getAnswer());
        userInputText.setPosition(jokeOutlineText.getX(), 297);
        userInputText.setText(userText);
        setGuesses();
        hint.clearMaps();
        hint.getGroup().removeAll();
        hint.clearCanvas(canvas);
        hint.initLetters(canvas);
    }

    /**
     * Updates the game state after a round is won or lost. Decreases the number of remaining guesses
     * and displays appropriate screens and messages. If the player runs out of guesses, a loss screen
     * is displayed. If the player guesses correctly, a success message is displayed and the game
     * progresses to the next round. If the player guesses incorrectly, an error message is displayed.
     * 
     * @param joke The joke object for the current round.
     */
    public void roundWinLoss(Joke joke) {

        guessesMinusOne();
        if (guessesRemaining == 0) {
            canvas.removeAll();
            lossScreen = new Image("newOuchie.png");
            canvas.add(lossScreen);
            lossScreen.setCenter(300, 400);
            score = new GraphicsText();
            score.setCenter(450, 335);
            score.setText("" + roundNumber);
            score.setFillColor(Color.BLACK);
            score.setFont("Comic Sans MS", FontStyle.PLAIN, 25);
            score.setFontSize(40.0);
            canvas.add(score);
        }
        if (answerCheck(MainGame.getCurrentJoke())) {
            System.out.println("Correct! You're a dad...");
            correctIncorrect.setText("Correct! You're a dad...");
            setGuesses();
            nextRound();
            reset();
        } else {
            correctIncorrect.setText("Incorrect! Try again.");
            System.out.println("Incorrect! Try again.");
            System.out.println(userText);
            System.out.println(joke.getAnswer());
        }
    }

    /**
     * Initializes the UI assets by adding and removing components from the canvas. This method removes
     * the start button and home screen, and adds various UI elements such as images, text, and hint
     * letters to the canvas.
     */
    private void initialiseUIAssets() {
        canvas.remove(startButton);
        canvas.remove(homeScreen);
        canvas.add(peterGriffin);
        canvas.add(round);
        canvas.add(guesses);
        canvas.add(jokeGraphicsText);
        canvas.add(jokeOutlineText);
        canvas.add(userInputText);
        canvas.add(correctIncorrect);
        canvas.add(hint.getGroup());
        hint.initLetters(canvas);

        // canvas.add(userInputBlinker);
    }

    /**
     * Initializes the home screen of the user interface. This method sets up the necessary components
     * such as images, buttons, text, and fonts.
     */
    public void initHomeScreen() {
        homeScreen = new Image("2.png");
        homeScreen.setCenter(300, 400);
        canvas.add(homeScreen);

        startButton = new Button("Start");
        canvas.add(startButton);
        startButton.setCenter(300, 400);
        startButton.onClick(() -> { initialiseUIAssets(); });

        round = new GraphicsText("", 20, 20);
        round.setText("Round:" + roundNumber);
        round.setFillColor(Color.BLACK);

        guesses = new GraphicsText("", 440, 20);
        guesses.setText("Guesses remaining: " + guessesRemaining);
        guesses.setFillColor(Color.BLACK);

        jokeGraphicsText = new GraphicsText("", 300, 250);
        jokeGraphicsText.setText(jokeText);
        jokeGraphicsText.setFillColor(Color.BLACK);
        jokeGraphicsText.setFont("Comic Sans MS", FontStyle.PLAIN, 25);
        jokeGraphicsText.setFontSize(25.0);

        jokeOutlineText = new GraphicsText("");
        jokeOutlineText.setText(jokeOutline);
        jokeOutlineText.setFillColor(Color.BLACK);
        jokeOutlineText.setFont("Monospaced", FontStyle.PLAIN, 17);

        userInputText = new GraphicsText("");
        userInputText.setFillColor(Color.BLACK);
        userInputText.setCenter(200, 297);
        userInputText.setFont("Monospaced", FontStyle.PLAIN, 17);

        correctIncorrect = new GraphicsText();
        correctIncorrect.setFillColor(Color.BLACK);
        correctIncorrect.setCenter(200, 200);
        correctIncorrect.setFont("Comic Sans MS", FontStyle.PLAIN, 17);

        peterGriffin = new Image("peter.png");
        peterGriffin.setCenter(300, 400);

        hint = new HintLetters();

    }

    /**
     * Updates the user interface by redrawing the canvas and updating the position of the user input
     * blinker.
     */
    public void update() {
        canvas.draw();
        userInputBlinker.updateBlinkerPosition();
    }

    /**
     * Sets the number of remaining guesses and updates the text displayed on the screen.
     */
    public void setGuesses() {
        guessesRemaining = 3;
        guesses.setText("Guesses remaining: " + guessesRemaining);
    }

    /**
     * Decreases the number of remaining guesses by one and updates the display.
     */
    public void guessesMinusOne() {
        guessesRemaining -= 1;
        guesses.setText("Guesses remaining: " + guessesRemaining);
    }

    /**
     * Increases the round number by 1 and updates the round label.
     */
    public void nextRound() {
        roundNumber += 1;
        round.setText("Round:" + roundNumber);
    }

    /**
     * Sets the text of the joke.
     * 
     * @param text the text of the joke
     */
    public void setJokeText(String text) {
        jokeText = text;
        jokeGraphicsText.setText(jokeText);
        jokeTextSizeTest();
        jokeGraphicsText.setCenter(300, 250);
    }

    /**
     * Sets the joke outline text by replacing non-space characters with underscores and replacing
     * dashes with spaces. Updates the joke outline text field and centers it at coordinates (300, 300).
     *
     * @param text the text to set as the joke outline
     */
    public void setJokeOutline(String text) {
        jokeOutline = text.replaceAll("[^\s'!?.,;-]", "_ ").replaceAll("-", " ");
        jokeOutlineText.setText(jokeOutline);
        jokeOutlineText.setCenter(300, 300);
    }

    /**
     * Appends the given text to the userText string, updates the userInputText display, and removes the
     * letters from the hint based on the given text.
     * 
     * @param text the text to be added to the userText string
     */
    public void addUserInput(String text) {
        userText += text + " ";
        userInputText.setText(userText);
        userInputText.setPosition(jokeOutlineText.getX(), 297);
        hint.removeLetters(text, canvas);
    }

    /**
     * Removes the last character from the user input and updates the user interface accordingly. If the
     * user input has only one character, it removes that character and updates the text field. If the
     * user input has more than one character, it removes the last two characters and updates the text
     * field. Additionally, it calls the hint's replaceLetter method to update the canvas.
     */
    public void removeUserInput() {
        if (userText.length() == 1) {
            String text = String.valueOf(userText.charAt(userText.length() - 1));
            userText = userText.substring(0, userText.length() - 1);
            userInputText.setText(userText);
            hint.replaceLetter(text, canvas);
        } else {
            String text = String.valueOf(userText.charAt(userText.length() - 2));
            userText = userText.substring(0, userText.length() - 2);
            userInputText.setText(userText);
            hint.replaceLetter(text, canvas);
        }
    }

    /**
     * Adjusts the font size of the joke text based on its length. If the length of the joke text is
     * greater than 25 characters, the font size is set to 20 using the "Comic Sans MS" font.
     */
    public void jokeTextSizeTest() {
        if (jokeText.length() > 25) {
            jokeGraphicsText.setFont("Comic Sans MS", FontStyle.PLAIN, 20);
        }
    }

    /**
     * Returns the current running state of the program.
     *
     * @return true if the program is running, false otherwise.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Retrieves the user input as a String.
     *
     * @return the user input as a String
     */
    public static String getUserInput() {
        return userText;
    }

    /**
     * Returns the GraphicsText object representing the outline of a joke.
     *
     * @return the GraphicsText object representing the joke outline
     */
    public GraphicsText getJokeOutlineText() {
        return jokeOutlineText;
    }

    /**
     * Increases the value of the integer by one.
     */
    public void plusOne() {
        theInt++;
    }

    /**
     * Returns the CanvasWindow object associated with this user interface.
     *
     * @return the CanvasWindow object
     */
    public CanvasWindow getCanvas() {
        return canvas;
    }
}
