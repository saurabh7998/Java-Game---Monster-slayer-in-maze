package dungeon;

/**
 * It represents the main graphical adventure game view. It has and shows all
 * the components required to play the game.
 */
public interface View {


  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * This is a method to save the current settings, so that whenever a game is
   * restarted we can use them to re-initialize the model.
   *
   * @param newReadOnlyModel  It represents the read-only dungeon model
   * @param row               It represents the number of rows in the dungeon
   * @param column            It represents the number of columns in the
   *                          dungeon.
   * @param interConnectivity It represents the interconnectivity of the
   *                          dungeon
   * @param dunType             It represents the type of dungeon.
   * @param treasurePercent   It represents the percentage of treasure in
   *                          dungeon.
   * @param diff              It represents the number of Monsters in the
   *                          dungeon.
   */
  void setNewSettings(ReadOnlyDungeonInterface newReadOnlyModel, int row,
                      int column, int interConnectivity,
                      DungeonType dunType, int treasurePercent, int diff);

  /**
   * Method to set the focus in the main view window so that any key events
   * which take place are detected even when the focus is shifted to another
   * panel.
   */
  void setFocus();


  /**
   * Method to make the view invisible.
   */
  void makeInvisible();
}
