package R18_G2_ASM1;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.io.IOException;

public class MoneyStack{
    private HashMap<MoneyType, Integer> money; //A: double or integer??
    //according to the comments in miro, may add "status of money"? since some money might be frozen and cannot be withdrawn, or this can be added to the card status
    //true is for normal and false is for frozen
    private boolean statuOfMoney = true;

    // constructor
    //seems we don't have parameters to pass
    public MoneyStack(){
        this.money = new LinkedHashMap <MoneyType, Integer>(); //replace hashmap with linkedhashmap
        money.put(MoneyType.HUNDRED_DOLLARS,50);
        money.put(MoneyType.FIFTY_DOLLARS, 50);
        money.put(MoneyType.TWENTY_DOLLARS, 50);
        money.put(MoneyType.TEN_DOLLARS,  50);
        money.put(MoneyType.FIVE_DOLLARS, 100);
        money.put(MoneyType.TWO_DOLLARS, 100);
        money.put(MoneyType.ONE_DOLLAR, 100);
        money.put(MoneyType.FIFTY_CENTS,  100);
        money.put(MoneyType.TWENTY_CENTS, 100);
        money.put(MoneyType.TEN_CENTS, 100);
        money.put(MoneyType.FIVE_CENTS, 100);
    }

    public double totalMoney() { //or double return type??
        double totalMoney = 0;
        for(MoneyType T: money.keySet()){
            totalMoney += T.getValue()*money.get(T);
        }
        return totalMoney;
    }

    //remove static?
    public void addMoney(MoneyType m, int amount) throws IOException{
       if(this.getMoney().containsKey(m) == false){
           System.out.println("Error: money type doesn't exist");
           throw new IOException("Error: money type doesn't exist");
       }else{
           int originalValue = this.getMoney().get(m);
           this.getMoney().replace(m,originalValue+amount);
       }
    }

    public boolean withdraw(MoneyStack c){ //needs edit i think....
        int needWithdraw = c.totalMoney();
        if (this.canWithdraw(c) == false){
            return false;
        } else{
            for(MoneyType key: this.getMoney().keySet()){
                int quotient = (int)(needWithdraw/key.getValue()); //casting??
                double remainder  = needWithdraw%key.getValue();
                if(quotient >= money.get(key)){
                    needWithdraw -= money.get(key)*key.getValue();
                    money.replace(key,0);
                }else{
                    int originalAmount = money.get(key);
                    needWithdraw = remainder;
                    money.replace(key,originalAmount-quotient);
                    // System.out.printf("original amount = [%d], needwithdraw = [%d], replacement = [%d]\n\n", originalAmount, needWithdraw, originalAmount-quotient);
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
        double needWithdraw = c.totalMoney();
        if (needWithdraw > this.totalMoney()){ //change this.money --> this?
            return false;
        } else{
            for(MoneyType key: this.getMoney().keySet()){
                int quotient =  (int)(needWithdraw/key.getValue()); 
                double remainder = (needWithdraw%key.getValue()); //typecast required, lossy conversion?
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

    //newly added functions below xdddd

    public HashMap <MoneyType, Integer> getMoney(){
        return this.money;
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
