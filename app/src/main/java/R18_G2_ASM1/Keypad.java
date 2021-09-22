package R18_G2_ASM1;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Keypad extends ATMComponent {

  private Map<Integer, KeypadButton> buttonMap = new HashMap<>();
  private final Scanner sc;
  private final PrintStream out;

  public Keypad(InputStream in, PrintStream out) {
    for (KeypadButton k : KeypadButton.values()) {
      buttonMap.put(k.getCode(), k);
    } 
    this.sc = new Scanner(in);
    this.out = out;
  }

  public KeypadButton pressButton() {
    boolean validPress = false; 
    KeypadButton button = null;
    while (!validPress) {
      Scanner sc = new Scanner(System.in);
      
      try {
        int userInput = Integer.parseInt(sc.nextLine());
        sc.close();
        button = buttonMap.get(userInput);
        if (button.isActive() == false) {
          System.out.println("Invalid selection. Please try again.");
          continue;
        }

      } catch (NumberFormatException e) {
        System.out.println("Invalid selection. Please try again.");
        continue;
      }
      validPress = true;
    }
    return button;
  }

  public int enterInt() {
    while (!sc.hasNextInt()) {
        out.println("Invalid selection. Please enter a number.");
        sc.nextLine();
    }
    return sc.nextInt();
  }

  public BigDecimal enterCashAmount() {
    while (!sc.hasNextBigDecimal()) {
      out.println("Invalid selection. Please enter dollar amount.");
      sc.nextLine();
    }
    return sc.nextBigDecimal().setScale(2,RoundingMode.HALF_UP); //rounds to 2dp.
  }
}
