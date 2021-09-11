public class T_Withdrawal extends Transaction {
    protected Card card;

    //checktype (coins/notes)

    // protected List<MoneyType> coin_cash_list = new ArrayList<MoneyType>();  --> enum class for this 

    private int totalAmountStored;

    private int MaxWithdrawalAmount;
    private int MinWithdrawalAmount;
    protected int deductAmount;

    public String name;

    public T_Withdrawal(int deductAmount, Card card){ //after selecting button to withdrawal (amount = deduct_amount?)
        super(card, deductAmount, MaxWithdrawalAmount, MinWithdrawalAmount); //reuseable from parent class (inherited), either deductAmount from card or from Transaction
        this.name = "withdrawal!";
    }

    public void deductFromCard(Card card){
        //first validate card, then deduct amount
        if (card!= null && this.canDeduct(card) == true){
            card.totalAmount -= this.deductAmount;
            return;
        } else if (card != null && this.canDeduct(card) == false) {
            System.out.println("cannot deduct money. Either not enough in account card or too much withdrawn thats not ok!");
            return;
        } else { //card is null
            System.out.println("Card is not valid! Unable to deduct amount.");
        }

    }
    public String getName(){
        return this.name;
    }
    
    public boolean canDeduct(Card card){ //amount can be taken from card
        if (card != null && card.totalAmount >= this.deductAmount){
            return true;
        } else {
            return false;
        }
    }

    public void check_withdrawal_limit(Card card){
        ;
        //error message if reached max withdrawal amount/min withdrawal amount (prompt to input again)
    }

    // public void checkCoinsCash(Card card){
    //     //if withdrawal amount is a whole number --> if divisble by notes, consider how much less + how many coins need to add up to it
    // }


    //algorithm to determine what cash to withdrawal (first consider cash, then coins)

    // option 1: let user on screen decide ether larger cash note or smaller cash note 
    // option 2: user can only pick larger amount on screen to withdrawal, if they dont want to, then 'cancel' and select a smaller amount
    
    public boolean exactWithdrawalAmountCheck(MoneyType monType){
        if (monType.HUNDRED_DOLLARS.getValue() == deductAmount) || (monType.FIFTY_DOLLARS.getValue() == deductAmount) || (monType.TWENTY_DOLLARS.getValue() == deductAmount){
            return True;
        } else { //either do for min amount/10, 5, or fixed smallest cash amount as 20
            return False; //requires further splitting up calc
        }
    }

    //pretend this map exists: {100_note: 100}

    
    public String withdrawalAlg(Card card, MoneyType monType){

        //validate card amount is enough

        //consider cash first:
        if (this.exactWithdrawalAmountCheck(monType) == True){
            return MoneyType;
        } else {
            // deduct amount or use modulo from moneytype
            return null;
        }

    }

}