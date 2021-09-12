package R18_G2_ASM1;

import java.util.List;

public class Session {
    private ATM attachedATM;
    private int sessionID;
    private SessionStatus currentStatus;
    private Transaction transaction;
    private Card card;
    private int pinAttemptNum;

    public Session(ATM atm) {
    }

    public void run(int cardNum){

    }

    public List<Transaction> getTransactions(){
        return null;
    }

    public SessionStatus validateSession(){
        return null;
    }

    public List<Card> retrieveCardDB(){
        return null;
    }

    public void transact(Account a){

    }

    public void checkPIN(){

    }

    public SessionStatus getStatus() {
        return null;
    }
}
