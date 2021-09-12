package R18_G2_ASM1;

import java.util.*;


import java.util.List;

public class Session {
    private ATM1 attachedATM;
    private int sessionID;
    private SessionStatus currentStatus;
    private Transaction transaction;
    private Card card;
    private int pinAttemptNum;


    public void run(int cardNum){

    }
    public Session(ATM1 ATM, Card card, Transaction transaction){
        this.attachedATM = ATM;
        this.card = card;
        this.transaction = transaction;
    }

    public SessionStatus getStatus(){
        return null;
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

    // public boolean checkPIN(){
    //     if (card.getPin().equals(attachedATM.askForPIN)){
    //         return true;
    //     }
    //     return false;

    // }

}
