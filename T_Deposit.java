public class T_Deposit extends Transaction {
    protected Card card;
    
    
    private int totalAmountStored;

    private int MaxWithdrawalAmount;
    private int MinWithdrawalAmount;
    protected int deductAmount;

    public T_Deposit(Card card){
        super(card, deductAmount, MaxWithdrawalAmount, MinWithdrawalAmount);
    }

    
    public void check_max_limit(Card card){

    }

    public void check_min_limit(Card card){

    }


    public void validation(Card card){
        //call this.check_max_limit(card)

        //call this.check_min_limit(card)
    }

    //checking user input amount, break it down into small parts
    //e.g. user inputs $150 (cash combo check)
   
    //usually, what happens is this cash gets stored in an envelope (spit out by ATM), then is placed inside ATM in a separate location, that then gets taken to the bank to be checked if authentic or not. A day later or some time later, once validated it will go through to use's card balance. Each user's transaction date, card details + balance is taken into consideration for every deposit (keeps track, can be notified if money is fake)
    public void validateDeposit(){
        // Since it only allow notes, how can we recognise the user inputs only note? Consider restricting the user to only input like 'notes_value, number of notes, ..., value, numbers', e.g. '5, 10, 20, 50 100'
    }



}