package testing;

import org.junit.Test;

import java.io.StringReader;

import dungeon.Dungeon;
import dungeon.DungeonConsoleController;
import dungeon.DungeonController;
import dungeon.DungeonInterface;
import dungeon.DungeonType;
import dungeon.MockRandomInterconnectivityWrapping;
import dungeon.Randomizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the Dungeon Controller along with the dungeon model
 * object for a non-wrapping dungeon.
 */
public class ControllerTestForWrapping {

  @Test
  public void testPickUpArrows() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 50;
    int difficulty = 5;

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
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

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
  public void testShootMonsterAtEast() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y Y E 1 Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    //Current Location of Player is 3:
    assertTrue(appendable.toString().contains("Current Location: 3"));

    /*
    Player shot an Otyugh at location 1 from current location 3. This proves
    that the arrow travels in a wrapping dungeon. Consider the following
    dungeon:
    | 1 | 2 | 3 |
    | 4 | 5 | 6 |
    | 7 | 8 | 9 |
     */

    assertTrue(appendable.toString().contains("An Otyugh at location: 1 was "
            + "shot but not killed."));
  }

  @Test
  public void testShootMonsterAtNorth() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y Y N 1 Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    //Current Location of Player is 3:
    assertTrue(appendable.toString().contains("Current Location: 3"));

    /*
    Player shot an Otyugh at location 9 from current location 3. This proves
    that the arrow travels in a wrapping dungeon. Consider the following
    dungeon:
    | 1 | 2 | 3 |
    | 4 | 5 | 6 |
    | 7 | 8 | 9 |
     */

    assertTrue(appendable.toString().contains("An Otyugh at location: 9 was "
            + "shot but not killed."));
  }


  @Test
  public void testShootMonsterAtWest() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y Y W 1 Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    //Current Location of Player is 3:
    assertTrue(appendable.toString().contains("Current Location: 3"));

    /*
    Player shot an Otyugh at location 8 from current location 3. This proves
    that the arrow travels in a wrapping dungeon. Consider the following
    dungeon:
    | 1 | 2 | 3 |
    | 4 | 5 | 6 |
    | 7 | 8 | 9 |
     */

    assertTrue(appendable.toString().contains("An Otyugh at location: 8 was "
            + "shot but not killed."));
  }

  @Test
  public void testKillMonster() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y Y W 1 W Y Y N 1 Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    //Current Location of Player is 3:
    assertTrue(appendable.toString().contains("Current Location: 3"));

    /*
    Player shot an Otyugh at location 8 from current location 3. This proves
    that the arrow travels in a wrapping dungeon. Consider the following
    dungeon:
    | 1 | 2 | 3 |
    | 4 | 5 | 6 |
    | 7 | 8 | 9 |
     */

    //Next location of player after moving from 3:
    assertTrue(appendable.toString().contains("Current Location: 2"));

    assertTrue(appendable.toString().contains("An Otyugh at location: 8 was killed."));
  }


  @Test
  public void testDetectLowSmell() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y Y W 1 W Y Y N 1 N Y Y N S Y E "
            + "2 E Y E 1 E Y Q ");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    assertTrue(appendable.toString().contains("Low Smell Detected"));
  }


  @Test
  public void testPlayerEatenByMonster() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y N N");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    assertTrue(appendable.toString().contains("Current Location: 3"));

    assertTrue(appendable.toString().contains("Chomp chomp chomp, "
            + "You have been eaten by the Otyugh"));
    assertTrue(appendable.toString().contains("Game Over"));
  }

  @Test
  public void testPlayerNotEatenByMonsterAndPlayerEscapes() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y Y N 1 N Y N Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    //This proves that the player has reached the end location of 9:
    assertTrue(appendable.toString().contains("Current Location: 9"));

    //The end location always has monster, but the monster did not eat the
    // player:
    assertFalse(appendable.toString().contains("Chomp chomp chomp, "
            + "You have been eaten by the Otyugh"));
  }

  @Test
  public void testQuitGame() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y N Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);
    assertTrue(appendable.toString().contains("Game Over"));
  }

  @Test
  public void testDetectStrongSmellIfMultipleMonstersPresentAtDistanceOfTWO() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y N W Y Y N 1 E N W N Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    String[] result = appendable.toString().split("\n");


    assertEquals("Strong smell detected", result[result.length - 10]);
    assertTrue(appendable.toString().contains("Strong smell detected"));
  }

  @Test
  public void testDetectionOfStrongSmell() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    StringReader input = new StringReader("Y Y");

    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);
    assertTrue(appendable.toString().contains("Strong smell detected"));
  }

  @Test
  public void testPlayerMovementToWest() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    StringReader input = new StringReader("Y Y Y W 1 W Y N Q");

    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    /*
     This shows that player moved from 3->2. Hence, proved that
     player
     travels in directions in east, south, west, north.
     (Imagine a 3x3 grid as follows)
     | 1 | 2 | 3 |
     | 4 | 5 | 6 |
     | 7 | 8 | 9 |
     */

    assertTrue(appendable.toString().contains("Current Location: 3"));
    assertTrue(appendable.toString().contains("Current Location: 2"));

  }

  @Test
  public void testPlayerMovementToNorth() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    StringReader input = new StringReader("Y Y Y W 1 W Y Y N 1 N Y Y Y E 1 E Q");

    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    /*
     This shows that player moved from 2->8. Hence, proved that
     player
     travels in directions in east, south, west, north.
     (Imagine a 3x3 grid as follows)
     | 1 | 2 | 3 |
     | 4 | 5 | 6 |
     | 7 | 8 | 9 |
     */

    assertTrue(appendable.toString().contains("Current Location: 3"));
    assertTrue(appendable.toString().contains("Current Location: 2"));
    assertTrue(appendable.toString().contains("Current Location: 8"));
  }

  @Test
  public void testPlayerMovementToEast() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    StringReader input = new StringReader("Y Y Y W 1 W Y Y N 1 N Y Y Y E 1 E Q");

    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    /*
     This shows that player moved from 8->9. Hence, proved that
     player
     travels in directions in east, south, west, north.
     (Imagine a 3x3 grid as follows)
     | 1 | 2 | 3 |
     | 4 | 5 | 6 |
     | 7 | 8 | 9 |
     */

    assertTrue(appendable.toString().contains("Current Location: 3"));
    assertTrue(appendable.toString().contains("Current Location: 2"));
    assertTrue(appendable.toString().contains("Current Location: 8"));
    assertTrue(appendable.toString().contains("Current Location: 9"));
  }

  @Test
  public void testPlayerDescription() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    StringReader input = new StringReader("Y Y Y W 1 W Y Y N 1 N Y Y Y E 1 E Q");

    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    assertTrue(appendable.toString().contains("Current Location: 3"));
    assertTrue(appendable.toString().contains("Location has treasures: "
            + "[DIAMOND, DIAMOND]"));
    assertTrue(appendable.toString().contains("Location has 1 arrows"));
  }

  @Test
  public void testPlayerStartsWithOnlyThreeArrows() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y N Y E 1 W N Y N 1 E N Y N 1 W N "
            + "Y N 1 Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);
    String[] result = appendable.toString().split("\n");

    assertEquals("You are out of arrows.", result[result.length - 8]);
    assertTrue(appendable.toString().contains("You are out of arrows."));

  }


  @Test
  public void testPossibleMovesFromCurrentLocation() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    StringReader input = new StringReader("Y Y Y W 1 Q");

    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);

    assertTrue(appendable.toString().contains("Player can now move to :"
            + "North || East || West || "));
  }

  @Test
  public void testIfPlayerWins() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);

    StringReader input = new StringReader("Y Y Y N 1 W Y N E Y N 1 N N N Q");

    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);
    assertTrue(appendable.toString().contains("Yay! You won. Do "
            + "you still wish to roam around?"));
  }


  //Exceptions:
  @Test
  public void testInvalidDistance() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y Y E -1 Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);
    assertTrue(appendable.toString().contains("You entered an invalid distance. Now " +
            "you will have to move!"));
  }


  @Test
  public void testNoMoreArrows() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y N Y E 1 W N Y N 1 E N Y N 1 W N "
            + "Y N 1 Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);
    String[] result = appendable.toString().split("\n");

    assertEquals("You are out of arrows.", result[result.length - 8]);
    assertTrue(appendable.toString().contains("You are out of arrows."));

  }


  @Test
  public void testMissedToShootOtyugh() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y Y S 1 Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);
    assertTrue(appendable.toString().contains("Ah! You could not shoot the "
            + "Otyugh."));
  }


  @Test
  public void testInvalidMove() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y Y S 1 T Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);
    assertTrue(appendable.toString().contains("Invalid move input"));

  }

  @Test
  public void testInvalidMoveToALocationNotConnectedToCurrentLocation() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y Y S 1 S Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);
    assertTrue(appendable.toString().contains("Cannot move in that direction."));

  }


  @Test
  public void testInvalidShootDirection() {
    Randomizer randomizer = new MockRandomInterconnectivityWrapping();
    int row = 3;
    int column = 3;
    int interconnectivity = 3;
    DungeonType dungeonType = DungeonType.WRAPPING;
    int treasurePercentage = 100;
    int difficulty = 5;

    DungeonInterface dungeon = new Dungeon(row, column, interconnectivity,
            dungeonType, treasurePercentage, randomizer, difficulty);


    StringReader input = new StringReader("Y Y Y T S 1 Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);

    c.playConsoleGame(dungeon);
    assertTrue(appendable.toString().contains("Enter valid direction to shoot at:"));

  }

  @Test(expected = IllegalArgumentException.class)
  public void ifModelNull() {
    DungeonInterface dungeonModel = null;

    StringReader input = new StringReader("Y Y Y E -1 Q");
    Appendable appendable = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, appendable);
    c.playConsoleGame(dungeonModel);
  }


}
