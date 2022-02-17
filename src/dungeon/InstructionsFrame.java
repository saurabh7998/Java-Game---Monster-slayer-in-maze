package dungeon;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


class InstructionsFrame extends JFrame {



  /**
   * Constructor to create the Instructions frame.
   */
  public InstructionsFrame() {
    super.setTitle("Instructions Page");
    setBounds(100, 100, 730, 489);
    getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    setVisible(true);
    displayContents();
  }

  private void displayContents() {

    JLabel firstInstruction = new JLabel("1. Click the on-screen buttons to move"
            + " the Player");
    getContentPane().add(firstInstruction);
    getContentPane().add(Box.createRigidArea(new Dimension(0, 25)));


    JLabel secondInstruction = new JLabel("2. Click on Treasure or Arrow buttons "
            + "to pick them up");
    getContentPane().add(secondInstruction);
    getContentPane().add(Box.createRigidArea(new Dimension(0, 25)));


    JLabel thirdInstruction = new JLabel("3. Press arrow keys to move the Player");
    getContentPane().add(thirdInstruction);
    getContentPane().add(Box.createRigidArea(new Dimension(0, 25)));

    JLabel fourthInstruction = new JLabel("4. Press and hold SHIFT key along with"
            + " the arrow key to activate the Shoot mode.");
    getContentPane().add(fourthInstruction);
    getContentPane().add(Box.createRigidArea(new Dimension(0, 25)));

    JLabel fifthInstruction =
            new JLabel("5. Press 'T' to pickup the Treasures if any");
    getContentPane().add(fifthInstruction);
    getContentPane().add(Box.createRigidArea(new Dimension(0, 25)));

    JLabel sixthInstruction = new JLabel("6. Press 'A' to pickup the Arrows if "
            + "any");
    getContentPane().add(sixthInstruction);
    getContentPane().add(Box.createRigidArea(new Dimension(0, 25)));

    JLabel seventhInstruction = new JLabel("7. You can also click on "
            + "locations to move the player to adjacent locations");
    getContentPane().add(seventhInstruction);
    getContentPane().add(Box.createRigidArea(new Dimension(0, 80)));


    JButton closeBtn = new JButton("Understood");
    closeBtn.addActionListener(e -> {
      setVisible(false);
    });

    getContentPane().add(closeBtn);


  }


}
