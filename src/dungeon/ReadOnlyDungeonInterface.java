package dungeon;


import java.util.List;


/**
 * This is the Dungeon Interface which represents our main Dungeon model.
 * Our dungeon is represented as 2-Dimensional Grid with dimensions as m x n
 * which is provided by the user.
 */
public interface ReadOnlyDungeonInterface {


  /**
   * This method is a method that helps us retrieve our built dungeon.
   *
   * @return 2-D dungeon grid of caves
   */
  public Location[][] getDungeon();


  /**
   * To get the y, and x coordinates of the location in the dungeon grid.
   * For example location 1 has coordinates (0,0), location 9 has coordinates
   * (2,2) in a 3x3 grid.
   *
   * @param a The location id of which we want the coordinates in the dungeon.
   * @return Coordinates of the location id in the dungeon.
   */
  DungeonIndex getIndex(int a);


  /**
   * This method is used to detect the Smell from the monsters inside the
   * dungeon. The player detects Strong Pungent Smell when there is a monster
   * which is one location away from it or when there is more than one
   * monster present at a distance of exactly 2 from the player's current
   * location. The player detects Low Smell when there is one monster present
   * from a distance of 2 from it.
   *
   * @param location the location from which to detect the smell.
   * @return Smell which is either strong  or  low.
   */
  public Smell detectSmell(Location location);


  //Player:


  /**
   * This method is used to get the current location of the player at any
   * given point of time. The location of the player is described in terms of
   * the Cave in which he currently is in at the moment.
   *
   * @return Player's current location.
   */
  public Location getPlayerLocation();


  /**
   * This method is used to retrieve the players accumulated wealth or
   * treasure. The player collects treasures from the caves as he explores
   * the dungeon.
   *
   * @return List of treasures collected by the Player so far.
   */
  public List<Treasure> getPlayerWealth();

  /**
   * Method to get the number of arrows which the player holds in any given
   * point of time.
   *
   * @return number of arrows the player currently has.
   */
  public int getPlayerArrowCount();


  //Location:

  /**
   * Method to get the Treasure list  of the Location in the dungeon.
   *
   * @return List of Treasures.
   */
  List getTreasureListFromLocation();


  /**
   * Method to get the number of arrows present in any given location of the
   * dungeon.
   *
   * @return number of arrows.
   */
  int getArrowCountInLocation();


  /**
   * Method to get the Identification number of the Location in the dungeon.
   *
   * @return id of the cave
   */
  public int getLocationId();

  /**
   * Method to get the location to the north of the current location.
   *
   * @return Location to the north of the current location.
   */
  public Location getNorthLocation();

  /**
   * Method to get the location to the south of the current location.
   *
   * @return Location to the south of the current location.
   */
  public Location getSouthLocation();

  /**
   * Method to get the location to the east of the current location.
   *
   * @return Location to the east of the current location.
   */
  public Location getEastLocation();

  /**
   * Method to get the location to the west of the current location.
   *
   * @return Location to the west of the current location.
   */
  public Location getWestLocation();

  /**
   * Method to get the final selected paths which were randomly selected
   * using the Kruskal's algorithm in order to build our dungeon.
   *
   * @return list of final paths selected to build the dungeon.
   */
  public List<Path> getFinalSelectedPathsForDungeon();

  /**
   * Method to return the initial path list that was used to select random
   * paths and put into final paths list by using Kruskal algorithm in order
   * to build the dungeon.
   *
   * @return returns the list of all total paths
   */
  public List<Path> getPathList();


  /**
   * Method to check if the player was able to successfully shoot the Otyugh
   * or not.
   *
   * @return true if successfully shot, else false.
   */
  public boolean shotOrNot();

  /**
   * Method to get the final goal location at which the player needs to reach
   * in order to win the game.
   *
   * @return Location id of the final goal location of the game.
   */
  public int getFinalGoalLocation();


  /**
   * Method to check if the player is alive or if he has been killed by the
   * Otyugh.
   *
   * @return true if the player is alive, else false if player is dead.
   */
  public boolean checkIfPlayerIsAlive();


}
