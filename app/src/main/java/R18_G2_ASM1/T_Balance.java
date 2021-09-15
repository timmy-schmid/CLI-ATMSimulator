package R18_G2_ASM1;

import java.util.Date;

public class T_Balance extends Transaction {
    private int totalAmountStored;
    private ATM1 attachedATM;

    protected double amount;
    protected TransactionType type;
    protected Account account;

    protected int transactionID;
    protected Date date;

    public T_Balance(ATM1 attachedATM, TransactionType type, Account account, double amount, Date date, int transactionID){
        super(attachedATM, type, account, amount, date, transactionID);
    }

    //prints ALL card details ???
    public void getBalance(Account account){
        if (account != null){
            if (account.getCards() != null){
                //loop through account cards list, printing details for them all
                account.printAllCardBalance();
                System.out.println(TransactionStatus.SUCCESS_BALANCE.toString()); //prints status info in the end
            } else {
                System.out.println("No available cards found on account. Cannot find balance.");
                return;
            }
        } else {
            System.out.println("No valid account found, please create one.");
        }
    }
}