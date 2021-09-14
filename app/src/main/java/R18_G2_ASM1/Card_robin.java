package R18_G2_ASM1;

import java.util.*;
import java.text.*;

public class Card_robin {
  protected int totalAmount;
  private int cardNumber;
  private Date start_date;
  private Date expiration_date;
  private boolean is_lost;
  private boolean is_blocked;
  private boolean is_expire;
  private int pin;
  private Account account;

  public Card_robin(int totalAmount, int cardNumber, Date start_date, Date expiration_date,
      boolean is_lost, boolean is_blocked, boolean is_expire, int pin, Account account) {
    this.totalAmount = totalAmount;
    this.cardNumber = cardNumber;
    this.start_date = start_date;
    this.expiration_date = expiration_date;
    this.is_lost = false;
    this.is_blocked = false;
    this.is_expire = false;
    this.pin = pin;
    this.account = account;
  }

  public Account getAccount() {
    return account;
  }

  public boolean isIs_blocked() {
    return is_blocked;
  }

  public boolean isIs_lost() {
    return is_lost;
  }

  public Date getExpiration_date() {
    return expiration_date;
  }

  public Date getStart_date() {
    return start_date;
  }

  public int getCardNumber() {
    return cardNumber;
  }

  public int getPin() {
    return pin;
  }

  public int getTotalAmount() {
    return totalAmount;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public void setCardNumber(int cardNumber) {
    this.cardNumber = cardNumber;
  }

  public void setExpiration_date(Date expiration_date) {
    this.expiration_date = expiration_date;
  }

  public void setIs_blocked(boolean is_blocked) {
    this.is_blocked = is_blocked;
  }

  public void setIs_lost(boolean is_lost) {
    this.is_lost = is_lost;
  }

  public void setPin(int pin) {
    this.pin = pin;
  }

  public void setTotalAmount(int totalAmount) {
    this.totalAmount = totalAmount;
  }

  public void setStart_date(Date start_date) {
    this.start_date = start_date;
  }

  public boolean isExpired() {
//    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
    Date now_date = new Date();
//    ft.format(now_date)
//    boolean is_expire = false
    int signal = now_date.compareTo(expiration_date);
    if(signal >= 0) {
      boolean is_expire = true;
    }
    return is_expire;
  }

  public boolean isAfterStartDate() {
    Date now_date = new Date();
    boolean is_AfterStartDate = false;
    int signal = now_date.compareTo(start_date);
    if(signal >= 0) {
      is_AfterStartDate = true;
    }
    return is_AfterStartDate;
  }

  public void blockCard() {
  }

  public void getCardDetails(){
    System.out.println("\nPrinting card details below!!!");
    System.out.println("Card name " + cardNumber + ", amount stored  = " + getTotalAmount() + ", expires on: "  + this.expiration_date);
  }


}
