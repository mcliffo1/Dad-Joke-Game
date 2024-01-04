package api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Joke {
    private String joke = "";

    /**
     * Represents a joke obtained from the "https://icanhazdadjoke.com/" API. The Joke class is
     * responsible for fetching a joke from the API and storing it.
     */
    public Joke() {
        while (!goodJoke()) {
            try {
                // Create a URL object with the API endpoint
                URL url = new URL("https://icanhazdadjoke.com/");

                // Open a connection to the URL
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Set request headers
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "text/plain");

                // Get the response code
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    joke = response.toString();
                } else {
                    System.out.println("Failed to fetch joke. Response code: " + responseCode);
                }

                // Close the connection
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns the joke as a string representation.
     */
    public String toString() {
        return joke;
    }

    /**
     * Determines if a joke is valid for our purposes and uses in UserInterface class
     */
    private boolean goodJoke() {
        boolean shortJoke = joke.length() < 60;  // Joke isn't too long

        int numQuest = 0;
        for (int i = 0; i < joke.length(); i++) {
            if (joke.charAt(i) == '?') {
                numQuest++;
            }
        }
        boolean oneQuestion = numQuest == 1;  // Joke only has one question

        boolean isQuestion = joke.contains("Who")
            || joke.contains("What")
            || joke.contains("Where")
            || joke.contains("When")
            || joke.contains("Why")
            || joke.contains("How");  // Joke contains a question word

        boolean canBeSplit = joke.contains("? ");  // Joke has a question mark

        boolean containsBadPunc = joke.contains("\'")
            || joke.contains(",")
            || joke.contains("\"")
            || joke.contains("-");  // Joke contains bad punctuation

        boolean containsBadChars = joke.contains("[^a-z$]|[^A-Z$]|[^0-9$]");  // Joke contains an ampersand

        boolean containsBin = joke.contains("bin") || joke.contains("E.T.") || joke.contains("Forest");  // One-off bad
                                                                                                         // jokes that
                                                                                                         // doesn't get
                                                                                                         // properly
                                                                                                         // formatted

        return isQuestion && shortJoke && oneQuestion && canBeSplit
            && !containsBadPunc && !containsBadChars && !containsBin;
    }

    /**
     * Returns the question part of the joke by parsing the string at question marks.
     */
    public String getQuestion() {
        String[] jokeSplit = joke.split("\\? ");
        String question = jokeSplit[0].toString().strip();
        return question + "?";
    }

    /**
     * Returns the answer to the joke, by parsing the other half of the string.
     */
    public String getAnswer() {
        String[] jokeSplit = joke.split("\\? ");
        String answer = jokeSplit[1].toString().strip();
        return answer;
    }


    public static void main(String[] args) {
        Joke joke1 = new Joke();
        System.out.println("Full Joke:" + joke1.toString());
        System.out.println("Joke Question:" + joke1.getQuestion());
        System.out.println("Joke Answer:" + joke1.getAnswer());
    }
}

