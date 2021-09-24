package R18_G2_ASM1;

import java.util.*;
import java.text.*;
import java.math.BigDecimal;

public class Card {
  protected BigDecimal balance;
  private int cardNumber;
  private Date startDate;
  private Date expirationDate;
  private boolean isLost;
  private boolean isBlocked;
  private boolean isExpire;
  private int pin;

  
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

  public boolean isBlocked() {
    return isBlocked;
  }

  public boolean isLost() {
    return isLost;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public Date getStartDate() {
    return startDate;
  }

  public int getCardNumber() {
    return cardNumber;
  }

  public int getPin() {
    return pin;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public void setCardNumber(int cardNumber) {
    this.cardNumber = cardNumber;
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }

  public void setIsBlocked(boolean isBlocked) {
    this.isBlocked = isBlocked;
  }

  public void setIsLost(boolean isLost) {
    this.isLost = isLost;
  }

  public void setPin(int pin) {
    this.pin = pin;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public boolean isExpired() {
    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
    ft.setTimeZone(TimeZone.getTimeZone("AEST"));
    Date now_date = new Date();
    ft.format(now_date);
    boolean isExpire = false;
    int signal = now_date.compareTo(expirationDate);
    if(signal >= 0) {
      isExpire = true;
    }
    return isExpire;
  }

  public boolean isAfterStartDate() {
    Date now_date = new Date();
    boolean is_AfterStartDate = false;
    int signal = now_date.compareTo(startDate);
    if(signal >= 0) {
      is_AfterStartDate = true;
    }
    return is_AfterStartDate;
  }

  public boolean checkPin(int pin) {
    if(this.pin - pin < 0.1) {
      return true;
    }
    else {
      return false;
    }
  }

  public void blockCard() {
    this.isBlocked = true;
  }

  public void getCardDetails(){
    System.out.println("\nPrinting card details below!!\n");
    System.out.printf("Card number [%d] has $%.2f amount remaining and expires on: " + this.expirationDate + ".\n", cardNumber, getBalance());
  }
}
