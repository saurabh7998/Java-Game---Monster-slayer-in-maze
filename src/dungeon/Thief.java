package dungeon;

/**
 * This class represents the Thief. Thief is a person who loots the player
 * and steals all his/her/their treasures. Thieves are only found in tunnel
 * locations of the dungeon.
 */
public class Thief {

  private String name;

  /**
   * Constructor to create a thief object.
   */
  public Thief(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Thief name cannot be null");
    }
    this.name = name;
  }

  /**
   * Method to get the name of the thief.
   *
   * @return name of the thief
   */
  public String getName() {
    return this.name;
  }
}
