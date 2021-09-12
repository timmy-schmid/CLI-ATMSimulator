package R18_G2_ASM1;

import java.util.Date;
import java.lang.Math;
import java.util.HashMap;

/*

TransactionStatusCode: used to record the state of the transaction i.e. if user doesn't have enough money in their account when withdrawing --> transaction status should be set to FailDeposit Enum

*/

public abstract class Transaction { //ABSTRACT CLASS

    //declaring fields + nonabstract methods
    protected int transactionID;
    protected Date date;
    protected double amount; 

    private int totalAmountStored;
    protected HashMap <String, Integer> splitWithdrawalAmountMap; // remainderStorageMap 

    protected TransactionType type;
    // protected TransactionStatusCode currentStatus;
    private ATM1 attachedATM;
    protected Account account;

    //where amount represents deductAmount or addAmount
    public Transaction(ATM1 attachedATM, TransactionType type, Account account, double amount, Date date, int transactionID){
        
        this.amount = amount; //how much user wants to extract from ATM! from here or from Card.deductAmount()???
        this.type = type;
        this.totalAmountStored = 100000; //comes from ATM getTotalAmountStored()? 
        this.attachedATM = attachedATM;
        this.account = account;
        this.splitWithdrawalAmountMap = new HashMap<>();
        this.date = date;
        this.transactionID = transactionID;
    }

    // returns account ID
    public int getID(){ 
        return this.account.getAccountID();
    }
    
    // returns account ID
    public Account getAccount(){
        return this.account;
    }

    public HashMap<String, Integer> getSplitWithdrawalAmountMap(){
        return this.splitWithdrawalAmountMap;
    }

    // retrieves which type it is from user input?
    public TransactionType getType(){
        return this.type;
    }
    
    public double getAmount(){
        return this.amount;
    }

    public void cancelOption(){
    }

    // public TransactionStatusCode getStatus(){ //create a transactionStatusCode class file?
    //     return this.currentStatus;
    // }

    public void modify(Account acc){
        account.deposit(amount);
    }

    public void run(){ //call in App class (where each button associates with its subclass (transaction, deposit or balance check?))
    }
    
    public boolean canDeductFromCard(Card card){ //where this card is the card user picked out of all cards found in Account (cardsList)
        //first validate card, then deduct amount
        if (card != null && card.getTotalAmount() >= this.amount){
            // card.totalAmount -= this.amount;
            //withdraw from account or from card class?
            return true;
        } else {
            return false;
        }
    }

    //finds remainder then stores the amount=deductAmount into a map and returns it
    public void findRemainder(){//double removeAmount){ //compare this against moneytypes
        // System.out.printf("LINE132 TRANSACTION ----------------- initial deductAmount = [%.2f]\n", this.amount);
        double temp1 = amount;

        int hundred = 0;
        int fifty = 0;
        int twenty = 0;
        int ten = 0;
        int five = 0;
        int two = 0;
        int one = 0;
        int fifty_c = 0;

        if (temp1 >= 100){
            hundred = (int)(temp1/100);
            temp1 = temp1 % 100;
        }

        if (temp1 >= 50){
            fifty = (int)(temp1/50);
            temp1 = temp1 % 50;

        } if (temp1 >= 20){
            twenty = (int)(temp1/20);
            temp1 = temp1 % 20;

        } if (temp1 >= 10){ // < 20
            ten = (int)(temp1/10);
            temp1 = temp1 % 10;

        } if (temp1 >= 5){
            five = (int)(temp1/5);
            temp1 = temp1 % 5;

        } if (temp1 >= 2){
            two = (int)(temp1/2);
            temp1 = temp1 % 2;

        } if (temp1 >= 1){
            one = (int)(temp1/1);
            temp1 = temp1 % 1;

        } else {
            fifty_c = (int)(temp1/0.5);
            temp1 = temp1 % 0.5;
        }

        //STORE THE RESULTS INTO THE HASHMAP!!!

        this.splitWithdrawalAmountMap.put("hundred", hundred);
        this.splitWithdrawalAmountMap.put("fifty", fifty);
        this.splitWithdrawalAmountMap.put("twenty", twenty);
        this.splitWithdrawalAmountMap.put("ten", ten);
        this.splitWithdrawalAmountMap.put("five", five);
        this.splitWithdrawalAmountMap.put("two", two);
        this.splitWithdrawalAmountMap.put("one", one);
        this.splitWithdrawalAmountMap.put("fifty_c", fifty_c);
    }

    public void printSplitWithdrawalAmountMap(){
        for (HashMap.Entry<String, Integer> entry : this.getSplitWithdrawalAmountMap().entrySet()) {
            if (entry.getValue() > 0){ //print only those with amount != 0
                System.out.printf("Money type: $%s ------ amount:[%d]\n", entry.getKey(), entry.getValue());
            }
        }
    }

    //after finding out amount of each moneytype required to deduct, check each amount and compare with moneytype value to see if there is enough
        //[n] vs MoneyType.HUNDRED_DOLLARS.getValue(100) , if n = 101 but value = 100, (use the 2nd option $50)

    public void compareReqWithMoneyTypeAmount(HashMap<String, Integer> keyValMap){ //remainderStoragemap
        //MoneyType type;

        if (keyValMap.get("hundred") > 0 && MoneyType.HUNDRED_DOLLARS.getAmount() >= keyValMap.get("hundred")){ //gets value e.g. {$100: [value]}
            MoneyType.HUNDRED_DOLLARS.amount -= keyValMap.get("hundred");
        }

        if (keyValMap.get("fifty") > 0 && MoneyType.FIFTY_DOLLARS.getAmount() >= keyValMap.get("fifty")){ 
            MoneyType.FIFTY_DOLLARS.amount -= keyValMap.get("fifty");
        }

        if (keyValMap.get("twenty") > 0 && MoneyType.TWENTY_DOLLARS.getAmount() >= keyValMap.get("twenty")){
            MoneyType.TWENTY_DOLLARS.amount -= keyValMap.get("twenty");
        }

        if (keyValMap.get("ten") > 0 && MoneyType.TEN_DOLLARS.getAmount() >= keyValMap.get("ten")){ 
            MoneyType.TEN_DOLLARS.amount -= keyValMap.get("ten");
        }

        if (keyValMap.get("five") > 0 && MoneyType.FIVE_DOLLARS.getAmount() >= keyValMap.get("five")){ 
            MoneyType.FIVE_DOLLARS.amount -= keyValMap.get("five");
        }

        if (keyValMap.get("two") > 0 && MoneyType.TWO_DOLLARS.getAmount() >= keyValMap.get("two")){ 
            MoneyType.TWO_DOLLARS.amount -= keyValMap.get("two");
        }

        if (keyValMap.get("one") > 0 && MoneyType.ONE_DOLLAR.getAmount() >= keyValMap.get("one")){ 
            MoneyType.ONE_DOLLAR.amount -= keyValMap.get("one");
        }

        if (keyValMap.get("fifty_c") > 0 && MoneyType.FIFTY_CENTS.getAmount() >= keyValMap.get("fifty_c")){ 
            MoneyType.FIFTY_CENTS.amount -= keyValMap.get("fifty_c");
        } else {
            System.out.println("CANNOT DO THE THINGS ABOVE BC: lacking money in ATM :( !\n");
        }
        //incomplete for 20c, 10c, 5c  ...
    }

    public void checkRunOut(){ //skip to next highest available amount and deduct from there
    }

    public void WITHDRAW_MONEY(Card card){
        this.findRemainder(); //store the amount into a map [notes/coins]
        this.compareReqWithMoneyTypeAmount(this.getSplitWithdrawalAmountMap());
        
        if (this.canDeductFromCard(card) == true) {
            card.totalAmount -= this.amount;
            System.out.println(TransactionStatus.SUCCESS_WITHDRAWAL.toString());
        } else {
            System.out.println(TransactionStatus.FAIL_WITHDRAWAL.toString());
        }
        return;
    }


}