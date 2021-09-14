package R18_G2_ASM1;

import java.util.Date;

public class T_Deposit extends Transaction {
    protected double amount;

    protected TransactionType type;
    private ATM1 attachedATM;
    protected Account account;

    protected int transactionID;
    protected Date date;

    public T_Deposit(ATM1 attachedATM, TransactionType type, Account account, double amount, Date date, int transactionID){
        super(attachedATM, type, account, amount, date, transactionID);
    }
   
    public void validateDeposit(){ //...
    }

    //overrides transaction's modify function
    public void modify(Account account){
        if (account != null) {
            account.deposit(super.amount); //or just modify that 1 card's totalAmount

            //account.getCardsList().get(0) --> user's 1st card --> deduct amount? --> or add to account class
            account.getCards().get(0).totalAmount += super.amount;
            System.out.println(TransactionStatus.SUCCESS_DEPOSIT.toString()); 
        
        } else {
            System.out.println(TransactionStatus.FAIL_DEPOSIT.toString()); 
        }
    }
    
    public void proceedDepositTransaction(Account account){
        //call atm.askForMoneyStack() in this function

        //this function prompt the user to select on how many of each denomination they want to deposit

        this.modify(account);
        // System.out.println("line 42 in T_DEPOSIT: account balance = " + account.getBalance());
    }  
}