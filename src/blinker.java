import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import edu.macalester.graphics.Rectangle;

/**
 * a class solely for the blinker feature of the user typing consisting of the blinking rectangle
 * that will indicate next letter and methods that calculate the blinker's coordinate movements
 */

public class Blinker {

    private Rectangle userInputBlinker;
    private Timer blinkTimer;
    private boolean blinkerVisible = true;
    private UserInterface userInterface;

    public Blinker(UserInterface userInterface) {
        this.userInterface = userInterface;
        initBlinker();
        // UserInterface.getUserInput();
    }

    /**
     * Sets the timing for toggling the blinker's visibility to create the animated effect
     */
    private void initBlinker() {
        blinkTimer = new Timer();
        blinkTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                toggleBlinkerVisibility();
            }
        }, 1, 450);
    }

    /**
     * The function toggles the rectangle's visibility to achieve the blinking effect
     */
    private void toggleBlinkerVisibility() {
        blinkerVisible = !blinkerVisible;
        Color blinkerColor = blinkerVisible ? Color.PINK : new Color(0, 0, 0, 0);
        userInputBlinker.setFillColor(blinkerColor);
    }

    /**
     * Creates the graphic indicator (a "blinker") by its set size, color, and location
     */
    public void createBlinker() {
        if (userInputBlinker == null) {
            double blinkerWidth = 12;
            double blinkerHeight = 18;
            Color blinkerColor = Color.PINK;

            double xCoordinate = calculateBlinkerXCoordinate();
            double yCoordinate = 380;

            userInputBlinker = new Rectangle(xCoordinate, yCoordinate, blinkerWidth, blinkerHeight);
            userInputBlinker.setFillColor(blinkerColor);
            userInputBlinker.setStrokeWidth(0);

            userInterface.getCanvas().add(userInputBlinker);
        }
    }

    /**
     * Finds the new x coordinate of the rectangle indicator as user input location updates
     */
    private double calculateBlinkerXCoordinate() {
        double initialX = userInterface.getJokeOutlineText().getX();
        double characterSpacing = 10;

        return initialX + UserInterface.getUserInput().length() * characterSpacing;
    }

    /**
     * Updates the physical location of the rectangle based on the new x coordinate
     */
    public void updateBlinkerPosition() {
        double xCoordinate = calculateBlinkerXCoordinate();
        double yCoordinate = 380;

        userInputBlinker.setPosition(xCoordinate, yCoordinate);
    }
}
