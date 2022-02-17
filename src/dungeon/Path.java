package dungeon;

/**
 * This class represents a dungeon.Path between two locations or caves in the dungeon.
 * A path is made up of a source cave and a destination cave.
 */
class Path {

  private Location source;
  private Location destination;

  /**
   * Constructs a Path object.
   *
   * @param source      It represents the source location in the path.
   * @param destination It represents the destination location in the path.
   */
  public Path(Location source, Location destination) {
    if (source == null || destination == null) {
      throw new IllegalArgumentException("Source cave or Destination cave "
              + "cannot be null");
    }
    this.source = source;
    this.destination = destination;
  }


  public Location getSource() {
    return this.source;
  }

  public Location getDestination() {
    return this.destination;
  }
}
