// package R18_G2_ASM1;

import java.util.Date;
import java.lang.Math;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.io.IOException;

/*

The Transaction class processes the instructions a user makes based on their input selection to either deposit, withdraw or check their account's balance. 

This class interacts with Moneystack class and ATM to retrieve details and check whether extraction of money is available or not.

*/

public class Transaction {
    /**
     * Date to log session/transaction times --> REMOVE LATER IF UNECESSARY?
     */
    protected Date date;
    
    protected double resetAmount; //delete later if not necessary
    protected double originalAmount; //delete later if not necessary
    protected double amount;  //represents total amount
    
    /**
     * A user's account
     */
    protected Account account;
    
    /**
     * The type of transaction
     */
    protected TransactionType type;

    private ATM attachedATM;
    private MoneyStack balance;
    protected int transactionID;


    /**
     * Amount to deposit (cash + coins), split into a map with each amount
     * Loop through this map to call function MoneyStack.addmoney(key, * value)
     */
    protected HashMap <MoneyType, Integer> depositAmountMap;

    // instead of having TransactionType type, use ATM's askForTransType();

    /**
     * Constructs a new Transaction object.
     * @param attachedATM an ATM object
     * @param type type of Transaction to proceed
     * @param account A user's account
     * @param transactionID A transaction's ID
     */
    public Transaction(ATM attachedATM, TransactionType type, Account account,  int transactionID){
        
        this.amount = amount; //set initially as just cash amount
        this.originalAmount = amount; //initial 
        this.type = type; //attachedATM.askForTransType();
        this.attachedATM = attachedATM;
        this.account = account;
        this.transactionID = transactionID;
        this.balance = attachedATM.getATMBalance();
        this.depositAmountMap = new LinkedHashMap<MoneyType, Integer>(); // preserves order of key, value sequence!
    }

    public void initialSetUpMap(){ //notes only!
        //initialise the values inside (amount = 0), key = MoneyType
        this.depositAmountMap.put(MoneyType.HUNDRED_DOLLARS,0);
        this.depositAmountMap.put(MoneyType.FIFTY_DOLLARS, 0);
        this.depositAmountMap.put(MoneyType.TWENTY_DOLLARS, 0);
        this.depositAmountMap.put(MoneyType.TEN_DOLLARS,  0);
        this.depositAmountMap.put(MoneyType.FIVE_DOLLARS, 0);
    }

    public HashMap <MoneyType, Integer> getDepositAmountMap(){
        return this.depositAmountMap;
    }

    public void resetDepositAmountMap(){
        for (HashMap.Entry <MoneyType, Integer> entry: this.getDepositAmountMap().entrySet()){
        this.depositAmountMap.replace(entry.getKey(), 0);
        }
    }

    /**
     * getID
     * @return returns a user account's ID
     */
    public int getID(){ 
        return this.account.getAccountID();
    }
    
    // returns ATM balance: MoneyStack

    /**
     * getMoneyStackBalance
     * @return returns A MoneyStack object storing amount of coins and cash stored in ATM
     * getBalance()
     */
    public MoneyStack getMoneyStackBalance(){ 
        return this.balance;
    }
    
    /**
     * getAccount
     * @return returns a user's account
     */
    public Account getAccount(){
        return this.account;
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
     * @param coin_amount extra amount to add if the transaction type is 'deposit'
     * @return total amount
     * if deposit: [cash only]
     * if withdrawal: [cash + coins]
     */
    public void setAmount(double coin_amount){
        this.amount += coin_amount;
    }

    // public void resetAmount(){
    //     this.amount = this.resetAmount; //if user changes their mind - cancel?
    // }

    /**
     * printMoneyStack
     * prints amount remaining on MoneyStack for debugging purposes
     * Accesses moneyStack object's getMoney() method to get the hashmap<MoneyType, * Integer>!
     */
    public void printMoneyStack(){
        for (HashMap.Entry <MoneyType, Integer> entry : this.getMoneyStackBalance().getMoney().entrySet()) {
            if (entry.getValue() > 0){ //print only those with amount != 0
                System.out.printf("Money type: $%s ------ amount:[%d]\n", entry.getKey(), entry.getValue());
            }
        }
    }

    //debugging purposes --> prints deposit map amount split up
    public void printDepositMap(){
        for (HashMap.Entry <MoneyType, Integer> entry : this.getDepositAmountMap().entrySet()) {
            if (entry.getValue() > 0){ //print only those with amount != 0
                System.out.printf("Deposit Money type: $%s ------ amount:[%d]\n", entry.getKey(), entry.getValue());
            }
        }
    }

    /**
        add into a hashmap the amount of deposit to split into coins + cash
        like findRemainder()/addMoney() function basically 
        @param amount the amount required to deposit into ATM
        
        handle exception when amount not divisble by 5/10 (must be notes, no coins)
        e.g. amount = 24.5 (not ok) vs 25 (ok)
     */
    public void splitDepositAmountUp(double amount) {
        if (amount%5 != 0) {
            System.out.println("Must be of notes format.");
            throw new IllegalArgumentException("Error: amount should only be notes, no coins accepted.");
        } else {    
            double total = amount; //decreases
            int toStoreAmount = 0; //key amount
            
            int temp = 0;
            //entry = key, map value = amount
            for (HashMap.Entry <MoneyType, Integer> entry : this.getDepositAmountMap().entrySet()) {    
                // System.out.printf("total = [%.2f], entry.getKey().getValue() = [%.2f]\n", total, entry.getKey().getValue());
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
     * Modifies a user's account if valid, adding money if type is 'Deposit'
     * and deducts money if type is 'Withdraw'
     * @param account a user's account
     * @param type type of transaction
     * prints amount remaining on MoneyStack for debugging purposes
     */
    public void modify(Account account, TransactionType type){
        if (account != null) {
            if (type == TransactionType.DEPOSIT) {
                account.deposit(this.amount);
                //modify card amount instead ??
            }
           
            else if (type == TransactionType.WITHDRAWAL) {
                //add coins amount to this.amout
                // this.setAmount(this.attachedATM.askForDollarAmount());
                account.withdraw(this.amount); // deduct money from acc
            }
        } else { //probs not necessary since you first validate card before doing this...
            System.out.println("Sorry your account is unavailable. Please try again.");
        }
    }

    /**
     * run
     * Runs transaction process depending on user's preferred type of transaction
     * @param type type of transaction
     */
    public void run(TransactionType type){ //call in App class (where each button associates with its subclass (transaction, deposit or balance check?))
        if (type == TransactionType.DEPOSIT){
            this.proceedDepositTransaction(this.getAccount());
       
        } else if (type == TransactionType.WITHDRAWAL){
            this.proceedWithdrawalTransaction(this.getAccount());
       
        } else if (type == TransactionType.BALANCE) {
            this.getBalanceInfo(this.getAccount());
        
        } else {
            System.out.println("Invalid transaction type!");
        }
    }

    /**
     * proceedDepositTransaction
     * Add's money onto user's account and adds notes/coins to MoneyStack
     * @param account a user's account
     */
    public void proceedDepositTransaction(Account account){
        //adding amount you want to store into ur account... (increase cash/coin in moneyStack)
        //where this.amount has to be converted into (MONEYTYPE) ??
        
        //now loop through depositAmountMap, 
        try {
            for (HashMap.Entry <MoneyType, Integer> entry : this.getDepositAmountMap().entrySet()) {    //where amount added is of type MoneyType = key, amount = map value
                this.getMoneyStackBalance().addMoney(entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Cannot add money into MoneyStack.");
            return;
        }

        
        // this.getBalance().addMoney(this.amount, this.amount);
        this.modify(account, TransactionType.DEPOSIT);
        //now print receipt
        this.attachedATM.printReceipt(this, this.getMoneyStackBalance());
    }


    /**
     * proceedWithdrawalTransaction
     * Deducts money from user's account and removes cash/coins from MoneyStack
     * Checks if user has enough money stored in account and
     * if there's enough amount stored in moneystack
     * @param account a user's account
     */
    public void proceedWithdrawalTransaction(Account account){
        if (account != null) {
            if (account.getBalance() >= this.amount && this.getMoneyStackBalance().getstatusOfMoney() == true){
                this.getMoneyStackBalance().canWithdraw(this.getMoneyStackBalance());
                this.getMoneyStackBalance().withdraw(this.getMoneyStackBalance()); //decrease amount in moneystack
                this.modify(account, TransactionType.WITHDRAWAL);
                //now print receipt
                this.attachedATM.printReceipt(this, this.getMoneyStackBalance());
            
             //frozen money!
            } else if (this.getMoneyStackBalance().getstatusOfMoney() == false){
                // System.out.println("Sorry not enough money stored in ATM, cannot withdraw desired amount.");
                System.out.println("Unable to withdraw from ATM due to, unavailable amounts of coins/cash. Sorry for the inconvenience, please try in another ATM or come another day.");
            }
            
            else {
                System.out.println("Sorry you don't have enough money stored on your account. Cannot proceed to withdraw money");
            } 

        } else { //account = null
            System.out.println("Sorry your account is unavailable. Please try again.");
        }
    }

    /**
     * getBalanceInfo
     * Prints a user's account details
     * @param account a user's account
     */
    public void getBalanceInfo(Account account){
        if (account != null){
            System.out.println("Account balance = " + account.getBalance());
            System.out.println("The balance query was successful");
            //now print receipt
            this.attachedATM.printReceipt(this, this.balance);
        } else {
            System.out.println("Sorry your account is unavailable. Please try again.");
        }
    }  //consider having multiple cards/acc later..
}