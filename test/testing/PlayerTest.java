package testing;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import dungeon.Cave;
import dungeon.Player;
import dungeon.Treasure;
import dungeon.TrueRandom;

import static org.junit.Assert.assertEquals;

/**
 * This class is used to test the functionalities of the Player entity in
 * our Dungeon model.
 */
public class PlayerTest {

  Player p;

  @Before
  public void setUp() {
    p = new Player(new TrueRandom());
  }

  @Test
  public void getPlayerLocation() {
    p.setPlayerLocation(new Cave(1));
    assertEquals(1, p.getPlayerLocation().getCaveId());
  }

  @Test
  public void setPlayerLocation() {
    p.setPlayerLocation(new Cave(2));
    assertEquals(2, p.getPlayerLocation().getCaveId());
  }

  @Test
  public void getWealth() {
    List<Treasure> t = new ArrayList<>();
    t.add(Treasure.SAPPHIRE);
    p.setWealth(t);

    assertEquals("SAPPHIRE", t.get(0).toString());
  }

  @Test
  public void setWealth() {
    List<Treasure> t = new ArrayList<>();
    t.add(Treasure.DIAMOND);
    p.setWealth(t);

    assertEquals(Treasure.DIAMOND, t.get(0));
  }


}