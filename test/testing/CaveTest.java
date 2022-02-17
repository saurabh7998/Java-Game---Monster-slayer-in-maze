package testing;

import org.junit.Before;
import org.junit.Test;

import dungeon.Cave;
import dungeon.CaveType;
import dungeon.Location;
import dungeon.Treasure;

import static org.junit.Assert.assertEquals;

/**
 * This class is used to test the functionalities of the Cave.
 */
public class CaveTest {

  private Location cave;

  @Before
  public void setUp() {
    cave = new Cave(1);
    cave.setTreasureList(Treasure.RUBY);
  }

  @Test
  public void getCaveId() {
    assertEquals(1, cave.getCaveId());
  }

  @Test
  public void getTreasureList() {
    assertEquals("RUBY", cave.getTreasureList().get(0).toString());
  }

  @Test
  public void setTreasureList() {
    cave.setTreasureList(Treasure.DIAMOND);
    assertEquals("DIAMOND", cave.getTreasureList().get(1).toString());
  }


  @Test
  public void getCaveType() {
    cave.setCaveType(CaveType.CAVE);
    assertEquals(CaveType.CAVE, cave.getCaveType());
  }

  @Test
  public void setCaveType() {
    cave.setCaveType(CaveType.TUNNEL);
    assertEquals(CaveType.TUNNEL, cave.getCaveType());
  }

  @Test
  public void getNorth() {
    cave.setNorth(new Cave(2));
    assertEquals(2, cave.getNorth().getCaveId());
  }

  @Test
  public void setNorth() {
    cave.setNorth(new Cave(3));
    assertEquals(3, cave.getNorth().getCaveId());
  }

  @Test
  public void getSouth() {
    cave.setSouth(new Cave(4));
    assertEquals(4, cave.getSouth().getCaveId());
  }

  @Test
  public void setSouth() {
    cave.setSouth(new Cave(5));
    assertEquals(5, cave.getSouth().getCaveId());
  }

  @Test
  public void getEast() {
    cave.setEast(new Cave(6));
    assertEquals(6, cave.getEast().getCaveId());
  }

  @Test
  public void setEast() {
    cave.setEast(new Cave(7));
    assertEquals(7, cave.getEast().getCaveId());
  }

  @Test
  public void getWest() {
    cave.setWest(new Cave(7));
    assertEquals(7, cave.getWest().getCaveId());
  }

  @Test
  public void setWest() {
    cave.setWest(new Cave(8));
    assertEquals(8, cave.getWest().getCaveId());
  }

}