package R18_G2_ASM1;


import java.io.PrintStream;
public class Display extends ATMComponent{

  private final PrintStream out;

  public Display(PrintStream out) {
    this.out = out;
  }
  
  public void displayMessage(StringBuilder s) {
    out.println(s);
  }

  public void displayMessage(String s) {
    out.println("\n"+s);
  }

  public void displayMessageNoNewLine(String s) {
    out.print(s);
  }

}