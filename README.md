# Are you a Dad?: The Ultimate Dad Jokes Guessing Game
## Hanna Chang, Max Clifford, Will St. John
### Project description:
Guess the right answers to the prompted dad jokes to find out if you qualify to be a "dad"!

### Technical Guide
To run the code, Java 17 should be installed.

The game consists mainly of 3 java classes: Joke.java ; MainGame.java ; and userInterface.java .
* Joke.java class allows the game to fetch a random joke from the API (https://icanhazdadjoke.com/) without the user having to gain direct access to it
* MainGame.java is the main class which contains the code for all the game logic (such as scanning for user's guess input, prompt display, fetching UI elements...)
* userInterface.java further complements the main class by containing all UI assets while also consisting of a round's win-loss method logic

To run the game, a user can simply run the MainGame class. This will allow for a new canvas window to be prompted to start the game.

### Help and References
* Advice from Prof P on calling API, font display problems, and project brainstorm
* https://icanhazdadjoke.com/api _API of the dad jokes_
* https://stackoverflow.com/questions/56942391/how-to-read-and-store-api-calls-responses-in-java _API help_
* https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html _further understanding of BufferedReader_
* https://docs.oracle.com/javase/8/docs/api/java/io/InputStreamReader.html _further understanding for charset_
* https://mac-comp127.github.io/kilt-graphics/ _kilt graphics_
* https://brandpalettes.com/family-guy-color-codes/ _family guy theme color codes_
* https://www.schemecolor.com/stewie-griffin-family-guy-cartoon-colors.php _more famility guy color codes_
* https://www.cleanpng.com/png-peter-griffin-lois-griffin-chris-griffin-stewie-gr-6004574/download-png.html _family guy and peter griffin png source_
* https://fontmeme.com/fonts/family-guy-font/ _family guy theme font download_

### Known issues:
There are no known design limitations that users should be aware of as of this moment.
Users should be aware that some of the jokes fetched from the API may not work seamlessly with the game, depending on the type of joke. While a lot of work has been done to filter out the non-game-compatible jokes, there may have been some unnoticed instances that need further filtering.

While we had added a blinker feature to indicate user input location, the function has been compromised while attempting to create a new class dedicated to it as it was difficult to navigate the blinking feature by itself from the size of the UserInterface class. While the feature worked previously, it had stopped working when moved to its own class. And due to time constraints, we had collectively decided to leave the code in to possibly debug in the future.

### Societal impact:
Our game assumes that its users have access to a computer device that can run a Java code. At an extreme level, one might find the requirement of a computer device unfair, based on financial reasons or living in rural enrionments with difficulty in public access (such as library comuters, city center computers, etc.).

Our game also assumes that its users have good vision and moter skills, in order to read the game prompts and to type in answers through the keyboard. We have not yet been able to implement any accessibility features such as text-to-speech conversions or voice commands due to time constraints and lack of expertise in the area. However we do recognize the room for development and improvement.

The API (icanhazdadjoke.com) generates jokes randomly and we do not have the ability to read and approve every jokes on it pre-code. Hence, we are unable to filter out any contents of the jokes which may be inappropriate or offensive. These are unintentional consequences as we simply do not have access to the list of jokes available on the API. This may also affect the equitability of our game in regards to inclusivity, accessibility, and diversity.

In the time of writing, we believe that our code does not have potential to exclude or cause harm to vulnerable populations other than previously stated (accessibility) and we do not anticipate its use in any malicious and oppressive situations.
