package R18_G2_ASM1;

/*
    "if subclass overrides every abstract method that it inherits, then that subclass is not abstract.
    If it inherits any abstract methods and DOESN'T override them, then the subclass also has abstract methods and must itself also be defined abstract"
    - internet
*/

import java.util.Date;

public class T_Withdrawal extends Transaction {
    
    protected double deductAmount;
    protected TransactionType type;
    protected Account account;

    protected int transactionID;
    protected Date date;
    
    private ATM1 attachedATM;
    
    // deductAmount = userInputAmount
    public T_Withdrawal(ATM1 attachedATM, TransactionType type, Account account, double deductAmount, Date date, int transactionID){
        super(attachedATM, type, account,  deductAmount, date, transactionID);
    }

    //overrides abstract class method
    public void modify(Account account){
        if (account != null) {
            account.deposit(super.amount); //or just modify that 1 card's totalAmount
            System.out.println(TransactionStatus.SUCCESS_DEPOSIT.toString()); 
        } else {
            System.out.println(TransactionStatus.FAIL_DEPOSIT.toString()); 
        }
    }
}