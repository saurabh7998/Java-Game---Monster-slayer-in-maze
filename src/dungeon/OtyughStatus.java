package dungeon;

class OtyughStatus {

  private int otyughLocation;
  private int otyughPreviousHealth;
  private int otyughCurrentHealth;
  private boolean isDead;

  /**
   * Constructor to create the OtyughStatus object.
   *
   * @param otyughLocation       It represents the Otyugh's location id.
   * @param otyughPreviousHealth It represents the health of Otyugh before
   *                             being shot.
   * @param otyughCurrentHealth  It represents the current health of the
   *                             Otyugh.
   * @param isDead               It represents the status of Otyugh, true
   *                             if alive, false if dead.
   */
  public OtyughStatus(int otyughLocation, int otyughPreviousHealth,
                      int otyughCurrentHealth, boolean isDead) {
    this.otyughLocation = otyughLocation;
    this.otyughPreviousHealth = otyughPreviousHealth;
    this.otyughCurrentHealth = otyughCurrentHealth;
    this.isDead = isDead;
  }

  /**
   * Method to get the location of the Otyugh.
   *
   * @return Location id
   */
  public int getOtyughLocation() {
    return otyughLocation;
  }

  /**
   * Method to get the previous health of the Otyugh.
   *
   * @return previous health of the Otyugh.
   */
  public int getOtyughPreviousHealth() {
    return otyughPreviousHealth;
  }

  /**
   * Method to get the current health of the Otyugh.
   *
   * @return current health of the otyugh.
   */
  public int getOtyughCurrentHealth() {
    return otyughCurrentHealth;
  }

  public boolean isDead() {
    return isDead;
  }

  /**
   * Copy Constructor.
   *
   * @param otyughStatus It represents the status of the Otyugh in terms of
   *                     previous and current health.
   */
  public OtyughStatus(OtyughStatus otyughStatus) {
    this(otyughStatus.getOtyughLocation(),
            otyughStatus.getOtyughPreviousHealth(),
            otyughStatus.getOtyughCurrentHealth(), otyughStatus.isDead());
  }


}
