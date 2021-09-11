public class ATM{
    private int total_amount_stored;

    private int MAX_withdrawal_amount;
    private int MIN_withdrawal_amount;
    
    /*
        according to commbank AUS: max withdrawal amount = $500/day, max deposit amount = $10000/day

        ATM holds $100, 000 in total
        composed of: fixed/forced? $90000 in cash

        every morning/have an alert saying theres not enough money left

    */
    
    public ATM(){
        this.MAX_withdrawal_amount = 1000; //say, per day
        this.MIN_withdrawal_amount = 20;
        this.total_amount_stored = 100000;
    }

    public static void main(String[] args){
        ATM atm = new ATM();

    }
}