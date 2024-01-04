import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import api.Joke;


/**
 * This class contains unit tests for the Joke class.
 */
public class JokeTests {
    @Test
    void testJokesOnlyContainOneQuestionMark() {
        ArrayList<String> jokes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Joke joke = new Joke();
            jokes.add(joke.getQuestion());
            System.out.println(joke.getQuestion());
        }
        for (String j : jokes) {
            int count = 0;
            for (int i = 0; i < j.length(); i++) {
                if (j.charAt(i) == '?') {
                    count++;
                }
            }
            assertTrue(count == 1, "Joke should contain only one question mark");
        }
    }

    @Test
    void testGoodJoke() {
        ArrayList<String> jokes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Joke joke = new Joke();
            jokes.add(joke.toString());
        }
        for (String j : jokes) {
            assertTrue(j.contains("?"));
            assertTrue(j.contains("Who")
                || j.contains("What")
                || j.contains("Where")
                || j.contains("When")
                || j.contains("Why")
                || j.contains("How"));
        }
    }

    @Test
    public void testGetQuestion() {
        Joke joke = new Joke();
        String question = joke.getQuestion();
        assertNotNull(question, "The question should not be null");
        assertTrue(question.endsWith("?"), "The question should end with a question mark");
    }

    @Test
    public void testGetAnswer() {
        Joke joke = new Joke();
        String answer = joke.getAnswer();
        assertNotNull(answer, "The answer should not be null");
    }
}