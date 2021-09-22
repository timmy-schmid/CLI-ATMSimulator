package R18_G2_ASM1;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.io.IOException;
import java.math.BigDecimal;


public class MoneyStack{
    private HashMap<MoneyType, Integer> money; //A: double or integer??
    //according to the comments in miro, may add "status of money"? since some money might be frozen and cannot be withdrawn, or this can be added to the card status
    //true is for normal and false is for frozen
    private boolean statuOfMoney = true;

    /* constructor
    //seems we don't have parameters to pass
    public MoneyStack(){
        this.money = new LinkedHashMap <MoneyType, Integer>(); //replace hashmap with linkedhashmap
        money.put(MoneyType.HUNDRED_DOLLARS,50); //5000
        money.put(MoneyType.FIFTY_DOLLARS, 50); //2500
        money.put(MoneyType.TWENTY_DOLLARS, 50);
        money.put(MoneyType.TEN_DOLLARS,  50);
        money.put(MoneyType.FIVE_DOLLARS, 100);
        money.put(MoneyType.TWO_DOLLARS, 100);
        money.put(MoneyType.ONE_DOLLAR, 100);
        money.put(MoneyType.FIFTY_CENTS,  100);
        money.put(MoneyType.TWENTY_CENTS, 100);
        money.put(MoneyType.TEN_CENTS, 100);
        money.put(MoneyType.FIVE_CENTS, 100);
    }*/

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

    public double totalMoney() { //or double return type?? //Tim - should return double.
        double totalMoney = 0;
        for(MoneyType T: money.keySet()){
            totalMoney += T.getValue()*money.get(T);
        }
        return totalMoney;
    }

    public HashMap <MoneyType, Integer> getMoney(){
        return this.money;
    }

    //remove static? Change to invalidtype exception? or just leave as IOexception?
    public void addMoney(MoneyType m, int amount) throws IOException{
       if(this.getMoney().containsKey(m) == false){
           System.out.println("Error: money type doesn't exist");
           throw new IOException("Error: money type doesn't exist");
       }else{
           int originalValue = this.getMoney().get(m);
           this.getMoney().replace(m,originalValue+amount);
       }
    }

    //consider what happens if you deduct over the original amount stored per note.. [amount becomes negative unless you set restrictions...]

    public boolean withdraw(MoneyStack c){ //needs edit i think....
        double needWithdraw = c.totalMoney();
        if (this.canWithdraw(c) == false){
            return false;
        } else{
            for(MoneyType key: this.getMoney().keySet()){
                int quotient = (int)(needWithdraw/key.getValue()); //casting??
                int remainder  = (int)(needWithdraw%key.getValue());
                if(quotient > money.get(key)){
                    needWithdraw -= money.get(key)*key.getValue();
                    money.replace(key,0);
                }else{
                    int originalAmount = money.get(key);
                    needWithdraw = remainder;
                    money.replace(key,originalAmount-quotient);
                }
            }
            return (needWithdraw == 0);
        }
    }

    public int query(MoneyType c) throws IOException{
        if (money.containsKey(c) == false){
            throw new IOException("Error: money type doesn't exist");
        } else{
            return this.getMoney().get(c);
        }
    }

    public boolean canWithdraw(MoneyStack c){
        double needWithdraw = c.totalMoney(); //Tim - changed to double.
        if (needWithdraw > this.totalMoney()){ //change this.money --> this?
            return false;
        } else{
            for(MoneyType key: this.getMoney().keySet()){
                int quotient =  (int)(needWithdraw/key.getValue());
                int remainder = (int)(needWithdraw%key.getValue()); //typecast required, lossy conversion?
                if(quotient > money.get(key)){
                    needWithdraw -= money.get(key)*key.getValue();
                }else{
                    needWithdraw = remainder ;
                }
            }
            return (needWithdraw == 0);
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
    public MoneyStack addMoneyStack(MoneyStack m){
        return null;
    }

    public MoneyStack withdrawStack(MoneyStack m){
        return null;
    }

}