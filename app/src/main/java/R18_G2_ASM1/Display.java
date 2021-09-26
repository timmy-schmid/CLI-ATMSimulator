package R18_G2_ASM1;


import java.io.PrintStream;

/**
 * Represents the physical Display unit of an ATM.
 * @author Tim Schmid
 * @version 1.0
 */
public class Display {

  private final PrintStream out;

  /**
   * Constructs and initialises a new Display unit
   * @param out the print stream in where the display is output to
   */
  public Display(PrintStream out) {
    this.out = out;
  }
    /**
   * Displays a message to the output stream formmated with a new line character at the end
   * @param s the message that is to be displayed.
   */
  public void displayMessage(StringBuilder s) {
    out.println(s);
  }
    /**
   * Displays a message to the output stream formmated with a new line character at the end
   * @param s the message that is to be displayed.
   */
  public void displayMessage(String s) {
    out.println(s);
  }
    /**
   * Displays a message to the output stream formmated with NO new line character at the end
   * @param s the message that is to be displayed.
   */
  public void displayMessageNoNewLine(String s) {
    out.print(s);
  }

}