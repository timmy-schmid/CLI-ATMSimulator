package R18_G2_ASM1;

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

    // Account userAAccount;
    // Account userBAccount;
    // Account userCAccount;

    double amount;
    ATM atm;
    Date date;

    @BeforeEach
    public void setUp() throws ParseException { //for date formatting
        app = new App();
        date = new Date();
        ATM atm = new ATM("Canberra");
       
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        startDate = dateFormat.parse("2018-06-01");
        expiraryDate = dateFormat.parse("2023-05-31");
    

        userA = new Card(38762.99, 55673, startDate, expiraryDate,
        false, true, false, 888888);
        userB = new Card(10000.00, 55674, startDate, expiraryDate,
        false, true, true, 777777);
        userC = new Card(10000.99, 55675, startDate, expiraryDate,
        false, false, false, 666666);

        amount = 300.50; //to withdraw
        double depositAmount = 150.00; // to deposit

        withdrawalA = new Transaction(atm, TransactionType.WITHDRAWAL, userA, 1);

        balanceCheckB = new Transaction(atm, TransactionType.BALANCE, userB, 2);

        depositC = new Transaction(atm, TransactionType.DEPOSIT, userC, 3);
    }

    @AfterEach
    public void tearDown(){ //eject card from atm?
        userA = null;
        userB = null;
        userC = null;

        withdrawalA = null;
        balanceCheckB = null;
        depositC = null;
    }

    @Test
    public void testNotNullCardAccount(){ //testing card obj not null
        assertNotNull(userA);
        assertNotNull(userB);
        assertNotNull(userC);
    }
    
    // @Test
    // public void testcanDeductFromCard(){ //testing withdrawing money from card is valid (i.e. user has enough money stored in card)
    //     boolean result = withdrawalA.canDeductFromCard(userA);
    //     assert (result == true);
    // }

    // @Test
    // public void testcantDeductFromCard(){ //not enough stored in card to deduct
    //     double temp = 250;
    //     userA.setTotalAmount(temp); //set card amount from 1500 to 250, deduct = 300
    //     boolean result = withdrawalA.canDeductFromCard(userA);
    //     assert (result == false );
    //     userA.setTotalAmount(withdrawalA.getAmount());
    // }

    // @Test
    // //prints output after splitting up deductAmount into different money/coin components 
    // public void testCashRemainder(){
    //     withdrawalA.findRemainder(); //withdrawal.getDeductAmount());
    //     assertNotNull(withdrawalA.getSplitWithdrawalAmountMap());
    // }

    // @Test
    // public void testCanDeposit(){
    //     double expected = userC.getTotalAmount()+depositAmount;
    //     depositC.proceedDepositTransaction(depositC.getAccount());
        
    //     double actual = userC.getTotalAmount();
    //     //assert userC has extra amount
    //     System.out.println("Expected = " + expected + " ;:::::: actual = " + actual);
    //     assertEquals(actual, expected,
    //             "Amount in card did not increase,proceedDepositTransaction function failed! ");
    //     // userC.getCardDetails();
    // }
}

    // //1 user using ATM machine
    // public static void testDeductFromATM(Transaction withdrawal){
    //     withdrawal.findRemainder();
    //     withdrawal.compareReqWithMoneyTypeAmount(withdrawal.getRemainderStorageMap());
    // }

    // //3 users using ATM machine (A, B, C)
    // public static void multipleTestDeductFromATM(Transaction withdrawalA, Transaction withdrawalB, Transaction withdrawalC){
       
    //     //where transactionID = userID? 

        // //just printing out ALL enum vals
    // public static void testMoneyType(){
    //     for (MoneyType type: MoneyType.values()) {
    //         System.out.println("type = "+type + ", value = " + type.getValue() +" , amount = " + type.getAmount());
    //     }
    // }
// }