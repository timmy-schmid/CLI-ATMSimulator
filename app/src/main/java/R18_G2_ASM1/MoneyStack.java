package R18_G2_ASM1;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.io.IOException;
import java.math.BigDecimal;


public class MoneyStack{
    private LinkedHashMap<MoneyType, Integer> money; //A: double or integer??
    //according to the comments in miro, may add "status of money"? since some money might be frozen and cannot be withdrawn, or this can be added to the card status
    //true is for normal and false is for frozen
    private boolean statuOfMoney = true;

    // constructor
    //should default to 0
    public MoneyStack(){
        this.money = new LinkedHashMap <MoneyType, Integer>(); //replace hashmap with linkedhashmap
        money.put(MoneyType.HUNDRED_DOLLARS,0);
        money.put(MoneyType.FIFTY_DOLLARS, 0);
        money.put(MoneyType.TWENTY_DOLLARS, 0);
        money.put(MoneyType.TEN_DOLLARS,  0);
        money.put(MoneyType.FIVE_DOLLARS, 0);
        money.put(MoneyType.TWO_DOLLARS, 0);
        money.put(MoneyType.ONE_DOLLAR, 0);
        money.put(MoneyType.FIFTY_CENTS,  0);
        money.put(MoneyType.TWENTY_CENTS, 0);
        money.put(MoneyType.TEN_CENTS, 0);
        money.put(MoneyType.FIVE_CENTS, 0);
    }

    public BigDecimal totalMoney(){
        BigDecimal totalMoney = new BigDecimal(0);
        for(MoneyType T: money.keySet()){
            totalMoney  = totalMoney.add(T.getValue().multiply(new BigDecimal(money.get(T))));
        }
        return totalMoney;
    }

    public HashMap <MoneyType, Integer> getMoney(){
        return this.money;
    }

    //remove static? Change to invalidtype exception? or just leave as IOexception?
    // delete the exception since we have all kind of money in ATM
    public void addMoney(MoneyType m, int amount) throws IOException {
        int originalValue = this.getMoney().get(m);
        this.getMoney().replace(m,originalValue+amount);
    }


    //consider what happens if you deduct over the original amount stored per note.. [amount becomes negative unless you set restrictions...]
    // cuz the quotient round down so deduct number is always small than the withdraw ,where the negative wouldn't show

    public boolean withdraw(MoneyStack c){ //needs edit i think....
        // double needWithdraw = c.totalMoney();
        BigDecimal needWithdraw = c.totalMoney();
        if (this.canWithdraw(c) == false){
            // System.out.println("CANT WITHDRAW!!!!! LINE 63 :(((((");
            return false;
        } else{
            BigDecimal[] dr;

            for(MoneyType key: this.getMoney().keySet()){
                dr = needWithdraw.divideAndRemainder(key.getValue());
                // int quotient = (int)(needWithdraw/key.getValue()); //casting??
                int quotient = dr[0].intValue();
                // double remainder  = (needWithdraw -key.getValue()*quotient);
                BigDecimal remainder  = (needWithdraw.subtract(key.getValue().multiply(new BigDecimal(quotient))));
                if(quotient >= money.get(key)){
                    // needWithdraw -= money.get(key)*key.getValue();
                    needWithdraw = needWithdraw.subtract(new BigDecimal(money.get(key)).multiply(key.getValue()));
                    money.replace(key,0);
                }else{
                    int originalAmount = money.get(key);
                    needWithdraw = remainder;
                    money.replace(key,(int)originalAmount-quotient);
                    // System.out.printf("REPLACEMENT LINE 74: MONEYSTACK [%.2f]---------- [%d]\n",  key.getValue(), (int)originalAmount-quotient);
                }
            }
        }
        return true;
    }

    public int query(MoneyType c) throws IOException{
        if (money.containsKey(c) == false){
            throw new IOException("Error: money type doesn't exist");
        } else{
            return this.getMoney().get(c);
        }
    }

    public boolean canWithdraw(MoneyStack c){
        BigDecimal needWithdraw = c.totalMoney();
        if (needWithdraw.compareTo(this.totalMoney()) > 0){
            return false;
        } else{
            BigDecimal[] dr;
            for(MoneyType key: this.getMoney().keySet()){
                dr = needWithdraw.divideAndRemainder(key.getValue());
                int quotient =  dr[0].intValue(); //(int)(needWithdraw/key.getValue());
                BigDecimal remainder  = (needWithdraw.subtract(key.getValue().multiply(new BigDecimal(quotient))));
                if(quotient > money.get(key)){
                    needWithdraw = needWithdraw.subtract(new BigDecimal(money.get(key)).multiply(key.getValue()));

                }else{
                    needWithdraw = remainder ;
                }
            }
            if (needWithdraw.compareTo(BigDecimal.ZERO) == 0){
                return true;
            } else { return false; }
        }
    }

    //since we got new attribute, a new method to get
    public void activateCash(){
        statuOfMoney = true;
    }
    public void freezeMoney(){
        statuOfMoney = false;
    }

    public boolean getstatusOfMoney(){
        return this.statuOfMoney;
    }

    //probably need to create new functions for this (ATM cash dispenser)??
    /*
        addMoneyStack(MoneyStack m) --> add to current moneystack
        balance.withdraw(MoneyStack m) --> for ejectMoney
    */
    public void addMoneyStack(MoneyStack m){
        if (m == null){
            // Anna - I.e. if when running addCash() in ATM, the admin presses 'Cancel', then this moneystack will be null!!
            // make sense
            return;
        }
        for(Map.Entry<MoneyType, Integer> ATMEntry: this.getMoney().entrySet()){
            for(Map.Entry<MoneyType, Integer> Addentry: m.getMoney().entrySet()){
                if(ATMEntry.getKey().equals(Addentry.getKey())){
                    this.getMoney().replace(ATMEntry.getKey(),ATMEntry.getValue()+Addentry.getValue());
                }
            }
        }
    }

    public boolean withdrawStack(MoneyStack m){
        for(Map.Entry<MoneyType, Integer> ATMEntry: this.getMoney().entrySet()){
            for(Map.Entry<MoneyType, Integer> Addentry: m.getMoney().entrySet()){
                if(ATMEntry.getKey().equals(Addentry.getKey())){
                    if (ATMEntry.getValue()<Addentry.getValue()){return false;}
                    else {this.getMoney().replace(ATMEntry.getKey(),ATMEntry.getValue()-Addentry.getValue());}
                }
            }
        }
        return true;
    }
}