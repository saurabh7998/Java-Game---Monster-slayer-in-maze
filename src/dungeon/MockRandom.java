package dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents an Entity that generates mock random numbers for the
 * use in our dungeon implementation. This class is specifically implemented
 * to generate random values for testing a NON-WRAPPING type of dungeon.
 */
public class MockRandom implements Randomizer {

  private List<Integer> randomLocationsInDungeon;

  private List<Integer> randomArrowLocation;

  private List<Integer> randomThiefLocations;

  int max;

  /**
   * Constructs a mock random object.
   * Initialises the randomNumbers list which is used to get predictable
   * random numbers.
   */
  public MockRandom() {
    this.randomLocationsInDungeon = new ArrayList<>();
    this.randomArrowLocation = new ArrayList<>();
    this.randomThiefLocations = new ArrayList<>();
    randomLocationsInDungeon.add(0);
    randomLocationsInDungeon.add(2);
    randomLocationsInDungeon.add(4);
    randomLocationsInDungeon.add(5);
    randomLocationsInDungeon.add(7);
    randomLocationsInDungeon.add(6);
    randomLocationsInDungeon.add(10);
    randomLocationsInDungeon.add(11);
    randomLocationsInDungeon.add(1);
    randomLocationsInDungeon.add(3);
    randomLocationsInDungeon.add(8);
    randomLocationsInDungeon.add(9);

    randomArrowLocation.add(1);
    randomArrowLocation.add(2);
    randomArrowLocation.add(7);
    randomArrowLocation.add(4);
    randomArrowLocation.add(5);
    randomArrowLocation.add(6);

    randomThiefLocations.add(6);
    randomThiefLocations.add(7);

  }

  /**
   * This is a method to generate a random number between a minimum and
   * maximum threshold specified by the user or caller of the method.
   *
   * @param min It represents the minimum threshold of the range in which
   *            you want the random number.
   * @param max It represents the minimum threshold of the range in which
   *            you want the random number.
   * @return Integer Random number in the range.
   */
  @Override
  public int getRandomValueInRange(int min, int max) {
    return ThreadLocalRandom.current().nextInt(max - min) + min;
  }

  /**
   * This is a method to generate a random value from zero to a maximum
   * threshold provided by the user or the caller.
   *
   * @param max It represents the maximum threshold of the random value
   *            to be generated.
   * @return integer random value.
   */
  @Override
  public int getRandomValue(int max) {
    return max / 2;
  }


  /**
   * To get a random start location and stop location for the PLayer to move
   * along a path which will be of at least length 5.
   *
   * @param min min bound of the range in which you want the random value
   * @param max max bound of the range in which you want the random value
   * @return List of  2 random numbers which will be selected as start and stop
   */
  @Override
  public List<Integer> getStartStopRandom(int min, int max) {
    List<Integer> list = new ArrayList<>();
    list.add(2);
    list.add(9);
    return list;
  }

  /**
   * This method is used to select a random path from the list of total paths
   * in order to build a dungeon.
   *
   * @return Index of the random path to be selected from the List of Paths.
   */
  @Override
  public int getRandomPathIndex() {
    if (randomLocationsInDungeon.size() > 0) {
      int op = randomLocationsInDungeon.get(0);
      randomLocationsInDungeon.remove(0);
      return op;
    } else {
      return 0;
    }
  }

  /**
   * To set the random max limit  set the random numbers list and clear the
   * list everytime the method is called in order to generate unique random
   * numbers.
   *
   * @param max It is the maximum size of the random numbers list.
   */
  @Override
  public void randomMax(int max) {
    this.max = max;
  }


  /**
   * Method to randomly determine whether the half injured monster will eat
   * the player or not.
   *
   * @return 1 if monster will kill the player, 0 if not.
   */
  @Override
  public int killPlayer() {
    return 0;
  }

  /**
   * Method to get random locations in the dungeon to put arrows in.
   *
   * @param dungeonGridSize It represents the number of locations in dungeon.
   * @return any random location id from the dungeon.
   */
  @Override
  public int getRandomLocationToPutArrow(int dungeonGridSize) {
    if (randomArrowLocation.size() > 0) {
      int op = randomArrowLocation.get(0);
      randomArrowLocation.remove(0);
      return op;
    } else {
      return 0;
    }
  }


  /**
   * Method to add 2 thieves in any 2 random tunnels.
   *
   * @param loweBound  It represents the lower bound of the range of tunnel
   *                   locations list size.
   * @param upperBound It represents the upper bound of the range of the
   *                   tunnel location list size.
   * @return random tunnel id to add the thief in.
   */
  @Override
  public int addThiefToTwoRandomTunnels(int loweBound, int upperBound) {
    int thiefLocation = randomThiefLocations.get(0);
    randomThiefLocations.remove(0);
    return thiefLocation;
  }


}
