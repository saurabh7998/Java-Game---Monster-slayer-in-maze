package testing;

import org.junit.Test;

import dungeon.Dungeon;
import dungeon.DungeonController;
import dungeon.DungeonGraphicalController;
import dungeon.DungeonType;
import dungeon.MockRandom;
import dungeon.MockView;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This is a class to test the Graphical Game Controller. It tests all the
 * methods and functionalities of this controller.
 */
public class GraphicalControllerTest {


  @Test
  public void testMovePlayerSouth() {
    Appendable out = new StringBuilder();
    Dungeon dungeonModel = new Dungeon(3, 3, 2,
            DungeonType.NON_WRAPPING, 50, new MockRandom(), 1);

    MockView mockView = new MockView(out);

    DungeonController controller =
            new DungeonGraphicalController(dungeonModel, mockView);

    controller.movePlayer("S");

    assertEquals("movePlayer method of controller called refresh method of "
            + "view", out.toString());
  }

  @Test
  public void testMovePlayerEast() {
    Appendable out = new StringBuilder();
    Dungeon dungeonModel = new Dungeon(3, 3, 2,
            DungeonType.NON_WRAPPING, 50, new MockRandom(), 1);

    MockView mockView = new MockView(out);

    DungeonController controller =
            new DungeonGraphicalController(dungeonModel, mockView);

    controller.movePlayer("E");

    assertEquals("movePlayer method of controller called refresh method of "
            + "view", out.toString());
  }

  @Test
  public void testMovePlayerWest() {
    Appendable out = new StringBuilder();
    Dungeon dungeonModel = new Dungeon(3, 3, 2,
            DungeonType.NON_WRAPPING, 50, new MockRandom(), 1);

    MockView mockView = new MockView(out);

    DungeonController controller =
            new DungeonGraphicalController(dungeonModel, mockView);

    controller.movePlayer("W");

    assertEquals("movePlayer method of controller called refresh method of "
            + "view", out.toString());
  }

  @Test
  public void testPickUpTreasure() {
    Appendable out = new StringBuilder();
    Dungeon dungeonModel = new Dungeon(3, 3, 2,
            DungeonType.NON_WRAPPING, 50, new MockRandom(), 1);

    MockView mockView = new MockView(out);

    DungeonController controller =
            new DungeonGraphicalController(dungeonModel, mockView);

    controller.movePlayer("S");
    controller.pickUpTreasure();

    assertTrue(out.toString().contains("pickUpTreasure method of controller "
            + "called refresh method of view"));
  }

  @Test
  public void testPickUpArrows() {
    Appendable out = new StringBuilder();
    Dungeon dungeonModel = new Dungeon(3, 3, 2,
            DungeonType.NON_WRAPPING, 50, new MockRandom(), 1);

    MockView mockView = new MockView(out);

    DungeonController controller =
            new DungeonGraphicalController(dungeonModel, mockView);

    controller.pickUpArrows();

    assertEquals("pickUpArrows method of controller called refresh method of "
            + "view", out.toString());
  }

  @Test
  public void testShoot() {
    Appendable out = new StringBuilder();
    Dungeon dungeonModel = new Dungeon(3, 3, 2,
            DungeonType.NON_WRAPPING, 50, new MockRandom(), 3);

    MockView mockView = new MockView(out);

    DungeonController controller =
            new DungeonGraphicalController(dungeonModel, mockView);

    controller.shoot("S", 1);

    assertEquals("shoot method of controller called refresh method of "
            + "view", out.toString());
  }

}
