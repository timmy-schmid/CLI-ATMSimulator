// package R18_G2_ASM1

import java.util.*;

public class Account {
  private int accountID;

  private List<Card1> cardsList;
  public Account(int accountID, List<Card1> cardsList){
    this.accountID = accountID;
    this.cardsList = cardsList;
  }
  public int getAccountID(){
    return this.accountID;
  }
  public List<Card1> getCardsList(){
    return this.cardsList;
  }

  public void getBalance(){ //for 1 card?
    // System.out.println("Total amount stored in this card is " + card.totalAmount);

  }

  public void printAllCardBalance(){
    //loop through cardsLIST, printing balance stored

  }
}