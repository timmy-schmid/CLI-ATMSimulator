package R18_G2_ASM1;


import java.util.List;

public class Session {
    private ATM1 attachedATM;
    private int sessionID;
    private SessionStatus currentStatus;
    private Transaction transaction;
    private Card1 card;
    private int pinAttemptNum;

    public void run(int cardNum){

    }

    public SessionStatus getStatus(){
        return null;
    }
    public List<Transaction> getTransactions(){
        return null;
    }

    public StatusCode validateSession(){
        return null;
    }

    public List<Card1> retrieveCardDB(){
        return null;
    }

    public void transact(Account a){

    }

    public void checkPIN(){
        
    }



}
