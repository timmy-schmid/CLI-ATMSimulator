public class T_Balance extends Transaction {
    protected Card card;

    private int totalAmountStored;

    private int MaxWithdrawalAmount;
    private int MinWithdrawalAmount;
    protected int deductAmount;

    public T_Balance(Card card){
        super(card, deductAmount, MaxWithdrawalAmount, MinWithdrawalAmount);
    }

    public int getCardBalance(Card card){
        return card.totalAmount;
    }

    public void printCardBalance(Card card){
        if (card != null) {
            
        System.out.println("Total amount stored in this card is " + card.totalAmount);
        } else {
            System.out.println("Cannot determine card balance due to error validating card. Sorry!");
        }
    }
}