package dungeon;

import java.util.ArrayList;
import java.util.List;


/**
 * This class represents the Player who shall travel in our dungeon.
 * A Player is an entity who travels the dungeon and explores it by
 * travelling from one cave to another. During its travel, the Player also
 * collects the treasures found along the way.
 */
public class Player implements PlayerInterface {

  private Location playerLocation;
  private List<Treasure> wealth;

  private int arrowCount;
  private boolean isAlive;
  private Randomizer randomizer;

  /**
   * Constructor to create a Player object.
   *
   * @param randomizer It represents the randomizer object which adds
   *                   randomness to the model and player abilities.
   */
  public Player(Randomizer randomizer) {
    if (randomizer == null) {
      throw new IllegalArgumentException("Randomizer cannot be null");
    }
    wealth = new ArrayList<>();
    this.arrowCount = 3;
    this.randomizer = randomizer;
    this.isAlive = true;
  }

  /**
   * Method to set the arrow count after the player picks up the arrows from
   * a location.
   *
   * @param numOfPickedArrows It represents the number of arrows picked by
   *                          the Player from a location.
   */
  @Override
  public void setArrowCount(int numOfPickedArrows) {
    this.arrowCount += numOfPickedArrows;
  }

  /**
   * Method to get the number of arrows which the player holds in any given
   * point of time.
   *
   * @return number of arrows the player currently has.
   */
  @Override
  public int getArrowCount() {
    return this.arrowCount;
  }


  /**
   * This method is used to shoot the arrows in order to kill the Monster
   * Otyughs inside the dungeon.
   *
   * @param direction the direction in which to shoot the arrow.
   * @param distance  the exact distance required to shoot an Otyugh.
   * @return The status of the otyugh which the player tried to kill.
   */
  @Override
  public OtyughStatus shoot(String direction, int distance) throws IllegalStateException {
    if (direction == null) {
      throw new IllegalArgumentException("Direction to shoot cannot be null");
    }
    Location arrowLocation = this.getPlayerLocation();
    Location previousArrowLocation = this.getPlayerLocation();
    if (this.getArrowCount() <= 0) {
      throw new IllegalStateException("You are out of arrows.\n");
    }
    if (distance <= 0) {
      throw new IllegalStateException("You entered an invalid distance. Now "
              + "you will have to move!\n");
    }

    if (direction.equals("N")) {
      if (this.getPlayerLocation().getNorth() != null) {
        this.arrowCount -= 1;
        previousArrowLocation = arrowLocation;
        arrowLocation = arrowLocation.getNorth();
        if (arrowLocation.getCaveType().equals(CaveType.CAVE)) {
          distance -= 1;
        }
        OtyughStatus otyughStatus = shootHelper(arrowLocation, previousArrowLocation, distance);
        return new OtyughStatus(otyughStatus);
      } else {
        throw new IllegalStateException("Ah! You could not shoot the Otyugh.\n");
      }
    } else if (direction.equals("S")) {
      if (this.getPlayerLocation().getSouth() != null) {
        this.arrowCount -= 1;
        previousArrowLocation = arrowLocation;
        arrowLocation = arrowLocation.getSouth();
        if (arrowLocation.getCaveType().equals(CaveType.CAVE)) {
          distance -= 1;
        }
        OtyughStatus otyughStatus = shootHelper(arrowLocation, previousArrowLocation, distance);
        return new OtyughStatus(otyughStatus);
      } else {
        throw new IllegalStateException("Ah! You could not shoot the Otyugh.\n");
      }
    } else if (direction.equals("E")) {
      if (this.getPlayerLocation().getEast() != null) {
        this.arrowCount -= 1;
        previousArrowLocation = arrowLocation;
        arrowLocation = arrowLocation.getEast();
        if (arrowLocation.getCaveType().equals(CaveType.CAVE)) {
          distance -= 1;
        }
        OtyughStatus otyughStatus = shootHelper(arrowLocation, previousArrowLocation, distance);
        return new OtyughStatus(otyughStatus);
      } else {
        throw new IllegalStateException("Ah! You could not shoot the Otyugh.\n");
      }
    } else if (direction.equals("W")) {
      if (this.getPlayerLocation().getWest() != null) {
        this.arrowCount -= 1;
        previousArrowLocation = arrowLocation;
        arrowLocation = arrowLocation.getWest();
        if (arrowLocation.getCaveType().equals(CaveType.CAVE)) {
          distance -= 1;
        }
        OtyughStatus otyughStatus = shootHelper(arrowLocation, previousArrowLocation, distance);
        return new OtyughStatus(otyughStatus);
      } else {
        throw new IllegalStateException("Ah! You could not shoot the Otyugh.\n");
      }
    } else {
      throw new IllegalStateException("Ah! You could not shoot the Otyugh.\n");
    }
  }


  /**
   * Method to check if the player in the dungeon is alive or not.
   *
   * @return true if player is alive, false if player is dead or eaten by
   *        Otyugh.
   */
  @Override
  public boolean checkIfPlayerIsAlive() {
    return this.isAlive;
  }


  private OtyughStatus shootHelper(Location arrowLocation,
                                   Location previousArrowLocation, int distance) {
    while (distance != 0) {
      if (arrowLocation.getCaveType().equals(CaveType.TUNNEL)) {
        if (arrowLocation.getNorth() != null && arrowLocation.getNorth()
                != previousArrowLocation) {
          previousArrowLocation = arrowLocation;
          arrowLocation = arrowLocation.getNorth();
          if (arrowLocation.getCaveType().equals(CaveType.CAVE)) {
            distance -= 1;
          }
        } else if (arrowLocation.getSouth() != null && arrowLocation.getSouth()
                != previousArrowLocation) {
          previousArrowLocation = arrowLocation;
          arrowLocation = arrowLocation.getSouth();
          if (arrowLocation.getCaveType().equals(CaveType.CAVE)) {
            distance -= 1;
          }
        } else if (arrowLocation.getEast() != null && arrowLocation.getEast()
                != previousArrowLocation) {
          previousArrowLocation = arrowLocation;
          arrowLocation = arrowLocation.getEast();
          if (arrowLocation.getCaveType().equals(CaveType.CAVE)) {
            distance -= 1;
          }
        } else {
          if (arrowLocation.getWest() != previousArrowLocation) {
            previousArrowLocation = arrowLocation;
            arrowLocation = arrowLocation.getWest();
            if (arrowLocation.getCaveType().equals(CaveType.CAVE)) {
              distance -= 1;
            }
          }
        }
      } else {
        if (previousArrowLocation == arrowLocation.getWest()) {
          if (arrowLocation.getEast() != null) {
            previousArrowLocation = arrowLocation;
            arrowLocation = arrowLocation.getEast();
            if (arrowLocation.getCaveType().equals(CaveType.CAVE)) {
              distance -= 1;
            }
          } else {
            break;
          }
        } else if (previousArrowLocation == arrowLocation.getEast()) {
          if (arrowLocation.getWest() != null) {
            previousArrowLocation = arrowLocation;
            arrowLocation = arrowLocation.getWest();
            if (arrowLocation.getCaveType().equals(CaveType.CAVE)) {
              distance -= 1;
            }
          } else {
            //put break condition or return condition here.
            break;
          }
        } else if (previousArrowLocation == arrowLocation.getSouth()) {
          if (arrowLocation.getNorth() != null) {
            previousArrowLocation = arrowLocation;
            arrowLocation = arrowLocation.getNorth();
            if (arrowLocation.getCaveType().equals(CaveType.CAVE)) {
              distance -= 1;
            }
          } else {
            //put break condition or return condition here.
            break;
          }
        } else if (previousArrowLocation == arrowLocation.getNorth()) {
          if (arrowLocation.getSouth() != null) {
            previousArrowLocation = arrowLocation;
            arrowLocation = arrowLocation.getSouth();
            if (arrowLocation.getCaveType().equals(CaveType.CAVE)) {
              distance -= 1;
            }
          } else {
            //put break condition or return condition here.
            break;
          }
        }
      }
    }

    if (distance == 0) {
      if (arrowLocation.hasMonster()) {
        //hit monster.
        int previousHealthOfOtyugh = arrowLocation.getMonster().getHealth();
        arrowLocation.deductHealthOfOtyugh();
        int currentHealthOfOtyugh = previousHealthOfOtyugh - 50;

        if (arrowLocation.hasMonster()) {

          return new OtyughStatus(arrowLocation.getCaveId(), previousHealthOfOtyugh,
                  currentHealthOfOtyugh, true);
        } else {

          return new OtyughStatus(arrowLocation.getCaveId(), previousHealthOfOtyugh,
                  currentHealthOfOtyugh, false);
        }

      }
    }
    throw new IllegalStateException("Ah! You could not shoot the Otyugh.\n");
  }


  /**
   * This method is used to get the current location of the player at any
   * given point of time. The location of the player is described in terms of
   * the Cave in which he currently is in at the moment.
   *
   * @return Player's current location.
   */
  @Override
  public Location getPlayerLocation() {
    return playerLocation;
  }


  /**
   * This method is used to set the Player's current location. The location
   * of the player is described in terms of the Cave in which he currently is
   * in at the moment.
   *
   * @param playerLocation It represents the players current location.
   */
  @Override
  public void setPlayerLocation(Location playerLocation) {
    if (playerLocation == null) {
      throw new IllegalArgumentException("Player location cannot be null");
    }
    this.playerLocation = playerLocation;
  }


  /**
   * This method is used to retrieve the players accumulated wealth or
   * treasure. The player collects treasures from the caves as he explores
   * the dungeon.
   *
   * @return List of treasures collected by the Player so far.
   */
  @Override
  public List<Treasure> getWealth() {
    return this.wealth;
  }


  /**
   * This method is used to set the wealth of the Player.The player collects
   * treasures from the caves as he exploresthe dungeon.
   *
   * @param wealth It is the wealth or treasure that the player recently
   *               collected from a cave.
   */
  @Override
  public void setWealth(List<Treasure> wealth) {
    if (wealth == null) {
      throw new IllegalArgumentException("Wealth cannot be null");
    }
    this.wealth.addAll(wealth);
  }


  /**
   * This method is used to move the player in the dungeon as per the
   * directions of the user. If the user asks the player to move north, the
   * player will move north and so on.
   *
   * @param location It represents the direction in which the player wants to
   *                 move in.
   */
  @Override
  public boolean move(String location) throws IllegalArgumentException {
    if (location == null) {
      throw new IllegalArgumentException("location to move cannot be null");
    }
    if (location.equals("E") && getPlayerLocation().getEast() == null
            || location.equals("W") && getPlayerLocation().getWest() == null
            || location.equals("N") && getPlayerLocation().getNorth() == null
            || location.equals("S") && getPlayerLocation().getSouth() == null) {
      throw new IllegalArgumentException("Cannot move in that direction.");
    }
    if (location.equals("E") && getPlayerLocation().getEast() != null) {
      setPlayerLocation(getPlayerLocation().getEast());
      getPlayerLocation().setExploreStatus(true);
      lootPlayer(getPlayerLocation());

      return !checkIfMonsterInLocationWillEatThePlayer(getPlayerLocation());
    } else if (location.equals("W") && getPlayerLocation().getWest() != null) {
      setPlayerLocation(getPlayerLocation().getWest());
      getPlayerLocation().setExploreStatus(true);
      lootPlayer(getPlayerLocation());

      return !checkIfMonsterInLocationWillEatThePlayer(getPlayerLocation());

    } else if (location.equals("N") && getPlayerLocation().getNorth() != null) {
      setPlayerLocation(getPlayerLocation().getNorth());
      getPlayerLocation().setExploreStatus(true);
      lootPlayer(getPlayerLocation());

      return !checkIfMonsterInLocationWillEatThePlayer(getPlayerLocation());

    } else if (location.equals("S") && getPlayerLocation().getSouth() != null) {
      setPlayerLocation(getPlayerLocation().getSouth());
      getPlayerLocation().setExploreStatus(true);
      lootPlayer(getPlayerLocation());

      return !checkIfMonsterInLocationWillEatThePlayer(getPlayerLocation());

    } else {
      return false;
    }

  }

  private boolean checkIfMonsterInLocationWillEatThePlayer(Location playerLocation) {
    if (playerLocation.hasMonster()
            && playerLocation.getMonster().getHealth() == 100) {
      this.isAlive = false;
      return true;
    } else if (playerLocation.hasMonster() && playerLocation.getMonster().getHealth() == 50) {
      if (randomizer.killPlayer() == 1) {
        this.isAlive = false;
        return true;
      }
      return randomizer.killPlayer() == 1;
    }
    return false;
  }

  private void lootPlayer(Location playerLocation) {
    if (playerLocation.hasThief()) {
      this.getWealth().clear();
      playerLocation.setExploreStatus(true);
    }
  }


  /**
   * This method allows the Player to pick up the treasure from its current
   * location. The Player picks up treasure only if the location in which
   * he/she/they are standing has treasures in it.
   */
  @Override
  public void pickUpTreasure() {
    if (getPlayerLocation().getTreasureList().size() > 0) {
      setWealth(getPlayerLocation().getTreasureList());
      getPlayerLocation().getTreasureList().clear();
    }
  }


  /**
   * This method allows the Player to pick up the arrows from its current
   * location. The Player picks up arrows only if the location in which
   * he/she/they are standing has treasures in it.
   */
  @Override
  public void pickUpArrows() {
    if (getPlayerLocation().getArrowCountInLocation() > 0) {
      setArrowCount(getPlayerLocation().getArrowCountInLocation());
      getPlayerLocation().resetArrowCount();
    }
  }


}
