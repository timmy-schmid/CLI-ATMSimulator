package R18_G2_ASM1;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Represents the physical Card Dispensor of an ATM.
 * @author Tim Schmid
 * @version 1.0
 */
public class CardDispensor {

  private final Scanner sc;

  /**
   * Constructs and initialises a new Card Dispensor
   * @param in the stream in where the card number is inputted into.
   */
  public CardDispensor(InputStream in) {
    sc = new Scanner(in);
  }

  /**
   * Asks the user for a card number. A valid card number is 5 postive digits
   * @return the card number that was inserted
   */
  public int insertCard() {

    String tempCardNum = sc.nextLine();

    if (tempCardNum.length() != 5) {
      throw new InvalidCardException("Invalid card number: Not 5 digits");
    }

    int cardNum;
    try {
      cardNum = Integer.parseInt(tempCardNum);
    } catch (NumberFormatException e) {
      throw new InvalidCardException("Invalid card number: invalid characters");
    }
    return cardNum;
  }
  /**
   * Ejects the card from the ATMand displays a useful message to the user.
   * @param d the display unit, where to display the message
   */
  public void ejectCard(Display d) {
    d.displayMessage("Your card has been ejected.");
  }
  
}
