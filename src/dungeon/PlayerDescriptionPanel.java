package dungeon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

class PlayerDescriptionPanel extends JPanel {

  private ReadOnlyDungeonInterface readOnlyDungeonModel;

  private BufferedImage treasureImage;
  private BufferedImage arrowImage;


  PlayerDescriptionPanel(ReadOnlyDungeonInterface readOnlyDungeonModel,
                         DungeonController controller) {
    if (readOnlyDungeonModel == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (controller == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }
    this.readOnlyDungeonModel = readOnlyDungeonModel;

    this.setBackground(Color.decode("#D2BF55"));
    this.setBackground(Color.decode("#D2BF55"));
    Border border = new LineBorder(Color.decode("#875053"), 1, true);
    setBorder(border);
    JLabel descriptionLabel = new JLabel("Player Inventory");
    this.add(descriptionLabel);
  }

  /**
   * Method to set the new model which is created in the new game.
   *
   * @param resetModel It represents the new model
   */
  public void setModel(ReadOnlyDungeonInterface resetModel) {
    if (readOnlyDungeonModel == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.readOnlyDungeonModel = resetModel;
  }

  /**
   * This is the paintComponent method of this panel. It is called everytime
   * a repaint() gets a call.
   *
   * @param g It is the Graphics type variable.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);


    Graphics2D g2d = (Graphics2D) g;


    g2d.drawString("Item Type ", 80, 70);
    g2d.drawString("|    Quantity collected", 180, 70);
    int offY = 100;
    List<Treasure> playerWealth = readOnlyDungeonModel.getPlayerWealth();

    if (playerWealth.size() > 0) {
      if (playerWealth.contains(Treasure.DIAMOND)) {
        try {
          treasureImage = ImageIO.read(getClass().getResource("/images"
                  + "/diamond" + ".png"));
          g2d.drawImage(treasureImage, 90, offY, null);
          g2d.drawString(String.valueOf(findNumberOfGivenTreasure(playerWealth,
                  Treasure.DIAMOND)), 230, offY + 15);
          offY += 70;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (playerWealth.contains(Treasure.RUBY)) {
        try {
          treasureImage = ImageIO.read(getClass().getResource("/images/ruby"
                  + ".png"));

          g2d.drawImage(treasureImage, 90, offY, null);
          g2d.drawString(String.valueOf(findNumberOfGivenTreasure(playerWealth,
                  Treasure.RUBY)), 230, offY + 15);
          offY += 70;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (playerWealth.contains(Treasure.SAPPHIRE)) {
        try {
          treasureImage = ImageIO.read(getClass().getResource("/images"
                  + "/emerald"
                  + ".png"));

          g2d.drawImage(treasureImage, 90, offY, null);
          g2d.drawString(String.valueOf(findNumberOfGivenTreasure(playerWealth,
                  Treasure.SAPPHIRE)), 230, offY + 15);

          offY += 70;
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        treasureImage = null;
      }


    }

    if (readOnlyDungeonModel.getPlayerArrowCount() > 0) {
      try {
        arrowImage = ImageIO.read(getClass().getResource("/images/arrow-black"
                + ".png"));
        g2d.drawImage(arrowImage, 90, offY, null);
        g2d.drawString(String.valueOf((Integer) readOnlyDungeonModel.getPlayerArrowCount()),
                230, offY + 10);
        offY += 70;
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      arrowImage = null;
    }

    Location playerCurrentLocation = readOnlyDungeonModel.getPlayerLocation();
    if (playerCurrentLocation.hasThief()) {
      g2d.drawString("You just got mugged by the thief :(", 90, offY + 10);
      offY += 50;
    }


  }

  private Integer findNumberOfGivenTreasure(List<Treasure> playerWealth,
                                            Treasure treasure) {
    int count = 0;
    for (Treasure t : playerWealth) {
      if (t.equals(treasure)) {
        count += 1;
      }
    }
    return (Integer) count;
  }
}
