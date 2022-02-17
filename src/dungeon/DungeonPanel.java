package dungeon;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


class DungeonPanel extends JPanel {

  private ReadOnlyDungeonInterface readOnlyDungeonModel;
  private DungeonController controller;
  private BufferedImage locationImg;
  private BufferedImage playerImg;
  private BufferedImage otyughImg;
  private BufferedImage thiefImg;
  private BufferedImage smellImage;
  private BufferedImage combinedImage;


  DungeonPanel(ReadOnlyDungeonInterface readOnlyDungeonModel, DungeonController controller) {
    if (readOnlyDungeonModel == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (controller == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }

    this.readOnlyDungeonModel = readOnlyDungeonModel;
    this.controller = controller;

    MouseAdapter clickAdapter = new MouseAdapter() {

      Location[][] dungeonGrid = readOnlyDungeonModel.getDungeon();

      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        Location playerCurrentLocation =
                readOnlyDungeonModel.getPlayerLocation();
        DungeonIndex playerIndex = getIndex(playerCurrentLocation.getCaveId());
        double coordinateOfX = e.getX();
        double coordinateOfY = e.getY();

        double positionOfClickX = Math.floor(coordinateOfY / 64);
        double positionOfClickY = Math.floor(coordinateOfX / 64);

        int positionOfPlayerX = playerIndex.getIndexX();
        int positionOfPlayerY = playerIndex.getIndexY();


        if (positionOfPlayerX - positionOfClickX == 1
                && positionOfPlayerY - positionOfClickY == 0) {
          controller.movePlayer("N");
        } else if (positionOfPlayerX - positionOfClickX == -1
                && positionOfPlayerY - positionOfClickY == 0) {
          controller.movePlayer("S");
        } else if (positionOfPlayerX - positionOfClickX == 0
                && positionOfPlayerY - positionOfClickY == 1) {
          controller.movePlayer("W");
        } else if (positionOfPlayerX - positionOfClickX == 0
                && positionOfPlayerY - positionOfClickY == -1) {
          controller.movePlayer("E");
        } else if (positionOfPlayerY == dungeonGrid[0].length - 1 && positionOfClickY == 0
                && positionOfClickX == positionOfPlayerX
                && (positionOfPlayerY - positionOfClickY) == positionOfPlayerY) {
          controller.movePlayer("E");
        } else if (positionOfPlayerY == 0 && positionOfClickY == dungeonGrid[0].length - 1
                && positionOfClickX == positionOfPlayerX
                && (positionOfClickY - positionOfPlayerY) == positionOfClickY) {
          controller.movePlayer("W");
        } else if (positionOfPlayerX == dungeonGrid.length - 1 && positionOfClickX == 0
                && positionOfClickY == positionOfPlayerY
                && (positionOfPlayerX - positionOfClickX) == positionOfPlayerX) {
          controller.movePlayer("S");
        } else if (positionOfPlayerX == 0 && positionOfClickX == dungeonGrid.length - 1
                && positionOfClickY == positionOfPlayerY
                && (positionOfClickX - positionOfPlayerX) == positionOfClickX) {
          controller.movePlayer("N");
        }

      }

      private DungeonIndex getIndex(int a) {
        int count = 0;
        for (int i = 0; i < dungeonGrid.length; i++) {
          for (int j = 0; j < dungeonGrid[0].length; j++) {
            count += 1;
            if (count == a) {
              DungeonIndex dungeonIndex = new DungeonIndex(i, j);
              return new DungeonIndex(dungeonIndex);
            }
          }
        }
        return null;
      }
    };

    addMouseListener(clickAdapter);
  }

  /**
   * Method to set the new model which is created in the new game.
   *
   * @param resetModel It represents the new model
   */
  public void setModel(ReadOnlyDungeonInterface resetModel) {
    this.readOnlyDungeonModel = resetModel;
  }


  /**
   * This is the paintComponent method of this panel. It is called everytime
   * a repaint() gets a call.
   *
   * @param p It is the Graphics type variable.
   */
  @Override
  public void paintComponent(Graphics p) {
    super.paintComponent(p);


    Graphics2D graphics2d = (Graphics2D) p;

    int offX = 0;
    int offY = 0;

    Location[][] dungeonGrid = this.readOnlyDungeonModel.getDungeon();
    Location playerCurrentLocation =
            this.readOnlyDungeonModel.getPlayerLocation();
    if (playerCurrentLocation.getCaveId() == readOnlyDungeonModel.getFinalGoalLocation()
            && !playerCurrentLocation.hasMonster()) {
      JOptionPane.showMessageDialog(this, "You win!!!\n Press OK to restart "
              + "the game.");
      controller.playGraphicalGame();
    }
    for (int i = 0; i < dungeonGrid.length; i++) {
      for (int j = 0; j < dungeonGrid[0].length; j++) {

        if (dungeonGrid[i][j].isLocationExplored()) {
          try {

            locationImg =
                    ImageIO.read(getClass().getResource("/images/"
                            + dungeonGrid[i][j].getCaveNeighbours() + ".png"));
            if (readOnlyDungeonModel.getPlayerLocation() == dungeonGrid[i][j]) {
              String playerImgPath = "";
              if (readOnlyDungeonModel.shotOrNot()) {
                playerImgPath = "/images/player-overlay.png";
                playerImg = ImageIO.read(getClass().getResource("/images"
                        + "/player-overlay.png"));
              } else {
                playerImgPath = "/images/player.png";
                playerImg = ImageIO.read(getClass().getResource("/images/player.png"));
              }
              combinedImage = overlay(locationImg, playerImgPath, 16);

              if (readOnlyDungeonModel.detectSmell(playerCurrentLocation) != null) {
                try {
                  if (readOnlyDungeonModel.detectSmell(playerCurrentLocation)
                          .equals(Smell.STRONG_PUNGENT_SMELL)) {
                    smellImage = ImageIO.read(getClass().getResource(
                            "/images/stench02.png"));
                    combinedImage = overlay(combinedImage, "/images/stench02"
                                    + ".png",
                            0);
                  } else if (readOnlyDungeonModel.detectSmell(playerCurrentLocation)
                          .equals(Smell.PUNGENT_SMELL)) {
                    smellImage = ImageIO.read(getClass().getResource(
                            "/images/stench01"
                                    + ".png"));
                    combinedImage = overlay(combinedImage, "/images/stench01"
                                    + ".png",
                            0);
                  }
                } catch (IOException e) {
                  e.printStackTrace();
                }
              } else {
                smellImage = null;
              }
            } else {
              playerImg = null;
            }
            if (dungeonGrid[i][j].hasMonster()) {
              otyughImg = ImageIO.read(getClass().getResource("/images/otyugh"
                      + ".png"));
              combinedImage = overlay(locationImg, "/images/otyugh.png", 16);
            } else {
              otyughImg = null;
            }

            if (dungeonGrid[i][j].hasThief()) {
              thiefImg = ImageIO.read(getClass().getResource("/images/thief"
                      + ".png"));
              combinedImage = overlay(locationImg, "/images/thief.png", 16);
            } else {
              thiefImg = null;
            }

          } catch (IOException e) {
            e.printStackTrace();
          }

          if (offX == dungeonGrid[0].length) {
            offY += 1;
            offX = 0;
          }
          if (otyughImg != null || playerImg != null || thiefImg != null) {
            graphics2d.drawImage(combinedImage, offX * 64, offY * 64, null);
          } else {
            graphics2d.drawImage(locationImg, offX * 64, offY * 64, null);
          }

          offX++;
        } else {
          try {
            if (offX == dungeonGrid[0].length) {
              offY += 1;
              offX = 0;
            }
            locationImg = ImageIO.read(getClass().getResource("/images/black"
                    + ".png"));
            graphics2d.drawImage(locationImg, offX * 64, offY * 64, null);
            offX++;
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

      }
    }
    if (readOnlyDungeonModel.getPlayerLocation().hasMonster()) {
      if (readOnlyDungeonModel.getPlayerLocation().getMonster().getHealth() == 100) {
        JOptionPane.showMessageDialog(this, "Chomp Chomp Chomp!!!");
        controller.playGraphicalGame();
      } else {
        if (!readOnlyDungeonModel.checkIfPlayerIsAlive()) {
          JOptionPane.showMessageDialog(this, "Chomp Chomp Chomp!!!");
          controller.playGraphicalGame();
        }
      }
    }
  }


  private BufferedImage overlay(BufferedImage starting, String fpath, int offset)
          throws IOException {
    BufferedImage overlay = ImageIO.read(getClass().getResource(fpath));
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, offset, offset, null);
    return combined;
  }

}



