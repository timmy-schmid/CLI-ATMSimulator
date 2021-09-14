package R18_G2_ASM1;
import java.util.*;

public class Account {
  
  private int accountID;
  private double balance;
  private ArrayList<Card> cards;

  //how to distinguish between which card is currently being used for transaction? [user input picks a card from their list of cards available??]

  public Account(int accountID, double balance, ArrayList<Card> cards) { //is balance in constructor necessary?
    this.accountID = accountID;
    this.balance = balance;
    this.cards = cards;
  }

  public int getAccountID() {
    return accountID;
  }

  public double getBalance() {
    return balance;
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  public void setAccountID(int accountID) {
    this.accountID = accountID;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public void setCards(ArrayList<Card> cards) {
    this.cards = cards;
  }

  public void withdraw(double amount) {
    double b = getBalance();
    this.balance = b - amount;
  }

  public void deposit(double amount) {  //also modify for card? --> this.card from this.getCardsList() --> add amount?
    double b = getBalance();
    this.balance = b + amount;
  }

  public void printAllCardBalance(){
    //loop through cardsLIST, printing balance stored
  }
}
