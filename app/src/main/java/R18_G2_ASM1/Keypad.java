package R18_G2_ASM1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Keypad extends ATMComponent {

  Map<Integer, KeypadButton> buttonMap = new HashMap<>();
  public Keypad() {
    for (KeypadButton k : KeypadButton.values()) {
      buttonMap.put(k.getCode(), k);
    }  
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
    boolean validPress = false; 
    int userInput = 0;
    while (!validPress) {
      Scanner sc = new Scanner(System.in);
      try {
        userInput = Integer.parseInt(sc.nextLine());
        sc.close();
      } catch (NumberFormatException e) {
        System.out.println("Invalid selection. Please enter a number.");
        continue;
      }
      validPress = true;
    }
    return userInput;
  }

  public double enterCashAmount() {
    boolean validPress = false; 
    double userInput = 0;
    while (!validPress) {
      Scanner sc = new Scanner(System.in);
      try {
        userInput = Math.round(Double.parseDouble(sc.nextLine())*100.0/100.0); //rounds to 2dp.
        sc.close();
      } catch (NumberFormatException e) {
        System.out.println("Invalid selection. Please enter a number.");
        continue;
      }
      validPress = true;
    }
    return userInput;
  }
}
