import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.IOException;

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
        int needWithdraw = c.totalMoney();
        if(money.canWithdraw(c) == false){
            return false;
        }else{
            for(MoneyType key: this.money.keySet()){
                int quotient = needWithdraw/key.getValue();
                int remainder  = needWithdraw%key.getValue();
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

    public int query(MoneyType c) throws IOExeception{
        if(money.containsKey(m) == false){
            throw new IOException("Error: money type doesn't exist");
        }else{
            return this.money.get(c);
        }
    }

    private boolean canWithdraw(MoneyStack c){
        int needWithdraw = c.totalMoney();
        if(needWithdraw > this.money.totalMoney()){
            return false;
        }else{
            for(MoneyType key: this.money.keySet()){
                int quotient = needWithdraw/key.getValue();
                int remainder = needWithdraw%key.getValue();
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

}