package dungeon;

/**
 * This represents the Interface of our controller.
 */
public interface DungeonController {


  /**
   * Execute a single game of dungeon given a dungeon Model. When the game
   * is over, the playGame method ends.
   *
   * @param model a non-null dungeon Model
   */
  void playConsoleGame(DungeonInterface model);


  /**
   * Method to move the player from current location to another in the
   * desired direction of the user.
   *
   * @param location It represents the direction in which to move.
   * @return true if move was successful, else return false.
   */
  boolean movePlayer(String location);

  /**
   * Method to allow player to pickup treasure from a location.
   */
  void pickUpTreasure();

  /**
   * Method to allow player to pickup arrows from a location.
   */
  void pickUpArrows();

  /**
   * Method to allow the player to shoot an Otyugh when smell is detected.
   * The player needs to specify the direction in which to shoot along with
   * the distance to shoot at.
   *
   * @param direction It represents the direction in which the player wants
   *                  to shoot the arrow.
   * @param distance  It represents the exact distance to shoot the arrow at.
   */
  void shoot(String direction, int distance);


  /**
   * This is a method to build or create a entirely new model. This method is
   * used when the user wants to play a new game. Each time the user wants to
   * play a new game, all the new settings will be taken as input from the
   * user and a new model will be created by the graphical game controller.
   *
   * @param row                It represents the number of rows in the dungeon.
   * @param column             It represents the number of columns in the
   *                           dungeon.
   * @param interconnectivity  It represents the interconnectivity of the
   *                           dungeon.
   * @param dungeonType        It represents the type of the dungeon.
   * @param treasurePercentage It represents the amount of treasure in dungeon.
   * @param difficulty         It represents the number of Otyughs in the
   *                           dungeon.
   */
  void buildNewModel(int row, int column, int interconnectivity,
                     DungeonType dungeonType, int treasurePercentage,
                     int difficulty);


  /**
   * This method is used to shift the focus back into the main game view. The
   * focus might get shifted to other panels while playing the game, hence it
   * is important to shift back the focus so that any action or key listening
   * events are registered and detected.
   */
  void setViewFocus();


  /**
   * Execute a single game of dungeon given a dungeon Model. When the game
   * is over, the playGame method ends.
   * This method is specifically for playing the Graphical Adventure Game.
   */
  void playGraphicalGame();
}
