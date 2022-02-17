package dungeon;

import java.util.Stack;


/**
 * This is the Dungeon Interface which represents our main Dungeon model.
 * Our dungeon is represented as 2-Dimensional Grid with dimensions as m x n
 * which is provided by the user.
 */
public interface DungeonInterface extends ReadOnlyDungeonInterface {



  /**
   * Method to build the dungeon grid. This method constructs a 2-dimensional
   * grid of dimensions m x n, where m and n are the number of rows and
   * columns specified by the user.
   */
  public void buildDungeonGrid();

  /**
   * This is a method to build a list of all possible paths that can be
   * connected in a dungeon of size m x n. Random paths are then selected
   * from the list built by this method to implement our dungeon model.
   */
  public void pathBuilder();

  /**
   * This is a method to build a minimum spanning tree that spans our
   * 2-dimensional dungeon grid. It selects random paths such that the
   * interconnectivity of our dungeon stays zero.
   */
  public void buildMst();

  /**
   * This method is used to add interconnectivity to our dungeon model.
   * Additional paths are selected from the total list of paths and added to
   * the dungeon grid to increase the interconnectivity of the dungeon.
   */
  public void addInterconnectivity();


  /**
   * This method is used to set the directions of the caves or locations in
   * our 2-dimensional dungeon grid.
   * The directions are set based on the cave's position in the grid.
   */
  public void setDirections();


  /**
   * This method is used to add or assign treasures into the caves.
   * The treasures can only be added to cave and not inside tunnels. The
   * treasure is added to random caves based on the user input which is
   * provided in percentage.
   */
  public void putTreasureInCaves();



  /**
   * Method is used to get the Path from the start location to end location.
   *
   * @return the stack that contains the path from the start to end location
   *          in order.
   */
  Stack<Integer> findPathFromStartToStop();


  /**
   * This is a method that allows us to put the specified number of monsters
   * in to the dungeon caves. If the number of monsters specified are greater
   * than the number of caves in the dungeon, then all the caves in the
   * dungeons are assigned monsters inside them.
   */
  public void putMonsterInCaves();

  /**
   * This is a method to put arrows in the dungeon. The arrows can be in both
   * tunnels and caves and their frequency is same as that of the treasures
   * in the dungeon.
   */
  public void putArrowsInDungeon();



  //Player:

  /**
   * This method is used to move the player in the dungeon as per the
   * directions of the user. If the user asks the player to move north, the
   * player will move north and so on.
   *
   * @param location It represents the Cave in which the player currently is.
   */
  public boolean movePlayer(String location);

  /**
   * This method is used to shoot the arrows in order to kill the Monster
   * Otyughs inside the dungeon.
   *
   * @param direction the direction in which to shoot the arrow.
   * @param distance  the exact distance required to shoot an Otyugh.
   * @return The status of the otyugh which the player tried to kill.
   */
  public OtyughStatus shootPlayer(String direction, int distance);



  /**
   * This method is used to set the Player's current location. The location
   * of the player is described in terms of the Cave in which he currently is
   * in at the moment.
   *
   * @param playerLocation It represents the players current location.
   */
  public void setPlayerLocation(Location playerLocation);

  /**
   * This method allows the Player to pick up the treasure from its current
   * location. The Player picks up treasure only if the location in which
   * he/she/they are standing has treasures in it.
   */
  public void pickUpTreasure();

  /**
   * This method allows the Player to pick up the arrows from its current
   * location. The Player picks up arrows only if the location in which
   * he/she/they are standing has treasures in it.
   */
  public void pickUpArrows();

  /**
   * Method to reset the arrow count in any location after picking up the
   * arrows in that given location.
   */
  public void resetArrowCountInLocation();



  /**
   * Method to add the thieves into the dungeon. This method will add only 2
   * thieves into any 2 unique tunnels. Thieves are entities that loot all the
   * treasure from the player.
   */
  public void addThiefInDungeon();



}
