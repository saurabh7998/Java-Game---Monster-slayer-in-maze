package dungeon;

import java.util.List;

/**
 * This interface represents the Player entity. The player is the person who
 * will enter our dungeon and travel from one cave to other via the tunnels.
 * Players description includes his current location, the treasure collected
 * by him and the possible moves he can take from current location.
 */
public interface PlayerInterface {

  /**
   * This method is used to get the current location of the player at any
   * given point of time. The location of the player is described in terms of
   * the Cave in which he currently is in at the moment.
   *
   * @return Player's current location.
   */
  public Location getPlayerLocation();

  /**
   * This method is used to set the Player's current location. The location
   * of the player is described in terms of the Cave in which he currently is
   * in at the moment.
   *
   * @param playerLocation It represents the players current location.
   */
  public void setPlayerLocation(Location playerLocation);

  /**
   * This method is used to retrieve the players accumulated wealth or
   * treasure. The player collects treasures from the caves as he explores
   * the dungeon.
   *
   * @return List of treasures collected by the Player so far.
   */
  public List<Treasure> getWealth();

  /**
   * This method is used to set the wealth of the Player.The player collects
   * treasures from the caves as he exploresthe dungeon.
   *
   * @param wealth It is the wealth or treasure that the player recently
   *               collected from a cave.
   */
  public void setWealth(List<Treasure> wealth);


  /**
   * This method is used to move the player in the dungeon as per the
   * directions of the user. If the user asks the player to move north, the
   * player will move north and so on.
   *
   * @param location It represents the Cave in which the player currently is.
   */
  public boolean move(String location);


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
   * Method to get the number of arrows which the player holds in any given
   * point of time.
   *
   * @return number of arrows the player currently has.
   */
  public int getArrowCount();

  /**
   * Method to set the arrow count after the player picks up the arrows from
   * a location.
   *
   * @param numOfPickedArrows It represents the number of arrows picked by
   *                          the Player from a location.
   */
  public void setArrowCount(int numOfPickedArrows);


  /**
   * This method is used to shoot the arrows in order to kill the Monster
   * Otyughs inside the dungeon.
   *
   * @param direction the direction in which to shoot the arrow.
   * @param distance  the exact distance required to shoot an Otyugh.
   * @return The status of the otyugh which the player tried to kill.
   */
  public OtyughStatus shoot(String direction, int distance);

  /**
   * Method to check if the player in the dungeon is alive or not.
   *
   * @return true if player is alive, false if player is dead or eaten by
   *          Otyugh.
   */
  public boolean checkIfPlayerIsAlive();


}
