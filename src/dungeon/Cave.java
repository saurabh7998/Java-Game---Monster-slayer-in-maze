package dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Cave or a Tunnel in our dungeon.Dungeon.
 * If the cave has exactly two paths connected to it, we call it a Tunnel
 * otherwise we call it dungeon.Cave.
 */
public class Cave implements Location {

  private int caveId;
  private Location north;
  private Location south;
  private Location east;
  private Location west;
  private CaveType caveType;
  private List<Treasure> treasureList;

  private List<Monster> monster;
  private int arrowCount;
  private Thief thief;

  private StringBuilder caveNeighbours;

  private boolean explored;


  /**
   * Constructs a cave object with a cave id.
   *
   * @param caveId It represents the unique identifier of a particular cave.
   */
  public Cave(int caveId) {
    if (caveId < 1) {
      throw new IllegalArgumentException("Cave id cannot be less than 1");
    }
    this.caveId = caveId;
    this.treasureList = new ArrayList<>();
    this.monster = new ArrayList<>();
    caveNeighbours = new StringBuilder();
    explored = false;
  }


  /**
   * Copy Constructor.
   *
   * @param cave It represents the Location in dungeon.
   */
  public Cave(Location cave) {
    this(cave.getCaveId());
  }


  /**
   * This is a method to get and know that a location is connected to how
   * many other locations and in which direction.
   *
   * @return String consisting of the initials of the directions in which a
   *          particular location has other connected locations
   */
  @Override
  public String getCaveNeighbours() {
    return caveNeighbours.toString();
  }


  /**
   * This method is used to track of the neighbours of any particular location
   * (cave or a tunnel) in all four directions.
   */
  @Override
  public void setCaveNeighbours(String s) {
    if (s == null) {
      throw new IllegalArgumentException("Cave neighbours string cannot be "
              + "null");
    }
    this.caveNeighbours.append(s);
  }

  /**
   * Method to add arrows into the location. Arrows can be added to both the
   * caves and tunnels.
   */
  public void addArrowToLocation() {
    this.arrowCount += 1;
  }

  /**
   * Method to get the number of arrows present in any given location of the
   * dungeon.
   *
   * @return number of arrows.
   */
  public int getArrowCountInLocation() {
    return this.arrowCount;
  }

  /**
   * Method to reset the arrow count in any location after picking up the
   * arrows in that given location.
   */
  public void resetArrowCount() {
    this.arrowCount = 0;
  }


  /**
   * Method to get the Identification number of the Location in the dungeon.
   *
   * @return id of the cave
   */

  @Override
  public int getCaveId() {
    return this.caveId;
  }

  /**
   * Method to get the Treasure list  of the Location in the dungeon.
   *
   * @return List of Treasures.
   */
  @Override
  public List getTreasureList() {
    return treasureList;
  }

  /**
   * Method to set the Treasure inside the Locations in the dungeon.
   *
   * @param treasure It represents the treasure.
   */
  @Override
  public void setTreasureList(Treasure treasure) {
    if (treasureList == null) {
      throw new IllegalArgumentException("List of treasure cannot be null");
    }
    this.treasureList.add(treasure);
  }

  /**
   * Method to add monster into the Cave locations of the Dungeon.
   *
   * @param otyugh It is the name of the monster type present in our dungeon.
   */
  public void addMonster(Monster otyugh) {
    if (otyugh == null) {
      throw new IllegalArgumentException("Otyugh cannot be null");
    }
    this.monster.add(otyugh);
  }

  /**
   * Method to check if the given Cave location in the dungeon has a monster
   * residing in it or not.
   *
   * @return true if monster is present, else return false.
   */
  @Override
  public boolean hasMonster() {
    return this.monster.size() > 0;
  }

  /**
   * Method to get the Monster present in the location.
   *
   * @return monster residing in the cave.
   */
  @Override
  public Monster getMonster() {
    if (this.monster.size() > 0) {
      return this.monster.get(0);
    }
    return null;
  }

  /**
   * Method to check if the location has been explored by the player or not.
   *
   * @return true if the location is explored, false if not.
   */
  @Override
  public boolean isLocationExplored() {
    return this.explored;
  }

  /**
   * Method to set the location's explored status to true when it is visited
   * by the player.
   *
   * @param exploreStatus It represents whether the location is explored or
   *                      not by the player.
   */
  @Override
  public void setExploreStatus(boolean exploreStatus) {
    this.explored = exploreStatus;
  }

  /**
   * Method to deduct the health of the Otyugh when it has been shot by an
   * arrow by the Player.
   */
  @Override
  public void deductHealthOfOtyugh() {
    this.monster.get(0).deductHealth(50);
    if (monster.get(0).getHealth() <= 0) {
      this.monster.remove(0);
    }
  }


  /**
   * Method to get the type of the Location in the dungeon. It can be either
   * a Cave or a Tunnel.
   *
   * @return Type of cave (ENUM)
   */
  @Override
  public CaveType getCaveType() {
    return caveType;
  }


  /**
   * Method to get the type of the Location in the dungeon. It can be either
   * a Cave or a Tunnel.
   *
   * @param caveType It represents the Type of Location.
   */
  @Override
  public void setCaveType(CaveType caveType) {
    if (caveType == null) {
      throw new IllegalArgumentException("List of treasure cannot be null");
    }
    this.caveType = caveType;
  }


  /**
   * Method to get the location to the north of the current location.
   *
   * @return Location to the north of the current location.
   */
  @Override
  public Location getNorth() {
    return north;
  }

  /**
   * Method  to set the location to the north of the current location.
   *
   * @param north It represents the Location to the north of the current
   *              location.
   */
  @Override
  public void setNorth(Location north) {
    if (north == null) {
      throw new IllegalArgumentException("north location cannot be null");
    }
    this.north = north;
  }


  /**
   * Method to get the location to the south of the current location.
   *
   * @return Location to the south of the current location.
   */
  @Override
  public Location getSouth() {
    return south;
  }


  /**
   * Method  to set the location to the south of the current location.
   *
   * @param south It represents the Location to the south of the current
   *              location.
   */
  @Override
  public void setSouth(Location south) {
    if (south == null) {
      throw new IllegalArgumentException("south location cannot be null");
    }
    this.south = south;
  }


  /**
   * * Method to get the location to the east of the current location.
   * *
   * * @return  Location to the east of the current location.
   */
  @Override
  public Location getEast() {
    return east;
  }

  /**
   * Method  to set the location to the east of the current location.
   *
   * @param east It represents the Location to the east of the current
   *             location.
   */
  @Override
  public void setEast(Location east) {
    if (east == null) {
      throw new IllegalArgumentException("east location cannot be null");
    }
    this.east = east;
  }


  /**
   * Method to get the location to the west of the current location.
   *
   * @return Location to the west of the current location.
   */
  @Override
  public Location getWest() {
    return west;
  }


  /**
   * Method  to set the location to the west of the current location.
   *
   * @param west It represents the Location to the west of the current
   *             location.
   */
  @Override
  public void setWest(Location west) {
    if (west == null) {
      throw new IllegalArgumentException("West location cannot be null");
    }
    this.west = west;
  }


  /**
   * This is a method to add thief in the dungeon's tunnel locations.
   *
   * @param thief It represents the Thief entity who is present in tunnels
   *              and loots the player's treasures.
   */
  @Override
  public void addThief(Thief thief) {
    if (thief == null) {
      throw new IllegalArgumentException("Thief cannot be null");
    }
    this.thief = thief;
  }


  /**
   * Method to check if a given tunnel has a thief inside it or not.
   *
   * @return true if thief is present, false if not present.
   */
  @Override
  public boolean hasThief() {
    return this.thief != null;
  }


}
