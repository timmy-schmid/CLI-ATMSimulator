package R18_G2_ASM1;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Asker {

  private final Scanner sc;
  private final PrintStream out;

  public Asker(InputStream in, PrintStream out) {
    this.sc = new Scanner(in);
    this.out = out;
  }

  public int ask(String msg) {
    out.println(msg);
    return sc.nextInt();
  }

  public int askForInt() {
    return sc.nextInt();
  }
  
}
