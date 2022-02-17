package dungeon;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * This class represents the Console based Controller of our Text-based
 * Adventure Game.
 */
public class DungeonConsoleController implements DungeonController {

  private final Appendable out;
  private final Scanner sc;

  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public DungeonConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    sc = new Scanner(in);
  }


  /**
   * Execute a single game of dungeon given a dungeon Model. When the game
   * is over, the playGame method ends.
   *
   * @param dungeonModel a non-null Dungeon Model
   */
  @Override
  public void playConsoleGame(DungeonInterface dungeonModel) {
    if (dungeonModel == null) {
      throw new IllegalArgumentException("Invalid model");
    }

    try {
      Stack<Integer> path = new Stack<>();

      try {
        path = dungeonModel.findPathFromStartToStop();
      } catch (IllegalStateException e) {
        this.out.append(e.getMessage());
        System.exit(0);
      }

      this.out.append("Recommended Path : " + path).append("\n");


      Location[][] myDungeon = dungeonModel.getDungeon();
      int start = (int) path.get(0);
      int end = (int) path.get(path.size() - 1);
      int goal = start;
      String input = " ";


      if (!performOperationsOnPlayer(dungeonModel)) {
        input = "Q";
      } else {
        if (dungeonModel.detectSmell(dungeonModel.getPlayerLocation())
                == Smell.STRONG_PUNGENT_SMELL) {
          this.out.append("Strong smell detected").append("\n");
          if (!shootOtyugh(dungeonModel)) {
            input = "Q";
          }
        } else if (dungeonModel.detectSmell(dungeonModel.getPlayerLocation())
                == Smell.PUNGENT_SMELL) {
          this.out.append("Low Smell Detected").append("\n");
          if (!shootOtyugh(dungeonModel)) {
            input = "Q";
          }
        }
        printPossibleMoves(dungeonModel);
      }


      while (!input.equals("Q")) {
        if (goal == end) {
          this.out.append("Yay! You won. Do "
                  + "you still wish to roam around?").append("\n");
          this.out.append("Enter Y to roam, else press any other key").append("\n");
          String roam = sc.next();
          if (!roam.equals("Y")) {
            break;
          }
        }
        this.out.append("\nEnter E/W/N/S: or enter Q to quit").append("\n");

        if (sc.hasNext()) {
          input = sc.next();
        }


        switch (input) {

          case "E":
            try {
              if (dungeonModel.movePlayer("E")) {
                if (!performOperationsOnPlayer(dungeonModel)) {
                  input = "Q";
                }
              } else {
                this.out.append("Chomp chomp chomp, You have been eaten by "
                        + "the Otyugh").append("\n");
                input = "Q";
                break;
              }

              if (dungeonModel.detectSmell(dungeonModel.getPlayerLocation())
                      == Smell.STRONG_PUNGENT_SMELL) {
                this.out.append("Strong Smell Detected").append("\n");
                if (!shootOtyugh(dungeonModel)) {
                  input = "Q";
                }

              } else if (dungeonModel.detectSmell(dungeonModel.getPlayerLocation())
                      == Smell.PUNGENT_SMELL) {
                this.out.append("Low Smell Detected").append("\n");
                if (!shootOtyugh(dungeonModel)) {
                  input = "Q";
                }
              }
              printPossibleMoves(dungeonModel);

              goal = dungeonModel.getLocationId();

            } catch (IllegalArgumentException e) {
              this.out.append(e.getMessage()).append("\n");
            }


            break;

          case "W":
            try {
              if (dungeonModel.movePlayer("W")) {
                if (!performOperationsOnPlayer(dungeonModel)) {
                  input = "Q";
                }
              } else {
                this.out.append("Chomp chomp chomp, You have been eaten by "
                        + "the Otyugh").append("\n");
                input = "Q";
                break;
              }

              if (dungeonModel.detectSmell(dungeonModel.getPlayerLocation())
                      == Smell.STRONG_PUNGENT_SMELL) {
                this.out.append("Strong smell detected").append("\n");
                if (!shootOtyugh(dungeonModel)) {
                  input = "Q";
                }
              } else if (dungeonModel.detectSmell(dungeonModel.getPlayerLocation())
                      == Smell.PUNGENT_SMELL) {
                this.out.append("Low Smell Detected").append("\n");
                if (!shootOtyugh(dungeonModel)) {
                  input = "Q";
                }
              }
              printPossibleMoves(dungeonModel);

              goal = dungeonModel.getLocationId();

            } catch (IllegalArgumentException e) {
              this.out.append(e.getMessage()).append("\n");
            }


            break;

          case "N":
            try {
              if (dungeonModel.movePlayer("N")) {
                if (!performOperationsOnPlayer(dungeonModel)) {
                  input = "Q";
                }
              } else {
                this.out.append("Chomp chomp chomp, You have been eaten by "
                        + "the Otyugh").append("\n");
                input = "Q";
                break;
              }

              if (dungeonModel.detectSmell(dungeonModel.getPlayerLocation())
                      == Smell.STRONG_PUNGENT_SMELL) {
                this.out.append("Strong smell detected").append("\n");
                if (!shootOtyugh(dungeonModel)) {
                  input = "Q";
                }
              } else if (dungeonModel.detectSmell(dungeonModel.getPlayerLocation())
                      == Smell.PUNGENT_SMELL) {
                this.out.append("Low Smell Detected").append("\n");
                if (!shootOtyugh(dungeonModel)) {
                  input = "Q";
                }
              }
              printPossibleMoves(dungeonModel);

              goal = dungeonModel.getLocationId();
            } catch (IllegalArgumentException e) {
              this.out.append(e.getMessage()).append("\n");
            }

            break;

          case "S":
            try {
              if (dungeonModel.movePlayer("S")) {
                if (!performOperationsOnPlayer(dungeonModel)) {
                  input = "Q";
                }
              } else {
                this.out.append("Chomp chomp chomp, You have been eaten by "
                        + "the Otyugh").append("\n");
                input = "Q";
                break;
              }

              if (dungeonModel.detectSmell(dungeonModel.getPlayerLocation())
                      == Smell.STRONG_PUNGENT_SMELL) {
                this.out.append("Strong smell detected").append("\n");
                if (!shootOtyugh(dungeonModel)) {
                  input = "Q";
                }
              } else if (dungeonModel.detectSmell(dungeonModel.getPlayerLocation())
                      == Smell.PUNGENT_SMELL) {
                this.out.append("Low Smell Detected").append("\n");
                if (!shootOtyugh(dungeonModel)) {
                  input = "Q";
                }
              }
              printPossibleMoves(dungeonModel);

              goal = dungeonModel.getLocationId();


            } catch (IllegalArgumentException e) {
              this.out.append(e.getMessage()).append("\n");
            }

            break;

          case "Q":
            input = "Q";
            break;

          default:
            this.out.append("Invalid move input").append("\n");
            break;
        }
      }

      this.out.append("\nGame Over").append("\n");
      this.out.append("Player collected total treasure of : "
              + dungeonModel.getPlayerWealth()).append("\n");

    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

  }

  private boolean performOperationsOnPlayer(DungeonInterface d) {
    try {
      printLocationDescription(d);
      if (d.getTreasureListFromLocation().size() > 0) {
        this.out.append("Do you want to pickup treasure? Press Y to "
                + "pickup. Press any other key to skip.").append("\n");

        String pickTreasure = " ";
        if (sc.hasNext()) {
          pickTreasure = sc.next();
        } else {
          return false;
        }

        if (pickTreasure.equals("Y")) {
          List<Treasure> locationTreasure = d.getPlayerLocation().getTreasureList();
          d.pickUpTreasure();
          this.out.append("Player collected treasure(s): "
                  + locationTreasure).append("\n");
        } else {
          this.out.append("You chose to skip picking up the treasure").append("\n");
        }
      }
      if (d.getArrowCountInLocation() > 0) {
        this.out.append("Do you want to pick up arrows? Press Y to "
                + "pickup. Press any other key to skip").append("\n");
        String pickArrow = " ";
        if (sc.hasNext()) {
          pickArrow = sc.next();
        } else {
          return false;
        }

        if (pickArrow.equals("Y")) {
          int arrowInLocation = d.getArrowCountInLocation();
          d.pickUpArrows();
          this.out.append("Player collected "
                  + arrowInLocation + " arrows").append("\n");
          d.resetArrowCountInLocation();
        } else {
          this.out.append("You chose to skip picking up the arrows.").append(
                  "\n");
        }
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
    return true;
  }

  private void printLocationDescription(DungeonInterface d) {
    try {
      this.out.append("Current Location: " + d.getLocationId()).append("\n");
      if (d.getTreasureListFromLocation().size() > 0) {
        this.out.append("Location has treasures: " + d.getTreasureListFromLocation()).append("\n");
      }
      if (d.getArrowCountInLocation() > 0) {
        this.out.append("Location has " + d.getArrowCountInLocation() + " "
                + "arrows").append("\n");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

  }

  private void printPossibleMoves(DungeonInterface d) {
    try {
      this.out.append("Player can now move to :");
      if (d.getNorthLocation() != null) {
        this.out.append("North || ");
      }
      if (d.getSouthLocation() != null) {
        this.out.append("South || ");
      }
      if (d.getEastLocation() != null) {
        this.out.append("East || ");
      }
      if (d.getWestLocation() != null) {
        this.out.append("West || ");
      }
      this.out.append("\n");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

  }

  private boolean shootOtyugh(DungeonInterface d) {
    try {
      this.out.append("Player has: " + d.getPlayerArrowCount() + " arrows").append("\n");
      this.out.append("Do you want to shoot an Otyugh? Press Y to shoot, "
              + "else press any other key to skip").append("\n");

      String inp = " ";
      if (sc.hasNext()) {
        inp = sc.next();
      } else {
        return false;
      }
      if (inp.equals("Y")) {
        this.out.append("Enter the direction to shoot at: N/S/E/W: ").append(
                "\n");
        String direction = " ";
        if (sc.hasNext()) {
          direction = sc.next();
        } else {
          return false;
        }
        while (!direction.equals("N") && !direction.equals("S")
                && !direction.equals("E") && !direction.equals("W")) {
          this.out.append("Enter valid direction to shoot at:").append("\n");
          direction = sc.next();
        }
        this.out.append("Enter the distance at which you want to shoot:").append("\n");
        String dist = " ";
        if (sc.hasNext()) {
          dist = sc.next();
        } else {
          return false;
        }
        try {
          OtyughStatus otyughStatus = d.shootPlayer(direction,
                  Integer.parseInt(dist));
          if (otyughStatus.isDead()) {
            this.out.append("An Otyugh at location: "
                    + otyughStatus.getOtyughLocation() + " was shot but not "
                    + "killed.").append("\n");

          } else {
            this.out.append("An Otyugh at location: "
                    + otyughStatus.getOtyughLocation() + " was killed.").append("\n");

          }
          this.out.append("Previous Health was: " + otyughStatus.getOtyughPreviousHealth()
                  + " && Current Health is: " + otyughStatus.getOtyughCurrentHealth()).append("\n");
          this.out.append("");
        } catch (IllegalStateException e) {
          this.out.append(e.getMessage()).append("\n");
        } catch (NumberFormatException e) {
          this.out.append("You entered an invalid distance. Now you will have"
                  + " to move!").append("\n");
        }
      } else {
        this.out.append("You chose to skip shooting").append("\n");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
    return true;
  }

  @Override
  public boolean movePlayer(String location) {
    //Blank
    return false;
  }

  @Override
  public void pickUpTreasure() {
    //Blank
  }

  @Override
  public void pickUpArrows() {
    //Blank
  }

  @Override
  public void shoot(String direction, int distance) {
    //Blank
  }

  @Override
  public void buildNewModel(int row, int column, int interconnectivity,
                            DungeonType dungeonType, int treasurePercentage,
                            int difficulty) {
    //Blank
  }

  @Override
  public void setViewFocus() {
    //Blank
  }


  @Override
  public void playGraphicalGame() {
    //Blank
  }


}
