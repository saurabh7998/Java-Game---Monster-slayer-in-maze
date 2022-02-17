package dungeon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


class MovesPanel extends JPanel {

  private ReadOnlyDungeonInterface readOnlyDungeonModel;
  private DungeonController controller;

  private JButton moveNorthBtn;
  private JButton moveSouthBtn;
  private JButton moveEastBtn;
  private JButton moveWestBtn;
  private GridBagConstraints constraints;


  MovesPanel(ReadOnlyDungeonInterface readOnlyDungeonModel,
             DungeonController controller) {
    if (readOnlyDungeonModel == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (controller == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }

    constraints = new GridBagConstraints();
    this.readOnlyDungeonModel = readOnlyDungeonModel;
    this.controller = controller;

    moveNorthBtn = new JButton("North");
    moveSouthBtn = new JButton("South");
    moveEastBtn = new JButton("East");
    moveWestBtn = new JButton("West");

    moveNorthBtn.setBackground(Color.decode("#875053"));
    moveNorthBtn.setOpaque(true);
    moveSouthBtn.setBackground(Color.decode("#875053"));
    moveSouthBtn.setOpaque(true);
    moveWestBtn.setBackground(Color.decode("#875053"));
    moveWestBtn.setOpaque(true);
    moveEastBtn.setBackground(Color.decode("#875053"));
    moveEastBtn.setOpaque(true);

    this.setLayout(new GridBagLayout());
    this.setBackground(Color.decode("#D2BF55"));
    Border border = new LineBorder(Color.decode("#875053"), 1, true);
    setBorder(border);
    addToGridBag(moveNorthBtn, 1, 0);
    addToGridBag(moveWestBtn, 0, 1);
    addToGridBag(moveEastBtn, 2, 1);
    addToGridBag(moveSouthBtn, 1, 2);

  }

  private void addToGridBag(JButton button, int x, int y) {
    constraints.gridx = x;
    constraints.gridy = y;
    add(button, constraints);
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


    int offY = 20;
    Location[][] dungeonGrid = readOnlyDungeonModel.getDungeon();


    if (moveNorthBtn.getActionListeners().length < 1) {
      moveNorthBtn.addActionListener(e -> {
        moveNorthBtn.setFocusable(false);
        controller.movePlayer("N");
        this.setFocusable(false);
        controller.setViewFocus();
      });
    }

    if (moveSouthBtn.getActionListeners().length < 1) {
      moveSouthBtn.addActionListener(e -> {
        controller.movePlayer("S");
        this.setFocusable(false);
        controller.setViewFocus();
      });
    }

    if (moveEastBtn.getActionListeners().length < 1) {
      moveEastBtn.addActionListener(e -> {
        controller.movePlayer("E");
        this.setFocusable(false);
        controller.setViewFocus();
      });
    }

    if (moveWestBtn.getActionListeners().length < 1) {
      moveWestBtn.addActionListener(e -> {
        controller.movePlayer("W");
        this.setFocusable(false);
        controller.setViewFocus();
      });
    }

    Location playerCurrentLocation = this.readOnlyDungeonModel.getPlayerLocation();

    if (readOnlyDungeonModel.detectSmell(playerCurrentLocation) != null) {
      if (readOnlyDungeonModel.detectSmell(playerCurrentLocation)
              .equals(Smell.STRONG_PUNGENT_SMELL)) {
        graphics2d.drawString("You smell something terrible! Beware!!!", 10,
                offY + 10);
        offY += 20;
      } else if (readOnlyDungeonModel.detectSmell(playerCurrentLocation)
              .equals(Smell.PUNGENT_SMELL)) {
        graphics2d.drawString("Huh, You smell something?", 10, offY + 10);
        offY += 20;
      }
    }

    if (readOnlyDungeonModel.shotOrNot()) {
      graphics2d.drawString("You shot the Otyugh Successfully", 10, offY + 20);
    }

    graphics2d.drawString("How to play?", 10, 300);
    graphics2d.drawString("Click on Menu -> Instructions", 10, 320);

  }




}
