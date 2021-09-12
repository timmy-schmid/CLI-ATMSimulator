import java.util.*;

public class Account {
  
  private int accountID;
  private double balance;
  private ArrayList<Card> cards;

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

  public ArrayList<Card> getCardsList() {
    return this.cards;
  }

  public void withdraw(int amount) {
    double b = getBalance();
    this.balance = b - amount;
  }

  public void deposit(int amount) {
    double b = getBalance();
    this.balance = b + amount;
  }

  public void printAllCardBalance(){
    //loop through cardsLIST, printing balance stored

  }
}