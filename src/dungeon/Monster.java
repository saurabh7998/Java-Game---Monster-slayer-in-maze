package dungeon;

/**
 * This class represents the Monster which are smelly isolated creatures that
 * are available in the dungeon locations. Their health reduces by 50 each
 * time they are shot. Once the health reaches zero, they die.
 */
class Monster {

  private int health;

  public Monster(int health) {
    this.health = health;
  }


  public void deductHealth(int health) {
    this.health -= health;
  }

  public int getHealth() {
    return this.health;
  }
}
