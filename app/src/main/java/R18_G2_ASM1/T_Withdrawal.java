// package R18_G2_ASM1;

/*
    all extensions from abstract class Transaction should have overriding methods???!!

    "if subclass overrides every abstract method that it inherits, then that subclass is not abstract.
    If it inherits any abstract methods and DOESN'T override them, then the subclass also has abstract methods and must itself also be defined abstract"
    - internet
*/
public class T_Withdrawal extends Transaction {
    protected Card1 card;

    private int MaxWithdrawalAmount;
    private int MinWithdrawalAmount;
    protected double deductAmount;

    public TransactionType name;

    //where name = enumType, deductAmount = userInputAmount
    public T_Withdrawal(TransactionType name, Card1 card, double deductAmount, int MaxWithdrawalAmount, int MinWithdrawalAmount){ //after selecting button to withdrawal (amount = deduct_amount?)
        super(name, card, deductAmount, MaxWithdrawalAmount, MinWithdrawalAmount); 
    }
    
    public void check_withdrawal_limit(Card1 card){
        ;
        //error message if reached max withdrawal amount/min withdrawal amount (prompt to input again)
    }

    //algorithm to determine what cash to withdrawal (first consider cash, then coins)

    // option 1: let user on screen decide ether larger cash note or smaller cash note 
    // option 2: user can only pick larger amount on screen to withdrawal, if they dont want to, then 'cancel' and select a smaller amount   
}