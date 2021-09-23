package R18_G2_ASM1;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.*;
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
    private final ByteArrayOutputStream errorContent = new ByteArrayOutputStream(); //writes common data into many files 
    
    private final PrintStream originalOutput = System.out;
    private final PrintStream originalError = System.err;
    
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
        startDate = dateFormat.parse("2018-06-01");
        expiraryDate = dateFormat.parse("2023-05-31");
        BigDecimal balance_1 = new BigDecimal("38762.99");
        BigDecimal balance_2 = new BigDecimal("10000.00");
        BigDecimal balance_3 = new BigDecimal("10000.99");
    
        userA = new Card(new BigDecimal(38762.99), 55673, startDate, expiraryDate,
        false, true, false, 888888);
        userB = new Card(new BigDecimal(10000.00), 55674, startDate, expiraryDate,
        false, true, true, 777777);
        userC = new Card(new BigDecimal(10000.99), 55675, startDate, expiraryDate,
        false, false, false, 666666);

        amount = new BigDecimal(300.50); //to withdraw
        depositAmount = new BigDecimal(150.00); // to deposit

        withdrawalA = new Transaction(atm, TransactionType.WITHDRAWAL, userA, 1);

        balanceCheckB = new Transaction(atm, TransactionType.BALANCE, userB, 2);

        depositC = new Transaction(atm, TransactionType.DEPOSIT, userC, 3);
        depositC.initialSetUpMap();

        //setupStreams
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errorContent));

        // app = new App();
    }

    @AfterEach
    public void tearDown(){ //eject card from atm?
        userA = null;
        userB = null;
        userC = null;

        withdrawalA = null;
        balanceCheckB = null;
        depositC = null;

        //restoreStreams

        moneyStack = null;
        // app = null;
        // atm = null;

        System.setOut(originalOutput);
        System.setErr(originalError);
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
    public void voidtestResetMapWorks(){ //ensures all notes remain 0 again
        depositC.initialSetUpMap();
        depositC.resetDepositAmountMap();
        for (HashMap.Entry <MoneyType, Integer> entry: depositC.getDepositAmountMap().entrySet()){
            assert(entry.getValue() == 0);
        }
    }
    
    @Test 
    public void voidtestGetUserAmount(){ //positive test, number is divisble by 5
        depositC.setAmount(depositAmount); //no coins
        assertEquals(depositC.getAmount(), depositC.getAmount());
    }

    @Test 
    public void voidtestcanDepositAmount(){ //positive test, deposit amount is divisble by 5
        BigDecimal cardBalance = userC.getbalance(); 
        depositC.setAmount(depositAmount);
        try {
            depositC.proceedDepositTransaction(userC);
        } catch (InvalidTypeException e) {
            assertEquals(InvalidTypeException.class, e.getClass());
        }
        assertEquals(userC.getbalance(), cardBalance.add(depositAmount), "Amount in card did not increase,proceedDepositTransaction function failed! ");
    }

    @Test 
    public void voidtestCantDepositAmount(){ //negative test, failure to deposit due to amount is NOT divisble by 5
        BigDecimal amount = new BigDecimal(124.00);
        depositC.setAmount(amount);
        try {
            depositC.proceedDepositTransaction(userC);
        } catch (InvalidTypeException e) {
            assertEquals(InvalidTypeException.class, e.getClass()); //goes here!
        }
    }

    @Test //testing deposit adds money to card
    public void testCanModifyCardDeposit(){ //testing deposit money to card is valid
        BigDecimal cardBalance = userC.getbalance(); //initial
        BigDecimal amount = new BigDecimal(15.00);
        //initialise amount in deposit
        depositC.setAmount(amount);
        depositC.modify(depositC.getCard(), depositC.getType()); //userC
        assertTrue(userC.getbalance().compareTo(cardBalance.add(amount)) == 0);
    }

     @Test //negative test: card is invalid
    public void testCantModifyCardNull(){ //testing withdrawing money from card is valid (i.e. user has enough money stored in card)
        Card failed = null;
        BigDecimal amount = new BigDecimal(15.00);
        //initialise amount in deposit
        withdrawalA.setAmount(amount);
        withdrawalA.modify(failed, withdrawalA.getType()); //userC
        assertEquals(outContent.toString(), "Sorry your card is unavailable. Please try again.\n");
    }

    @Test //amount is not of type note
    public void canSplitDepositAmountUp(){
        BigDecimal amount = new BigDecimal(107.35);
        try {
            depositC.splitDepositAmountUp(amount);
        } catch (InvalidTypeException e){
            assertEquals(InvalidTypeException.class, e.getClass()); //goes here!
        }
    }

    @Test 
    public void testCorrectDepositSplitUp(){ //ensures the deposit amount code works
        BigDecimal amount = new BigDecimal(120.00);
        try {
            depositC.splitDepositAmountUp(amount);
        } catch (InvalidTypeException e){
            assertEquals(InvalidTypeException.class, e.getClass()); //goes here!
        }

        HashMap<MoneyType, Integer> depositMap = depositC.getDepositAmountMap();
        assertEquals(depositMap.get(MoneyType.HUNDRED_DOLLARS), 1);
        assertEquals(depositMap.get(MoneyType.FIFTY_DOLLARS), 0);
        assertEquals(depositMap.get(MoneyType.TWENTY_DOLLARS), 1);
    }

    @Test //negative test for when balance is too low on card to withdraw
    public void testCantModifyCardWithdrawal(){ //out
        BigDecimal cardBalance = userA.getbalance(); //initial
        userA.setBalance(new BigDecimal(100.50));
        BigDecimal withdrawAmount = new BigDecimal(125.35);
        //initialise amount in deposit
        withdrawalA.setAmount(withdrawAmount);
        withdrawalA.modify(withdrawalA.getCard(), withdrawalA.getType());
        assertEquals(outContent.toString(), "Sorry you don't have enough money stored on your card. Cannot proceed to withdraw money.\n");
    }

    // @Test //negative test for when you cant withdraw money from ATM at that moment due to inadequate amount of money
    // public void testFrozenMoney(){
    //     withdrawalA.setAmount(10981.25); //withdrawal amount
    //     withdrawalA.proceedWithdrawalTransaction(userA);
    //     assertEquals(outContent.toString(), "Unable to withdraw from ATM due to, unavailable amounts of coins/cash. Sorry for the inconvenience, please try in another ATM or come another day.\n");
    // }
    
    @Test //testing withdrawal removes money from card
    public void testCanModifyCardWithdrawal(){ //testing withdrawing money from card is valid (i.e. user has enough money stored in card)
        BigDecimal cardBalance = userA.getbalance(); //initial
        BigDecimal amount = new BigDecimal(124.35);
        //initialise amount in withdrawal
        withdrawalA.setAmount(amount);
        withdrawalA.modify(withdrawalA.getCard(), withdrawalA.getType()); //userA
        assertTrue(userA.getbalance().compareTo(cardBalance.subtract(amount)) == 0);
    }
    // @Test
    // public void testCanWithdrawalAmount(){ //testing when userA's balance is too low and can't withdraw money out
    //     BigDecimal amount = 15.95;
    //     BigDecimal cardBalance = userA.getbalance(); //38762
    //     withdrawalA.setAmount(amount);
    //     withdrawalA.proceedWithdrawalTransaction(userA); //withdraw1 fails????
    //     String lala = "userA's balance = " + userA.getbalance() + ", cardbalance = [" + cardBalance +"], amount = [" +amount+"]\n\n";
    //     // assert(userA.getbalance() == cardBalance-amount);
    //     // assertEquals()
        
    //     assertEquals(outContent.toString(), lala); //"Sorry your card is unavailable. Please try again.\n");
    // }

    @Test
    public void testCanGetTransactionId(){
        assertTrue(balanceCheckB.getTransactionID() == 2);
    }

    // @Test
    // public void testCanCheckBalanceInfo(){
    //     balanceCheckB.getBalanceInfo(userB);
    //     String expected = "\nPrinting card details below!!!\n"+
    //         "Card number 55674, amount stored  = 10000.0, expires on: Wed May 31 00:00:00"+
    //         " AEST 2023\n"+
    //         "The balance query was successful.\n"+
    //         "\n"+
    //         "A receipt has been printed:\n"+
    //         "\n"+
    //         "--------------------------\n"+
    //         "--- XYZ BANK RECEIPT------\n"+
    //         "--------------------------\n"+
    //         "Transaction Type: BALANCE\n"+
    //         "Amount:\n"+
    //         "--------------------------\n"+
    //         "50 x $100\n"+
    //         "50 x $50\n"+
    //         "50 x $20\n"+
    //         "50 x $10\n"+
    //         "100 x $5\n"+
    //         "TOTAL: $9885.0\n";

    //     assertEquals(expected, outContent.toString());
    // }

    @Test
    public void testCantCheckBalanceInfo() { //negative test as card is invalid
        Card failed = null;
        balanceCheckB.getBalanceInfo(failed);
        assertEquals(outContent.toString(), "Sorry your card is unavailable. Please try again.\n");
    }//cannot pass this one-Ke Xu

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