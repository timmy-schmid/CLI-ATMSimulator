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

    double amount;
    double depositAmount;

    ATM atm;
    MoneyStack moneyStack;
    Date date;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); //for testing printing statements
    private final ByteArrayOutputStream errorContent = new ByteArrayOutputStream(); //writes common data into many files 
    private final PrintStream originalOutput = System.out;
    private final PrintStream originalError = System.err;
    
    
    @BeforeEach
    public void setUp() throws ParseException { //for date formatting
        app = new App();
        date = new Date();
        moneyStack = new MoneyStack();
        ATM atm = new ATM("Canberra", moneyStack);
       
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

        amount = 300.50; //to withdraw
        depositAmount = 150.00; // to deposit

        withdrawalA = new Transaction(atm, TransactionType.WITHDRAWAL, userA, 1);

        balanceCheckB = new Transaction(atm, TransactionType.BALANCE, userB, 2);

        depositC = new Transaction(atm, TransactionType.DEPOSIT, userC, 3);
        depositC.initialSetUpMap();

        //setupStreams
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errorContent));
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
        double cardBalance = userC.getbalance(); 
        depositC.setAmount(depositAmount);
        // depositC.modify(userC.getCard(), depositC.getType());
        try {
            depositC.proceedDepositTransaction(userC);
        } catch (InvalidTypeException e) {
            assertEquals(InvalidTypeException.class, e.getClass());
        }
        assertEquals(userC.getbalance(), cardBalance+depositAmount, "Amount in card did not increase,proceedDepositTransaction function failed! ");
    }

    @Test 
    public void voidtestCantDepositAmount(){ //negative test, failure to deposit due to amount is NOT divisble by 5
        double amount = 124.00;
        depositC.setAmount(amount);
        try {
            depositC.proceedDepositTransaction(userC);
        } catch (InvalidTypeException e) {
            assertEquals(InvalidTypeException.class, e.getClass()); //goes here!
        }
    }


    @Test //testing deposit adds money to card
    public void testCanModifyCardDeposit(){ //testing withdrawing money from card is valid (i.e. user has enough money stored in card)
        double cardBalance = userC.getbalance(); //initial
        double amount = 15.00;
        //initialise amount in deposit
        depositC.setAmount(amount);
        depositC.modify(depositC.getCard(), depositC.getType()); //userC
        assert(userC.getbalance() == (cardBalance + amount));
    }

    // @Test //negative test for when balance is too low on card to withdraw
    // public void testCantModifyCardWithdraw(){
    //     double cardBalance = userA.getbalance(); //initial
    //     userA.setBalance(100.50);
    //     double withdrawAmount = 125.35;
    //     //initialise amount in deposit
    //     withdrawalA.setAmount(withdrawAmount);
    //     withdrawalA.modify(withdrawalA.getCard(), withdrawalA.getType());
    //     assertEquals(errorContent.toString(), "Sorry you don't have enough money stored on your card. Cannot proceed to withdraw money.");

    // }
    @Test //negative test for when balance is too low on card to withdraw
    public void out(){
        double cardBalance = userA.getbalance(); //initial
        userA.setBalance(100.50);
        double withdrawAmount = 125.35;
        //initialise amount in deposit
        withdrawalA.setAmount(withdrawAmount);
        withdrawalA.modify(withdrawalA.getCard(), withdrawalA.getType());
        assertEquals(outContent.toString(), "Sorry you don't have enough money stored on your card. Cannot proceed to withdraw money.\n");
    }

    // @Test //testing withdrawal removes money from card
    // public void testCanModifyCardWithdrawal(){ //testing withdrawing money from card is valid (i.e. user has enough money stored in card)
    //     double cardBalance = userA.getbalance(); //initial

    //     //initialise amount in withdrawal
    //     withdrawalA.setAmount(amount);
    //     withdrawalA.modify(withdrawalA.getCard(), withdrawalA.getType()); //userA
    //     assert(userA.getbalance() == (cardBalance - amount));
    // }
    @Test
    public void testCanWithdrawalAmount(){ //testing when userA's balance is too low and can't withdraw money out
        double amount = 15.95;
        double cardBalance = userA.getbalance();
        withdrawalA.setAmount(amount);
        withdrawalA.proceedWithdrawalTransaction(userA);
        assert(userA.getbalance() == cardBalance-amount);
    }

    // public void testRunWrongType() throws InvalidTypeException{ //invalid type to proceed transaction
    //     try {
    //         depositC.run("HELLO");
    //     } catch (InvalidTypeException e) {
    //         assertEquals(InvalidTypeException.class, e.getClass()); //goes here!
    //     }
    // }

}