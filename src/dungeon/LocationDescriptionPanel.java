package dungeon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


class LocationDescriptionPanel extends JPanel {

  private ReadOnlyDungeonInterface readOnlyDungeonModel;
  private DungeonController controller;
  private BufferedImage treasureImage;
  private BufferedImage arrowImage;
  private JButton treasureBtn;
  private JButton arrowBtn;


  LocationDescriptionPanel(ReadOnlyDungeonInterface readOnlyDungeonModel,
                           DungeonController controller) {
    if (readOnlyDungeonModel == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (controller == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }
    this.readOnlyDungeonModel = readOnlyDungeonModel;
    this.controller = controller;
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    treasureBtn = new JButton("Click to pickup");
    treasureBtn.setHorizontalAlignment(SwingConstants.CENTER);
    treasureBtn.setEnabled(false);
    arrowBtn = new JButton("Click to pickup");
    arrowBtn.setHorizontalAlignment(SwingConstants.CENTER);
    treasureBtn.setSize(new Dimension(70, 20));
    arrowBtn.setSize(new Dimension(70, 20));
    arrowBtn.setEnabled(false);
    add(treasureBtn);
    add(arrowBtn);

    this.setBackground(Color.decode("#D2BF55"));
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

    Graphics2D graphics2d = (Graphics2D) g;

    Location playerCurrentLocation = readOnlyDungeonModel.getPlayerLocation();

    if (playerCurrentLocation.getTreasureList().size() > 0) {
      try {
        treasureBtn.setEnabled(true);
        if (playerCurrentLocation.getTreasureList().contains(Treasure.RUBY)) {
          treasureImage = ImageIO.read(getClass().getResource("/images/ruby"
                  + ".png"));
          treasureBtn.setIcon(new ImageIcon(treasureImage));
        } else if (playerCurrentLocation.getTreasureList().contains(Treasure.SAPPHIRE)) {
          treasureImage = ImageIO.read(getClass().getResource("/images/emerald"
                  + ".png"));
          treasureBtn.setIcon(new ImageIcon(treasureImage));
        } else if (playerCurrentLocation.getTreasureList().contains(Treasure.DIAMOND)) {
          treasureImage = ImageIO.read(getClass().getResource("/images/diamond"
                  + ".png"));
          treasureBtn.setIcon(new ImageIcon(treasureImage));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      treasureImage = null;
      treasureBtn.setIcon(null);
    }

    if (playerCurrentLocation.getArrowCountInLocation() > 0) {
      arrowBtn.setEnabled(true);
      try {
        arrowImage = ImageIO.read(getClass().getResource("/images/arrow-black"
                + ".png"));
        arrowBtn.setIcon(new ImageIcon(arrowImage));

      } catch (IOException e) {
        e.printStackTrace();
      }

    } else {
      arrowImage = null;
      arrowBtn.setIcon(null);
    }


    if (treasureBtn.getActionListeners().length < 1) {
      treasureBtn.addActionListener(e -> {
        controller.pickUpTreasure();
        treasureBtn.setEnabled(false);
        this.setFocusable(false);
        controller.setViewFocus();
      });
    }

    if (arrowBtn.getActionListeners().length < 1) {
      arrowBtn.addActionListener(e -> {
        controller.pickUpArrows();
        arrowBtn.setEnabled(false);
        this.setFocusable(false);
        controller.setViewFocus();
      });
    }

  }
}
