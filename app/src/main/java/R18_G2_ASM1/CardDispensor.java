package R18_G2_ASM1;

import java.io.InputStream;
import java.util.Scanner;
public class CardDispensor extends ATMComponent {

  private final Scanner sc;

  public CardDispensor(InputStream in) {
    sc = new Scanner(in);
  }

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

  public void ejectCard(Display d) {
    d.displayMessage("Your card has been ejected.");
  }
  
}
