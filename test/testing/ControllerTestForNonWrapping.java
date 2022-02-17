package testing;

import org.junit.Test;

import java.io.StringReader;

import dungeon.Dungeon;
import dungeon.DungeonConsoleController;
import dungeon.DungeonController;
import dungeon.DungeonInterface;
import dungeon.DungeonType;
import dungeon.MockRandom;
import dungeon.Randomizer;

import static org.junit.Assert.assertTrue;

/**
 * This class tests the Dungeon Controller along with the dungeon model
 * object for a non-wrapping dungeon.
 */
public class ControllerTestForNonWrapping {

  @Test
  public void testPlayerMovementInAllDirections() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 3;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y Y S 1 E Y Y S 1 S Y Y N W Y Y "
            + "Y W 1 Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    /*
     This shows that player moved from 2->3->6->5->2. Hence, proved that
     player
     travels in directions in east, south, west, north.
     (Imagine a 3x3 grid as follows)
     | 1 | 2 | 3 |
     | 4 | 5 | 6 |
     | 7 | 8 | 9 |
     */

    assertTrue(appendable.toString().contains("Current Location: 2"));
    assertTrue(appendable.toString().contains("Current Location: 3"));
    assertTrue(appendable.toString().contains("Current Location: 6"));
    assertTrue(appendable.toString().contains("Current Location: 5"));

  }


  @Test
  public void testPickUpArrows() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 3;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    assertTrue(appendable.toString().contains("Location has 1 arrows"));
    assertTrue(appendable.toString().contains("Player collected 1 arrows"));
  }


  @Test
  public void testPickUpTreasure() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 3;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    assertTrue(appendable.toString().contains("Location has treasures: "
            + "[DIAMOND, DIAMOND]"));
    assertTrue(appendable.toString().contains("Player collected total " +
            "treasure of : [DIAMOND, DIAMOND]"));

  }


  @Test
  public void testKillMonster() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 3;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y Y S 1 E Y Y S 1 Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    assertTrue((appendable.toString().contains("An Otyugh at location: 5 was killed.")));
  }

  @Test
  public void testShootMonster() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 3;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    StringReader input = new StringReader("Y Y Y S 1 Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    /*
    Shoot in south direction from location 2 and shoot monster in location 5.
     */
    assertTrue(appendable.toString().contains("An Otyugh at location: 5 was "
            + "shot but not killed."));

  }

  @Test
  public void testShootInEastDirection() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 3;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    StringReader input = new StringReader("Y Y Y E 1 Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    assertTrue(appendable.toString().contains("An Otyugh at location: 5 was "
            + "shot but not killed."));
  }

  @Test
  public void testShootInWestDirection() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 3;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    StringReader input = new StringReader("Y Y Y W 1 Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    /*
    Shot from location 2 in west direction. Location 1 is Tunnel.
     */
    assertTrue(appendable.toString().contains("An Otyugh at location: 4 was "
            + "shot but not killed."));
  }

  @Test
  public void testShootInNorthDirection() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 3;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    StringReader input = new StringReader("Y Y N E Y N S Y Y Y N 2 Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    /*
    Shot from location 6 in north direction. Location 3 is Tunnel, 2 is cave,
     1 is tunnel again, and finally 4 is cave where we shoot the monster.
     6->3->2->1->4 (Imagine a 3x3 grid)
     */
    assertTrue(appendable.toString().contains("An Otyugh at location: 4 was "
            + "shot but not killed."));
  }

  @Test
  public void testDetectionOfLowSmell() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 3;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    StringReader input = new StringReader("Y Y Y S 1 E Y Y");

    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);
    assertTrue(appendable.toString().contains("Low Smell Detected"));
  }


  @Test
  public void testDetectionOfStrongSmell() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 3;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    StringReader input = new StringReader("Y Y");

    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);
    assertTrue(appendable.toString().contains("Strong smell detected"));
  }

  @Test
  public void testShootOtyughWhichIsVeryFarFromCurrentLocation() {
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 3;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    StringReader input = new StringReader("Y Y N W Y Y Y S 2 Q");

    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);


    /*
    Player moves from initial location 2 to location 1.
    From location 1 it shoots arrow at location 9 which is far from location 1.
   */
    assertTrue(appendable.toString().contains("An Otyugh at location: 9 was "
            + "shot but not killed."));
  }


  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    // Testing when something goes wrong with the Appendable
    // Here we are passing it a mock of an Appendable that always fails
    Randomizer randomizer = new MockRandom();
    int row = 3;
    int column = 3;
    int interconnectivity = 2;
    DungeonType dungeonType = DungeonType.NON_WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 3;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    StringReader input = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
    Appendable gameLog = new FailingAppendable();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playConsoleGame(dungeon);
  }

}
