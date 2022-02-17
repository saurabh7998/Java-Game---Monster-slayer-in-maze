package dungeon;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


/**
 * This class represents the settings view. This window allows you to set the
 * dungeon settings in order to start a new game.
 */
public class ConfigurationsView extends JFrame {

  private DungeonController controller;
  private boolean isSetup;

  /**
   * Constructor to create and initialize the initial Settings view/window.
   * This window allows you to set the dungeon settings in order to start a
   * new game.
   *
   * @param controller It represents the controller of the graphical
   *                   adventure game.
   */
  public ConfigurationsView(DungeonController controller) {
    if (controller == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }
    this.controller = controller;
    setBounds(100, 100, 730, 489);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setLayout(null);
    init();
    this.setVisible(true);
  }

  private void init() {
    JLabel rowLabel = new JLabel("Row Count");
    rowLabel.setBounds(30, 68, 100, 14);
    this.getContentPane().add(rowLabel);
    JTextField rowTextField = new JTextField();
    rowTextField.setBounds(228, 65, 86, 20);
    this.getContentPane().add(rowTextField);
    rowTextField.setColumns(10);

    JLabel columnLabel = new JLabel("Column Count");
    columnLabel.setBounds(30, 115, 100, 14);
    getContentPane().add(columnLabel);
    JTextField columnTextField = new JTextField();
    columnTextField.setBounds(228, 112, 86, 20);
    getContentPane().add(columnTextField);
    columnTextField.setColumns(10);

    JLabel interconnectivityLabel = new JLabel("Interconnectivity");
    interconnectivityLabel.setBounds(30, 162, 120, 14);
    getContentPane().add(interconnectivityLabel);
    JTextField interconnectivityTextField = new JTextField();
    interconnectivityTextField.setBounds(228, 159, 86, 20);
    getContentPane().add(interconnectivityTextField);
    interconnectivityTextField.setColumns(10);

    JLabel dungeonTypeLabel = new JLabel("Dungeon Type");
    dungeonTypeLabel.setBounds(30, 209, 100, 14);
    getContentPane().add(dungeonTypeLabel);

    JLabel wrappingLabel = new JLabel("Wrapping");
    wrappingLabel.setBounds(228, 209, 100, 20);
    getContentPane().add(wrappingLabel);
    JRadioButton wrappingRadioBtn = new JRadioButton("");
    wrappingRadioBtn.setBounds(292, 209, 109, 23);
    getContentPane().add(wrappingRadioBtn);

    JLabel nonWrappingLabel = new JLabel("NonWrapping");
    nonWrappingLabel.setBounds(392, 209, 100, 20);
    getContentPane().add(nonWrappingLabel);
    JRadioButton nonWrappingRadioBtn = new JRadioButton("");
    nonWrappingRadioBtn.setBounds(500, 209, 109, 23);
    getContentPane().add(nonWrappingRadioBtn);

    JLabel treasurePercentLabel = new JLabel("Treasure Percent");
    treasurePercentLabel.setBounds(30, 256, 120, 14);
    getContentPane().add(treasurePercentLabel);
    JTextField treasurePercentTextField = new JTextField();
    treasurePercentTextField.setBounds(228, 253, 86, 20);
    getContentPane().add(treasurePercentTextField);
    treasurePercentTextField.setColumns(10);

    JLabel difficultyLabel = new JLabel("No. of monsters");
    difficultyLabel.setBounds(30, 303, 120, 14);
    getContentPane().add(difficultyLabel);
    JTextField difficultyTextField = new JTextField();
    difficultyTextField.setBounds(228, 300, 86, 20);
    getContentPane().add(difficultyTextField);
    difficultyTextField.setColumns(10);


    JButton submitBtn = new JButton("Submit");
    submitBtn.setBackground(Color.BLUE);
    submitBtn.setForeground(Color.MAGENTA);
    submitBtn.setBounds(30, 387, 89, 23);
    getContentPane().add(submitBtn);

    submitBtn.addActionListener(event -> {
      int row = 0;
      int column = 0;
      int interconnectivity = 0;
      int treasure = 0;
      int difficulty = 0;

      boolean ifRowInvalid = false;
      boolean ifColumnInvalid = false;
      boolean ifInterconnectivityInvalid = false;
      boolean ifTreasureInvalid = false;
      boolean ifDifficultyInvalid = false;

      boolean isNotEmpty = true;


      DungeonType dungeonType = null;
      try {

        if (rowTextField.getText().isEmpty() || (columnTextField.getText().isEmpty())
                || (interconnectivityTextField.getText().isEmpty())
                || (treasurePercentTextField.getText().isEmpty())
                || ((wrappingRadioBtn.isSelected()) && (nonWrappingRadioBtn.isSelected()))
                || ((!wrappingRadioBtn.isSelected()) && (!nonWrappingRadioBtn.isSelected()))
                || (treasurePercentTextField.getText().isEmpty())
                || (difficultyTextField.getText().isEmpty())) {

          isNotEmpty = false;
          throw new IllegalArgumentException("Incorrect Data");

        }
      } catch (IllegalArgumentException iae) {
        JOptionPane.showMessageDialog(null, iae.getMessage());
      }

      if (isNotEmpty) {
        try {
          row = Integer.parseInt(rowTextField.getText());
        } catch (IllegalArgumentException e) {
          JOptionPane.showMessageDialog(null, "Row should be an integer "
                  + "number");
          ifRowInvalid = true;
        }
        try {
          column = Integer.parseInt(columnTextField.getText());
        } catch (IllegalArgumentException e) {
          JOptionPane.showMessageDialog(null, "Column should be an integer"
                  + "number");
          ifColumnInvalid = true;
        }
        try {
          interconnectivity =
                  Integer.parseInt(interconnectivityTextField.getText());
        } catch (IllegalArgumentException e) {
          JOptionPane.showMessageDialog(null, "Interconnectivity should be"
                  + " an integer number");
          ifInterconnectivityInvalid = true;
        }
        try {
          treasure = Integer.parseInt(treasurePercentTextField.getText());
        } catch (IllegalArgumentException e) {
          JOptionPane.showMessageDialog(null, "Treasure Percent should be"
                  + " an integer number");
          ifTreasureInvalid = true;
        }
        try {
          difficulty = Integer.parseInt(difficultyTextField.getText());
        } catch (IllegalArgumentException e) {
          JOptionPane.showMessageDialog(null, "Difficulty  should "
                  + "be an integer number");
          ifDifficultyInvalid = true;
        }

        try {
          if (Integer.parseInt(rowTextField.getText()) < 4) {
            throw new IllegalArgumentException("Row count "
                    + "cannot be less than 4");
          }
        } catch (IllegalArgumentException e) {
          JOptionPane.showMessageDialog(null, "Row count "
                  + "cannot be less than 4");
          ifRowInvalid = true;
        }

        try {
          if (Integer.parseInt(columnTextField.getText()) < 4) {
            throw new IllegalArgumentException("Column count "
                    + "cannot be less than 4");
          }
        } catch (IllegalArgumentException e) {
          JOptionPane.showMessageDialog(null, "Column count "
                  + "cannot be less than 4");
          ifColumnInvalid = true;
        }

        try {
          if (Integer.parseInt(interconnectivityTextField.getText()) < 0) {
            throw new IllegalArgumentException("Interconnectivity cannot "
                    + "be negative");
          }
        } catch (IllegalArgumentException e) {
          JOptionPane.showMessageDialog(null, "Interconnectivity cannot "
                  + "be negative");
          ifInterconnectivityInvalid = true;
        }
        try {
          if (Integer.parseInt(treasurePercentTextField.getText()) < 0) {
            throw new IllegalArgumentException("Treasure percent cannot "
                    + "be negative");
          }
        } catch (IllegalArgumentException e) {
          JOptionPane.showMessageDialog(null, "Treasure percent cannot "
                  + "be negative");
          ifTreasureInvalid = true;
        }

        try {
          if (Integer.parseInt(difficultyTextField.getText()) < 0) {
            throw new IllegalArgumentException("Difficulty cannot "
                    + "be negative");
          }
        } catch (IllegalArgumentException e) {
          JOptionPane.showMessageDialog(null, "Difficulty cannot "
                  + "be negative");
          ifDifficultyInvalid = true;
        }


        if (wrappingRadioBtn.isSelected()) {
          dungeonType = DungeonType.WRAPPING;
        } else if (nonWrappingRadioBtn.isSelected()) {
          dungeonType = DungeonType.NON_WRAPPING;
        }

      }

      if (!ifRowInvalid && !ifColumnInvalid && !ifInterconnectivityInvalid
              && !ifTreasureInvalid && !ifDifficultyInvalid) {
        JOptionPane.showMessageDialog(null,
                "Press OK to Enter the Dungeon!");
        controller.buildNewModel(row, column, interconnectivity, dungeonType,
                treasure, difficulty);
      }
    });

  }

}
