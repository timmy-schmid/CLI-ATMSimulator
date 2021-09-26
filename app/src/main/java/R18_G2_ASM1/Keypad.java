package R18_G2_ASM1;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * Represents the physical Keypad unit of an ATM
 * @author Tim Schmid
 * @version 1.0
 */
public class Keypad {

  private Map<Integer, KeypadButton> buttonMap = new HashMap<>();
  
  private PrintStream out;
  private Scanner sc;


  /**
   * Constructs a new keyboard unit. By default all keyboard buttons are active
   * @param in the input stream where the keypad is extracting input from
   * @param out the print stream where useful user prompts are dipslayed to
   */
  public Keypad(InputStream in, PrintStream out) {
    for (KeypadButton k : KeypadButton.values()) {
      buttonMap.put(k.getCode(), k);
    } 
    this.sc = new Scanner(in);
    this.out = out;
  }

    /**
   * Prompts the user to press a button. Only accepts button presses of activated buttons.If an incorrect button is pressed, the user will be continously prompted.
   * @return returns a button press.
   */
  public KeypadButton pressButton() {
    boolean validPress = false; 
    KeypadButton button = null;
    while (!validPress) {

      try {
        int userInput = Integer.parseInt(sc.nextLine());
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
    /**
   * Prompts the user to enter an integer. If an Integer is not entered the user will be continously prompted until a correct input is entered.
   * @return the integer the user has entered.
   */
  public int enterInt() {
    while (!sc.hasNextInt()) {
        out.println("Invalid selection. Please enter a number.");
        sc.nextLine();
    }
    int intToReturn = sc.nextInt();
    
    if (sc.hasNextLine()) {
      sc.nextLine();
    }
    
    return intToReturn;
  }
    /**
   * Prompts the user to enter a cash amount. If a decimal is entered that is not valid the user will be repromted until correct there is valid input
   * @return the cash amount entered in the form of BigDecimal to prevent financial rounding errors.
   */
  public BigDecimal enterCashAmount() {
    while (!sc.hasNextBigDecimal()) {
      out.println("Invalid selection. Please enter dollar amount.");
      sc.nextLine();
    }

    BigDecimal amountToReturn = sc.nextBigDecimal().setScale(2,RoundingMode.HALF_UP); //rounds to 2dp.

    if (sc.hasNextLine()) {
      sc.nextLine();
    }

    return amountToReturn;
  }
}
