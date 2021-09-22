import java.util.*;
import java.io.IOException;
import java.math.BigDecimal;

public class MoneyStack{
    private Map<MoneyType, double> money;
    //according to the comments in miro, may add "status of money"? since some money might be frozen and cannot be withdrawn, or this can be added to the card status
    //true is for normal and false is for frozen
    private boolean statuOfMoney = true;

    // constructor
    //seems we don't have parameters to pass
    public MoneyStack(){
        this.money = new HashMap<MoneyType, int>();
        money.put(HUNDRED_DOLLARS,50);
        money.put(FIFTY_DOLLARS, 50);
        money.put(TWENTY_DOLLARS, 50);
        money.put(TEN_DOLLARS  50);
        money.put(FIVE_DOLLARS, 100);
        money.put(TWO_DOLLARS, 100);
        money.put(ONE_DOLLAR, 100);
        money.put(FIFTY_CENTS,  100);
        money.put(TWENTY_CENTS, 100);
        money.put(TEN_CENTS, 100);
        money.put(FIVE_CENTS, 100);
    }

    public double totalMoney() {
        int totalMoney = 0;
        for(Moneytype: T: money.keySet()){
            totalMoney += T.getValue()*money.get(T);
        }
        return totalMoney;
    }

    //overload for BigDecimal calculation
    public BigDecimal totalMoney() {
        BigDecimal totalMoney = new BigDecimal(0);
        for(MoneyType T: money.keySet()){
            BigDecimal add_num = BigDecimal.valueOf(T.getValue()*money.get(T));
            totalMoney = totalMoney.add(add_num);
        }
        return totalMoney;
    }

    public static void addMoney(MoneyType m, int amount) throws IOException{
       if(money.containsKey(m) == false){
           System.out.println("Error: money type doesn't exist");
           throw new IOException("Error: money type doesn't exist");
       }else{
           int originalValue = this.money.get(m);
           this.money.replace(m,originalValue+amount);
       }
    }

    public boolean withdraw(MoneyStack c){
        BigDecimal needWithdraw = c.totalMoney();
        if(money.canWithdraw(c) == false){
            return false;
        }else{
            for(MoneyType key: this.getMoney().keySet()){
                BigDecimal notesValue = BigDecimal.valueOf(key.getValue());
                BigDecimal quotient = needWithdraw.divide(notesValue);
                int intQuotient =  quotient.intValue();
                quotient = BigDecimal.valueOf(intQuotient);
                BigDecimal remainder = needWithdraw.subtract(notesValue.multiply(BigDecimal.valueOf(intQuotient)));
                BigDecimal notesAmount = new BigDecimal(money.get(key));
                if(notesAmount.compareTo(BigDecimal.valueOf(intQuotient)) == 0 || notesAmount.compareTo(BigDecimal.valueOf(intQuotient)) == -1){
                    BigDecimal alreadyCal = notesAmount.multiply(notesValue);
                    needWithdraw = needWithdraw.subtract(alreadyCal) ;
                    money.replace(key,0);
                }else{
                    int originalAmount = money.get(key);
                    needWithdraw = remainder;
                    money.replace(key,(int)originalAmount-intQuotient);
                }
            }
        }
        return true;
    }

    public int query(MoneyType c) throws IOExeception{
        if(money.containsKey(m) == false){
            throw new IOException("Error: money type doesn't exist");
        }else{
            return this.money.get(c);
        }
    }

    private boolean canWithdraw(MoneyStack c){
        BigDecimal needWithdraw = c.totalMoney();
        if (needWithdraw.compareTo(this.totalMoney()) == 1){ //change this.money --> this?
            return false;
        } else{
            for(MoneyType key: this.getMoney().keySet()){
                BigDecimal notesValue = BigDecimal.valueOf(key.getValue());
                System.out.println(notesValue);
                BigDecimal quotient = needWithdraw.divide(notesValue);
                int intQuotient =  quotient.intValue();
                quotient = BigDecimal.valueOf(intQuotient);
                BigDecimal remainder = needWithdraw.subtract(notesValue.multiply(quotient));
                BigDecimal notesAmount = BigDecimal.valueOf(money.get(key));
                if(notesAmount.compareTo(BigDecimal.valueOf(intQuotient)) == 0 || notesAmount.compareTo(BigDecimal.valueOf(intQuotient)) == -1){
                    BigDecimal alreadyCal = notesAmount.multiply(notesValue);
                    needWithdraw = needWithdraw.subtract(alreadyCal) ;
                }else{
                    needWithdraw = remainder ;
                }
            }
            return (needWithdraw.compareTo(BigDecimal.valueOf(0)) == 0);
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
    // should it be boolean or void?
    public void addMoneyStack(MoneyStack m){
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
        return true;}

}