import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Point;

/**
 * The HintLetters class represents a collection of hint letters in a word guessing game. It manages
 * the display and manipulation of hint letters on a canvas.
 */
public class HintLetters {
    private GraphicsGroup group;
    private ArrayList<Point> coords;
    private static HashMap<String, GraphicsText> map;
    private HashMap<String, GraphicsText> backupMap;
    private HashMap<String, Integer> countMap;

    public HintLetters() {
        group = new GraphicsGroup();
        coords = new ArrayList<>();
        map = new HashMap<>();
        backupMap = new HashMap<>();
        countMap = new HashMap<>();
    }


    /**
     * Removes a letter from the canvas and updates the internal data structures. The letter is
     * specified by the given parameter.
     * 
     * @param letter the letter to be removed
     * @param canvas the canvas window where the letter is displayed
     */
    public void removeLetters(String letter, CanvasWindow canvas) {
        letter = letter.toLowerCase();
        if (countMap.containsKey(letter)) {
            int count = countMap.get(letter);
            if (count > 0) {
                backupMap.put(letter, map.get(letter + count));
                group.remove(map.get(letter + count));
                canvas.remove(map.get(letter + count));
                map.remove(letter + count);
                countMap.put(letter, count - 1);
            }
        }
        canvas.draw();
    }


    /**
     * Replaces a letter in the canvas with a modified version. The modified version includes the count
     * of the letter appended to it. The letter is converted to lowercase before replacing. The replaced
     * letter is added to the canvas and the group of letters.
     *
     * @param letter The letter to be replaced.
     * @param canvas The canvas window where the letter is displayed.
     */
    public void replaceLetter(String letter, CanvasWindow canvas) {
        letter = letter + countMap.get(letter.toLowerCase());
        letter = letter.toLowerCase();
        System.out.println(map);
        map.put(letter, backupMap.get(letter));
        backupMap.remove(letter);
        group.add(map.get(letter));
        canvas.add(map.get(letter));
    }

    /**
     * Initializes the letters on the canvas based on the current joke's answer. Each letter is
     * represented by a GraphicsText object and added to the canvas. The count of each letter is stored
     * in a HashMap.
     *
     * @param canvas The CanvasWindow object where the letters will be displayed.
     */
    public void initLetters(CanvasWindow canvas) {
        makeCoords();
        fillCountMap();
        int j = 0;
        for (char letter : MainGame.getCurrentJoke().getAnswer().toCharArray()) {

            GraphicsText singleLetter = new GraphicsText();
            singleLetter.setCenter(coords.get(j));
            singleLetter.setFillColor(Color.BLACK);
            singleLetter.setFont("Comic Sans MS", FontStyle.PLAIN, 25);
            singleLetter.setFontSize(20.0);
            singleLetter.setText(String.valueOf(letter));
            j++;

            if (countMap.containsKey(String.valueOf(letter).toLowerCase())) {
                Integer count = countMap.get(String.valueOf(letter).toLowerCase());
                count++;
                countMap.put(String.valueOf(letter).toLowerCase(), count);
            } else {
                countMap.put(String.valueOf(letter).toLowerCase(), 1);

            }
            group.add(singleLetter);
            Integer bill = countMap.get(String.valueOf(letter).toLowerCase());
            map.put(String.valueOf(letter).toLowerCase() + bill, singleLetter);
            canvas.add(singleLetter);

        }
    }

    /**
     * Fills the countMap with entries with the letter of the alphabet as keys, and 0 for all of the
     * values.
     */
    public void fillCountMap() {
        for (char c = 'a'; c <= 'z'; c++) {
            String letter = String.valueOf(c);
            countMap.put(letter, 0);
        }
    }

    /**
     * Clears the maps used for storing hint letters. This method clears both the main map and the
     * backup map.
     */
    public void clearMaps() {
        map.clear();
        backupMap.clear();
        countMap.clear();
    }

    /**
     * Removes all of the hintLetters from the canvas.
     * 
     * @param canvas the canvas from which to remove the letters.
     */
    public void clearCanvas(CanvasWindow canvas) {
        for (GraphicsText Gtext : map.values()) {
            Gtext.setText("");
            canvas.remove(Gtext);
        }
        clearMaps();
    }

    /**
     * getter for map
     * 
     * @return HashMap map
     */
    public static HashMap<String, GraphicsText> getMap() {
        return map;
    }

    /**
     * Returns the GraphicsGroup associated with this object.
     *
     * @return the GraphicsGroup associated with this object.
     */
    public GraphicsGroup getGroup() {
        return group;
    }

    /**
     * Generates random coordinates and adds them to the 'coords' list.
     */
    private void makeCoords() {
        Random random = new Random();
        int i = 0;

        while (i < 25) {
            Double xran = random.nextDouble(100, 500);
            Double yran = random.nextDouble(100, 200);
            i++;
            coords.add(new Point(xran, yran));

        }


    }
}
