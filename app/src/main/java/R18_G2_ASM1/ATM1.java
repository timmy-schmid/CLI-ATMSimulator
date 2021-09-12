// package R18_G2_ASM1;

public class ATM1{
    private int total_amount_stored;

    // private int MAX_withdrawal_amount;
    // private int MIN_withdrawal_amount;
    
    /*
        according to commbank AUS: max withdrawal amount = $500/day, max deposit amount = $100,000/day

        ATM holds $100, 000 in total
        composed of: fixed/forced? $99,000 in cash, 1000 in coins?

        have an alert that pops up whenever theres not enough money left --> reaches min amount
    */
    
    public ATM1(){
        // this.MAX_withdrawal_amount = 1000; //say, per day
        // this.MIN_withdrawal_amount = 20;
        this.total_amount_stored = 100000;
    }

    public static void main(String[] args){
        ATM1 atm = new ATM1();

    }
}