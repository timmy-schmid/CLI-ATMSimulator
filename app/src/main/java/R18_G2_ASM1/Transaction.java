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
 * TO DO: requires storing total amount as (coins + notes) separately from ATM
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

    protected ATM_logger atmLogger;

    /**
     * Amount to deposit (notes + coins), split into a map with each amount
     * Loop through this map to call function MoneyStack.addmoney(key, * value)
     */
    protected HashMap <MoneyType, Integer> depositAmountMap;

    protected MoneyStack NOTES;
    protected MoneyStack COINS;

    /**
     * Constructs a new Transaction object.
     * @param attachedATM an ATM object
     * @param type type of Transaction to proceed
     * @param card A user's card
     */
    public Transaction(ATM attachedATM, TransactionType type, Card card){
        
        this.amount = new BigDecimal(0);
        this.type = type;
        this.attachedATM = attachedATM;
        this.card = card;
        this.balance = attachedATM.getATMBalance(); //moneyStack object
        this.depositAmountMap = new LinkedHashMap <MoneyType, Integer>(); // preserves order of key, value sequence!
        this.initialSetUpMap();
        this.atmLogger = attachedATM.getATMLogger();
        this.NOTES = null;
        this.COINS = null;
    }

    /**
     * initialSetUpMap
     * Initialises the number of notes in depositAmountMap at the start as 0
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
     * getMoneyStackBalance
     * @return returns A MoneyStack object storing amount of coins and notes stored in ATM
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
     * @param extraAmount extra amount to add to existing amount
     * if deposit: [notes only]
     * if withdrawal: [notes + coins]
     */
    public void setAmount(BigDecimal extraAmount){
        this.amount  = this.amount.add(extraAmount);
    }

    // /**
    //  * printMoneyStack
    //  * prints amount stored on map for debugging purposes
    //  * @param map stores amount for each money type
    //  */
    // public void printMoneyStack(HashMap <MoneyType, Integer> map){
    //     for (HashMap.Entry <MoneyType, Integer> entry : map.entrySet()) {
    //         System.out.printf("Money type: $%s ------ amount:[%d]\n", entry.getKey(), entry.getValue());
    //     }
    // }

    public boolean checkIfDepositAmountMapChanged(){
        boolean changed = false; //remained 0 for each moneyType maount
        int i = 0;
        for (HashMap.Entry <MoneyType, Integer> entry : this.getDepositAmountMap().entrySet()) {
            if (entry.getValue() == 0){
                i+=1;
            }
        }

        if (i != 5){ //there are 5 types of notes possible
            changed = true; 
        }
        return changed;
    }

    /**
     add into a hashmap the amount of deposit to split into coins + notes
     @param amount the amount required to deposit into ATM in notes
     handle exception when amount not divisble by 5/10 (must be notes, no coins)
     */

    public void splitDepositAmountUp(BigDecimal amount) { 
        BigDecimal div = new BigDecimal("5");
        BigDecimal[] dr = amount.divideAndRemainder(div);
        if(dr[1].signum() != 0){
            // throw new InvalidTypeException("Error: amount should only be notes, no coins accepted.");
            System.out.println("Error: amount should only be notes, no coins accepted");
            return;
        } else {    
            BigDecimal total = amount; //decreases
            int toStoreAmount = 0; //key amount

            //entry = key, map value = amount
            for (HashMap.Entry <MoneyType, Integer> entry : this.getDepositAmountMap().entrySet()) {    
                if (total.compareTo(entry.getKey().getValue()) >= 0) {
                    dr = total.divideAndRemainder(entry.getKey().getValue());
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
     */
    public void modify(Card card, TransactionType type){
        BigDecimal minCardBalance = new BigDecimal(5.00);

        if (card != null){
            if (type == TransactionType.DEPOSIT) {
                card.balance = card.balance.add(this.amount);
            
            } else if (type == TransactionType.WITHDRAWAL){
                if (card.getBalance().compareTo(this.amount) >= 0){
                    if (card.getBalance().compareTo(minCardBalance) <= 0){ //when balance is low on card, print on screen/write to log file?
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
     * @return whether the transaction was successful or not and if it wasn't, a specific message saying what was wrong
     */
    public String run(TransactionType type) {
        String returnMessage = null;
        
        this.NOTES = this.attachedATM.askForMoneyStackNotes();
        if (this.NOTES == null){
            return "Transaction cancelled";
        }
        this.setAmount(this.NOTES.totalMoney()); //add notes to amount required for transaction if NOTES object isn't null

        if (type == TransactionType.DEPOSIT){
            returnMessage = this.proceedDepositTransaction(this.getCard());
            return returnMessage;
        
        } else if (type == TransactionType.WITHDRAWAL){
            this.COINS = this.attachedATM.askForMoneyStackCoins(type);
            if (this.COINS == null){
                return "Transaction cancelled"; //FAILED TO RETRIEVE COINS AMOUNT FROM MONEYSTACK TO WITHDRAWAL
            }

            this.setAmount(this.COINS.totalMoney()); //add extra coins for withdrawal if NOTES object isn't null
            returnMessage = this.proceedWithdrawalTransaction(this.getCard());
            return returnMessage;

        } else if (type == TransactionType.BALANCE) {
            returnMessage = this.getBalanceInfo(this.getCard());
            return returnMessage;
        }
        return returnMessage; //invalid type of transaction?
    }

    /**
     * proceedDepositTransaction
     * Add's money onto user's card and adds notes/coins to MoneyStack
     * @param card a user's card
     * @return the status of the transaction
     */
    public String proceedDepositTransaction(Card card) {
        BigDecimal div = new BigDecimal("5");
        BigDecimal[] dr = amount.divideAndRemainder(div);
        if(dr[1].signum() != 0){
            // throw new InvalidTypeException("Cannot deposit coins, only notes.");
            return "Cannot deposit coins, only notes. Deposit Unsuccessful";
        }
        // retrieve amount for each note type to add to existing money stack
        this.splitDepositAmountUp(this.amount);
        boolean changed = this.checkIfDepositAmountMapChanged();
        //if no change to depositAmountMap, it means that the amoun to split up was not of type not, and therefore should remain as 0 for each money type.
        if (changed == false){
            return "Deposit Unsuccessful";
        }

        //now loop through depositAmountMap, to add moneytype + amount back into moneyStack
        try {
            for (HashMap.Entry <MoneyType, Integer> entry : this.getDepositAmountMap().entrySet()) {
                this.getMoneyStackBalance().addMoney(entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Cannot add money into MoneyStack.");
            return "Deposit Unsuccessful";
        }
        this.modify(card, TransactionType.DEPOSIT);

        //now print receipt
        this.attachedATM.printReceipt(this, this.getMoneyStackBalance());
        this.attachedATM.getATMLogger().createLogMessage("Transaction.deposit", StatusType.INFO, "The Deposit Transaction was successfully completed.");
        this.resetDepositAmountMap();
        return "Deposit successful";
    }

    /**
     * proceedWithdrawalTransaction
     * Deducts money from user's card and removes notes/coins from MoneyStack
     * Checks if user has enough money stored in card and
     * if there's enough amount stored in moneystack
     * @param card a user's card
     * @return the status of the transaction
     */
    public String proceedWithdrawalTransaction(Card card){
        boolean result = false;
        result = this.getMoneyStackBalance().withdraw(this.NOTES);
        result = this.getMoneyStackBalance().withdraw(this.COINS);

        // this.printMoneyStack(this.getMoneyStackBalance().getMoney());
        if (result == false){
            this.attachedATM.getATMLogger().createLogMessage("transaction.withdrawal", StatusType.ERROR, "transaction was unsuccessful!: inadequate amount of money stored in ATM");
            return "insufficient cash available in ATM";
        }

        if (this.getMoneyStackBalance().getstatusOfMoney() == true){
            this.modify(card, TransactionType.WITHDRAWAL);
            this.attachedATM.getATMLogger().createLogMessage("transaction.withdrawal", StatusType.INFO, "The Withdrawal Transaction was successfully completed.");
            
            return "Withdraw successful";
        }
        return null;
    }

    /**
     * getBalanceInfo
     * Prints a user's card details and a receipt
     * @param card a user's card
     * @return the status of the transaction
     */
    public String getBalanceInfo(Card card){
        if (card != null){
            card.getCardDetails();
            System.out.println("The balance query was successful.");
            //now print receipt
            this.attachedATM.printReceipt(this, this.getMoneyStackBalance());
            this.attachedATM.getATMLogger().createLogMessage("transaction.getBalanceInfo", StatusType.INFO, "check balance was successful!");
            return "Balance successful";

        } else {
            System.out.println("Sorry your card is unavailable. Please try again.");
            return "Balance unsuccessful";
        }
    }
}