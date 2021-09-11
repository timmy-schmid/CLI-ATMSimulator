import java.util.Date;

/*

Transaction class is abstract (can be subclassed into 3 categories but NOT instantiated)

- all these astract methods are to be implemented in child classes

*/

public abstract class Transaction { //ABSTRACT CLASS

    /*

       
       protected  TransactionStatusCode: currentStatus;
       protected TransactionType: type --> withdrawal, deposit or checkBalance'
        Account acc;
    */


    //declaring fields + nonabstract methods
    protected int transactionID;
    protected Date date;
    protected boolean toCancel;
    private Card card;
    protected int deductAmount; 

    private int totalAmountStored;

    private int MaxWithdrawalAmount;
    private int MinWithdrawalAmount;

    protected Date[][] date_list ; //[[start_date], [end_date]]? 

    protected String type;
    public Transaction(String type, Card card, int deductAmount, int MaxWithdrawalAmount, int MinWithdrawalAmount){
        // this.date_list = card.getDateDetails();
        this.deductAmount = deductAmount; //how much user wants to extract from ATM! from here or from card.deductAmount()???
        this.type=type;
        this.MaxWithdrawalAmount = MaxWithdrawalAmount;
        this.MinWithdrawalAmount = MinWithdrawalAmount;

        this.totalAmountStored = 100000;
    }

    // public int getID(){ //requires input card ID to check status after getting id
    //     return -1;
    // }

    //where enum = withdrawal, deposit or balance
    // public TransactionType getType(){enum TransactionType type){ //retrieves which type it is from user input?
    public String getType(){
        return type;
    }
    
    public void validate_card_amount(Card card){
        //either call this method from card or let subclasses of transaction call this method

        //checks card amount
    }

    public void cancelOption(){

    }

    // the information printed on the receipt includes transaction number, transaction type, amount withdrawn, and account balance

}