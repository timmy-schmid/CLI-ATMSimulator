package R18_G2_ASM1;

public class T_Deposit extends Transaction {
    protected Card1 card;
    private int totalAmountStored;

    private int MaxWithdrawalAmount;
    private int MinWithdrawalAmount;
    protected double deductAmount;
    public TransactionType name;

    //TODO: pass in an instance of ATM class so can extract its methods!

    public T_Deposit(TransactionType name, Card1 card, double deductAmount, int MaxWithdrawalAmount, int MinWithdrawalAmount){
        super(name, card, deductAmount, MaxWithdrawalAmount, MinWithdrawalAmount);
    }

    public void check_max_limit(Card1 card){
    }

    public void check_min_limit(Card1 card){
    }
   
    public void validateDeposit(){ //...
    }
    
    public void proceedDepositTransaction(){
        //call atm.askForMoneyStack() in this function

        //this function prompt the user to select on how many of each denomination they want to deposit
    }
}