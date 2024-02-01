import java.util.Scanner;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.io.FileReader;
import java.util.Dictionary;

class Game {
  public static FileReader file = new FileReader("Text.txt");
  public static Scanner sc;n
  
  public static void main(String[] args) throws Exception {
    ArrayList<String> categories = new ArrayList<String>();
    Dictionary<String, String> list = new Hashtable<String, String>();
    Scanner sc = new Scanner(file);

    int amountOfTurns = 7;
    
    while (sc.hasNext()) {
      String word = sc.next();
      String[] seperated = word.split(":");
      categories.add(seperated[0]);
      list.put(seperated[0], seperated[1]);
    }

    //main game loop
    while (amountOfTurns > 0) {
      //choose a random element
      System.out.println("Category: " + list.get);
    }
  }
}
