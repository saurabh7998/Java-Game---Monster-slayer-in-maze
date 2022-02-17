package dungeon;

import java.io.InputStreamReader;
import java.util.Scanner;


/**
 * This is the driver class containing the Main method. The main method
 * inside the driver class will create the controller and start the dungeon
 * game in either the text based mode or the graphical mode.
 */
public class Driver {

  /**
   * Main method.
   *
   * @param args String arguments which are passed through the CLI
   */
  public static void main(String[] args) {
    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;

    String gameChoice = "0";
    try {
      while (Integer.parseInt(gameChoice) != 1 || Integer.parseInt(gameChoice) != 2) {
        System.out.println("Which Game Do you want to play?\n Press 1 to play "
                + "Graphical Game.\n Press 2 to play text-based game ");
        Scanner sc = new Scanner(System.in);

        gameChoice = sc.next();

        if (Integer.parseInt(gameChoice) == 1) {
          DungeonController controller = new DungeonGraphicalController();
          controller.playGraphicalGame();
        } else if (Integer.parseInt(gameChoice) == 2) {
          int row = Integer.parseInt(args[0]);
          int column = Integer.parseInt(args[1]);
          int interconnectivity = Integer.parseInt(args[2]);
          String dungeonType = args[3];
          int treasurePercentage = Integer.parseInt(args[4]);
          int difficulty = Integer.parseInt(args[5]);
          DungeonInterface dungeonModel = new Dungeon(row, column, interconnectivity,
                  DungeonType.valueOf(dungeonType), treasurePercentage,
                  new TrueRandom(), difficulty);


          DungeonController controller = new DungeonConsoleController(input, output);
          controller.playConsoleGame(dungeonModel);
        } else {
          System.out.println("Please enter a valid input choice");
        }

      }
    } catch (NumberFormatException e) {
      System.out.println("Enter a valid number");
    }

  }

}
