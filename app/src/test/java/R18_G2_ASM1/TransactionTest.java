package R18_G2_ASM1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.AfterAll;

class TransactionTest {
    Transaction withdrawalA;
    Transaction balanceCheckB;
    T_Deposit depositC;

    App app;

    Card userA; // withdraw
    Card userB; // deposit
    Card userC; // balance check

    Date date;

    ArrayList<Card> cardsListA; 
    ArrayList<Card> cardsListB;
    ArrayList<Card> cardsListC;

    Account userAAccount;
    Account userBAccount;
    Account userCAccount;

    double amount;
    double depositAmount;

    ATM1 atm1;

    @BeforeEach
    public void setUp(){
        app = new App();
        date = new Date();
        atm1 = new ATM1();

        userA = new Card("BOB",1500, 33333, date, date, false, false, false, 333); //adjust start/end date
        userB = new Card("DYLAN", 1000, 22222, date, date, false, false, false, 222);
        userC = new Card("MILLY", 1000, 11111, date, date, false, false, false, 111);

        amount = 300.00; //to withdraw
        depositAmount = 150.50;

        cardsListA = new ArrayList<>();
        cardsListB = new ArrayList<>();
        cardsListC = new ArrayList<>();
        
        cardsListA.add(userA);
        cardsListB.add(userB);
        cardsListC.add(userC);

        userAAccount = new Account(userA.getCardNumber(), userA.getTotalAmount(), cardsListA);

        userBAccount = new Account(userB.getCardNumber(), userB.getTotalAmount(), cardsListB);

        userCAccount = new Account(userC.getCardNumber(), userC.getTotalAmount(), cardsListC);


        withdrawalA = new T_Withdrawal(atm1, TransactionType.WITHDRAWAL, userAAccount, amount, date, userA.getCardNumber());

        balanceCheckB = new T_Balance(atm1, TransactionType.BALANCE, userBAccount, amount, date, userB.getCardNumber());

        depositC = new T_Deposit(atm1, TransactionType.DEPOSIT, userCAccount, depositAmount, date, userC.getCardNumber());


    }

    @AfterEach
    public void tearDown(){ //eject card from atm?
        userA = null;
        userB = null;
        userC = null;

        withdrawalA = null;
        balanceCheckB = null;
        depositC = null;

        userAAccount = null;
        userBAccount = null;
        userCAccount = null;

        cardsListA = null;  
        cardsListB = null;  
        cardsListC = null;  
    }

    @Test
    public void testNotNullCardAccount(){ //testing card obj not null
        assertNotNull(userA);
        assertNotNull(userAAccount);
    }
    
    @Test
    public void testcanDeductFromCard(){ //testing withdrawing money from card is valid (i.e. user has enough money stored in card)
        boolean result = withdrawalA.canDeductFromCard(userA);
        assert (result == true);
    }

    @Test
    public void testcantDeductFromCard(){ //not enough stored in card to deduct
        double temp = 250;
        userA.setTotalAmount(temp); //set card amount from 1500 to 250, deduct = 300
        boolean result = withdrawalA.canDeductFromCard(userA);
        assert (result == false );
        userA.setTotalAmount(withdrawalA.getAmount());
    }

    @Test
    //prints output after splitting up deductAmount into different money/coin components 
    public void testCashRemainder(){
        withdrawalA.findRemainder(); //withdrawal.getDeductAmount());
        assertNotNull(withdrawalA.getSplitWithdrawalAmountMap());
    }

    @Test
    public void testCanDeposit(){
        double expected = userC.getTotalAmount()+depositAmount;
        depositC.proceedDepositTransaction(depositC.getAccount());
        
        double actual = userC.getTotalAmount();
        //assert userC has extra amount
        System.out.println("Expected = " + expected + " ;:::::: actual = " + actual);
        assertEquals(actual, expected,
                "Amount in card did not increase,proceedDepositTransaction function failed! ");
        // userC.getCardDetails();
    }
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