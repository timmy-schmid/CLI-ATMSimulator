package R18_G2_ASM1;

import java.util.Date;
import java.lang.Math;
import java.util.HashMap;

/*

Transaction class is abstract (can be subclassed into 3 categories but NOT instantiated)

- all these astract methods are to be implemented in child classes
*/

public abstract class Transaction { //ABSTRACT CLASS

    //declaring fields + nonabstract methods
    protected int transactionID;
    protected Date date;
    protected boolean toCancel;
    protected Card1 card1;
    protected double deductAmount; 

    private int totalAmountStored;

    private int MaxWithdrawalAmount;
    private int MinWithdrawalAmount;

    protected HashMap<String, Integer> remainderStorageMap;

    protected TransactionType type;
    // protected TransactionStatusCode currentStatus;
    // protected Account account;

    public Transaction(TransactionType type, Card1 card1, double deductAmount, int MaxWithdrawalAmount, int MinWithdrawalAmount){
        this.deductAmount = deductAmount; //how much user wants to extract from ATM! from here or from Card1.deductAmount()???
        this.type = type;
        this.MaxWithdrawalAmount = MaxWithdrawalAmount;
        this.MinWithdrawalAmount = MinWithdrawalAmount;
        this.card1 = card1;
        this.totalAmountStored = 100000;

        this.remainderStorageMap = new HashMap<>();
    }

    public int getID(){ //requires input Card1 ID to check status after getting id
        return this.card1.getID();
    }

    public HashMap<String, Integer> getRemainderStorageMap(){
        return this.remainderStorageMap;
    }

    // retrieves which type it is from user input?
    public TransactionType getType(){
        return this.type;
    }
    
    public double getDeductAmount(){
        return this.deductAmount;
    }
    // public void validateCardAmount(Card1 card1){
    //     //checks card amount --> call from card class function?
    // }

    public void cancelOption(){
    }

    //FIRST CREATE TransactionStatusCode + ACCOUNT CLASS FILE BEFORE UNCOMMENTING THIS OUT!!!!!


    // public TransactionStatusCode getStatus(){
    //     return this.currentStatus;
    // }
    // public void modify(Account acc){

    // }

    public void run(){ //call in App class (where each button associates with its subclass (transaction, deposit or balance check?))
    }
    
    public boolean canDeduct(){//Card1 card){ //amount can be taken from card
        if (this.card1 != null && this.card1.getTotalAmount() >= this.deductAmount){
            return true;
        } else {
            return false;
        }
    }

    public void deductFromCard(){
        //first validate card, then deduct amount
        if (this.card1!= null && canDeduct() == true){
            this.card1.totalAmount -= this.deductAmount;
            System.out.printf("LINE 26 IN T+WITHDRAWAL CLASS: card amount = [%2f], deductAmount = [%2f]\n", this.card1.getTotalAmount(), this.deductAmount);
            return;
        } else if (this.card1 != null && this.canDeduct() == false) {
            System.out.println("cannot deduct money. Either not enough in account card or too much withdrawn thats not ok!");
            return;
        } else { //card is null
            System.out.println("Card is not valid! Unable to deduct amount.");
        }
    }
    
    //finds remainder then stores the deductAmount into a map and returns it
    public void findRemainder(){//double removeAmount){ //compare this against moneytypes

        // System.out.printf("LINE132 TRANSACTION ----------------- initial deductAmount = [%.2f]\n", deductAmount);
        double temp1 = deductAmount;
        // int temp1 = (int)temp;

        int hundred = 0;
        int fifty = 0;
        int twenty = 0;
        int ten = 0;
        int five = 0;
        int two = 0;
        int one = 0;
        int fifty_c = 0;

        if (temp1 >= 100){
            // System.out.println("STEP 1: >= 100");
            hundred = (int)(temp1/100);
            temp1 = temp1 % 100;
            // System.out.printf("hundred = [%d], temp = [%.2f]\n", hundred, temp1);
        }

        if (temp1 >= 50){
            System.out.println("STEP 2 >= 50");
            fifty = (int)(temp1/50);
            temp1 = temp1 % 50;

            // System.out.printf("fifty = [%d], temp = [%.2f]\n", fifty, temp1);
        } if (temp1 >= 20){
            // System.out.println("STEP 3 >= 20");
            twenty = (int)(temp1/20);
            temp1 = temp1 % 20;

        } if (temp1 >= 10){ // < 20
            // System.out.println("STEP 4 >= 10");
            ten = (int)(temp1/10);
            temp1 = temp1 % 10;

        } if (temp1 >= 5){
            // System.out.println("STEP 5 >= 5");
            five = (int)(temp1/5);
            temp1 = temp1 % 5;

        } if (temp1 >= 2){
            // System.out.println("STEP 6 >= 2: use $2 coin");
            two = (int)(temp1/2);
            temp1 = temp1 % 2;
            // System.out.printf("TWO = [%d], temp = [%.2f]\n", two, temp1);

        } if (temp1 >= 1){
            // System.out.println("STEP 7 >= 1: use $1 coin");
            one = (int)(temp1/1);
            temp1 = temp1 % 1;
            // System.out.printf("ONE = [%d], temp = [%.2f]\n", one, temp1);

        } else {
            // System.out.println("STEP 8 >= 0.5: use 50c coin CENTS CHECK!");
            fifty_c = (int)(temp1/0.5);
            temp1 = temp1 % 0.5;
            // System.out.printf("fifty CENTS = [%d], temp = [%.2f]\n", fifty_c, temp1);
        }

        //STORE THE RESULTS INTO THE HASHMAP!!!

        this.remainderStorageMap.put("hundred", hundred);
        this.remainderStorageMap.put("fifty", fifty);
        this.remainderStorageMap.put("twenty", twenty);
        this.remainderStorageMap.put("ten", ten);
        this.remainderStorageMap.put("five", five);
        this.remainderStorageMap.put("two", two);
        this.remainderStorageMap.put("one", one);
        this.remainderStorageMap.put("fifty_c", fifty_c);

    }

    public void printRemainderStorageMap(){
        for (HashMap.Entry<String, Integer> entry : this.getRemainderStorageMap().entrySet()) {
            if (entry.getValue() > 0){
                //print only those with amount != 0
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

}