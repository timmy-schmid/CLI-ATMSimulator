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
    protected BigDecimal amount;     
        
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

    protected MoneyStack CASH;
    protected MoneyStack COINS;

    /**
     * Constructs a new Transaction object.
     * @param attachedATM an ATM object
     * @param type type of Transaction to proceed
     * @param card A user's card
     * @param transactionID A transaction's ID
     */
    public Transaction(ATM attachedATM, TransactionType type, Card card,  int transactionID){
        
        this.amount = new BigDecimal(0); //set initially as just cash amount, requires ATM working first
        this.type = type;
        this.attachedATM = attachedATM;
        this.card = card;
        this.transactionID = transactionID;
        this.balance = attachedATM.getATMBalance(); //moneyStack object
        this.depositAmountMap = new LinkedHashMap <MoneyType, Integer>(); // preserves order of key, value sequence!
        this.newMoneyStack = new LinkedHashMap <MoneyType, Integer>();
        this.initialSetUpMap();
        this.atmLogger = attachedATM.getATMLogger();
        this.CASH = null; //attachedATM.askForMoneyStackNotes();
        this.COINS = null; //attachedATM.askForMoneyStackCoins();
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
    }

    /**
     * getDepositAmountMap
     * @return a map storing notes and their amount created by
     * splitting up user's required amount for deposit
     */
    public HashMap <MoneyType, Integer> getDepositAmountMap(){
        return this.depositAmountMap;
    }

    // /**
    //  * getNewMoneyStack
    //  * @return a map storing notes and their amount created by
    //  * splitting up user's required amount for withdrawal
    //  */
    // public HashMap <MoneyType, Integer> getNewMoneyStack(){
    //     return this.newMoneyStack;
    // }

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
     * getTransactionID  ------------ PROBABLY REMOVE THIS!
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
    public BigDecimal getAmount(){
        return this.amount;
    }

    /**
     * setAmount
     * Sets user total amount to a different amount
     * @param coinAmount extra amount to add if the transaction type is 'deposit'
     * if deposit: [cash only]
     * if withdrawal: [cash + coins]
     */
    public void setAmount(BigDecimal coinAmount){
        this.amount  = this.amount.add(coinAmount);
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
     @param amount the amount required to deposit into ATM in notes
     @throws InvalidTypeException If coins have been added.
     handle exception when amount not divisble by 5/10 (must be notes, no coins)
     e.g. amount = 24.5 (not ok) vs 25 (ok)
     */

    // public void splitDepositAmountUp(double amount) throws InvalidTypeException{ 
    public void splitDepositAmountUp(BigDecimal amount) throws InvalidTypeException{ 
        BigDecimal div = new BigDecimal("5");
        BigDecimal[] dr = amount.divideAndRemainder(div);
        // if (amount%5 != 0) { //NEEDS EDIT!!!! what if .50 or .35c?
        if(dr[1].signum() != 0){
            throw new InvalidTypeException("Error: amount should only be notes, no coins accepted.");
        } else {    
            BigDecimal total = amount; //decreases
            int toStoreAmount = 0; //key amount

            //entry = key, map value = amount
            for (HashMap.Entry <MoneyType, Integer> entry : this.getDepositAmountMap().entrySet()) {    
                if (total.compareTo(entry.getKey().getValue()) >= 0) {
                // if (total >= entry.getKey().getValue()) {
                    dr = total.divideAndRemainder(entry.getKey().getValue());
                    // toStoreAmount = (int)(total/entry.getKey().getValue()); //where amount added is of type MoneyType --> quotient
                    // total = total%entry.getKey().getValue();
                    total = dr[1];
                    this.depositAmountMap.put(entry.getKey(), dr[0].intValue());
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
        BigDecimal minCardBalance = new BigDecimal(5.00);

        if (card != null){
            if (type == TransactionType.DEPOSIT) {
                card.balance = card.balance.add(this.amount); // += this.amount;
            
            } else if (type == TransactionType.WITHDRAWAL){
                if (card.getbalance().compareTo(this.amount) >= 0){
                    if (card.getbalance().compareTo(minCardBalance) <= 0){ //when balance is low on card, print on screen/write to log file?
                        this.attachedATM.getATMLogger().createLogMessage("transaction.withdrawal", StatusType.ERROR, "Your card balance is less than $5.00.");
                    }
                    card.balance = card.balance.subtract(this.amount);
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

    /**
     * run
     * Runs transaction process depending on user's preferred type of transaction
     * @param type type of transaction
     */
    public void run(TransactionType type) {

        this.CASH = this.attachedATM.askForMoneyStackNotes();
        this.setAmount(this.CASH.totalMoney()); //add cash to amount required for transaction
        System.out.println("LINE 311 TRANSACTION TYPE: " + type);
        if (type == TransactionType.DEPOSIT){
            try {
                this.proceedDepositTransaction(this.getCard());
            } catch (InvalidTypeException e){
                e.printStackTrace();
            }

        } else if (type == TransactionType.WITHDRAWAL){

            this.COINS = this.attachedATM.askForMoneyStackCoins();
            if (this.COINS == null){
                System.out.println("FAILED TO RETRIEVE COINS AMOUNT FROM MONEYSTACK TO WITHDRAWAL!");
                return;
            }

            this.setAmount(this.COINS.totalMoney()); //add extra coins for withdrawal
            this.proceedWithdrawalTransaction(this.getCard());

        } else if (type == TransactionType.BALANCE) {
            this.getBalanceInfo(this.getCard());
        }
    }

    /**
     * proceedDepositTransaction
     * Add's money onto user's card and adds notes/coins to MoneyStack
     * @param card a user's card
     * @throws InvalidTypeException if coins are deposited.
     */
    public void proceedDepositTransaction(Card card) throws InvalidTypeException {
        BigDecimal div = new BigDecimal("5");
        BigDecimal[] dr = amount.divideAndRemainder(div); //n%5
        // if (amount%5 != 0) { //NEEDS EDIT!!!! what if .50 or .35c?
        if(dr[1].signum() != 0){

        // if (this.getAmount()%5 != 0){ //not of type note!
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
        System.out.printf("LINE357 BEFORE: USER CARD AMOUNT = [%.2f]\n", card.getbalance());
        this.modify(card, TransactionType.DEPOSIT);
        System.out.printf("AFTER: USER CARD AMOUNT = [%.2f]\n", card.getbalance());

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
        // this.printMoneyStack(this.getMoneyStackBalance().getMoney());
        // System.out.println("BEFORE:::::: LINE 360!!!!!!\nAFTER\n");

        boolean result = false;
        result = this.getMoneyStackBalance().withdraw(this.CASH);
        result = this.getMoneyStackBalance().withdraw(this.COINS);

        this.printMoneyStack(this.getMoneyStackBalance().getMoney());
        if (result == false){

            // } catch (Exception e){ //frozen money?
            //     System.out.println("Unable to withdraw from ATM due to, unavailable amounts of coins/cash. Sorry for the inconvenience, please try in another ATM or come another day.");

            this.attachedATM.getATMLogger().createLogMessage("transaction.withdrawal", StatusType.ERROR, "transaction was unsuccessful!: inadequate amount of money stored in ATM");
        }

        if (this.getMoneyStackBalance().getstatusOfMoney() == true){
            System.out.printf("LINE395 BEFORE: USER CARD AMOUNT = [%.2f]\n", card.getbalance());
            this.modify(card, TransactionType.WITHDRAWAL);
            System.out.printf("LINE397 BEFORE: USER CARD AMOUNT = [%.2f]\n", card.getbalance());
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