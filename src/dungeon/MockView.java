package dungeon;

import java.io.IOException;

/**
 * This class represents the Mock View. Mock View is created solely for the
 * purpose of testing the Graphical Controller.
 */
public class MockView implements View {

  private final Appendable out;


  /**
   * Constructor to create the Mock View.
   *
   * @param out It represents the Appendable which is used to log the
   *            contents and used for testing.
   */
  public MockView(Appendable out) {
    if (out == null) {
      throw new IllegalArgumentException("Appendable can't be null");
    }
    this.out = out;
  }


  /**
   * Refresh the view to reflect any changes in the game state.
   */
  @Override
  public void refresh() {
    StackTraceElement[] arrayOfTrace = Thread.currentThread().getStackTrace();
    StackTraceElement element = arrayOfTrace[2];
    String methodName = element.getMethodName();
    try {
      this.out.append(methodName + " method of controller called refresh "
              + "method of view");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Make the view visible to start the game session.
   */
  @Override
  public void makeVisible() {
    try {
      this.out.append("Make visible called");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

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
   * @param dunType           It represents the type of dungeon.
   * @param treasurePercent   It represents the percentage of treasure in
   *                          dungeon.
   * @param diff              It represents the number of Monsters in the
   */
  @Override
  public void setNewSettings(ReadOnlyDungeonInterface newReadOnlyModel, int row,
                             int column, int interConnectivity, DungeonType dunType,
                             int treasurePercent, int diff) {
    try {
      this.out.append("setNewSettings() called");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Method to set the focus in the main view window so that any key events
   * which take place are detected even when the focus is shifted to another
   * panel.
   */
  @Override
  public void setFocus() {
    try {
      this.out.append("setFocus() called");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to make the view invisible.
   */
  @Override
  public void makeInvisible() {
    try {
      this.out.append("makeInvisible() called");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
