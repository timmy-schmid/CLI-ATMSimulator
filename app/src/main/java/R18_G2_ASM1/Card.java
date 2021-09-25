package R18_G2_ASM1;

import java.util.*;
import java.text.*;
import java.math.BigDecimal;

/**
 * The Card class represents the various attributes of the card that can be used in ATM,
 * including balance, cardNumber, the start date, the expiration date, if the card is lost,
 * if the card is blocked, if the card is expired and pin number.
 * Meanwhile, it includes methods that can set, get and determine the attributes of the card.
 * @author Junbin Gou
 * @version 1.0
 */

public class Card {
  protected BigDecimal balance;
  private int cardNumber;
  private Date startDate;
  private Date expirationDate;
  private boolean isLost;
  private boolean isBlocked;
  private boolean isExpire;
  private int pin;

  /**
   * Constructs and inialises a new Card.
   * @param balance A BigDecimal representation of the balance
   * @param cardNumber An integer representation of the card number
   * @param startDate A BigDecimal representation of the start date of the card
   * @param expirationDate A Date representation of the expiration date of the card
   * @param isLost A boolean representation of the losted status of the card
   * @param isBlocked A boolean representation of the blocked status of the card
   * @param isExpire A boolean representation of the expired status of the card
   * @param pin An integer representation of the pin number of the card
   */
  
  public Card(BigDecimal balance, int cardNumber, Date startDate, Date expirationDate,
    boolean isLost, boolean isBlocked, boolean isExpire, int pin) {
    this.balance = balance;
    this.cardNumber = cardNumber;
    this.startDate = startDate;
    this.expirationDate = expirationDate;
    this.isLost = isLost;
    this.isBlocked = isBlocked;
    this.isExpire = isExpire;
    this.pin = pin;
  }

  /**
   * @return the card status, blocked or not.
   */
  public boolean isBlocked() {
    return isBlocked;
  }

  /**
   * @return the card status, lost or not.
   */
  public boolean isLost() {
    return isLost;
  }

  /**
   * @return the expiration date of the card.
   */
  public Date getExpirationDate() {
    return expirationDate;
  }

  /**
   * @return the start date of the card.
   */
  public Date getStartDate() {
    return startDate;
  }

  /**
   * @return the card number.
   */
  public int getCardNumber() {
    return cardNumber;
  }

  /**
   * @return the pin number of the card.
   */
  public int getPin() {
    return pin;
  }

  /**
   * @return the balance of the card.
   */
  public BigDecimal getBalance() {
    return balance;
  }

  /**
   * Set the balance of the card.
   * @param balance A BigDecimal representation of the balance
   */
  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  /**
   * Set the number of the card.
   * @param cardNumber An integer representation of the card number
   */
  public void setCardNumber(int cardNumber) {
    this.cardNumber = cardNumber;
  }

  /**
   * Set the expiration date of the card.
   * @param expirationDate A Date representation of the expiration date of the card
   */
  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }

  /**
   * Set the blocked status of the card.
   * @param isBlocked A boolean representation of the blocked status of the card
   */
  public void setIsBlocked(boolean isBlocked) {
    this.isBlocked = isBlocked;
  }

  /**
   * Set the losted status of the card.
   * @param isLost A boolean representation of the losted status of the card
   */
  public void setIsLost(boolean isLost) {
    this.isLost = isLost;
  }

  /**
   * Set the pin number of the card.
   * @param pin An integer representation of the pin number of the card
   */
  public void setPin(int pin) {
    this.pin = pin;
  }

  /**
   * Set the start date of the card.
   * @param startDate A BigDecimal representation of the start date of the card
   */
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  /**
   * Decide if the card is expired or not by comparing the expiration date of the card and current time.
   * @return if the card is expired or not.
   */
  public boolean isExpired() {

    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
    ft.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"));
    Date now_date = new Date();
    ft.format(now_date);
    boolean isExpire = false;
    int signal = now_date.compareTo(expirationDate);
    if(signal >= 0) {
      isExpire = true;
    }
    return isExpire;
  }

  /**
   * Decide if the card can be used after the start date by comparing the start date of the card and the current time.
   * @return if the current time is after the start date of the card.
   */
  public boolean isAfterStartDate() {
    Date now_date = new Date();
    boolean is_AfterStartDate = false;
    int signal = now_date.compareTo(startDate);
    if(signal >= 0) {
      is_AfterStartDate = true;
    }
    return is_AfterStartDate;
  }

  /**
   * Check if the input pin number is correct or not.
   * @param pin An integer representation of the pin number of the card
   * @return if the input pin number is the same as the pin number of the card.
   */
  public boolean checkPin(int pin) {
    if(this.pin - pin < 0.1) {
      return true;
    }
    else {
      return false;
    }
  }
  /**
   * Method to block the card.
   */
  public void blockCard() {
    this.isBlocked = true;
  }

  /**
   * Print out detailed card information.
   */
  public void getCardDetails(){
    SimpleDateFormat ft = new SimpleDateFormat ("EE d MMM yyyy hh:mm aaa z");
    ft.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"));
    System.out.println("\nPrinting card details below!!\n");
    System.out.printf("Card number [%d] has $%.2f amount remaining and expires on: " + ft.format(this.expirationDate) + ".\n", cardNumber, getBalance());
  }
}
