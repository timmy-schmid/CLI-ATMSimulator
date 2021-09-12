// package R18_G2_ASM1;

/*
    all extensions from abstract class Transaction should have overriding methods???!!

    "if subclass overrides every abstract method that it inherits, then that subclass is not abstract.
    If it inherits any abstract methods and DOESN'T override them, then the subclass also has abstract methods and must itself also be defined abstract"
    - internet
*/

import java.util.Date;

public class T_Withdrawal extends Transaction {
    protected Card1 card;

    // private int MaxWithdrawalAmount;
    // private int MinWithdrawalAmount;
    protected double deductAmount;

    protected TransactionType type;
    private ATM1 attachedATM;
    protected Account account;

    protected int transactionID;
    protected Date date;
    

    //where type = enumType, deductAmount = userInputAmount
    public T_Withdrawal(ATM1 attachedATM, TransactionType type, Account account, double deductAmount, Date date, int transactionID){ //after selecting button to withdrawal (amount = deduct_amount?)
        super(attachedATM, type, account,  deductAmount, date, transactionID);
    }
    
    // public void check_withdrawal_limit(Card1 card){ //get card used (function in Card class returning which card from list)
    //     ;
    //     //error message if reached max withdrawal amount/min withdrawal amount (prompt to input again)
    // }

    //algorithm to determine what cash to withdrawal (first consider cash, then coins)

    // option 1: let user on screen decide ether larger cash note or smaller cash note 
    // option 2: user can only pick larger amount on screen to withdrawal, if they dont want to, then 'cancel' and select a smaller amount   
    

    //checks whether card has enough money on it
    public void check_max_limit(Card1 card){ //pass in list of cards from acc?
    }

    public void check_min_limit(Card1 card){
    }
}