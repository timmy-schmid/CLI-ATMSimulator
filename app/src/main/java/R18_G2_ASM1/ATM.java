package R18_G2_ASM1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import R18_G2_ASM1.Session.InvalidTypeException;

import java.io.IOException;

/**
 * The ATM class represents the central shell of an XYZ Bank ATM. 
 * An ATM is made up of objects that represent the physical components that make up the ATM.
 * These components include: CardDispensor, CashDispensor, Keypad and Display.
 * The ATM class is used to interact with these various components which can be influenced by an ATM users actions.
 * @author Tim Schmid
 * @version 1.0
 */
public class ATM {

  private String terminalLocation;
  private MoneyStack balance;
  private Session currentSession;
  private CashDispensor cashDispensor;
  private Keypad keypad;
  private CardDispensor cardDispensor;
  private Display display;

/**
 * Constructs and inialises a new ATM with a balance of AUD $0
 * @param location A string representation of where the ATM is located
 */
  public ATM(String location) {
    this.terminalLocation = location;

    //Construct ATM components
    this.cashDispensor = new CashDispensor();
    this.keypad = new Keypad();
    this.cardDispensor = new CardDispensor();
    this.display = new Display();
  }

/**
 * Constructs and inialises a new ATM with an initial balance represented by a MoneyStack object.
 * @param location A string representation of where the ATM is located
 * @param m the starting balance of the ATM
 */
  public ATM(String location, MoneyStack m) {
    this(location);
    this.balance = m;
  }
  
 /**
 * Starts up the ATM to interact with a user.
 * A user is promoted to insert their card. After insertion an ATM session commences.
 * After completion of the ATM session, the ATM interacts with its internal components depending on the sessions status.
 * 
 * Possible session status' are:
 * <ul>
 *  <li>INVALID_CARD_NUMBER - The card number does not match a card from XYZ bank database</li>
 *  <li>CARD_NOT_ACTIVE - The card entered is not active as it's start date is later than the current date.</li>
 *  <li>CARD_EXPIRED - The current date is before the entered cards expiration date.</li>
 *  <li>CARD_LOST - The card entered has been reported as lost/stolen.</li>
 *  <li>CARD_BLOCKED - The card entered has been blocked due to too many PIN attempts.</li>
 *  <li>ADMIN_MODE - The ATM is set to Admin Mode to perform admin tasks such as topping up money or changing the ATM's location.</li>
 *  <li>CANCELLED - The session was cancelled by the user.</li>
 *  <li>SUCCESS - The session was succesfully completed by the user.</li>
 * </ul>
 * 
 * The ATM will shutdown after a session status has been resolved.
 * @throws InvalidTypeException
 */
  public void run() throws InvalidTypeException {
    currentSession = new Session(this);

    //Welcome Menu:
    StringBuilder s = new StringBuilder();
    s.append("--------------------------\n");
    s.append("---WELCOME TO XYZ BANK ---\n");
    s.append("--------------------------\n");
    display.displayMessage(s.toString());

    //insert card
    int cardNum;
    try {
      cardNum = cardDispensor.insertCard();
    } catch (InvalidCardException e) {
      display.displayMessage("\n" + e.getMessage());
      cardDispensor.ejectCard();
      return;
    }

    //run session
    currentSession.run(cardNum);

    if (currentSession.getStatus() == SessionStatus.ADMIN_MODE) {

      KeypadButton.deactivateAll();
      KeypadButton.ONE.activate();
      KeypadButton.TWO.activate();
      KeypadButton.THREE.activate();

      s = new StringBuilder();
      s.append("ADMIN MODE - Please select from the following options:\n");
      s.append("  1. Deposit money into ATM\n");
      s.append("  2. Change Location of ATM\n");
      s.append("  3. Cancel\n");

      display.displayMessage(s.toString());

      if (keypad.pressButton() == KeypadButton.ONE) {
        addCash();
      } else if (keypad.pressButton() == KeypadButton.TWO) {
        changeLocation();
      }
      cardDispensor.ejectCard();

    //Move this messaging to Enum?
    } else if (currentSession.getStatus() == SessionStatus.CARD_LOST) {
      display.displayMessage("The card you have entered has been reported as Lost or Stolen.\n");          
      display.displayMessage("Your card has been confiscated. Please contact XYZ Bank to issue a new card.\n");
      display.displayMessage("We apologise for any inconvienience.\n");
    } else if (currentSession.getStatus() == SessionStatus.CARD_BLOCKED) {
      display.displayMessage("The card you have entered has been blocked due to too many PIN attempts.\n");          
      display.displayMessage("Please contact XYZ Bank to unblock your card.\n");
      display.displayMessage("We apologise for any inconvienience.\n");
      cardDispensor.ejectCard();
    } else if (currentSession.getStatus() == SessionStatus.CARD_EXPIRED) {
      display.displayMessage("The card you have entered has expired.\n");          
      display.displayMessage("Please contact XYZ Bank to issue a new card.\n");
      display.displayMessage("We apologise for any inconvienience.\n");
      cardDispensor.ejectCard();
    } else if (currentSession.getStatus() == SessionStatus.CARD_NOT_ACTIVE) {
      display.displayMessage("The card you have entered is only active from a later date.\n");   
      display.displayMessage("We apologise for any inconvienience.\n");
      cardDispensor.ejectCard();
    } else if (currentSession.getStatus() == SessionStatus.INVALID_CARD_NUMBER) {
      display.displayMessage("The card you have entered is not a card from our bank.\n");  
      display.displayMessage("This ATM only accepts bank cards from XYZ\n");  
      display.displayMessage("We apologise for any inconvienience.");
      cardDispensor.ejectCard();
    } else if (currentSession.getStatus() == SessionStatus.SUCCESS) {
      display.displayMessage("The Transaction was successfully completed.\n");        
      display.displayMessage("Thank-you for using XYZ Bank :)\n");   
      cardDispensor.ejectCard();     
    }
  }


  /**
   * used to query the ATM user on how many denominations of 1 (or more) Australian bank notes they would like to deposit.
   * The user is given an option of $100, $50, $20, $10 and $5 notes to choose from using the keypad.
   * A user can select cancel at any time in the process.
   * The user can select finish at any time in the process to finalise the query.
   * @return a MoneyStack representation of the total amount of notes the user has entered. If the user cancels during the process null is returned.
   */
  public MoneyStack askForMoneyStackNotes() {

    MoneyStack m = new MoneyStack();
    StringBuilder s = new StringBuilder();

    KeypadButton.deactivateAll();
    KeypadButton.ONE.activate();
    KeypadButton.TWO.activate();
    KeypadButton.THREE.activate();
    KeypadButton.FOUR.activate();
    KeypadButton.FIVE.activate();
    KeypadButton.SIX.activate();
    KeypadButton.SEVEN.activate();

    //Keeps looping until FINISH is selected
    while (true) { // 
      s.setLength(0); // clears the stringbuilder buffer.
      s.append("DEPOSIT MONEY - NOTES - Please select a denomination to deposit:\n");
      s.append("  1. $100\n");
      s.append("  2. $50\n");
      s.append("  3. $20\n");
      s.append("  4. $10\n");
      s.append("  5. $5\n");
      s.append("  6. FINISH\n");
      s.append("  7. CANCEL\n");
      s.append("  TOTAL TO DEPOSIT: $" + String.valueOf(m.totalMoney()) + "\n");
      display.displayMessage(s.toString());
      
      String howMany = "How many bills would you like to deposit: ";
    
      try { //exception handling!
        if (keypad.pressButton() == KeypadButton.ONE) {
          display.displayMessage(howMany);
          m.addMoney(MoneyType.HUNDRED_DOLLARS,keypad.enterInt());
        } else if (keypad.pressButton() == KeypadButton.TWO) {
          display.displayMessage(howMany);
          m.addMoney(MoneyType.FIFTY_DOLLARS,keypad.enterInt());
        }  else if (keypad.pressButton() == KeypadButton.THREE) {
          display.displayMessage(howMany);
          m.addMoney(MoneyType.TWENTY_DOLLARS,keypad.enterInt());
        } else if (keypad.pressButton() == KeypadButton.FOUR) {
          display.displayMessage(howMany);
          m.addMoney(MoneyType.TEN_DOLLARS,keypad.enterInt());  
        } else if (keypad.pressButton() == KeypadButton.FIVE) {
          display.displayMessage(howMany);
          m.addMoney(MoneyType.FIVE_DOLLARS,keypad.enterInt());  
        } else if (keypad.pressButton() == KeypadButton.SIX) {
          break;  
        } else if (keypad.pressButton() == KeypadButton.SEVEN) {
          return null; // return NULL object if cancelled.      
        }
      } catch (IOException e){
        System.out.println("Error: money type doesn't exist");
        return null;
      }
        
    }
    return m;
  }
  
  /**
   * used interally by the ATM to query an admin user on how many denominations of 1 (or more) Australian coins they would like to deposit.
   * The user is given an option of $2, $1, 50c, 20c, 10c and 5c coins to choose from using the keypad.
   * An admin user can select cancel at any time in the process.
   * The admin user can select finish at any time in the process to finalise the query.
   * @return a MoneyStack representation of the total amount of coins the admin user has entered. If the admin user cancels during the process null is returned.
   */
  private MoneyStack askForMoneyStackCoins() {

    MoneyStack m = new MoneyStack();
    StringBuilder s = new StringBuilder();

    KeypadButton.deactivateAll();
    KeypadButton.ONE.activate();
    KeypadButton.TWO.activate();
    KeypadButton.THREE.activate();
    KeypadButton.FOUR.activate();
    KeypadButton.FIVE.activate();
    KeypadButton.SIX.activate();
    KeypadButton.SEVEN.activate();
    KeypadButton.EIGHT.activate();

    //Keeps looping until FINISH is selected
    while (true) { // 
      s.setLength(0); // clears the stringbuilder buffer.
      s.append("DEPOSIT MONEY - COINS - Please select a denomination to deposit:\n");
      s.append("  1. $2\n");
      s.append("  2. $1\n");
      s.append("  3. 50c\n");
      s.append("  4. 20c\n");
      s.append("  5. 10c\n");
      s.append("  6. 5c\n");
      s.append("  7. FINISH\n");
      s.append("  8. CANCEL\n");
      s.append("  TOTAL TO DEPOSIT: $" + String.valueOf(m.totalMoney()) + "\n");
      display.displayMessage(s.toString());
      
      String howMany = "How many bills would you like to deposit: ";
      try {
        if (keypad.pressButton() == KeypadButton.ONE) {
          display.displayMessage(howMany);
          m.addMoney(MoneyType.TWO_DOLLARS,keypad.enterInt());
        } else if (keypad.pressButton() == KeypadButton.TWO) {
          display.displayMessage(howMany);
          m.addMoney(MoneyType.ONE_DOLLAR,keypad.enterInt());
        }  else if (keypad.pressButton() == KeypadButton.THREE) {
          display.displayMessage(howMany);
          m.addMoney(MoneyType.FIFTY_CENTS,keypad.enterInt());
        } else if (keypad.pressButton() == KeypadButton.FOUR) {
          display.displayMessage(howMany);
          m.addMoney(MoneyType.TWENTY_CENTS,keypad.enterInt());  
        } else if (keypad.pressButton() == KeypadButton.FIVE) {
          display.displayMessage(howMany);
          m.addMoney(MoneyType.TEN_CENTS,keypad.enterInt());  
        } else if (keypad.pressButton() == KeypadButton.SIX) {
          display.displayMessage(howMany);
          m.addMoney(MoneyType.FIVE_CENTS,keypad.enterInt()); 
        } else if (keypad.pressButton() == KeypadButton.SEVEN) {
          break;
        } else if (keypad.pressButton() == KeypadButton.SEVEN) {
          return null; // return NULL object if cancelled.      
        }
      } catch (IOException e){
        System.out.println("Error: money type doesn't exist");
        return null;
      }
    }
    return m;
  }


  /**
   * used internally by the ATM to top-up the ATM via an admin user.
   * The admin can select from notes or coins (or both) to deposit.
   * The admin user can select cancel at any time in the process. In this instance the balance remains the same as before.
   * The admin user can select finish at any time in the process to finalise the query. In this instance the amount is topped up by the specified amount
   */
  private void addCash() {

    StringBuilder s = new StringBuilder();
    MoneyStack m = new MoneyStack();

    KeypadButton.deactivateAll();
    KeypadButton.ONE.activate();
    KeypadButton.TWO.activate();
    KeypadButton.THREE.activate();
    KeypadButton.FOUR.activate();

    //Keeps looping until FINISH is selected
    while (true) {
      s.setLength(0); // clears the stringbuilder buffer.
      s.append("ADMIN MODE - DEPOSIT MONEY - Choose From the Below:\n");
      s.append("  1. Deposit notes into ATM\n");
      s.append("  2. Deposit coins into ATM\n");
      s.append("  3. Finish\n");
      s.append("  4. Cancel\n");
      s.append("  TOTAL TO DEPOSIT: $" + String.valueOf(m.totalMoney()) + "\n");
      display.displayMessage(s.toString());

      if (keypad.pressButton() == KeypadButton.ONE) {
        m.addMoneyStack(askForMoneyStackNotes());
      } else if (keypad.pressButton() == KeypadButton.TWO) {
        m.addMoneyStack(askForMoneyStackCoins());
      }  else if (keypad.pressButton() == KeypadButton.THREE) {
        cashDispensor.depositMoney(m,balance);
        break;
      } else if (keypad.pressButton() == KeypadButton.FOUR) {
        display.displayMessage("Deposit has been cancelled!");
        break;
      } 
    }
  }

  /**
   * This class is used internally by the ATM via an admin user to change the location of the ATM
   */
  private void changeLocation() {
    display.displayMessage("Please enter a new location"); 
    Scanner sc = new Scanner(System.in);
    this.terminalLocation = sc.nextLine();
    sc.close();
  }

/**
   * Used to query the user for the personal identification number (PIN) for their card.
   * Only 4 digit PIN's are allowed. If a user incorrectly enters their PIN they are prompted to try again.
   * @return the PIN number entered by the user
   */
  public int askForPIN() {


    display.displayMessage("Please enter your 4 digit PIN:"); 

    boolean validLength = false;
    int pin = keypad.enterInt();
    while (!validLength) {
      if (String.valueOf(pin).length() != 4) {
        display.displayMessage("PIN is not 4 digits. Try again:");
        continue;
      }
      validLength = true;
    }
    return pin;
  }

  /**
   * Used to query the user for the type of transaction they would like to make from their account.
   * They can select from the options: withdrawal, deposit or balance check.
   * @return a TransactionType enum based on the user's selection
   */
  public TransactionType askForTransType() {

    KeypadButton.deactivateAll();
    KeypadButton.ONE.activate();
    KeypadButton.TWO.activate();
    KeypadButton.THREE.activate();

    MoneyStack m = new MoneyStack();
    StringBuilder s = new StringBuilder();

    s = new StringBuilder();
    s.append("Please select the type of transaction you would like to make:");
    s.append("  1. WITHDRAWAL\n");
    s.append("  2. DEPOSIT\n");
    s.append("  3. BALANCE CHECK\n");
    display.displayMessage(s.toString()); 






    if (keypad.pressButton() == KeypadButton.ONE) {
      return TransactionType.WITHDRAWAL;
    } else if (keypad.pressButton() == KeypadButton.TWO) {
      return TransactionType.DEPOSIT;
    }  else {
      return TransactionType.BALANCE; 
    }
  }

  /**
   * Used to obtain the ATM's current balance.
   * @return a MoneyStack representation of the ATM's current balance
   */
  public MoneyStack getATMBalance() {
    return balance;
  }
  
  /**
   * Used to print a receipt (display to the screen) the details of a particular transaction.
   * Only deposits and withdrawals can be printed to a receipt.
   * The transaction number, transaction type and amount are printed on the receipt.
   * @param t the Transaction object that all the the transaction metadata is pulled from.
   * @param m the Moneystack representation of the withdrawal/deposit.
   */  
  public void printReceipt(Transaction t, MoneyStack m) {
    StringBuilder s = new StringBuilder();
    s.append("A receipt has been printed:\n\n");

    s.append("--------------------------\n");
    s.append("--- XYZ BANK RECEIPT------\n");
    s.append("--------------------------\n");
    s.append("Transaction No: " + t.getID() + "\n");
    s.append("Transaction Type: " + t.getType() + "\n");
    s.append("Amount:\n");
    s.append("--------------------------\n");

    try { //hand exception!
      s.append(m.query(MoneyType.HUNDRED_DOLLARS) + " x $100\n");
      s.append(m.query(MoneyType.FIFTY_DOLLARS) + " x $50\n");
      s.append(m.query(MoneyType.TWENTY_DOLLARS) + " x $20\n");
      s.append(m.query(MoneyType.TEN_DOLLARS) + " x $10\n");
      s.append(m.query(MoneyType.FIVE_DOLLARS) + " x $5\n");

      if (t.getType() == TransactionType.WITHDRAWAL) {
        s.append(m.query(MoneyType.TWO_DOLLARS) + " x $2\n");
        s.append(m.query(MoneyType.ONE_DOLLAR) + " x $1\n");
        s.append(m.query(MoneyType.FIFTY_CENTS) + " x 50c\n");
        s.append(m.query(MoneyType.TWENTY_CENTS) + " x 20c\n");
        s.append(m.query(MoneyType.TEN_CENTS) + " x 10c\n");
        s.append(m.query(MoneyType.FIVE_CENTS) + " x 5c\n");
      }
    
      s.append("TOTAL: $" + m.totalMoney());
      display.displayMessage(s.toString());
    } catch (IOException e) {
      System.out.println("Error: money type doesn't exist.");
    }
  }

  /**
   * Used to interact with the ATM cash dispenser in order to dispense cash.
   * @param m the Moneystack representation of how much cash is to be dispensed.
   */  
  public void dispenseCash(MoneyStack m) {
    cashDispensor.ejectMoney(m, balance);
  }
  



}