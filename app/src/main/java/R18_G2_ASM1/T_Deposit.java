// package R18_G2_ASM1;
import java.util.Date;

public class T_Deposit extends Transaction {
    private int totalAmountStored;
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
            account.deposit(amount); //or just modify that 1 card's totalAmount
            System.out.println(TransactionStatus.SUCCESS_DEPOSIT.toString()); 
        } else {
            System.out.println(TransactionStatus.FAIL_DEPOSIT.toString()); 
        }
        //account.getCardsList().get(0) --> user's 1st card --> deduct amount?
    }
    
    public void proceedDepositTransaction(Account account){
        //call atm.askForMoneyStack() in this function

        //this function prompt the user to select on how many of each denomination they want to deposit

        //then call deposit() (from Card class)
        this.modify(account);
    }  
}