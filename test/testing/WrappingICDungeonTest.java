package testing;

import org.junit.Test;

import java.util.Stack;

import dungeon.CaveType;
import dungeon.Dungeon;
import dungeon.DungeonIndex;
import dungeon.DungeonInterface;
import dungeon.DungeonType;
import dungeon.Location;
import dungeon.MockRandomInterconnectivityWrapping;
import dungeon.Randomizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is used to Test the functionalities of a Wrapping dungeon type
 * which has an interconnectivity of more than zero.
 */
public class WrappingICDungeonTest {

  @Test
  public void addInterconnectivity() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    Dungeon dungeon = new Dungeon(3, 3, 3, DungeonType.WRAPPING, 50,
            randomizer, 5);

    assertEquals((dungeon.getDungeon().length * dungeon.getDungeon()[0].length - 1) + 3,
            dungeon.getFinalSelectedPathsForDungeon().size());
  }


  @Test
  public void setDirections() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    DungeonInterface dungeon = new Dungeon(3, 3, 3, DungeonType.WRAPPING, 50,
            randomizer, 5);


    Location[][] dungeonGrid = dungeon.getDungeon();

    //Test wrapping directions of Location 3 ( which has coordinates (0,2) ) :
    assertEquals(dungeonGrid[0][2].getNorth(), dungeonGrid[2][2]);
    assertEquals(dungeonGrid[0][2].getEast(), dungeonGrid[0][0]);
    assertEquals(dungeonGrid[0][2].getWest(), dungeonGrid[0][1]);
  }

  @Test
  public void identifyCavesAndTunnels() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    DungeonInterface dungeon = new Dungeon(3, 3, 3, DungeonType.WRAPPING, 50,
            randomizer, 5);


    assertTrue(dungeon.getDungeon()[0][0].getCaveType().toString().equals(
            "CAVE"));
    assertTrue(dungeon.getDungeon()[0][1].getCaveType().toString().equals(
            "TUNNEL"));
    assertTrue(dungeon.getDungeon()[0][2].getCaveType().toString().equals(
            "CAVE"));
    assertTrue(dungeon.getDungeon()[1][0].getCaveType().toString().equals(
            "CAVE"));
    assertTrue(dungeon.getDungeon()[1][1].getCaveType().toString().equals(
            "CAVE"));
    assertTrue(dungeon.getDungeon()[1][2].getCaveType().toString().equals(
            "TUNNEL"));
    assertTrue(dungeon.getDungeon()[2][0].getCaveType().toString().equals(
            "TUNNEL"));
    assertTrue(dungeon.getDungeon()[2][1].getCaveType().toString().equals(
            "CAVE"));
    assertTrue(dungeon.getDungeon()[2][2].getCaveType().toString().equals(
            "CAVE"));

  }

  @Test
  public void testPathBetweenStartAndStop() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    Dungeon dungeon = new Dungeon(3, 3, 3, DungeonType.WRAPPING, 50,
            randomizer, 5);

    Stack path = dungeon.findPathFromStartToStop();

    assertTrue(path.size() >= 5);
  }

  @Test
  public void testTreasurePercentage() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    DungeonInterface dungeon = new Dungeon(3, 3, 3, DungeonType.WRAPPING, 50,
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
  public void testMovePlayerInAllDirectionsModel() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    Stack path = dungeon.findPathFromStartToStop();
    int start = (int) path.get(0);
    int end = (int) path.get(path.size() - 1);

    Location[][] myDungeon = dungeon.getDungeon();
    DungeonIndex playerIndex = dungeon.getIndex(start);
    dungeon.setPlayerLocation(myDungeon[playerIndex.getIndexX()][playerIndex.getIndexY()]);
    assertEquals(3, dungeon.getLocationId());

    dungeon.movePlayer("E");  //Move in east direction.
    dungeon.movePlayer("W");  //Move in west direction.
    dungeon.movePlayer("W");
    dungeon.movePlayer("N");  //Move in north direction.
    dungeon.movePlayer("N");
    dungeon.movePlayer("S");  //Move in south direction.
    dungeon.movePlayer("E");  //Reached the end location 9.

    assertEquals(9, dungeon.getLocationId());
  }


}
