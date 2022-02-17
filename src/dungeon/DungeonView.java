package dungeon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * This class represents the main dungeon game view. It consists of all the
 * panels and other components like buttons etc. that are required to play
 * the Graphical Adventure Game.
 */
public class DungeonView extends JFrame implements View, KeyListener {

  private ReadOnlyDungeonInterface readOnlyDungeonModel;
  private DungeonController controller;

  private JPanel mainPanel;
  private JPanel upperPanel;
  private JPanel lowerPanel;
  private DungeonPanel dungeonPanel;
  private MovesPanel movesPanel;
  private LocationDescriptionPanel locationDescriptionPanel;
  private PlayerDescriptionPanel playerDescriptionPanel;


  private int rowCount;
  private int columnCount;
  private int interconnectivity;
  private int difficulty;
  private int treasurePercentage;
  private DungeonType dungeonType;

  /**
   * Constructor to create an object of our game view.
   *
   * @param readOnlyDungeonModel It represents the model which can only be
   *                             read.
   * @param controller           It represents the controller.
   */
  public DungeonView(ReadOnlyDungeonInterface readOnlyDungeonModel,
                     DungeonController controller) {
    if (readOnlyDungeonModel == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (controller == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }
    super.setTitle("Dungeon Adventure");
    this.readOnlyDungeonModel = readOnlyDungeonModel;
    this.controller = controller;
    this.setSize(new Dimension(800, 800));

    mainPanel = new JPanel();
    BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
    mainPanel.setLayout(boxLayout);


    upperPanel = new JPanel();
    upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
    upperPanel.setPreferredSize(new Dimension(800, 400));
    lowerPanel = new JPanel();
    lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.X_AXIS));


    dungeonPanel = new DungeonPanel(readOnlyDungeonModel, controller);
    locationDescriptionPanel = new LocationDescriptionPanel(readOnlyDungeonModel, controller);
    movesPanel = new MovesPanel(readOnlyDungeonModel, controller);
    movesPanel.setPreferredSize(new Dimension(400, 400));
    movesPanel.setMaximumSize(new Dimension(400, 400));
    playerDescriptionPanel = new PlayerDescriptionPanel(readOnlyDungeonModel, controller);


    manageLayouts();
    initNewMenu();
    pack();
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  private void manageLayouts() {
    dungeonPanel.setPreferredSize(new Dimension(64 * readOnlyDungeonModel.getDungeon()[0].length,
            64 * readOnlyDungeonModel.getDungeon().length));
    JScrollPane scrollPane = new JScrollPane(dungeonPanel,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.getVerticalScrollBar().setUnitIncrement(100);
    scrollPane.getHorizontalScrollBar().setUnitIncrement(100);
    scrollPane.setWheelScrollingEnabled(true);


    upperPanel.add(scrollPane);
    upperPanel.add(locationDescriptionPanel);
    lowerPanel.add(movesPanel);
    lowerPanel.add(playerDescriptionPanel);
    upperPanel.setBackground(Color.decode("#D2BF55"));


    mainPanel.add(upperPanel);
    mainPanel.add(lowerPanel);

    this.getContentPane().add(mainPanel);
    this.setFocusable(true);
    this.requestFocusInWindow();

    if (this.getKeyListeners().length < 1) {
      addKeyListener(this);
    }
  }

  private void initNewMenu() {
    JMenuBar mb;
    JMenu menu;

    mb = new JMenuBar();
    mainPanel.add(mb);

    JMenuItem i1;
    JMenuItem i2;

    menu = new JMenu("Menu");
    i1 = new JMenuItem("New Game");
    i2 = new JMenuItem("Restart Game");
    JMenuItem i3;
    JMenuItem i4;
    i3 = new JMenuItem("Instructions");
    i4 = new JMenuItem("Quit");
    menu.add(i1);
    menu.add(i2);
    menu.add(i3);
    menu.add(i4);

    mb.add(menu);
    this.setJMenuBar(mb);

    i1.addActionListener(e -> {
      this.setVisible(false);
      controller.playGraphicalGame();
      this.setVisible(false);
      setVisible(false);
    });
    i2.addActionListener(e -> {
      controller.buildNewModel(rowCount, columnCount, interconnectivity,
              dungeonType, treasurePercentage, difficulty);
      this.setVisible(false);
    });
    i3.addActionListener(e -> {
      InstructionsFrame instructionsFrame = new InstructionsFrame();
    });
    i4.addActionListener(e -> {
      System.exit(ABORT);
    });
  }


  /**
   * Refresh the view to reflect any changes in the game state.
   */
  @Override
  public void refresh() {
    makeVisible();
    repaint();
  }

  /**
   * Make the view visible to start the game session.
   */
  @Override
  public void makeVisible() {
    setVisible(true);
  }


  /**
   * This is a method to save the current settings, so that whenever a game is
   * restarted we can use them to re-initialize the model.
   *
   * @param newReadOnlyModel  It represents the read-only dungeon model
   * @param row               It represents the number of rows in the dungeon
   * @param column            It represents the number of columns in the
   *                          dungeon.
   * @param interConnectivity It represents the interconnectivity of the
   *                          dungeon
   * @param dunType           It represents the type of dungeon.
   * @param treasurePercent   It represents the percentage of treasure in
   *                          dungeon.
   * @param diff              It represents the number of Monsters in the
   *                          dungeon.
   */
  @Override
  public void setNewSettings(ReadOnlyDungeonInterface newReadOnlyModel, int row,
                             int column, int interConnectivity,
                             DungeonType dunType, int treasurePercent, int diff) {
    if (readOnlyDungeonModel == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (dunType == null) {
      throw new IllegalArgumentException("Dungeon Type cannot be null");
    }

    this.readOnlyDungeonModel = newReadOnlyModel;
    this.rowCount = row;
    this.columnCount = column;
    this.interconnectivity = interConnectivity;
    this.dungeonType = dunType;
    this.treasurePercentage = treasurePercent;
    this.difficulty = diff;


    dungeonPanel.setModel(this.readOnlyDungeonModel);
    movesPanel.setModel(readOnlyDungeonModel);
    locationDescriptionPanel.setModel(readOnlyDungeonModel);
  }

  /**
   * Method to set the focus in the main view window so that any key events
   * which take place are detected even when the focus is shifted to another
   * panel.
   */
  @Override
  public void setFocus() {
    this.setFocusable(true);
    this.requestFocusInWindow();
  }

  /**
   * Method to make the view invisible.
   */
  @Override
  public void makeInvisible() {
    this.setVisible(false);
  }

  /**
   * Invoked when a key has been typed.
   * See the class description for {@link KeyEvent} for a definition of
   * a key typed event.
   *
   * @param e the event to be processed
   */
  @Override
  public void keyTyped(KeyEvent e) {
    //We do not want to register any event when a key is typed.
  }

  /**
   * Invoked when a key has been pressed.
   * See the class description for {@link KeyEvent} for a definition of
   * a key pressed event.
   *
   * @param e the event to be processed
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (!e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_UP) {
      controller.movePlayer("N");
    }
    if (!e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_DOWN) {
      controller.movePlayer("S");
    }
    if (!e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_RIGHT) {
      controller.movePlayer("E");
    }
    if (!e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_LEFT) {
      controller.movePlayer("W");
    }
    if (!e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_T) {
      controller.pickUpTreasure();
    }
    if (!e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_A) {
      controller.pickUpArrows();
    }
    if (e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_UP) {

      int dist = Integer.parseInt(JOptionPane.showInputDialog("Enter "
              + "distance"));
      controller.shoot("N", dist);
    }
    if (e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_DOWN) {

      int dist = Integer.parseInt(JOptionPane.showInputDialog("Enter "
              + "distance"));
      JOptionPane optionPane = new JOptionPane();

      controller.shoot("S", dist);
    }
    if (e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_RIGHT) {

      int dist = Integer.parseInt(JOptionPane.showInputDialog("Enter "
              + "distance"));
      controller.shoot("E", dist);
    }
    if (e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_LEFT) {

      int dist = Integer.parseInt(JOptionPane.showInputDialog("Enter "
              + "distance"));
      controller.shoot("W", dist);
    }
  }

  /**
   * Invoked when a key has been released.
   * See the class description for {@link KeyEvent} for a definition of
   * a key released event.
   *
   * @param e the event to be processed
   */
  @Override
  public void keyReleased(KeyEvent e) {
    //We do not want to register any event when a key is released.
  }


}
