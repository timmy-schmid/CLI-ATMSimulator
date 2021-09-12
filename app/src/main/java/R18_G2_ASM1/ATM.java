package R18_G2_ASM1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATM {
  private static final int ADMIN_CARD_NUMBER = 99999;
  private static int CountID = 0;
  private int terminalID;
  private String terminalLocation;
  private MoneyStack balance;
  private List<Session> sessionLog;
  private Session currentSession;
  private CashDispensor cashDispensor;
  private Keypad keypad;
  private CardDispensor cardDispensor;
  private Display display;

  public ATM(String location) {
    this.terminalLocation = location;
    this.sessionLog = new ArrayList<>();
    terminalID = ++CountID;

    //Construct ATM components
    this.cashDispensor = new CashDispensor();
    this.keypad = new Keypad();
    this.cardDispensor = new CardDispensor();
    this.display = new Display();
  }

  public ATM(String location, MoneyStack m) {
    this(location);
    this.balance = m;
  }
  
  public void run() {
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
    }
    return m;
  }

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
    }
    return m;
  }

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

  private void changeLocation() {
    display.displayMessage("Please enter a new location"); 
    Scanner sc = new Scanner(System.in);
    terminalLocation = sc.nextLine();
    sc.close();
  }

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

  public TranscationType askForTransType() {

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
      return TranscationType.WIHTDRAWAL;
    } else if (keypad.pressButton() == KeypadButton.TWO) {
      return TranscationType.DEPOSIT;
    }  else {
      return TranscationType.BALANCE; 
    }
  }

  public MoneyStack getATMBalance() {
    return balance;
  }
  
  public void printReceipt(Transaction t, MoneyStack m) {
    //transaction number, transaction type, amount withdrawn, account balance.
    StringBuilder s = new StringBuilder();
    s.append("A receipt has been printed:\n\n");

    s.append("--------------------------\n");
    s.append("--- XYZ BANK RECEIPT------\n");
    s.append("--------------------------\n");
    s.append("Trascation No: " + t.getID() + "\n");
    s.append("Trascation Type: " + t.getType() + "\n");
    s.append("Amount:\n");
    s.append("--------------------------\n");
    s.append(m.query(MoneyType.HUNDRED_DOLLARS) + " x $100\n");
    s.append(m.query(MoneyType.FIFTY_DOLLARS) + " x $50\n");
    s.append(m.query(MoneyType.TWENTY_DOLLARS) + " x $20\n");
    s.append(m.query(MoneyType.TEN_DOLLARS) + " x $10\n");
    s.append(m.query(MoneyType.FIVE_DOLLARS) + " x $5\n");

    if (t.getType() == TranscationType.DEPOSIT) {
      s.append(m.query(MoneyType.TWO_DOLLARS) + " x $2\n");
      s.append(m.query(MoneyType.ONE_DOLLAR) + " x $1\n");
      s.append(m.query(MoneyType.FIFTY_CENTS) + " x 50c\n");
      s.append(m.query(MoneyType.TWENTY_CENTS) + " x 20c\n");
      s.append(m.query(MoneyType.TEN_CENTS) + " x 10c\n");
      s.append(m.query(MoneyType.FIVE_CENTS) + " x 5c\n");
    }
  
    s.append("TOTAL: $" + m.totalMoney());
    display.displayMessage(s.toString());
  }

  public void dispenseCash(MoneyStack m) {
    cashDispensor.ejectMoney(m, balance);
  }
  



}