import java.util.Scanner;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;

/**
 * A simple word guessing game where the player attempts to guess a word from a
 * specified category.
 * The game provides an option for an admin to view the selected word during
 * gameplay.
 *
 * @author Zander Coffman and Jacob Pape
 * @version 1.0
 */
public class Game {
  public static FileReader file;
  public static Scanner sc;
  public static String word = "";
  public static ArrayList<Character> listOfChars = new ArrayList<Character>();

  // Set admin mode to false by default
  public static boolean adminMode = true;

  /**
   * The main method that initializes the game, reads categories and words from a
   * file,
   * and starts the main game loop.
   *
   * @param args The command-line arguments (not used in this program)
   * @throws Exception If an error occurs while reading the file
   */
  public static void main(String[] args) throws Exception {
    file = new FileReader("Text.txt");
    ArrayList<String> categories = new ArrayList<String>();
    Map<String, List<String>> categoryMap = new HashMap<String, List<String>>();

    Scanner sc = new Scanner(file);
    Scanner input = new Scanner(System.in);

    int amountOfTurns = 7;

    // Read categories and words from file
    while (sc.hasNextLine()) {
      String line = sc.nextLine(); // Read the whole line instead of just a word
      String[] separated = line.split(":");

      if (separated.length == 2) { // Check if the line contains both category and word
        String category = separated[0];
        String word = separated[1];

        categories.add(category);

        if (categoryMap.containsKey(category)) {
          categoryMap.get(category).add(word);
        } else {
          List<String> words = new ArrayList<String>();
          words.add(word);
          categoryMap.put(category, words);
        }
      }
    }

    // Main game loop
    boolean wantsToPlay = true;
    while (wantsToPlay) {
      Collections.shuffle(categories);
      String randomCategory = categories.get(0); // Choose a random category

      List<String> wordsInCategory = categoryMap.get(randomCategory);
      Collections.shuffle(wordsInCategory);
      word = wordsInCategory.get(0); // Choose a random word from the selected category

      // Display category and word if in admin mode
      if (adminMode) {
        System.out.println("Category: " + randomCategory);
        System.out.println("Word is " + word);
      }

      amountOfTurns = 7; // Reset turns for a new game

      while (amountOfTurns > 0) {
        System.out.println("Category: " + randomCategory);
        System.out.println(printoutUnderlinedCharacters());
        System.out.println("You have " + amountOfTurns + " guess(es) to solve the puzzle.\nGuess a letter:");

        String characterGuessed = input.next().toLowerCase(); // Convert input to lowercase

        // Check if the guessed character is in the word
        if (word.toLowerCase().contains(characterGuessed)) {
          addCharToArrayList(characterGuessed.charAt(0)); // Add the guessed character to the list
          System.out.println(catchGuess(characterGuessed));
        } else {
          amountOfTurns--;
          System.out.println("Incorrect guess. You have " + amountOfTurns + " turn(s) remaining.");
        }

        // Check if the word has been guessed
        if (word.equals(printoutUnderlinedCharacters())) {
          break;
        }
      }

      // Ask if the player wants to play again
      System.out.println("Do you want to play again? (yes/no)");
      String playAgain = input.next();
      wantsToPlay = playAgain.equalsIgnoreCase("yes");
    }

    // closes bc we dont like memory leaks
    input.close();
  }

  /**
   * Returns the first character of a string.
   *
   * @param w The input string.
   * @return The first character of the input string.
   */
  public static char returnChar(String w) {
    return w.charAt(0);
  }

  /**
   * Checks the guessed letters and displays them in the word.
   *
   * @param guess The guessed letters.
   * @return A string representing the guessed and blank spaces in the word.
   */
  public static String catchGuess(String guess) {
    String wordcheck = "";

    for (char a : guess.toCharArray()) {
      if (word.toLowerCase().contains(String.valueOf(a).toLowerCase())) {
        wordcheck += a;
      } else {
        wordcheck += "_";
      }
    }

    return wordcheck;
  }

  /**
   * Adds a character to the list of guessed characters.
   *
   * @param e The character to be added.
   */
  public static void addCharToArrayList(char e) {
    listOfChars.add(e);
  }

  /**
   * Checks if a character is in the list of guessed characters.
   *
   * @param test    The character to be checked.
   * @param special The special character representing a non-existent character
   *                (e.g., '~').
   * @return True if the character is in the list; false otherwise.
   */
  public static boolean returnInCharacterList(char test, char special) {
    for (char e : listOfChars) {
      if (Character.toLowerCase(e) == Character.toLowerCase(test)) {
        return true;
      }
    }
    // Display a message if the guessed character is not in the word
    if (special != '~') {
      System.out.println("Sorry, there are no " + test + "'s in the puzzle.");
    }

    return false;
  }

  /**
   * Creates a string with underlined characters for the word.
   *
   * @return A string with guessed characters and underscores for unknown
   *         characters.
   */
  public static String printoutUnderlinedCharacters() {
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < word.length(); i++) {
      if (returnInCharacterList(word.charAt(i), '~')) {
        output.append(word.charAt(i));
      } else {
        output.append("_");
      }
    }
    return output.toString();
  }
}
