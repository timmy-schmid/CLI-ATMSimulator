// package R18_G2_ASM1;

/*
    "if subclass overrides every abstract method that it inherits, then that subclass is not abstract.
    If it inherits any abstract methods and DOESN'T override them, then the subclass also has abstract methods and must itself also be defined abstract"
    - internet
*/

import java.util.Date;

public class T_Withdrawal extends Transaction {
    protected double deductAmount;

    protected TransactionType type;
    private ATM1 attachedATM;
    protected Account account;

    protected int transactionID;
    protected Date date;
    

    // deductAmount = userInputAmount
    public T_Withdrawal(ATM1 attachedATM, TransactionType type, Account account, double deductAmount, Date date, int transactionID){ //after selecting button to withdrawal (amount = deduct_amount?)
        super(attachedATM, type, account,  deductAmount, date, transactionID);
    }

    //overrides abstract class method
    // public void modify(Account acc){
    //     account.withdraw(amount);
    // }
}