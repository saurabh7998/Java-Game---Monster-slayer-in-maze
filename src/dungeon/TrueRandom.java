package dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


/**
 * This class represents an Entity that generates truly random numbers for the
 * use in our dungeon implementation.
 */
public class TrueRandom implements Randomizer {

  private Random random = new Random();
  private List<Path> pathList = new ArrayList<>();

  private List<Integer> randomNumbers;


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
    return random.nextInt(max - min) + min;
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
    return random.nextInt(max);
  }

  @Override
  public List<Integer> getStartStopRandom(int min, int max) {
    List<Integer> startStopList = new ArrayList<>();
    startStopList.add(random.nextInt(max - min) + min);
    startStopList.add(random.nextInt(max - min) + min);
    return startStopList;
  }


  /**
   * This method is used to select a random path from the list of total paths
   * in order to build a dungeon.
   *
   * @return Index of the random path to be selected from the List of Paths.
   */
  @Override
  public int getRandomPathIndex() {
    if (this.randomNumbers.size() == 1) {
      int output = this.randomNumbers.get(0);
      this.randomNumbers.remove(0);
      return output;
    } else if (this.randomNumbers.size() == 0) {
      return 0;
    } else {
      int randomNumberIndex = ThreadLocalRandom
              .current()
              .nextInt(0, this.randomNumbers.size());
      int output = this.randomNumbers.get(randomNumberIndex);
      this.randomNumbers.remove(randomNumberIndex);
      return output;
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
    this.randomNumbers = new ArrayList<>();
    this.randomNumbers.clear();
    for (int i = 0; i < max; i++) {
      this.randomNumbers.add(i);
    }
  }

  /**
   * Method to randomly determine whether the half injured monster will eat
   * the player or not.
   *
   * @return 1 if monster will kill the player, 0 if not.
   */
  @Override
  public int killPlayer() {
    return random.nextInt(2);
  }

  /**
   * Method to get random locations in the dungeon to put arrows in.
   *
   * @param dungeonGridSize It represents the number of locations in dungeon.
   * @return any random location id from the dungeon.
   */
  @Override
  public int getRandomLocationToPutArrow(int dungeonGridSize) {
    return getRandomValue(dungeonGridSize);
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
    return getRandomValueInRange(loweBound, upperBound);
  }

}
