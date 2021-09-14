package R18_G2_ASM1;

import java.util.Scanner;

public class CardDispensor extends ATMComponent{

  public int insertCard() {

    System.out.print("Please insert your card (5 digits): ");

    Scanner sc = new Scanner(System.in);

    String tempCardNum = sc.nextLine();
    sc.close();

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
  public void ejectCard() {
    System.out.println("Your card has been ejected."); //how do we get this to the display??
  }
  
}
