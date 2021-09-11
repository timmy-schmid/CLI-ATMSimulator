package R18_G2_ASM1;

public class T_Balance extends Transaction {
    private Card1 card;
    private int totalAmountStored;
    private int MaxWithdrawalAmount;
    private int MinWithdrawalAmount;
    protected double deductAmount;

    public TransactionType name;

    public T_Balance(TransactionType name, Card1 card, double deductAmount, int MaxWithdrawalAmount, int MinWithdrawalAmount){
        super(name, card, deductAmount, MaxWithdrawalAmount, MinWithdrawalAmount);
    }

    //prints card details
    public void getBalance(Card1 card){

        //validate card first --> call card.validation() function? 
        if (card != null) {
            System.out.println("Total amount stored in this card is " + card.totalAmount);
        } else {
            System.out.println("Cannot determine card balance due to error validating card. Sorry!");
        }
    }
}