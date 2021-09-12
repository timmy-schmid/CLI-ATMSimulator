<<<<<<< HEAD
package R18_G2_ASM1;
=======
// package R18_G2_ASM1;
import java.util.Date;
>>>>>>> 47f870d89e34b6f53b80954a49dae355d7e8c84d

public class T_Balance extends Transaction {
    // private Card1 card;
    private int totalAmountStored;
    protected double deductAmount;

    protected TransactionType type;
    private ATM1 attachedATM;
    protected Account account;

    protected int transactionID;
    protected Date date;


    public T_Balance(ATM1 attachedATM, TransactionType type, Account account, double deductAmount, Date date, int transactionID){
        super(attachedATM, type, account, deductAmount, date, transactionID);
    }

    //prints ALL card details ???
    public void getBalance(Account account){
        if (account != null){
            if (account.getCardsList() != null){
                //loop through account cards list, printing details for them all
                account.printAllCardBalance();
            } else {
                System.out.println("No available cards found on account. Cannot find balance.");
                return;
            }
        } else {
            System.out.println("No valid account found, please create one.");
        }
    }
}