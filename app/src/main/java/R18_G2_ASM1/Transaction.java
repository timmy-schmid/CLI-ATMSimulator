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
 * The Transaction class processes the instructions a user makes based on
 * their input selection to either deposit, withdraw or check their account's balance.
 * This class interacts with Moneystack and ATM class to retrieve detail and check whether extraction of money is available or not.
 * @author Anna Su
 * @version 1.0
 */
public class Transaction {
    
    /**
     * A user's desired amount
     */
    protected BigDecimal amount;
        
    /**
     * A user's card
     */
    protected Card card;

    /**
     * The type of transaction
     */
    protected TransactionType type;

    /**
     * An ATM object to access moneyStack
     */
    private ATM attachedATM;

    /**
     * A MoneyStack object
     */
    private MoneyStack balance;

    /**
     * An atmLogger object for writing log messages to a log file
     */
    protected ATM_logger atmLogger;

    /**
     * Amount to deposit (notes only), split into a map with moneyType as key and the * amount as value
     */
    protected HashMap <MoneyType, Integer> depositAmountMap;

    /**
     * A moneyStack object storing notes a user wants
     */
    protected MoneyStack NOTES;

    /**
     * A moneyStack object storing coins a user wants
     */
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
        this.depositAmountMap = new LinkedHashMap <MoneyType, Integer>(); // preserves order of key, value sequence
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
     * Sets user's total amount to a different amount
     * @param extraAmount extra amount to add to existing amount that must be greater than or 0
     *
     */
    public void setAmount(BigDecimal extraAmount){
        if (extraAmount.compareTo(new BigDecimal(0)) >= 0) {
            this.amount  = this.amount.add(extraAmount);
        }
    }

    /**
     * checkIfDepositAmountMapChanged
     * Loops through to check if amount changed
     * @return whether the amount in map changed or not
     */
    public boolean checkIfDepositAmountMapChanged(){
        boolean changed = false; //remains 0 for each moneyType amount
        int i = 0;
        for (HashMap.Entry <MoneyType, Integer> entry : this.getDepositAmountMap().entrySet()) {
            if (entry.getValue() == 0) {
                i+=1;
            }
        }

        if (i != 5){ //there are 5 types of notes possible
            changed = true; 
        }
        return changed;
    }

    /**
     * splitDepositAmountUp
     * add into a hashmap the amount of deposit to split
     * @param amount the amount required to deposit into ATM in notes
     * Checks to ensure only notes are accepted when proceeding with deposit transaction
     */
    public void splitDepositAmountUp(BigDecimal amount) { 
        BigDecimal div = new BigDecimal("5");
        BigDecimal[] dr = amount.divideAndRemainder(div);
        if(dr[1].signum() != 0){
            System.out.println("Error: amount should only be notes, no coins accepted");
            return;
        } else {
            BigDecimal total = amount;
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
     * A warning is written to file and displayed if the card balance is low
     * @param card a user's card
     * @param type type of transaction
     * @return whether the modification was successful or not
     */
    public String modify(Card card, TransactionType type){
        BigDecimal minCardBalance = new BigDecimal(5.00);

        if (card != null){
            if (type == TransactionType.DEPOSIT) {
                card.balance = card.balance.add(this.amount);
                return "SUCCESS";

            } else if (type == TransactionType.WITHDRAWAL){
                if (card.getBalance().compareTo(this.amount) >= 0){
                    // when balance is low on card, print on screen/write to log file?
                    if (card.getBalance().compareTo(minCardBalance) <= 0){ 
                        System.out.println("Warning, your card balance is less than $5.00");
                        this.attachedATM.getATMLogger().createLogMessage("transaction.withdrawal", StatusType.WARNING, "Your card balance is less than $5.00.");
                    }
                    card.balance = card.balance.subtract(this.amount);
                    return "SUCCESS";
                
                } else {
                    System.out.println("Sorry you don't have enough money stored on your card. Cannot proceed to withdraw money.");
                    this.attachedATM.getATMLogger().createLogMessage("transaction.withdrawal", StatusType.ERROR, "card balance is too low, can't withdraw");
                    return "FAILED";
                }
            }
        } 
        // card = null
        this.atmLogger.createLogMessage("Transaction.modify", StatusType.ERROR, "Sorry card is unavailable. Please try again.");
        return "Sorry your card is unavailable. Please try again.";
    }

    /**
     * run
     * Runs transaction process depending on user's preferred type of transaction
     * @param type type of transaction
     * @return whether the transaction was successful or not and if it wasn't, a specific message saying what was wrong
     */
    public String run(TransactionType type) {
        String returnMessage = null;
        if (type != TransactionType.BALANCE) {
            this.NOTES = this.attachedATM.askForMoneyStackNotes(type);
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
                    return "Transaction cancelled"; //User cancels transaction
                }

                this.setAmount(this.COINS.totalMoney()); //add extra coins for withdrawal if NOTES object isn't null
                returnMessage = this.proceedWithdrawalTransaction(this.getCard());
                return returnMessage;
            }
        } 
        returnMessage = this.getBalanceInfo(this.getCard());
        return returnMessage;
    }

    /**
     * proceedDepositTransaction
     * Add's money onto user's card and adds notes to MoneyStack
     * A receipt is printed and card details are shown after every successful deposit transaction
     * The status of the transaction is written to a log file
     * @param card a user's card
     * @return the status of the transaction
     */
    public String proceedDepositTransaction(Card card) {
        BigDecimal div = new BigDecimal("5");
        BigDecimal[] dr = amount.divideAndRemainder(div);
        if (dr[1].signum() != 0){
            return "Cannot deposit coins, only notes. Deposit Unsuccessful";
        }
        // retrieve amount for each note type to add to existing money stack
        this.splitDepositAmountUp(this.amount);
        boolean changed = this.checkIfDepositAmountMapChanged();
        //if no change to depositAmountMap, it means that the amoun to split up was not of type not, and therefore should remain as 0 for each money type.
        if (changed == false){
            return "Deposit Unsuccessful";
        }

        //now loop through depositAmountMap, to add moneytype + amount back into moneyStack for ATM
        try {
            for (HashMap.Entry <MoneyType, Integer> entry : this.getDepositAmountMap().entrySet()) {
                this.getMoneyStackBalance().addMoney(entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Cannot add money into MoneyStack.");
            return "Deposit Unsuccessful";
        }
        this.modify(card, TransactionType.DEPOSIT);
         //now print card details + receipt
        card.getCardDetails();
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
     * A receipt is printed and card details are shown after every successful withdrawal transaction
     * @param card a user's card
     * @return the status of the transaction
     */
    public String proceedWithdrawalTransaction(Card card){
        boolean result = false;
        result = this.getMoneyStackBalance().withdraw(this.NOTES);
        result = this.getMoneyStackBalance().withdraw(this.COINS);

        if (result == false){
            this.attachedATM.getATMLogger().createLogMessage("transaction.withdrawal", StatusType.ERROR, "transaction was unsuccessful!: inadequate amount of money stored in ATM");
            return "insufficient cash available in ATM";
        }

        if (this.getMoneyStackBalance().getstatusOfMoney() == true) {
            String status = this.modify(card, TransactionType.WITHDRAWAL);
            if (status != "SUCCESS"){
                return "Withdraw unsuccessful";
            }
            this.attachedATM.getATMLogger().createLogMessage("transaction.withdrawal", StatusType.INFO, "The Withdrawal Transaction was successfully completed.");

            card.getCardDetails();
            this.attachedATM.printReceipt(this, this.getMoneyStackBalance());
            return "Withdraw successful";
        }
        return null;
    }

    /**
     * getBalanceInfo
     * Prints a user's card details
     * @param card a user's card
     * @return the status of the transaction
     */
    public String getBalanceInfo(Card card){
        if (card != null) {
            card.getCardDetails();
            System.out.println("The balance query was successful.");
            this.attachedATM.getATMLogger().createLogMessage("transaction.getBalanceInfo", StatusType.INFO, "check balance was successful!");
            return "Balance successful";

        } else {
            System.out.println("Sorry your card is unavailable. Please try again.");
            return "Balance unsuccessful";
        }
    }
}