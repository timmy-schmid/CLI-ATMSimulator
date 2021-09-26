package R18_G2_ASM1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.io.PrintStream;
import java.math.BigDecimal;

class TransactionTest {
  Transaction withdrawalA;
  Transaction balanceCheckB;
  Transaction depositC;

  App app;

  Card userA; // withdraw
  Card userB; // deposit
  Card userC; // balance check

  Date startDate;
  Date expiraryDate;
  DateFormat dateFormat;

  BigDecimal depositAmount;

  ATM atm;
  MoneyStack moneyStack; //has money inside    
  Date date;

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); //for testing printing statements
  
  private final PrintStream originalOutput = System.out;    
  private BigDecimal amount;
  
  @BeforeEach
  public void setUp() throws ParseException { //for date formatting
    date = new Date();

    //setting up amount in ATM!
    moneyStack = new MoneyStack();
    try {
        moneyStack.addMoney(MoneyType.HUNDRED_DOLLARS, 50);
        moneyStack.addMoney(MoneyType.FIFTY_DOLLARS, 50);
        moneyStack.addMoney(MoneyType.TWENTY_DOLLARS, 50);
        moneyStack.addMoney(MoneyType.TEN_DOLLARS,  50);
        moneyStack.addMoney(MoneyType.FIVE_DOLLARS, 100);
        moneyStack.addMoney(MoneyType.TWO_DOLLARS, 100);
        moneyStack.addMoney(MoneyType.ONE_DOLLAR, 100);
        moneyStack.addMoney(MoneyType.FIFTY_CENTS,  100);
        moneyStack.addMoney(MoneyType.TWENTY_CENTS, 100);
        moneyStack.addMoney(MoneyType.TEN_CENTS, 100);
        moneyStack.addMoney(MoneyType.FIVE_CENTS, 100);
    
    } catch (IOException e){
        assertEquals(IOException.class, e.getClass());
    }

    ATM atm = new ATM("Canberra", moneyStack);
    
    dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"));
    startDate = dateFormat.parse("2018-06-01");
    expiraryDate = dateFormat.parse("2023-05-31");
    
    BigDecimal balance_1 = new BigDecimal("38762.99");
    BigDecimal balance_2 = new BigDecimal("10000.00");
    BigDecimal balance_3 = new BigDecimal("10000.99");

    userA = new Card(balance_1, 55673, startDate, expiraryDate,
    false, true, false, 888888);
    userB = new Card(balance_2, 55674, startDate, expiraryDate,
    false, true, true, 777777);
    userC = new Card(balance_3, 55675, startDate, expiraryDate,
    false, false, false, 666666);

    amount = new BigDecimal(300.50); //to withdraw
    depositAmount = new BigDecimal(150.00); // to deposit

    withdrawalA = new Transaction(atm, TransactionType.WITHDRAWAL, userA);

    balanceCheckB = new Transaction(atm, TransactionType.BALANCE, userB);

    depositC = new Transaction(atm, TransactionType.DEPOSIT, userC);
    depositC.initialSetUpMap();

    //set up streams
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void tearDown(){ 
    userA = null;
    userB = null;
    userC = null;

    withdrawalA = null;
    balanceCheckB = null;
    depositC = null;

    moneyStack = null;
    //restoreStreams
    System.setOut(originalOutput);
  }

  @Test
  public void testNotNullCardAccount(){ //testing card obj not null
    assertNotNull(userA);
    assertNotNull(userB);
    assertNotNull(userC);
  }

  @Test
  public void testNotNullMoneyStack(){
    assertNotNull(depositC.getMoneyStackBalance());
  }
  
  @Test
  public void testSetUpMapWorks(){ //testing depositAmountApp is not null
    assertNotNull(depositC.getDepositAmountMap());
  }
  
  @Test
  public void testResetMapWorks(){ //ensures all notes remain 0 again
    depositC.initialSetUpMap();
    depositC.resetDepositAmountMap();
    for (HashMap.Entry <MoneyType, Integer> entry: depositC.getDepositAmountMap().entrySet()){
        assert(entry.getValue() == 0);
    }
  }
  
  @Test 
  public void testGetUserAmount(){ //positive test, number is divisble by 5
    depositC.setAmount(depositAmount); //no coins
    assertEquals(depositC.getAmount(), depositC.getAmount());
  }

  @Test 
  public void testCanDepositAmount(){ //positive test, deposit amount is divisble by 5
    BigDecimal cardBalance = userC.getBalance(); 
    depositC.setAmount(depositAmount);
    String result = depositC.proceedDepositTransaction(userC);
    assertEquals(result, "Deposit successful");
    assertEquals(userC.getBalance(), cardBalance.add(depositAmount), "Amount in card did not increase,proceedDepositTransaction function failed! ");
  }

  @Test 
  public void testCantDepositAmount(){ //negative test, failure to deposit due to amount is NOT divisble by 5
    BigDecimal amount = new BigDecimal(124.00);
    depositC.setAmount(amount);
    String result = depositC.proceedDepositTransaction(userC);
    assertEquals(result, "Cannot deposit coins, only notes. Deposit Unsuccessful");
  }

  @Test 
  public void testCanModifyCardDeposit(){ //testing deposit money to card is valid
    BigDecimal cardBalance = userC.getBalance(); //initial
    BigDecimal amount = new BigDecimal(15.00);
    //initialise amount in deposit
    depositC.setAmount(amount);
    depositC.modify(depositC.getCard(), depositC.getType()); 
    assertTrue(userC.getBalance().compareTo(cardBalance.add(amount)) == 0);
  }

    @Test 
  public void testCantModifyCardNull(){ //testing withdrawing money from card is valid (i.e. user has enough money stored in card)
    Card failed = null;
    BigDecimal amount = new BigDecimal(15.00);
    //initialise amount in deposit
    withdrawalA.setAmount(amount);
    String status = withdrawalA.modify(failed, withdrawalA.getType());
    assertEquals(status, "Sorry your card is unavailable. Please try again.");
  } 

  @Test //amount is not of type note
  public void canSplitDepositAmountUp(){
    BigDecimal amount = new BigDecimal(107.34);
    depositC.splitDepositAmountUp(amount);
    boolean changed = depositC.checkIfDepositAmountMapChanged();
    assert(changed == false);
  }

  @Test 
  public void testCorrectDepositSplitUp(){ //ensures the deposit amount code works
    BigDecimal amount = new BigDecimal(120.00);
    depositC.splitDepositAmountUp(amount);
    boolean changed = depositC.checkIfDepositAmountMapChanged();
    assert(changed == true);
    HashMap<MoneyType, Integer> depositMap = depositC.getDepositAmountMap();
    assertEquals(depositMap.get(MoneyType.HUNDRED_DOLLARS), 1);
    assertEquals(depositMap.get(MoneyType.FIFTY_DOLLARS), 0);
    assertEquals(depositMap.get(MoneyType.TWENTY_DOLLARS), 1);
  }

  @Test //negative test for when balance is too low on card to withdraw
  public void testCantModifyCardWithdrawal(){ //out
    BigDecimal cardBalance = userA.getBalance(); //initial
    userA.setBalance(new BigDecimal(100.50));
    BigDecimal withdrawAmount = new BigDecimal(125.35);
    //initialise amount in deposit
    withdrawalA.setAmount(withdrawAmount);
    String status = withdrawalA.modify(withdrawalA.getCard(), withdrawalA.getType());
    assertEquals(outContent.toString(), "Sorry you don't have enough money stored on your card. Cannot proceed to withdraw money.\n");
    assertEquals(status, "FAILED");
  }
  
  @Test 
  public void testCanModifyCardWithdrawal(){ //testing withdrawing money from card is valid (i.e. user has enough money stored in card)
    BigDecimal cardBalance = userA.getBalance(); //initial
    BigDecimal amount = new BigDecimal(124.35);
    //initialise amount in withdrawal
    withdrawalA.setAmount(amount);
    String status = withdrawalA.modify(withdrawalA.getCard(), withdrawalA.getType()); //userA
    assertTrue(userA.getBalance().compareTo(cardBalance.subtract(amount)) == 0);
    assertEquals(status, "SUCCESS");
  }

  @Test
  public void testCanCheckBalanceInfo() throws ParseException{
    balanceCheckB.getBalanceInfo(userB);

    SimpleDateFormat tmp = new SimpleDateFormat ("yyyy-MM-dd");
    tmp.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"));
    Date d = tmp.parse("2023-05-31");

    SimpleDateFormat ft = new SimpleDateFormat ("EE d MMM yyyy hh:mm aaa z");
    ft.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"));

    String expected = "\nPrinting card details below!!\n\n"+
        "Card number [55674] has $10000.00 amount remaining and expires on: " + ft.format(d) + ".\n" +
        "The balance query was successful.\n";
    assertEquals(expected, outContent.toString());
  }

  @Test
  public void testCantCheckBalanceInfo() { //negative test as card is invalid
    Card failed = null;
    balanceCheckB.getBalanceInfo(failed);
    assertEquals(outContent.toString(), "Sorry your card is unavailable. Please try again.\n");
  }

  @Test //the map amount changed! --> positive test case for withdrawal transaction
  public void testMoneyStackChanged(){
    MoneyStack withdrawAmountMap = new MoneyStack();
    try {
        withdrawAmountMap.addMoney(MoneyType.HUNDRED_DOLLARS, 1);
        withdrawAmountMap.addMoney(MoneyType.HUNDRED_DOLLARS, 0);
        withdrawAmountMap.addMoney(MoneyType.FIFTY_DOLLARS, 0);
        withdrawAmountMap.addMoney(MoneyType.TWENTY_DOLLARS, 1);
        withdrawAmountMap.addMoney(MoneyType.TEN_DOLLARS,  0);
        withdrawAmountMap.addMoney(MoneyType.FIVE_DOLLARS, 3);
        withdrawAmountMap.addMoney(MoneyType.TWO_DOLLARS, 0);
        withdrawAmountMap.addMoney(MoneyType.ONE_DOLLAR, 0);

    } catch (IOException e){
        assertEquals(IOException.class, e.getClass());
    }
    HashMap <MoneyType, Integer> map = withdrawalA.getMoneyStackBalance().getMoney();
    
    assertNotNull(map);
    moneyStack.withdraw(withdrawAmountMap);
    
    assertEquals(map.get(MoneyType.HUNDRED_DOLLARS), 49);
    assertEquals(map.get(MoneyType.TWENTY_DOLLARS), 49);
    assertEquals(map.get(MoneyType.FIVE_DOLLARS), 99);
    assertEquals(map.get(MoneyType.ONE_DOLLAR), 100);
  }
}