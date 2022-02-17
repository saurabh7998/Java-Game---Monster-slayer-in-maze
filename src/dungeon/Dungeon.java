package dungeon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * This class represents the main dungeon grid. It builds the 2-Dimensional
 * Dungeon grid which consists of caves and tunnels. This dungeon is build in
 * such a way that every cave inside is connected to every other cave through
 * at least one path. The size and the interconnectivity of the dungeon
 * varies based on the user input. The dungeon can be wrapping or non
 * wrapping based on user inputs.
 */
public class Dungeon implements DungeonInterface {
  private int row;
  private int column;
  private int interconnectivity;
  private DungeonType dungeonType;
  private int treasurePercentage;
  private int difficulty;

  private List<Path> pathList;
  private List<Location> caveList;

  private Randomizer randomizer;

  private int[] father;

  private int edgeCount;
  private List<Integer> caveLocations;
  private List<Integer> tunnelLocations;


  private double frequencyOfTreasures;

  private List<Path> finalSelectedPathsForDungeon;

  private Location[][] dungeonGrid;

  private PlayerInterface player;
  private int[][] adj;
  Stack<Integer> startToStopPath;

  private boolean shotOrNot;

  /**
   * Constructs a Dungeon with dimensions m rows and n columns as specified
   * by the user. It also sets the desired interconnectivity, dungeon type
   * and treasure percentage.
   *
   * @param row                It is the number of rows in the dungeon grid.
   * @param column             It is the number of columns in the dungeon grid.
   * @param interconnectivity  It is the amount of interconnectivity in the
   *                           dungeon grid.
   * @param dungeonType        It is the type of the dungeon grid.
   * @param treasurePercentage It is the minimum percentage of caves that
   *                           need to have treasure.
   * @param randomizer         It is a randomizer object used to control the
   *                           randomness of the model.
   */
  public Dungeon(int row, int column, int interconnectivity,
                 DungeonType dungeonType, int treasurePercentage,
                 Randomizer randomizer, int difficulty) {

    if (row < 3 || column < 3) {
      throw new IllegalArgumentException("Row or column count cannot be less "
              + "than 3");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("Interconnectivity cannot be less "
              + "than 0");
    }
    if (treasurePercentage > 100 || treasurePercentage < 0) {
      throw new IllegalArgumentException("Treasure percent cannot be greater "
              + "than 100 or less than 0");
    }
    if (randomizer == null) {
      throw new IllegalArgumentException("randomizer cannot be null");
    }
    if (dungeonType == null) {
      throw new IllegalArgumentException("Dungeon Type cannot be null");
    }

    this.row = row;
    this.column = column;
    this.interconnectivity = interconnectivity;
    this.dungeonType = dungeonType;
    this.treasurePercentage = treasurePercentage;
    this.randomizer = randomizer;
    this.difficulty = difficulty;

    father = new int[this.row * this.column + 1];
    for (int i = 0; i < father.length; i++) {
      father[i] = i;
    }
    finalSelectedPathsForDungeon = new ArrayList<>();
    caveLocations = new ArrayList();
    tunnelLocations = new ArrayList();
    startToStopPath = new Stack<>();
    edgeCount = 0;
    player = new Player(this.randomizer);
    shotOrNot = false;

    buildDungeonGrid();
    pathBuilder();
    buildMst();
    addInterconnectivity();
    setDirections();
    putTreasureInCaves();
    Stack<Integer> path = new Stack<>();
    try {
      path = findPathFromStartToStop();
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
      System.exit(0);
    }
    putMonsterInCaves();
    putArrowsInDungeon();
    setLocationNeighbours();
    addThiefInDungeon();
  }


  /**
   * Method to build the dungeon grid. This method constructs a 2-dimensional
   * grid of dimensions m x n, where m and n are the number of rows and
   * columns specified by the user.
   */
  @Override
  public void buildDungeonGrid() {
    caveList = new ArrayList<>();
    dungeonGrid = new Cave[this.row][this.column];
    int count = 1;
    for (int i = 0; i < this.row; i++) {
      for (int j = 0; j < this.column; j++) {
        dungeonGrid[i][j] = new Cave(count);
        caveList.add(dungeonGrid[i][j]);
        count++;
      }
    }
  }


  /**
   * This is a method to build a list of all possible paths that can be
   * connected in a dungeon of size m x n. Random paths are then selected
   * from the list built by this method to implement our dungeon model.
   */
  @Override
  public void pathBuilder() {
    if (this.dungeonType == DungeonType.NON_WRAPPING) {
      buildNormalDungeonPaths();
    }

    if (this.dungeonType == DungeonType.WRAPPING) {
      buildWrappingDungeonPaths();
    }


  }


  private void buildNormalDungeonPaths() {
    pathList = new ArrayList<>();

    for (int i = 1; i <= (row * column); i++) {

      Location cave = caveList.get(i - 1);

      if (i % column != 0 && i <= (row * column - column)) {
        pathList.add(new Path(cave, new Cave(i + 1)));
        pathList.add(new Path(cave, new Cave(i + column)));
      } else if (i <= (row * column - column)) {
        pathList.add(new Path(cave, new Cave(i + column)));
      } else {
        if (i < (row * column)) {
          pathList.add(new Path(cave, new Cave(i + 1)));
        }
      }
    }
  }

  private void buildWrappingDungeonPaths() {
    pathList = new ArrayList<>();
    int wrap = 1;
    for (int i = 1; i <= (row * column); i++) {
      Location cave = caveList.get(i - 1);
      if (i % column != 0) {
        pathList.add(new Path(cave, new Cave(i + 1)));
      } else if (i % column == 0) {
        pathList.add(new Path(cave, new Cave(wrap)));
        wrap += column;
      }
    }

    wrap = 1;
    for (int i = 1; i <= (row * column); i++) {
      Location cave = caveList.get(i - 1);
      if (i <= (row * column) - column) {
        pathList.add(new Path(cave, new Cave(i + column)));
      } else if (i > (row * column) - column) {
        pathList.add(new Path(cave, new Cave(wrap)));
        wrap += 1;
      }
    }
  }


  /**
   * This is a method to build a minimum spanning tree that spans our
   * 2-dimensional dungeon grid. It selects random paths such that the
   * interconnectivity of our dungeon stays zero.
   */
  @Override
  public void buildMst() {

    while (edgeCount < (row * column)) {
      if (edgeCount >= row * column - 1) {
        break;
      }

      randomizer.randomMax(pathList.size());
      Path p = pathList.get(randomizer.getRandomPathIndex());
      int sourceId = p.getSource().getCaveId();
      int destId = p.getDestination().getCaveId();

      int a = -1;
      int b = -1;

      if (getRootParent(sourceId) != getRootParent(destId)) {
        a = sourceId;
        b = destId;
        join(a, b);
        //put path p in selected path list.
        finalSelectedPathsForDungeon.add(p);
        edgeCount++;
      }
    }

  }


  /**
   * This method is used to add interconnectivity to our dungeon model.
   * Additional paths are selected from the total list of paths and added to
   * the dungeon grid to increase the interconnectivity of the dungeon.
   */
  @Override
  public void addInterconnectivity() {
    for (int i = 0; i < this.interconnectivity; i++) {
      if (pathList.size() == 0) {
        break;
      }

      randomizer.randomMax(pathList.size());
      Path additionalPath = pathList.get(randomizer.getRandomPathIndex());

      while (finalSelectedPathsForDungeon.contains(additionalPath)) {
        additionalPath = pathList.get(randomizer.getRandomPathIndex());
      }
      finalSelectedPathsForDungeon.add(additionalPath);
    }

  }


  /**
   * This method is used to set the directions of the caves or locations in
   * our 2-dimensional dungeon grid.
   * The directions are set based on the cave's position in the grid.
   */
  @Override
  public void setDirections() {
    if (this.dungeonType == DungeonType.NON_WRAPPING) {
      setNormalDungeonDirections();
    }
    if (this.dungeonType == DungeonType.WRAPPING) {
      setWrappingDungeonDirections();
    }
    createAdjMatrix();
  }

  private void setNormalDungeonDirections() {
    for (int i = 0; i < finalSelectedPathsForDungeon.size(); i++) {
      Location srcCave = finalSelectedPathsForDungeon.get(i).getSource();
      Location destCave = finalSelectedPathsForDungeon.get(i).getDestination();

      if (Math.abs(srcCave.getCaveId() - destCave.getCaveId()) == 1
              && srcCave.getCaveId() < destCave.getCaveId()) {
        DungeonIndex srcCaveIndexInDungeon = getIndex(srcCave.getCaveId());
        DungeonIndex destCaveIndexInDungeon = getIndex(destCave.getCaveId());
        dungeonGrid[srcCaveIndexInDungeon.getIndexX()][srcCaveIndexInDungeon.getIndexY()]
                .setEast(dungeonGrid[destCaveIndexInDungeon
                        .getIndexX()][destCaveIndexInDungeon.getIndexY()]);
        dungeonGrid[destCaveIndexInDungeon.getIndexX()][destCaveIndexInDungeon
                .getIndexY()].setWest(dungeonGrid[srcCaveIndexInDungeon
                .getIndexX()][srcCaveIndexInDungeon.getIndexY()]);
      }
      if (Math.abs(srcCave.getCaveId() - destCave.getCaveId()) > 1
              && srcCave.getCaveId() < destCave.getCaveId()) {
        DungeonIndex srcCaveIndexInDungeon = getIndex(srcCave.getCaveId());
        DungeonIndex destCaveIndexInDungeon = getIndex(destCave.getCaveId());
        dungeonGrid[srcCaveIndexInDungeon.getIndexX()][srcCaveIndexInDungeon.getIndexY()]
                .setSouth(dungeonGrid[destCaveIndexInDungeon
                        .getIndexX()][destCaveIndexInDungeon.getIndexY()]);
        dungeonGrid[destCaveIndexInDungeon.getIndexX()][destCaveIndexInDungeon
                .getIndexY()].setNorth(dungeonGrid[srcCaveIndexInDungeon
                .getIndexX()][srcCaveIndexInDungeon.getIndexY()]);
      }
    }
  }

  private void setWrappingDungeonDirections() {
    for (int i = 0; i < finalSelectedPathsForDungeon.size(); i++) {
      Location srcCave = finalSelectedPathsForDungeon.get(i).getSource();
      Location destCave = finalSelectedPathsForDungeon.get(i).getDestination();

      if (Math.abs(srcCave.getCaveId() - destCave.getCaveId()) == 1
              && srcCave.getCaveId() < destCave.getCaveId()) {
        DungeonIndex srcCaveIndexInDungeon = getIndex(srcCave.getCaveId());
        DungeonIndex destCaveIndexInDungeon = getIndex(destCave.getCaveId());
        dungeonGrid[srcCaveIndexInDungeon.getIndexX()][srcCaveIndexInDungeon.getIndexY()]
                .setEast(dungeonGrid[destCaveIndexInDungeon
                        .getIndexX()][destCaveIndexInDungeon.getIndexY()]);
        dungeonGrid[destCaveIndexInDungeon.getIndexX()][destCaveIndexInDungeon.getIndexY()]
                .setWest(dungeonGrid[srcCaveIndexInDungeon
                        .getIndexX()][srcCaveIndexInDungeon.getIndexY()]);

      } else if (Math.abs(srcCave.getCaveId() - destCave.getCaveId()) > 1
              && (srcCave.getCaveId() == destCave.getCaveId() - dungeonGrid[0].length)) {
        DungeonIndex srcCaveIndexInDungeon = getIndex(srcCave.getCaveId());
        DungeonIndex destCaveIndexInDungeon = getIndex(destCave.getCaveId());
        dungeonGrid[srcCaveIndexInDungeon.getIndexX()][srcCaveIndexInDungeon.getIndexY()]
                .setSouth(dungeonGrid[destCaveIndexInDungeon
                        .getIndexX()][destCaveIndexInDungeon.getIndexY()]);
        dungeonGrid[destCaveIndexInDungeon.getIndexX()][destCaveIndexInDungeon
                .getIndexY()].setNorth(dungeonGrid[srcCaveIndexInDungeon
                .getIndexX()][srcCaveIndexInDungeon.getIndexY()]);
      } else if (Math.abs(srcCave.getCaveId() - destCave.getCaveId()) > 1
              && (srcCave.getCaveId() == destCave.getCaveId() + (column - 1))) {
        DungeonIndex srcCaveIndexInDungeon = getIndex(srcCave.getCaveId());
        DungeonIndex destCaveIndexInDungeon = getIndex(destCave.getCaveId());
        dungeonGrid[srcCaveIndexInDungeon.getIndexX()][srcCaveIndexInDungeon.getIndexY()]
                .setEast(dungeonGrid[destCaveIndexInDungeon
                        .getIndexX()][destCaveIndexInDungeon.getIndexY()]);
        dungeonGrid[destCaveIndexInDungeon.getIndexX()][destCaveIndexInDungeon
                .getIndexY()].setWest(dungeonGrid[srcCaveIndexInDungeon
                .getIndexX()][srcCaveIndexInDungeon.getIndexY()]);
      } else if (Math.abs(srcCave.getCaveId() - destCave.getCaveId()) > 1
              && (srcCave.getCaveId() == destCave.getCaveId() + ((row * column) - column))) {
        DungeonIndex srcCaveIndexInDungeon = getIndex(srcCave.getCaveId());
        DungeonIndex destCaveIndexInDungeon = getIndex(destCave.getCaveId());
        dungeonGrid[srcCaveIndexInDungeon.getIndexX()][srcCaveIndexInDungeon.getIndexY()]
                .setSouth(dungeonGrid[destCaveIndexInDungeon
                        .getIndexX()][destCaveIndexInDungeon.getIndexY()]);
        dungeonGrid[destCaveIndexInDungeon.getIndexX()][destCaveIndexInDungeon
                .getIndexY()].setNorth(dungeonGrid[srcCaveIndexInDungeon
                .getIndexX()][srcCaveIndexInDungeon.getIndexY()]);
      } else {
        //Do nothing.
      }

    }
  }


  /**
   * This method is used to track of the neighbours of any particular location
   * (cave or a tunnel) in all four directions.
   */
  private void setLocationNeighbours() {
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < column; j++) {
        setLocationNeighbourHelper(i, j);
      }
    }
  }

  private void setLocationNeighbourHelper(int i, int j) {
    if (dungeonGrid[i][j].getNorth() != null) {
      dungeonGrid[i][j].setCaveNeighbours("N");
    }
    if (dungeonGrid[i][j].getEast() != null) {
      dungeonGrid[i][j].setCaveNeighbours("E");
    }
    if (dungeonGrid[i][j].getSouth() != null) {
      dungeonGrid[i][j].setCaveNeighbours("S");
    }
    if (dungeonGrid[i][j].getWest() != null) {
      dungeonGrid[i][j].setCaveNeighbours("W");
    }
  }


  /**
   * This method is used to identify and recognize the locations in our
   * dungeon into two categories: Cave, and Tunnel.
   * If a location has exactly 2 locations connected to it, then it is a
   * TUNNEL, otherwise it is a CAVE.
   */

  private void identifyCavesAndTunnels() {
    for (int i = 0; i < this.row; i++) {
      for (int j = 0; j < this.column; j++) {
        if ((dungeonGrid[i][j].getEast() != null
                && dungeonGrid[i][j].getWest() != null && dungeonGrid[i][j].getNorth() == null
                && dungeonGrid[i][j].getSouth() == null)
                || (dungeonGrid[i][j].getEast() != null && dungeonGrid[i][j].getNorth() != null
                && dungeonGrid[i][j].getWest() == null && dungeonGrid[i][j].getSouth() == null)
                || (dungeonGrid[i][j].getEast() != null && dungeonGrid[i][j].getSouth() != null
                && dungeonGrid[i][j].getWest() == null && dungeonGrid[i][j].getNorth() == null)
                || (dungeonGrid[i][j].getWest() != null && dungeonGrid[i][j].getNorth() != null
                && dungeonGrid[i][j].getEast() == null && dungeonGrid[i][j].getSouth() == null)
                || (dungeonGrid[i][j].getWest() != null && dungeonGrid[i][j].getSouth() != null
                && dungeonGrid[i][j].getEast() == null && dungeonGrid[i][j].getNorth() == null)
                || (dungeonGrid[i][j].getNorth() != null && dungeonGrid[i][j].getSouth() != null
                && dungeonGrid[i][j].getEast() == null && dungeonGrid[i][j].getWest() == null)) {
          dungeonGrid[i][j].setCaveType(CaveType.TUNNEL);
          tunnelLocations.add(dungeonGrid[i][j].getCaveId());
        } else {
          dungeonGrid[i][j].setCaveType(CaveType.CAVE);
          caveLocations.add(dungeonGrid[i][j].getCaveId());
        }
      }
    }
  }


  /**
   * This method is used to add or assign treasures into the caves.
   * The treasures can only be added to cave and not inside tunnels. The
   * treasure is added to random caves based on the user input which is
   * provided in percentage.
   */
  @Override
  public void putTreasureInCaves() {
    identifyCavesAndTunnels();
    double numberOfCavesWithTreasure =
            (caveLocations.size() * this.treasurePercentage) / 100;

    for (int i = 0; i < numberOfCavesWithTreasure; i++) {
      int c =
              caveLocations.get(randomizer.getRandomValueInRange(0,
                      caveLocations.size()));
      DungeonIndex index = getIndex(c);

      int removeCaveWithAddedTreasure = caveLocations.indexOf(c);
      caveLocations.remove(removeCaveWithAddedTreasure);

      int randomTreasure = randomizer.getRandomValue(3) + 1;
      if (randomTreasure == 1) {
        dungeonGrid[index.getIndexX()][index.getIndexY()].setTreasureList(Treasure.RUBY);
        frequencyOfTreasures += 1;
      } else if (randomTreasure == 2) {
        dungeonGrid[index.getIndexX()][index.getIndexY()].setTreasureList(Treasure.DIAMOND);
        dungeonGrid[index.getIndexX()][index.getIndexY()].setTreasureList(Treasure.DIAMOND);
        frequencyOfTreasures += 2;
      } else if (randomTreasure == 3) {
        dungeonGrid[index.getIndexX()][index.getIndexY()].setTreasureList(Treasure.SAPPHIRE);
        dungeonGrid[index.getIndexX()][index.getIndexY()].setTreasureList(Treasure.SAPPHIRE);
        dungeonGrid[index.getIndexX()][index.getIndexY()].setTreasureList(Treasure.SAPPHIRE);
        frequencyOfTreasures += 3;
      }
    }

  }

  /**
   * This is a method to put arrows in the dungeon. The arrows can be in both
   * tunnels and caves and their frequency is same as that of the treasures
   * in the dungeon.
   */
  @Override
  public void putArrowsInDungeon() {
    double numberOfCavesWithArrows = frequencyOfTreasures;
    int tempArrowList = 0;

    for (int i = 0; i < numberOfCavesWithArrows; i++) {
      int randomLocation =
              randomizer.getRandomLocationToPutArrow(this.row
                      * this.column) + 1;
      DungeonIndex randomLocationIndex = getIndex(randomLocation);
      dungeonGrid[randomLocationIndex.getIndexX()][randomLocationIndex.getIndexY()]
              .addArrowToLocation();
      tempArrowList += 1;
    }
  }


  /**
   * Method to add the thieves into the dungeon. This method will add only 2
   * thieves into any 2 unique tunnels. Thieves are entities that loot all the
   * treasure from the player.
   */
  @Override
  public void addThiefInDungeon() {
    identifyCavesAndTunnels();
    double numberOfCavesWithThief = 2;

    Thief thiefOne = new Thief("Helsinki");
    Thief thiefTwo = new Thief("Berlin");
    ArrayList<Thief> thieves = new ArrayList<>();
    thieves.add(thiefOne);
    thieves.add(thiefTwo);

    for (int i = 0; i < numberOfCavesWithThief; i++) {
      int c =
              tunnelLocations.get(randomizer.addThiefToTwoRandomTunnels(0,
                      tunnelLocations.size()));
      DungeonIndex index = getIndex(c);

      int removeCaveWithAddedTreasure = tunnelLocations.indexOf(c);
      tunnelLocations.remove(removeCaveWithAddedTreasure);

      dungeonGrid[index.getIndexX()][index.getIndexY()].addThief(thieves.get(0));
      thieves.remove(0);
    }
  }


  /**
   * This is a method that allows us to put the specified number of monsters
   * in to the dungeon caves. If the number of monsters specified are greater
   * than the number of caves in the dungeon, then all the caves in the
   * dungeons are assigned monsters inside them.
   */
  @Override
  public void putMonsterInCaves() {
    caveLocations.clear();
    identifyCavesAndTunnels();


    int startCaveId = startToStopPath.get(0);
    int endCaveId = startToStopPath.get(startToStopPath.size() - 1);

    caveLocations.remove(caveLocations.indexOf(endCaveId));
    caveLocations.remove(caveLocations.indexOf(startCaveId));

    List<Location> cavesWithMonsters = new ArrayList<>();
    DungeonIndex endCaveIndexInDungeon = getIndex(endCaveId);
    dungeonGrid[endCaveIndexInDungeon.getIndexX()]
            [endCaveIndexInDungeon.getIndexY()].addMonster(new Monster(100));
    cavesWithMonsters.add(dungeonGrid[endCaveIndexInDungeon.getIndexX()]
            [endCaveIndexInDungeon.getIndexY()]);
    this.difficulty -= 1;

    int numOfIteration = caveLocations.size();

    if (this.difficulty > caveLocations.size()) {
      for (int i = 0; i < numOfIteration; i++) {
        Monster otyugh = new Monster(100);

        int randomCave =
                caveLocations.get(randomizer.getRandomValueInRange(0,
                        caveLocations.size()));
        DungeonIndex index = getIndex(randomCave);

        int removeCaveWithAddedMonster = caveLocations.indexOf(randomCave);
        caveLocations.remove(removeCaveWithAddedMonster);

        dungeonGrid[index.getIndexX()][index.getIndexY()].addMonster(otyugh);
        cavesWithMonsters.add(dungeonGrid[index.getIndexX()][index.getIndexY()]);
      }
    } else {
      for (int i = 0; i < this.difficulty; i++) {
        Monster otyugh = new Monster(100);

        int c =
                caveLocations.get(randomizer.getRandomValueInRange(0,
                        caveLocations.size()));
        DungeonIndex index = getIndex(c);

        int removeCaveWithAddedMonster = caveLocations.indexOf(c);
        caveLocations.remove(removeCaveWithAddedMonster);

        dungeonGrid[index.getIndexX()][index.getIndexY()].addMonster(otyugh);
        cavesWithMonsters.add(dungeonGrid[index.getIndexX()][index.getIndexY()]);
      }
    }

  }


  /**
   * This method is used to detect the Smell from the monsters inside the
   * dungeon. The player detects Strong Pungent Smell when there is a monster
   * which is one location away from it or when there is more than one
   * monster present at a distance of exactly 2 from the player's current
   * location. The player detects Low Smell when there is one monster present
   * from a distance of 2 from it.
   *
   * @param location the location from which to detect the smell.
   * @return Smell which is either strong  or  low.
   */
  @Override
  public Smell detectSmell(Location location) {
    if (location == null) {
      throw new IllegalArgumentException("Location cannot be null");
    }
    Location playerCurrentLocation = player.getPlayerLocation();
    if ((playerCurrentLocation.hasMonster()
            || playerCurrentLocation.getEast() != null
            && playerCurrentLocation.getEast().hasMonster())
            || (playerCurrentLocation.getWest() != null
            && playerCurrentLocation.getWest().hasMonster())
            || (playerCurrentLocation.getNorth() != null
            && playerCurrentLocation.getNorth().hasMonster())
            || (playerCurrentLocation.getSouth() != null
            && playerCurrentLocation.getSouth().hasMonster())) {
      return Smell.STRONG_PUNGENT_SMELL;
    }

    Set<Integer> detectedCave = new HashSet<>();

    int counter = 0;
    if (playerCurrentLocation.getEast() != null) {
      if (playerCurrentLocation.getEast().getEast() != null
              && playerCurrentLocation.getEast().getEast().hasMonster()
              && !detectedCave.contains(playerCurrentLocation.getEast().getEast().getCaveId())) {
        counter++;
        detectedCave.add(playerCurrentLocation.getEast().getEast().getCaveId());
      }
      if (playerCurrentLocation.getEast().getNorth() != null
              && playerCurrentLocation.getEast().getNorth().hasMonster()
              && !detectedCave.contains(playerCurrentLocation.getEast().getNorth().getCaveId())) {
        counter++;
        detectedCave.add(playerCurrentLocation.getEast().getNorth().getCaveId());
      }
      if (playerCurrentLocation.getEast().getSouth() != null
              && playerCurrentLocation.getEast().getSouth().hasMonster()
              && !detectedCave.contains(playerCurrentLocation.getEast().getSouth().getCaveId())) {
        counter++;
        detectedCave.add(playerCurrentLocation.getEast().getSouth().getCaveId());
      }
    }

    if (playerCurrentLocation.getWest() != null) {
      if (playerCurrentLocation.getWest().getWest() != null
              && playerCurrentLocation.getWest().getWest().hasMonster()
              && !detectedCave.contains(playerCurrentLocation.getWest().getWest().getCaveId())) {
        counter++;
        detectedCave.add(playerCurrentLocation.getWest().getWest().getCaveId());
      }
      if (playerCurrentLocation.getWest().getNorth() != null
              && playerCurrentLocation.getWest().getNorth().hasMonster()
              && !detectedCave.contains(playerCurrentLocation.getWest().getNorth().getCaveId())) {
        counter++;
        detectedCave.add(playerCurrentLocation.getWest().getNorth().getCaveId());
      }
      if (playerCurrentLocation.getWest().getSouth() != null
              && playerCurrentLocation.getWest().getSouth().hasMonster()
              && !detectedCave.contains(playerCurrentLocation.getWest().getSouth().getCaveId())) {
        counter++;
        detectedCave.add(playerCurrentLocation.getWest().getSouth().getCaveId());
      }
    }

    if (playerCurrentLocation.getNorth() != null) {
      if (playerCurrentLocation.getNorth().getNorth() != null
              && playerCurrentLocation.getNorth().getNorth().hasMonster()
              && !detectedCave.contains(playerCurrentLocation.getNorth().getNorth().getCaveId())) {
        counter++;
        detectedCave.add(playerCurrentLocation.getNorth().getNorth().getCaveId());
      }
      if (playerCurrentLocation.getNorth().getWest() != null
              && playerCurrentLocation.getNorth().getWest().hasMonster()
              && !detectedCave.contains(playerCurrentLocation.getNorth().getWest().getCaveId())) {
        counter++;
        detectedCave.add(playerCurrentLocation.getNorth().getWest().getCaveId());
      }
      if (playerCurrentLocation.getNorth().getEast() != null
              && playerCurrentLocation.getNorth().getEast().hasMonster()
              && !detectedCave.contains(playerCurrentLocation.getNorth().getEast().getCaveId())) {
        counter++;
        detectedCave.add(playerCurrentLocation.getNorth().getEast().getCaveId());
      }
    }

    if (playerCurrentLocation.getSouth() != null) {
      if (playerCurrentLocation.getSouth().getSouth() != null
              && playerCurrentLocation.getSouth().getSouth().hasMonster()
              && !detectedCave.contains(playerCurrentLocation.getSouth().getSouth().getCaveId())) {
        counter++;
        detectedCave.add(playerCurrentLocation.getSouth().getSouth().getCaveId());
      }
      if (playerCurrentLocation.getSouth().getWest() != null
              && playerCurrentLocation.getSouth().getWest().hasMonster()
              && !detectedCave.contains(playerCurrentLocation.getSouth().getWest().getCaveId())) {
        counter++;
        detectedCave.add(playerCurrentLocation.getSouth().getWest().getCaveId());
      }
      if (playerCurrentLocation.getSouth().getEast() != null
              && playerCurrentLocation.getSouth().getEast().hasMonster()
              && !detectedCave.contains(playerCurrentLocation.getSouth().getEast().getCaveId())) {
        counter++;
        detectedCave.add(playerCurrentLocation.getSouth().getEast().getCaveId());
      }
    }

    if (counter > 1) {
      return Smell.STRONG_PUNGENT_SMELL;
    }
    if (counter == 1) {
      return Smell.PUNGENT_SMELL;
    }


    return null;
  }


  /**
   * To get the y, and x coordinates of the location in the dungeon grid.
   * For example location 1 has coordinates (0,0), location 9 has coordinates
   * (2,2) in a 3x3 grid.
   *
   * @param a The location id of which we want the coordinates in the dungeon.
   * @return Coordinates of the location id in the dungeon.
   */
  @Override
  public DungeonIndex getIndex(int a) {
    int count = 0;
    for (int i = 0; i < this.row; i++) {
      for (int j = 0; j < this.column; j++) {
        count += 1;
        if (count == a) {
          DungeonIndex dungeonIndex = new DungeonIndex(i, j);
          return new DungeonIndex(dungeonIndex);
        }
      }
    }
    return null;
  }

  private int getRootParent(int childCave) {
    while (father[childCave] != childCave) {
      childCave = father[childCave];
    }
    return childCave;
  }

  private void join(int newCave, int oldCave) {
    int fatherOfNewCave = getRootParent(newCave);
    int fatherOfOldCave = getRootParent(oldCave);
    father[fatherOfNewCave] = fatherOfOldCave;
  }


  /**
   * Method to represent the dungeon as a String which shows the information
   * of every cave and the caves to the north,south,east,and west to itself.
   *
   * @return String form of the dungeon.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < dungeonGrid.length; i++) {
      for (int j = 0; j < dungeonGrid[0].length; j++) {
        String s = toStringHelper(i, j);
        sb.append(s + "\n").trimToSize();
      }
    }

    return sb.toString().trim();
  }


  private String toStringHelper(int i, int j) {
    StringBuilder sb = new StringBuilder();
    sb.append(dungeonGrid[i][j].getCaveId() + " : ").trimToSize();
    if (dungeonGrid[i][j].getNorth() != null) {
      sb.append(dungeonGrid[i][j].getNorth().getCaveId() + " | ").trimToSize();
    } else if (dungeonGrid[i][j].getNorth() == null) {
      sb.append("- | ").trimToSize();
    }
    if (dungeonGrid[i][j].getSouth() != null) {
      sb.append(dungeonGrid[i][j].getSouth().getCaveId() + " | ").trimToSize();
    } else if (dungeonGrid[i][j].getSouth() == null) {
      sb.append("- | ").trimToSize();
    }
    if (dungeonGrid[i][j].getEast() != null) {
      sb.append(dungeonGrid[i][j].getEast().getCaveId() + " | ").trimToSize();
    } else if (dungeonGrid[i][j].getEast() == null) {
      sb.append("- | ").trimToSize();
    }
    if (dungeonGrid[i][j].getWest() != null) {
      sb.append(dungeonGrid[i][j].getWest().getCaveId() + " | ").trimToSize();
    } else if (dungeonGrid[i][j].getWest() == null) {
      sb.append("- | ").trimToSize();
    }
    if (dungeonGrid[i][j].getTreasureList().size() > 0) {
      sb.append("Treasures");
    }
    if (dungeonGrid[i][j].getArrowCountInLocation() > 0) {
      sb.append("Arrows");
    }
    if (dungeonGrid[i][j].hasMonster()) {
      sb.append("Monster");
    }
    sb.append(" " + dungeonGrid[i][j].getCaveNeighbours());
    return sb.toString().trim();
  }


  /**
   * This method is a method that helps us retrieve our built dungeon.
   *
   * @return 2-D dungeon grid of caves
   */
  public Location[][] getDungeon() {
    return this.dungeonGrid;
  }


  private static int count = 0;


  private void createAdjMatrix() {

    adj =
            new int[(dungeonGrid.length * dungeonGrid[0].length) + 1][
                    (dungeonGrid.length * dungeonGrid[0].length) + 1];
    for (int i = 0; i < dungeonGrid.length; i++) {
      for (int j = 0; j < dungeonGrid[0].length; j++) {
        int src = dungeonGrid[i][j].getCaveId();
        if (dungeonGrid[i][j].getSouth() != null) {
          int des = dungeonGrid[i][j].getSouth().getCaveId();
          adj[src][des] = 1;
        }
        if (dungeonGrid[i][j].getNorth() != null) {
          int des = dungeonGrid[i][j].getNorth().getCaveId();
          adj[src][des] = 1;
        }
        if (dungeonGrid[i][j].getWest() != null) {
          int des = dungeonGrid[i][j].getWest().getCaveId();
          adj[src][des] = 1;
        }
        if (dungeonGrid[i][j].getEast() != null) {
          int des = dungeonGrid[i][j].getEast().getCaveId();
          adj[src][des] = 1;
        }
      }
    }

  }


  /**
   * Method to find the path from start cave to end cave in the 2-Dimensional
   * dungeon. This path is calculated using Depth-First Search Algorithm and
   * this method also ensures that the path between start and end node is
   * always greater than or equal to 5.
   *
   * @return path from start to end.
   */
  @Override
  public Stack<Integer> findPathFromStartToStop() throws IllegalStateException {

    int start = -1;
    int stop = -1;


    while (start == stop) {
      randomizer.randomMax(dungeonGrid.length * dungeonGrid[0].length);

      start = randomizer.getStartStopRandom(1,
              dungeonGrid.length * dungeonGrid[0].length + 1).get(0);
      while (dungeonGrid[getIndex(start).getIndexX()]
              [getIndex(start).getIndexY()].getCaveType() != CaveType.CAVE) {
        start = randomizer.getStartStopRandom(1,
                dungeonGrid.length * dungeonGrid[0].length + 1).get(0);
      }

      stop = randomizer.getStartStopRandom(1,
              dungeonGrid.length * dungeonGrid[0].length + 1).get(1);
    }

    if (count > 100) {
      throw new IllegalStateException("No path with minimum 5 distance found "
              + "between the start and stop locations. Please run the program "
              + "again by changing the dimensions of the dungeon.");
    }

    boolean[] discovered =
            new boolean[(dungeonGrid.length * dungeonGrid[0].length) + 1];

    if (isPathAvailable(this.adj, start, stop, discovered, startToStopPath)) {
      DungeonIndex playerStartLocation = getIndex(start);
      player.setPlayerLocation(dungeonGrid[playerStartLocation
              .getIndexX()][playerStartLocation.getIndexY()]);
      player.getPlayerLocation().setExploreStatus(true);
      return startToStopPath;

    } else {
      count++;
      findPathFromStartToStop();
      return startToStopPath;
    }
  }


  private boolean isPathAvailable(int[][] graph, int begin, int goal,
                                  boolean[] visited, Stack<Integer> path) {

    //recognize and identify the current cave as visited
    visited[begin] = true;
    //include the current cave in the path
    path.add(begin);
    //if destination cave is found
    if (begin == goal && path.size() > 5
            && dungeonGrid[getIndex(goal).getIndexX()]
            [getIndex(goal).getIndexY()].getCaveType() == CaveType.CAVE) {
      return true;
    }

    for (int i = 0; i < graph[begin].length; i++) {
      if (graph[begin][i] == 1) {
        if (!visited[i]) {
          if (isPathAvailable(graph, i, goal, visited, path)) {
            return true;
          }
        }
      }
    }

    //backtrack and remove the current cave from the path
    path.pop();
    //return false if destination cave is not reachable from start cave / source
    return false;
  }


  /**
   * Method to get the final selected paths which were randomly selected
   * using the Kruskal's algorithm in order to build our dungeon.
   *
   * @return list of final paths selected to build the dungeon.
   */
  @Override
  public List<Path> getFinalSelectedPathsForDungeon() {
    return new ArrayList<>(finalSelectedPathsForDungeon);
  }


  /**
   * Method to return the initial path list that was used to select random
   * paths and put into final paths list by using Kruskal algorithm in order
   * to build the dungeon.
   *
   * @return list of all the possible paths for a given sized dungeon.
   */
  @Override
  public List<Path> getPathList() {
    return new ArrayList<>(pathList);
  }

  /**
   * Method to check if the player was able to successfully shoot the Otyugh
   * or not.
   *
   * @return true if successfully shot, else false.
   */
  @Override
  public boolean shotOrNot() {
    return shotOrNot;
  }


  /**
   * Method to get the final goal location at which the player needs to reach
   * in order to win the game.
   *
   * @return Location id of the final goal location of the game.
   */
  @Override
  public int getFinalGoalLocation() {
    return startToStopPath.get(startToStopPath.size() - 1);

  }


  /**
   * This method is used to move the player in the dungeon as per the
   * directions of the user. If the user asks the player to move north, the
   * player will move north and so on.
   *
   * @param location It represents the Cave in which the player currently is.
   */
  @Override
  public boolean movePlayer(String location) {
    this.shotOrNot = false;
    return this.player.move(location);
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
  public OtyughStatus shootPlayer(String direction, int distance) {
    OtyughStatus otyughStatus = player.shoot(direction, distance);
    if (otyughStatus.getOtyughCurrentHealth() < otyughStatus.getOtyughPreviousHealth()) {
      this.shotOrNot = true;
    } else {
      this.shotOrNot = false;
    }
    return new OtyughStatus(otyughStatus);
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
    return player.getPlayerLocation();
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
    player.setPlayerLocation(playerLocation);
  }

  /**
   * This method allows the Player to pick up the treasure from its current
   * location. The Player picks up treasure only if the location in which
   * he/she/they are standing has treasures in it.
   */
  @Override
  public void pickUpTreasure() {
    player.pickUpTreasure();
  }

  /**
   * This method allows the Player to pick up the arrows from its current
   * location. The Player picks up arrows only if the location in which
   * he/she/they are standing has treasures in it.
   */
  @Override
  public void pickUpArrows() {
    player.pickUpArrows();
  }

  /**
   * This method is used to retrieve the players accumulated wealth or
   * treasure. The player collects treasures from the caves as he explores
   * the dungeon.
   *
   * @return List of treasures collected by the Player so far.
   */
  @Override
  public List<Treasure> getPlayerWealth() {
    return player.getWealth();
  }

  /**
   * Method to get the number of arrows which the player holds in any given
   * point of time.
   *
   * @return number of arrows the player currently has.
   */
  @Override
  public int getPlayerArrowCount() {
    return player.getArrowCount();
  }

  /**
   * Method to get the Treasure list  of the Location in the dungeon.
   *
   * @return List of Treasures.
   */
  @Override
  public List getTreasureListFromLocation() {
    return player.getPlayerLocation().getTreasureList();
  }

  /**
   * Method to get the number of arrows present in any given location of the
   * dungeon.
   *
   * @return number of arrows.
   */
  @Override
  public int getArrowCountInLocation() {
    return player.getPlayerLocation().getArrowCountInLocation();
  }


  /**
   * Method to reset the arrow count in any location after picking up the
   * arrows in that given location.
   */
  @Override
  public void resetArrowCountInLocation() {
    player.getPlayerLocation().resetArrowCount();
  }


  /**
   * Method to get the Identification number of the Location in the dungeon.
   *
   * @return id of the cave
   */
  @Override
  public int getLocationId() {
    return player.getPlayerLocation().getCaveId();
  }

  /**
   * Method to get the location to the north of the current location.
   *
   * @return Location to the north of the current location.
   */
  @Override
  public Location getNorthLocation() {
    return player.getPlayerLocation().getNorth();
  }

  /**
   * Method to get the location to the south of the current location.
   *
   * @return Location to the south of the current location.
   */
  @Override
  public Location getSouthLocation() {
    return player.getPlayerLocation().getSouth();
  }

  /**
   * Method to get the location to the east of the current location.
   *
   * @return Location to the east of the current location.
   */
  @Override
  public Location getEastLocation() {
    return player.getPlayerLocation().getEast();
  }

  /**
   * Method to get the location to the west of the current location.
   *
   * @return Location to the west of the current location.
   */
  @Override
  public Location getWestLocation() {
    return player.getPlayerLocation().getWest();
  }


  /**
   * Method to check if the player is alive or if he has been killed by the
   * Otyugh.
   *
   * @return true if the player is alive, else false if player is dead.
   */
  @Override
  public boolean checkIfPlayerIsAlive() {
    return player.checkIfPlayerIsAlive();
  }

}
