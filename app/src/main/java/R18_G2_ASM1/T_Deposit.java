// package R18_G2_ASM1;
import java.util.Date;

public class T_Deposit extends Transaction {
    // protected Card card;
    private int totalAmountStored;
    protected double deductAmount;

    protected TransactionType type;
    private ATM1 attachedATM;
    protected Account account;

    protected int transactionID;
    protected Date date;

    //TODO: pass in an instance of ATM class so can extract its methods!

    public T_Deposit(ATM1 attachedATM, TransactionType type, Account account, double deductAmount, Date date, int transactionID){
        super(attachedATM, type, account, deductAmount, date, transactionID);
    }
   
    public void validateDeposit(){ //...
    }

    public void modify(Account account){ //call inside proceedDepositTransaction function

    }
    
    public void proceedDepositTransaction(){
        //call atm.askForMoneyStack() in this function

        //this function prompt the user to select on how many of each denomination they want to deposit

        //then call deposit() (from Card class)
    }

   
}