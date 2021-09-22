package R18_G2_ASM1;

import java.lang.Math;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.*;
import java.math.BigDecimal;

/** 
* TO DO: requires storing total amount as (coins + cash) separately from ATM
* The Transaction class processes the instructions a user makes based on
* their input selection to either deposit, withdraw or check their * account's balance. 

* This class interacts with Moneystack and ATM class to retrieve detail and check whether extraction of money is available or not.
* @author Anna Su
* @version 1.0
*/
public class Transaction {
    
    // protected double originalAmount; //delete later if not necessary
    protected double amount;  //represents total amount
    
    /**
     * A user's card
     */
    protected Card card;
    
    /**
     * The type of transaction
     */
    protected TransactionType type;

    private ATM attachedATM;
    private MoneyStack balance;
    protected int transactionID;

    protected ATM_logger atmLogger;

    /**
     * Amount to deposit (cash + coins), split into a map with each amount
     * Loop through this map to call function MoneyStack.addmoney(key, * value)
     */
    protected HashMap <MoneyType, Integer> depositAmountMap;
    protected HashMap <MoneyType, Integer> newMoneyStack;//used for withdrawing money from moneyStack

    /**
     * Constructs a new Transaction object.
     * @param attachedATM an ATM object
     * @param type type of Transaction to proceed
     * @param card A user's card
     * @param transactionID A transaction's ID
     */
    public Transaction(ATM attachedATM, TransactionType type, Card card,  int transactionID){
        
        this.amount = 0; //set initially as just cash amount, requires ATM working first
        this.type = type; //attachedATM.askForTransType();
        this.attachedATM = attachedATM;
        this.card = card;
        this.transactionID = transactionID;
        this.balance = attachedATM.getATMBalance(); //moneyStack object
        this.depositAmountMap = new LinkedHashMap <MoneyType, Integer>(); // preserves order of key, value sequence!
        this.newMoneyStack = new LinkedHashMap <MoneyType, Integer>();
        this.initialSetUpMap();

        this.atmLogger = attachedATM.getATMLogger();
    }

    /**
     * initialSetUpMap
     * Initialises the number of notes in depositAmountMap at the start as 0
     * Initialises the number of notes+coins in newMoneyStack at the start as 0
     */
    public void initialSetUpMap(){ //for notes only!
        this.depositAmountMap.put(MoneyType.HUNDRED_DOLLARS,0);
        this.depositAmountMap.put(MoneyType.FIFTY_DOLLARS, 0);
        this.depositAmountMap.put(MoneyType.TWENTY_DOLLARS, 0);
        this.depositAmountMap.put(MoneyType.TEN_DOLLARS,  0);
        this.depositAmountMap.put(MoneyType.FIVE_DOLLARS, 0);

        this.newMoneyStack.put(MoneyType.HUNDRED_DOLLARS,0);
        this.newMoneyStack.put(MoneyType.FIFTY_DOLLARS, 0);
        this.newMoneyStack.put(MoneyType.TWENTY_DOLLARS, 0);
        this.newMoneyStack.put(MoneyType.TEN_DOLLARS,  0);
        this.newMoneyStack.put(MoneyType.FIVE_DOLLARS, 0);
        this.newMoneyStack.put(MoneyType.TWO_DOLLARS, 0);
        this.newMoneyStack.put(MoneyType.ONE_DOLLAR, 0);
        this.newMoneyStack.put(MoneyType.FIFTY_CENTS, 0);
        this.newMoneyStack.put(MoneyType.TWENTY_CENTS, 0);
        this.newMoneyStack.put(MoneyType.TEN_CENTS, 0);
        this.newMoneyStack.put(MoneyType.FIVE_CENTS, 0);
    }

    /**
     * getDepositAmountMap
     * @return a map storing notes and their amount created by 
     * splitting up user's required amount for deposit
     */
    public HashMap <MoneyType, Integer> getDepositAmountMap(){
        return this.depositAmountMap;
    }

    /**
     * getNewMoneyStack
     * @return a map storing notes and their amount created by 
     * splitting up user's required amount for withdrawal
     */
    public HashMap <MoneyType, Integer> getNewMoneyStack(){
        return this.newMoneyStack;
    }

    /**
     * resetDepositAmountMap
     * Resets the amount of notes stored in the map as 0
     */
    public void resetDepositAmountMap(){
        for (HashMap.Entry <MoneyType, Integer> entry: this.getDepositAmountMap().entrySet()){
        this.depositAmountMap.replace(entry.getKey(), 0);
        }
    }

    /**
     * getTransactionID
     * @return returns the transaction ID
     */
    public int getTransactionID(){ 
        return this.transactionID;
    }
    
    /**
     * getMoneyStackBalance
     * @return returns A MoneyStack object storing amount of coins and cash stored in ATM
     */
    public MoneyStack getMoneyStackBalance(){ 
        return this.balance;
    }
    
    /**
     * getCard
     * @return returns a user's card
     */
    public Card getCard(){
        return this.card;
    }

    /**
     * getType
     * @return retrieves a user's preferred type of transaction
     */
    public TransactionType getType(){
        return this.type;
    }
    
    /**
     * getAmount
     * @return returns a user's total desired amount
     */
    public double getAmount(){
        return this.amount;
    }

    /**
     * setAmount
     * Sets user total amount to a different amount
     * @param coinAmount extra amount to add if the transaction type is 'deposit'
     * if deposit: [cash only]
     * if withdrawal: [cash + coins]
     */
    public void setAmount(double coinAmount){
        this.amount += coinAmount;
    }

    /**
     * printMoneyStack
     * prints amount stored on map for debugging purposes
     * @param map stores amount for each money type
     */
    public void printMoneyStack(HashMap <MoneyType, Integer> map){
        for (HashMap.Entry <MoneyType, Integer> entry : map.entrySet()) {
            System.out.printf("Money type: $%s ------ amount:[%d]\n", entry.getKey(), entry.getValue());
        }
    }

    /**
        add into a hashmap the amount of deposit to split into coins + cash
        like findRemainder()/addMoney() function basically 
        @param amount the amount required to deposit into ATM
        
        handle exception when amount not divisble by 5/10 (must be notes, no coins)
        e.g. amount = 24.5 (not ok) vs 25 (ok)
     */
    public void splitDepositAmountUp(double amount) throws InvalidTypeException{ //doesn't consider max limit tho...
        if (amount%5 != 0) {
            // System.out.println("Must be of notes format.");
            throw new InvalidTypeException("Error: amount should only be notes, no coins accepted.");
        } else {    
            double total = amount; //decreases
            int toStoreAmount = 0; //key amount
            
            //entry = key, map value = amount
            for (HashMap.Entry <MoneyType, Integer> entry : this.getDepositAmountMap().entrySet()) {    
                if (total >= entry.getKey().getValue()) {
                    toStoreAmount = (int)(total/entry.getKey().getValue()); //where amount added is of type MoneyType
                    total = total%entry.getKey().getValue();
                    this.depositAmountMap.put(entry.getKey(), toStoreAmount);
                }
            }
        }
    }

    /**
     * modify
     * Modifies a user's card if valid, adding money if type is 'Deposit'
     * and deducts money if type is 'Withdraw'
     * @param card a user's card
     * @param type type of transaction
     * prints amount remaining on MoneyStack for debugging purposes
     */
    public void modify(Card card, TransactionType type){
        if (card != null){
            if (type == TransactionType.DEPOSIT) {
                card.balance += this.amount;
            
            } else if (type == TransactionType.WITHDRAWAL){
                if (card.getbalance() >= this.amount) { //INCOMPLETE!!!!
                    //add coins amount to this.amout
                    // this.setAmount(this.attachedATM.askForDollarAmount());
                    card.balance -= this.amount;
                } else {
                    System.out.println("Sorry you don't have enough money stored on your card. Cannot proceed to withdraw money.");
                    this.attachedATM.getATMLogger().createLogMessage("transaction.withdrawal", StatusType.ERROR, "card balance is too low, can't withdraw");
                } 
            }
        } else { //card = null
            System.out.println("Sorry your card is unavailable. Please try again.");
            this.atmLogger.createLogMessage("Transaction.modify", StatusType.ERROR, "Sorry your card is unavailable. Please try again.");
        }
    }

    public void deductFromMoneyStack(){
        // for (HashMap.Entry <MoneyType, Integer> entry: this.getDepositAmountMap().entrySet()){
        for (HashMap.Entry <MoneyType, Integer> entry : this.getNewMoneyStack().entrySet()) {
            if (this.getMoneyStackBalance().getMoney().containsKey(entry.getKey()) == true){
                HashMap<MoneyType, Integer> monMap =  this.getMoneyStackBalance().getMoney();
                int difference =monMap.get(entry.getKey())-entry.getValue();
                this.getMoneyStackBalance().getMoney().replace(entry.getKey(), difference);
            }
        }
    }

    public MoneyType findNextMoneyTypeAvailable(Double cashNote, int noteAmount){ //converted double to moneystack - findNextDoubleAvailable
        MoneyType next = null;
        Iterator iterator = this.getMoneyStackBalance().getMoney().keySet().iterator();

        while (iterator.hasNext()){ //loop through keys (notes number)
            MoneyType entry = (MoneyType) iterator.next(); 
            if (Double.compare(entry.getValue(), cashNote) == 0){
                next = (MoneyType)iterator.next();
                break;
            }
        }
        return next;
    }

    public void withdrawNEW(double retrieveAmount){ 
        //calculateStorageAmount
        double total = this.getMoneyStackBalance().totalMoney();
        int toStoreAmount = 0;
        MoneyType next = null;

        for (HashMap.Entry <MoneyType, Integer> entry : this.getMoneyStackBalance().getMoney().entrySet()) {
      
        if (retrieveAmount <= total){ //enough stored in ATM to be withdrawn
            next = entry.getKey();
            if (retrieveAmount >= entry.getKey().getValue()){        
                toStoreAmount = (int)(retrieveAmount/next.getValue());  
                double diff = toStoreAmount - entry.getValue(); //8-2
                if (diff >= 0){ //more tostoreAmount than in moneystack
                    this.newMoneyStack.replace(next, entry.getValue()); 
                    this.getMoneyStackBalance().getMoney().replace(next, 0); //entry.getValue());
                    next = this.findNextMoneyTypeAvailable(entry.getKey().getValue(), entry.getValue()); //$50
                    toStoreAmount = (int)((retrieveAmount-(entry.getValue()*entry.getKey().getValue()))/next.getValue());  

                    this.newMoneyStack.replace(next, toStoreAmount); 
                    this.getMoneyStackBalance().getMoney().replace(next, entry.getValue());
                    
                } else { //exact match ? - deduct straight up amount from this.money?
                    this.newMoneyStack.replace(next, toStoreAmount);
                }
            }
        }
        retrieveAmount = retrieveAmount%next.getValue();
        }
    }
        
    /**
     * run
     * Runs transaction process depending on user's preferred type of transaction
     * @param type type of transaction
     */
    public void run(TransactionType type) {
        if (type == TransactionType.DEPOSIT){
            try {
                this.proceedDepositTransaction(this.getCard());
            } catch (InvalidTypeException e){
                e.printStackTrace();
            }
    
        } else if (type == TransactionType.WITHDRAWAL){
            this.proceedWithdrawalTransaction(this.getCard());
    
        } else if (type == TransactionType.BALANCE) {
            this.getBalanceInfo(this.getCard());
        }
    }

    /**
     * proceedDepositTransaction
     * Add's money onto user's card and adds notes/coins to MoneyStack
     * @param card a user's card
     */
    public void proceedDepositTransaction(Card card) throws InvalidTypeException {
        if (this.getAmount()%5 != 0){ //not of type note!
            throw new InvalidTypeException("Cannot deposit coins, only notes.");
        }
        // retrieve amount for each note type to add to existing money stack
        this.splitDepositAmountUp(this.amount);
        this.printMoneyStack(this.getDepositAmountMap());
        
        //now loop through depositAmountMap, to add moneytype + amount back into moneyStack
        try {
            for (HashMap.Entry <MoneyType, Integer> entry : this.getDepositAmountMap().entrySet()) { //where amount added is of type MoneyType = key, amount = map value
                this.getMoneyStackBalance().addMoney(entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Cannot add money into MoneyStack.");
            return;
        }
        this.modify(card, TransactionType.DEPOSIT);

        //now print receipt
        this.attachedATM.printReceipt(this, this.getMoneyStackBalance());
        this.attachedATM.getATMLogger().createLogMessage("Transaction.deposit", StatusType.INFO, "The Deposit Transaction was successfully completed.");
        this.resetDepositAmountMap();
    }

    /**
     * proceedWithdrawalTransaction
     * Deducts money from user's card and removes cash/coins from MoneyStack
     * Checks if user has enough money stored in card and
     * if there's enough amount stored in moneystack
     * @param card a user's card
     */
    public void proceedWithdrawalTransaction(Card card){
        this.withdrawNEW(this.amount);
        this.deductFromMoneyStack();
        
        // } catch (Exception e){ //frozen money?
        //     System.out.println("Unable to withdraw from ATM due to, unavailable amounts of coins/cash. Sorry for the inconvenience, please try in another ATM or come another day.");  

        //     this.attachedATM.getATMLogger().createLogMessage("transaction.withdrawal", messageType.ERROR, "transaction was unsuccessful!: inadequate amount of money stored in ATM");
        // }
        if (this.getMoneyStackBalance().getstatusOfMoney() == true){
            // this.printMoneyStack(this.getMoneyStackBalance().getMoney());
            this.modify(card, TransactionType.WITHDRAWAL);            
            this.attachedATM.getATMLogger().createLogMessage("transaction.withdrawal", StatusType.INFO, "The Withdrawal Transaction was successfully completed.");
        }
    }

    /**
     * getBalanceInfo
     * Prints a user's card details
     * @param card a user's card
     */
    public void getBalanceInfo(Card card){
        if (card != null){
            card.getCardDetails();
            System.out.println("The balance query was successful.");
            //now print receipt
            this.attachedATM.printReceipt(this, this.getMoneyStackBalance());
            this.attachedATM.getATMLogger().createLogMessage("transaction.getBalanceInfo", StatusType.INFO, "check balance was successful!");

        } else {
            System.out.println("Sorry your card is unavailable. Please try again.");
        }
    }
}