package dungeon;

import java.util.List;

/**
 * This interface represents the Location in the dungeon.
 * A location can be of type Cave or Type Tunnel based on the number od other
 * locations connected to it. Each location in the dungeon has its own own
 * attributes like id, type, treasure, and adjacent caves in 4 directions.
 */
public interface Location {


  /**
   * Method to get the Identification number of the Location in the dungeon.
   *
   * @return id of the cave
   */
  int getCaveId();

  /**
   * Method to get the Treasure list  of the Location in the dungeon.
   *
   * @return List of Treasures.
   */
  List getTreasureList();

  /**
   * Method to set the Treasure inside the Locations in the dungeon.
   *
   * @param treasure It represents the treasure.
   */
  void setTreasureList(Treasure treasure);

  /**
   * Method to get the type of the Location in the dungeon. It can be either
   * a Cave or a Tunnel.
   *
   * @return Type of cave (ENUM)
   */
  CaveType getCaveType();

  /**
   * Method to get the type of the Location in the dungeon. It can be either
   * a Cave or a Tunnel.
   *
   * @param caveType It represents the Type of Location.
   */
  void setCaveType(CaveType caveType);

  /**
   * Method to get the location to the north of the current location.
   *
   * @return Location to the north of the current location.
   */
  Location getNorth();

  /**
   * Method  to set the location to the north of the current location.
   *
   * @param north It represents the Location to the north of the current
   *              location.
   */
  void setNorth(Location north);

  /**
   * Method to get the location to the south of the current location.
   *
   * @return Location to the south of the current location.
   */
  Location getSouth();

  /**
   * Method  to set the location to the south of the current location.
   *
   * @param south It represents the Location to the south of the current
   *              location.
   */
  void setSouth(Location south);


  /**
   * Method to get the location to the east of the current location.
   *
   * @return Location to the east of the current location.
   */
  Location getEast();


  /**
   * Method  to set the location to the east of the current location.
   *
   * @param east It represents the Location to the east of the current
   *             location.
   */
  void setEast(Location east);


  /**
   * Method to get the location to the west of the current location.
   *
   * @return Location to the west of the current location.
   */
  Location getWest();


  /**
   * Method  to set the location to the west of the current location.
   *
   * @param west It represents the Location to the west of the current
   *             location.
   */
  void setWest(Location west);

  /**
   * Method to add monster into the Cave locations of the Dungeon.
   *
   * @param otyugh It is the name of the monster type present in our dungeon.
   */
  void addMonster(Monster otyugh);

  /**
   * Method to add arrows into the location. Arrows can be added to both the
   * caves and tunnels.
   */
  void addArrowToLocation();

  /**
   * Method to deduct the health of the Otyugh when it has been shot by an
   * arrow by the Player.
   */
  void deductHealthOfOtyugh();

  /**
   * Method to check if the given Cave location in the dungeon has a monster
   * residing in it or not.
   *
   * @return true if monster is present, else return false.
   */
  public boolean hasMonster();

  /**
   * Method to get the number of arrows present in any given location of the
   * dungeon.
   *
   * @return number of arrows.
   */
  int getArrowCountInLocation();

  /**
   * Method to reset the arrow count in any location after picking up the
   * arrows in that given location.
   */
  public void resetArrowCount();

  /**
   * Method to get the Monster present in the location.
   *
   * @return monster residing in the cave.
   */
  public Monster getMonster();


  /**
   * This method is used to track of the neighbours of any particular location
   * (cave or a tunnel) in all four directions.
   */
  void setCaveNeighbours(String s);

  /**
   * This is a method to get and know that a location is connected to how
   * many other locations and in which direction.
   *
   * @return String consisting of the initials of the directions in which a
   *          particular location has other connected locations
   */
  String getCaveNeighbours();


  /**
   * Method to check if the location has been explored by the player or not.
   *
   * @return true if the location is explored, false if not.
   */
  boolean isLocationExplored();

  /**
   * Method to set the location's explored status to true when it is visited
   * by the player.
   *
   * @param exploreStatus It represents whether the location is explored or
   *                      not by the player.
   */
  void setExploreStatus(boolean exploreStatus);

  /**
   * This is a method to add thief in the dungeon's tunnel locations.
   *
   * @param thief It represents the Thief entity who is present in tunnels
   *              and loots the player's treasures.
   */
  void addThief(Thief thief);

  /**
   * Method to check if a given tunnel has a thief inside it or not.
   *
   * @return true if thief is present, false if not present.
   */
  boolean hasThief();
}
