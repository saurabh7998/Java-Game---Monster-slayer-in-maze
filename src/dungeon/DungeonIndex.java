package dungeon;

/**
 * This is a class to get the index of the 2-D dungeon grid.
 * As the dungeon is 2 dimensional, it has two parameters to denote its index
 * which is index(i,j).
 */
public class DungeonIndex {
  private int indexX;
  private int indexY;

  /**
   * To get the x-coordinate of the dungeon grid.
   *
   * @return x-coordinate of the grid.
   */
  public int getIndexX() {
    return indexX;
  }


  /**
   * To get the y-coordinate of the dungeon grid.
   *
   * @return y-coordinate of the grid.
   */
  public int getIndexY() {
    return indexY;
  }


  /**
   * Constructor that constructs the dungeon index.
   *
   * @param i x-coordinate of the grid.
   * @param j y-coordinate of the grid.
   */
  public DungeonIndex(int i, int j) {
    this.indexX = i;
    this.indexY = j;
  }

  /**
   * Copy constructor.
   *
   * @param dungeonIndex It is the index or coordinates inside dungeon.
   */
  DungeonIndex(DungeonIndex dungeonIndex) {
    this(dungeonIndex.getIndexX(), dungeonIndex.getIndexY());
  }


}
