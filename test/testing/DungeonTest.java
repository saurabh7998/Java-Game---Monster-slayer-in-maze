package testing;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import dungeon.CaveType;
import dungeon.Dungeon;
import dungeon.DungeonIndex;
import dungeon.DungeonInterface;
import dungeon.DungeonType;
import dungeon.Location;
import dungeon.MockRandom;
import dungeon.Randomizer;
import dungeon.Smell;
import dungeon.Treasure;
import dungeon.TrueRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is used to test the Non-Wrapping dungeon type with an
 * interconnectivity of more than zero.
 */
public class DungeonTest {


  @Test(expected = IllegalArgumentException.class)
  public void testDungeonConstructor() {
    Randomizer trueRandom = new TrueRandom();
    DungeonInterface dungeon = new Dungeon(2, 1, 3,
            DungeonType.NON_WRAPPING, 50,
            trueRandom, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDungeonConstructor2() {
    Randomizer trueRandom = new TrueRandom();
    DungeonInterface dungeon = new Dungeon(3, 4, -2,
            DungeonType.NON_WRAPPING, 50,
            trueRandom, 3);
  }


  @Test
  public void buildDungeonGrid() {
    Randomizer trueRandom = new TrueRandom();
    Randomizer randomizer = new TrueRandom();
    DungeonInterface dungeon = new Dungeon(5, 6, 2,
            DungeonType.NON_WRAPPING, 50,
            trueRandom, 3);


    assertEquals(30, dungeon.getDungeon().length
            * dungeon.getDungeon()[0].length);
  }


  @Test
  public void testBuildMST() {
    Randomizer randomizer = new TrueRandom();
    Dungeon dungeon = new Dungeon(5, 6, 2,
            DungeonType.NON_WRAPPING, 50,
            randomizer, 3);

    assertEquals(dungeon.getDungeon().length * dungeon.getDungeon()[0]
                    .length,
            dungeon.getFinalSelectedPathsForDungeon().size() - 1);

  }

  @Test
  public void testAddInterconnectivity() {
    Randomizer randomizer = new MockRandom();
    Dungeon dungeon = new Dungeon(3, 3, 2,
            DungeonType.NON_WRAPPING, 50,
            randomizer, 3);


    assertEquals(((dungeon.getDungeon().length * dungeon.getDungeon()[0]
                    .length - 1) + 2),
            dungeon.getFinalSelectedPathsForDungeon().size());
  }

  @Test
  public void setDirections() {
    Randomizer randomizer = new MockRandom();
    DungeonInterface dungeon = new Dungeon(3, 3, 2,
            DungeonType.NON_WRAPPING, 50,
            randomizer, 3);


    //Test all 4 directions of cave 1 (0,0) in the dungeon grid.
    assertTrue(dungeon.getDungeon()[0][0].getEast()
            == dungeon.getDungeon()[0][1]);
    assertTrue(dungeon.getDungeon()[0][0].getNorth() == null);
    assertTrue(dungeon.getDungeon()[0][0].getSouth() ==
            dungeon.getDungeon()[1][0]);
    assertTrue(dungeon.getDungeon()[0][0].getWest() == null);

    //Test the all 4 directions of cave 5 (1,1) in the dungeon grid.
    assertTrue(dungeon.getDungeon()[1][1].getSouth()
            == null);
    assertTrue(dungeon.getDungeon()[1][1].getNorth()
            == dungeon.getDungeon()[0][1]);
    assertTrue(dungeon.getDungeon()[1][1].getEast()
            == dungeon.getDungeon()[1][2]);
    assertTrue(dungeon.getDungeon()[1][1].getWest()
            == dungeon.getDungeon()[1][0]);

  }

  @Test
  public void identifyCavesAndTunnels() {
    Randomizer randomizer = new MockRandom();
    DungeonInterface dungeon = new Dungeon(3, 3, 2,
            DungeonType.NON_WRAPPING, 50,
            randomizer, 3);


    assertTrue(dungeon.getDungeon()[0][0].getCaveType().toString().equals(
            "TUNNEL"));
    assertTrue(dungeon.getDungeon()[0][1].getCaveType().toString().equals(
            "CAVE"));
    assertTrue(dungeon.getDungeon()[0][2].getCaveType().toString().equals(
            "TUNNEL"));
    assertTrue(dungeon.getDungeon()[1][0].getCaveType().toString().equals(
            "CAVE"));
    assertTrue(dungeon.getDungeon()[1][1].getCaveType().toString().equals(
            "CAVE"));
    assertTrue(dungeon.getDungeon()[1][2].getCaveType().toString().equals(
            "TUNNEL"));
    assertTrue(dungeon.getDungeon()[2][0].getCaveType().toString().equals(
            "TUNNEL"));
    assertTrue(dungeon.getDungeon()[2][1].getCaveType().toString().equals(
            "TUNNEL"));
    assertTrue(dungeon.getDungeon()[2][2].getCaveType().toString().equals(
            "CAVE"));
  }


  @Test
  public void putTreasureInCaves() {
    Randomizer randomizer = new MockRandom();
    DungeonInterface dungeon = new Dungeon(3, 3, 2,
            DungeonType.NON_WRAPPING,
            100,
            randomizer, 3);


    Location[][] myDungeon = dungeon.getDungeon();

    List<DungeonIndex> indexList = new ArrayList<>();
    for (int i = 0; i < myDungeon.length; i++) {
      for (int j = 0; j < myDungeon[0].length; j++) {
        if (myDungeon[i][j].getCaveType().equals(CaveType.CAVE) && myDungeon[i][j]
                .getTreasureList().size() > 0) {
          indexList.add(new DungeonIndex(i, j));
        }
      }
    }


    for (int i = 0; i < indexList.size(); i++) {
      DungeonIndex a = indexList.get(0);
      Location cave = myDungeon[a.getIndexX()][a.getIndexY()];
      assertEquals("[DIAMOND, DIAMOND]", cave.getTreasureList().toString());
    }

  }


  @Test
  public void testPathBetweenStartAndStop() {
    Randomizer randomizer = new MockRandom();
    DungeonInterface dungeon = new Dungeon(3, 3, 2,
            DungeonType.NON_WRAPPING, 50,
            randomizer, 3);


    Stack path = dungeon.findPathFromStartToStop();
    assertTrue(path.size() >= 5);
  }


  @Test
  public void testTreasurePercentage() {
    Randomizer randomizer = new MockRandom();
    DungeonInterface dungeon = new Dungeon(3, 3, 2,
            DungeonType.NON_WRAPPING,
            50,
            randomizer, 5);


    Location[][] d = dungeon.getDungeon();
    int caveCount = 0;
    int caveWithTreasureCount = 0;
    for (int i = 0; i < d.length; i++) {
      for (int j = 0; j < d[0].length; j++) {
        if (d[i][j].getCaveType().equals(CaveType.CAVE)) {
          caveCount++;
          if (d[i][j].getTreasureList().size() > 0) {
            caveWithTreasureCount++;
          }
        }
      }
    }
    assertTrue(caveWithTreasureCount >= caveCount * 0.5);

  }

  @Test
  public void testMovePlayerToEndPath() {
    Randomizer randomizer = new MockRandom();
    DungeonInterface dungeon = new Dungeon(3, 3, 2,
            DungeonType.NON_WRAPPING,
            50, randomizer, 3);


    Stack path = dungeon.findPathFromStartToStop();
    int start = (int) path.get(0);
    int end = (int) path.get(path.size() - 1);

    Location[][] myDungeon = dungeon.getDungeon();
    DungeonIndex playerIndex = dungeon.getIndex(start);
    dungeon.setPlayerLocation(myDungeon[playerIndex.getIndexX()]
            [playerIndex.getIndexY()]);
    assertEquals(2, dungeon.getLocationId());

    dungeon.movePlayer("E");
    dungeon.movePlayer("S");
    dungeon.movePlayer("W");
    dungeon.movePlayer("W");
    dungeon.movePlayer("S");
    dungeon.movePlayer("E");
    dungeon.movePlayer("E");

    assertEquals(9, dungeon.getLocationId());


  }


  @Test
  public void getDungeon() {
    Randomizer randomizer = new MockRandom();
    DungeonInterface dungeon = new Dungeon(3, 3, 2,
            DungeonType.NON_WRAPPING,
            50, randomizer, 3);


    assertTrue(dungeon.getDungeon().length == 3);
    assertTrue(dungeon.getDungeon()[0].length == 3);
  }


  @Test
  public void testPutMonsterInCaves() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 50;
    int difficulty = 2;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    Location[][] dungeonGrid = dungeon.getDungeon();
    int monsterCount = 0;

    for (int i = 0; i < dungeonGrid[0].length; i++) {
      for (int j = 0; j < dungeonGrid.length; j++) {
        if (dungeonGrid[i][j].hasMonster()) {
          monsterCount++;
        }
      }
    }

    //This proves that the number of monsters added to the dungeon are the
    // same number which the user specified in the difficulty parameter.
    assertEquals(difficulty, monsterCount);

  }


  @Test
  public void testDetectSmell() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 50;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    /*
    Initially the Player is at Location 2 which has an Otyugh at a distance
     of 2 distance from it. So it will detect a (low) pungent smell.
     */
    assertEquals(Smell.STRONG_PUNGENT_SMELL,
            dungeon.detectSmell(dungeon.getPlayerLocation()));

    //Move player to South of location 2, i.e. to location 5.
    dungeon.movePlayer("E");

    /*
    Player currently at location 5 will detect strong smell coming from
     location 4 which has a monster.
     */
    assertEquals(Smell.PUNGENT_SMELL,
            dungeon.detectSmell(dungeon.getPlayerLocation()));
  }

  @Test
  public void testStartCaveDoesNotHaveMonster() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 50;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    Stack<Integer> path = new Stack<>();
    try {
      path = dungeon.findPathFromStartToStop();
    } catch (IllegalStateException e) {
      System.exit(0);
    }


    int start = (int) path.get(0);
    int end = (int) path.get(path.size() - 1);

    Location[][] myDungeon = dungeon.getDungeon();
    DungeonIndex playerIndex = dungeon.getIndex(start);
    dungeon.setPlayerLocation(myDungeon[playerIndex.getIndexX()]
            [playerIndex.getIndexY()]);
    assertEquals(2, dungeon.getLocationId());

    //This checks that monster is not present at start location.
    assertTrue(!dungeon.getPlayerLocation().hasMonster());
  }

  @Test
  public void testEndCaveHasMonster() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 50;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    Stack<Integer> path = new Stack<>();
    try {
      path = dungeon.findPathFromStartToStop();
    } catch (IllegalStateException e) {
      System.exit(0);
    }

    int start = (int) path.get(0);

    Location[][] myDungeon = dungeon.getDungeon();
    DungeonIndex playerIndex = dungeon.getIndex(start);
    dungeon.setPlayerLocation(myDungeon[playerIndex.getIndexX()]
            [playerIndex.getIndexY()]);
    assertEquals(2, dungeon.getLocationId());

    dungeon.movePlayer("E");
    dungeon.movePlayer("S");
    dungeon.movePlayer("W");
    dungeon.movePlayer("W");
    dungeon.movePlayer("S");
    dungeon.movePlayer("E");
    dungeon.movePlayer("E");

    assertEquals(9, dungeon.getLocationId());

    //This checks and proves that the end cave has a monster always.
    assertTrue(dungeon.getPlayerLocation().hasMonster());
  }

  @Test
  public void testIfStartAndEndLocationsAreCaves() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 50;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    Stack<Integer> path = new Stack<>();
    try {
      path = dungeon.findPathFromStartToStop();
    } catch (IllegalStateException e) {
      System.exit(0);
    }


    int start = (int) path.get(0);
    int end = (int) path.get(path.size() - 1);

    Location[][] myDungeon = dungeon.getDungeon();

    DungeonIndex playerStartIndex = dungeon.getIndex(start);
    dungeon.setPlayerLocation(myDungeon[playerStartIndex.getIndexX()]
            [playerStartIndex.getIndexY()]);
    assertEquals(2, dungeon.getLocationId());

    assertTrue(dungeon.getPlayerLocation().getCaveType().equals(CaveType.CAVE));

    DungeonIndex playerEndIndex = dungeon.getIndex(end);
    dungeon.setPlayerLocation(myDungeon[playerEndIndex.getIndexX()]
            [playerEndIndex.getIndexY()]);
    assertEquals(9, dungeon.getLocationId());

    assertTrue(myDungeon[playerEndIndex.getIndexX()][playerEndIndex.getIndexY()]
            .getCaveType().equals(CaveType.CAVE));
  }

  @Test
  public void testIfArrowsAreAssignedInBothCavesAndTunnels() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 50;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    Stack<Integer> path = new Stack<>();
    try {
      path = dungeon.findPathFromStartToStop();
    } catch (IllegalStateException e) {
      System.exit(0);
    }


    int start = (int) path.get(0);

    Location[][] myDungeon = dungeon.getDungeon();
    DungeonIndex playerIndex = dungeon.getIndex(start);
    dungeon.setPlayerLocation(myDungeon[playerIndex.getIndexX()]
            [playerIndex.getIndexY()]);
    assertEquals(2, dungeon.getLocationId());

    //Proves that Location 2 is a Cave
    assertEquals(CaveType.CAVE, dungeon.getPlayerLocation().getCaveType());
    //This proves that Cave 2 has arrows inside it.
    assertTrue(dungeon.getPlayerLocation().getArrowCountInLocation() > 0);

    //Move Player to location 1: which is a tunnel
    dungeon.movePlayer("E");
    assertEquals(3, dungeon.getLocationId());
    //Proves that Location 3 is a Tunnel
    assertEquals(CaveType.TUNNEL, dungeon.getPlayerLocation().getCaveType());
    //This proves that Tunnel 3 has arrows inside it.
    assertTrue(dungeon.getPlayerLocation().getArrowCountInLocation() > 0);
  }


  @Test
  public void testIfThiefAdded() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 50;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    int numberOfThieves = 0;
    Location[][] dungeonGrid = dungeon.getDungeon();

    for (int i = 0; i < dungeonGrid.length; i++) {
      for (int j = 0; j < dungeonGrid[0].length; j++) {
        if (dungeonGrid[i][j].hasThief()) {
          numberOfThieves++;
        }
      }
    }

    //We always add only 2 thieves in the dungeon.
    assertEquals(2, numberOfThieves);
  }

  @Test
  public void testIfThievesAreAddedOnlyInTunnels() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 50;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    Location[][] dungeonGrid = dungeon.getDungeon();
    for (int i = 0; i < dungeonGrid.length; i++) {
      for (int j = 0; j < dungeonGrid[0].length; j++) {
        if (dungeonGrid[i][j].hasThief()) {
          assertTrue(dungeonGrid[i][j].getCaveType().equals(CaveType.TUNNEL));
        }
      }
    }
  }

  @Test
  public void checkIfThiefLootsPlayer() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 50;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    Location[][] dungeonGrid = dungeon.getDungeon();
    Location thiefLocation = null;
    for (int i = 0; i < dungeonGrid.length; i++) {
      for (int j = 0; j < dungeonGrid[0].length; j++) {
        if (dungeonGrid[i][j].hasThief()) {
          thiefLocation = dungeonGrid[i][j];
          break;
        }
      }
    }

    //Add treasure to the player's bag.
    dungeon.getPlayerWealth().add(Treasure.SAPPHIRE);

    //This shows that Player has a treasure of Sapphire.
    //assertTrue(dungeon.getPlayerWealth().contains(Treasure.SAPPHIRE));

    dungeon.movePlayer("S");
    dungeon.movePlayer("W");
    assertTrue(dungeon.getPlayerWealth().size() > 0);

    //Moving to this location will move the player to encounter the thief.
    dungeon.movePlayer("S");
    //After the player encounters thief, he is looted. Hence, wealth of player
    // equals 0.
    assertTrue(dungeon.getPlayerWealth().size() == 0);
  }
}