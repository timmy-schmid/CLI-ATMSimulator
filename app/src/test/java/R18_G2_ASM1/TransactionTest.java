package R18_G2_ASM1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

class TransactionTest {
    T_Withdrawal withdrawal;
    // Transaction withdrawal;
    Transaction balanceCheck;
    Transaction deposit;
    public static final int MAX_AMOUNT = 500;
    public static final int MIN_AMOUNT = 5;
    App app;

    Card1 userA;

    @BeforeAll
    public void setUp(){
        app = new App();

        userA = new Card1("BOB", 10000, "15/08/2019", "20/08/2022");
        withdrawal = new T_Withdrawal("Withdrawal", userA, 200, 500, 5);
        // balanceCheck = new Transaction(userA);
        // deposit = new T_Deposit(userA);
    }

    @AfterAll //@AfterEach
    public void tearDown(){
        userA = null;
        withdrawal = null;
    }

    @Test
    public void testNotNullCard(){ //testing card obj not null
        assertNotNull(userA);
    }
    
    @Test
    public void testcanDeductMoney(){ //testing withdrawing money from card is valid (i.e. user has enough money stored in card)
        boolean result = withdrawal.canDeduct(userA);
        assert (result ==true);
    }

    // @Test
    // public void testcanDeductExactAmount(){ //testing cash returned (whole num, note)
    //     MoneyType total_hundred_amount = new EnumTest(HUNDRED_DOLLARS.10);
    //     boolean result = withdrawal.exactWithdrawalAmountCheck(userA);
    //     assert (result ==true);
    // }
}

    // public static void testPrintCard1(Card1 userA){
    //     userA.getCardDetails();
    // }

    // public static void testDeductMoneyFromCard(Transaction withdrawal){
    //     withdrawal.deductFromCard();
    // }

    // //just printing out ALL enum vals
    // public static void testMoneyType(){
    //     for (MoneyType type: MoneyType.values()) {
    //         System.out.println("type = "+type + ", value = " + type.getValue() +" , amount = " + type.getAmount());
    //     }
    // }

    // // public static void testExactWithdrawalAmount(Transaction withdrawal){
    // //     //first time withdrawing money from ATM (Full originally)
    // //    boolean result =  withdrawal.exactWithdrawalAmountCheck();
    // // //    assert(MoneyType.HUNDRED_DOLLARS.getAmount() == 49); //decreased by 1
    // // }

    // //prints output after splitting up deductAmount into different money/coin components 
    // public static void testCashRemainder(Transaction withdrawal){
    //     withdrawal.findRemainder(); //withdrawal.getDeductAmount());
    //     withdrawal.printRemainderStorageMap();
    // }


    // //1 user using ATM machine
    // public static void testDeductFromATM(Transaction withdrawal){
    //     withdrawal.findRemainder();
    //     withdrawal.compareReqWithMoneyTypeAmount(withdrawal.getRemainderStorageMap());
    // }

    
    // //3 users using ATM machine (A, B, C)
    // public static void multipleTestDeductFromATM(Transaction withdrawalA, Transaction withdrawalB, Transaction withdrawalC){
       
    //     withdrawalA.findRemainder();
    //     withdrawalA.compareReqWithMoneyTypeAmount(withdrawalA.getRemainderStorageMap()); //deduct from ATM
    //     withdrawalA.deductFromCard();
        
    //     withdrawalB.findRemainder();
    //     withdrawalB.compareReqWithMoneyTypeAmount(withdrawalB.getRemainderStorageMap());
    //     withdrawalB.deductFromCard();

    //     withdrawalC.findRemainder();
    //     withdrawalC.compareReqWithMoneyTypeAmount(withdrawalC.getRemainderStorageMap()); //deduct from ATM
    //     withdrawalC.deductFromCard();
    // }


    // public static void main(String[] args) {
    //     ATM1 atm1 = new ATM1();

    //     List<Card1> cardsListA = new ArrayList<>();
    //     List<Card1> cardsListB = new ArrayList<>();
    //     List<Card1> cardsListC = new ArrayList<>();

    //     Card1 userA = new Card1("BOB", 111,1000, "15/08/2019", "20/08/2022");
    //     cardsListA.add(userA);
    //     Account userAAccount = new Account(userA.getID(), cardsListA);

    //     Card1 userB = new Card1("DYLAN", 121,1000, "25/08/2020", "15/10/2022");
    //     cardsListB.add(userB);
    //     Account userBAccount = new Account(userB.getID(), cardsListB);

    //     Card1 userC = new Card1("MILLY", 131,1000, "01/04/2020", "25/12/2021");
    //     cardsListC.add(userC);
    //     Account userCAccount = new Account(userC.getID(), cardsListC);


    //     Date date = new Date();
    //     double deductAmountAB = 59.50;
    //     double deductAmountC = 120.00;

    //     //where transactionID = userID? 
    //     Transaction withdrawalA = new T_Withdrawal(atm1, TransactionType.WITHDRAWAL, userAAccount, deductAmountAB, date, userA.getID());

    //     Transaction withdrawalB = new T_Withdrawal(atm1, TransactionType.WITHDRAWAL, userBAccount, deductAmountAB, date, userB.getID());

    //     Transaction withdrawalC = new T_Withdrawal(atm1, TransactionType.WITHDRAWAL, userCAccount, deductAmountC, date, userC.getID());

    //     // testCashRemainder(withdrawal);
    //     // testDeductFromATM(withdrawal);
    //     multipleTestDeductFromATM(withdrawalA, withdrawalB, withdrawalC);
    //     testMoneyType();

    //     System.out.println("\n\n");
    //     testPrintCard1(userA);

    //     testPrintCard1(userB);
    //     testPrintCard1(userC);
    // }
// }