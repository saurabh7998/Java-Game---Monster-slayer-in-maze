package dungeon;


import java.util.List;

/**
 * This interface is created for the purpose of creating true and mock random
 * values that shall be used in our Dungeon model.
 */
public interface Randomizer {

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
  int getRandomValueInRange(int min, int max);

  /**
   * This is a method to generate a random value from zero to a maximum
   * threshold provided by the user or the caller.
   *
   * @param max It represents the maximum threshold of the random value
   *            to be generated.
   * @return integer random value.
   */
  int getRandomValue(int max);


  /**
   * To get a random start location and stop location for the PLayer to move
   * along a path which will be of at least length 5.
   *
   * @param min min bound of the range in which you want the random value
   * @param max max bound of the range in which you want the random value
   * @return List of  2 random numbers which will be selected as start and stop
   */
  List<Integer> getStartStopRandom(int min, int max);


  /**
   * This method is used to select a random path from the list of total paths
   * in order to build a dungeon.
   *
   * @return Index of the random path to be selected from the List of Paths.
   */
  public int getRandomPathIndex();

  /**
   * To set the random max limit  set the random numbers list and clear the
   * list everytime the method is called in order to generate unique random
   * numbers.
   *
   * @param max It is the maximum size of the random numbers list.
   */
  public void randomMax(int max);

  /**
   * Method to randomly determine whether the half injured monster will eat
   * the player or not.
   *
   * @return 1 if monster will kill the player, 0 if not.
   */
  public int killPlayer();

  /**
   * Method to get random locations in the dungeon to put arrows in.
   *
   * @param dungeonGridSize It represents the number of locations in dungeon.
   * @return any random location id from the dungeon.
   */
  public int getRandomLocationToPutArrow(int dungeonGridSize);

  /**
   * Method to add 2 thieves in any 2 random tunnels.
   *
   * @param loweBound  It represents the lower bound of the range of tunnel
   *                   locations list size.
   * @param upperBound It represents the upper bound of the range of the
   *                   tunnel location list size.
   * @return random tunnel id to add the thief in.
   */
  public int addThiefToTwoRandomTunnels(int loweBound, int upperBound);
}
