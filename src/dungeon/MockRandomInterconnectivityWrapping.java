package dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents an Entity that generates mock random numbers for the
 * use in our dungeon implementation. This class is specifically implemented
 * to generate random values for testing a WRAPPING type of dungeon which has
 * an interconnectivity greater than zero.
 */
public class MockRandomInterconnectivityWrapping implements Randomizer {

  private  List<Integer> randomLocationInDungeon;
  private List<Integer> randomArrowLocation;

  /**
   * Constructs a MockRandomIWrapping object to produce predictable random
   * values for the Wrapping dungeon with interconnectivity.
   */
  public MockRandomInterconnectivityWrapping() {
    this.randomLocationInDungeon = new ArrayList<>();
    this.randomArrowLocation = new ArrayList<>();
    randomLocationInDungeon.add(2);
    randomLocationInDungeon.add(1);
    randomLocationInDungeon.add(12);
    randomLocationInDungeon.add(7);
    randomLocationInDungeon.add(13);
    randomLocationInDungeon.add(5);
    randomLocationInDungeon.add(4);
    randomLocationInDungeon.add(16);
    randomLocationInDungeon.add(17);
    randomLocationInDungeon.add(3);
    randomLocationInDungeon.add(8);
    randomLocationInDungeon.add(12);

    randomArrowLocation.add(1);
    randomArrowLocation.add(2);
    randomArrowLocation.add(7);
    randomArrowLocation.add(4);
    randomArrowLocation.add(5);
    randomArrowLocation.add(6);
  }

  /**
   * This is a method to generate a fixed random number between a minimum and
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
   * This is a method to generate a fixed value from zero to a maximum
   * threshold provided by the user or the caller.
   *
   * @param max It represents the maximum threshold of the random value
   *            to be generated.
   * @return integer fixed value.
   */
  @Override
  public int getRandomValue(int max) {
    return max / 2;
  }


  /**
   * To get a fixed start location and stop location for the PLayer to move
   * along a path which will be of at least length 5.
   *
   * @param min it represents the minimum bound
   * @param max it represents the max bound
   * @return List of fixed numbers.
   */
  @Override
  public List<Integer> getStartStopRandom(int min, int max) {
    List<Integer> list = new ArrayList<>();
    list.add(3);
    list.add(9);
    return list;
  }


  /**
   * This method is used to select a fixed path from the list of total paths
   * in order to build a dungeon.
   *
   * @return Index of the fixed path to be selected from the List of Paths.
   */
  @Override
  public int getRandomPathIndex() {
    if (randomLocationInDungeon.size() > 0) {
      int op = randomLocationInDungeon.get(0);
      randomLocationInDungeon.remove(0);
      return op;
    } else {
      return 0;
    }
  }

  /**
   * To set the fixed random max limit  set the random numbers list and clear
   * list everytime the method is called in order to generate unique random
   * numbers.
   *
   * @param max It is the maximum size of the random numbers list.
   */
  @Override
  public void randomMax(int max) {
    //this.randomNumbers.clear();
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
    return 0;
  }
}
