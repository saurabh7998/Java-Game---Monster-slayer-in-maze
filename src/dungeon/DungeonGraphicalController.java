package dungeon;


/**
 * This class represents the Controller of our Graphical Adventure Game.
 * It acts as a mediator between the model and view and relays control back
 * and forth.
 */
public class DungeonGraphicalController implements DungeonController {

  private DungeonInterface model;
  private View view;


  public DungeonGraphicalController(Dungeon dungeonModel, MockView view) {
    this.model = dungeonModel;
    this.view = view;
  }

  /**
   * Constructor to create the Graphical controller object which will create
   * the model and view.
   */
  public DungeonGraphicalController() {
    //This constructor is empty because the model and view will be created
    // inside the buildNewModel() method.

  }


  /**
   * Execute a single game of dungeon given a dungeon Model. When the game
   * is over, the playGame method ends.
   */
  @Override
  public void playGraphicalGame() {

    if (this.view != null) {
      this.view.makeInvisible();
    }
    ConfigurationsView configurationsView = new ConfigurationsView(this);
    configurationsView.setVisible(true);
  }


  /**
   * Method to move the player from current location to another in the
   * desired direction of the user.
   *
   * @param location It represents the direction in which to move.
   * @return true if move was successful, else return false.
   */
  @Override
  public boolean movePlayer(String location) {
    boolean movedOrNot = this.model.movePlayer(location);
    view.refresh();
    return movedOrNot;
  }


  /**
   * Method to allow player to pickup treasure from a location.
   */
  @Override
  public void pickUpTreasure() {
    this.model.pickUpTreasure();
    view.refresh();
  }

  /**
   * Method to allow player to pickup arrows from a location.
   */
  @Override
  public void pickUpArrows() {
    this.model.pickUpArrows();
    view.refresh();
  }

  /**
   * Method to allow the player to shoot an Otyugh when smell is detected.
   * The player needs to specify the direction in which to shoot along with
   * the distance to shoot at.
   *
   * @param direction It represents the direction in which the player wants
   *                  to shoot the arrow.
   * @param distance  It represents the exact distance to shoot the arrow at.
   */
  @Override
  public void shoot(String direction, int distance) {
    this.model.shootPlayer(direction, distance);
    view.refresh();
  }


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
  @Override
  public void buildNewModel(int row, int column, int interconnectivity,
                            DungeonType dungeonType, int treasurePercentage,
                            int difficulty) {

    this.model = new Dungeon(row, column, interconnectivity, dungeonType,
            treasurePercentage, new TrueRandom(), difficulty);

    this.view = new DungeonView((ReadOnlyDungeonInterface) this.model, this);
    this.view.setNewSettings(model, row, column, interconnectivity, dungeonType,
            treasurePercentage, difficulty);
    this.view.refresh();
  }


  /**
   * This method is used to shift the focus back into the main game view. The
   * focus might get shifted to other panels while playing the game, hence it
   * is important to shift back the focus so that any action or key listening
   * events are registered and detected.
   */
  @Override
  public void setViewFocus() {
    this.view.setFocus();
  }


  /**
   * Execute a single game of dungeon given a dungeon Model. When the game
   * is over, the playGame method ends.
   *
   * @param dungeonModel a non-null dungeon Model
   */
  @Override
  public void playConsoleGame(DungeonInterface dungeonModel) {
    //This method is used in the Console Based Game, not in this Graphical Game.
  }


}
