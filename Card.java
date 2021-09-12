import java.util.*;

public class Card {
	// protected Date start_date;
	// protected Date end_date;
	protected int totalAmount;
	
	//key = string: start_date, end_date, value = actual date format [CONVERT STRING BACK TO DATE]
	
	protected HashMap<String, String> full_date;

	protected String[][] date_list ; //[[start_date], [end_date]]? 


	//where toBeDetermined represents how much card currently holds (totalamount)
	public Card (int toBeDetermined, String start_date, String end_date){
		this.totalAmount = toBeDetermined;
		this.full_date = new HashMap<>();
		// this.full_date.put("START", );
		// this.full_date.put("EXPIRES", );
		this.date_list = null;
		// this.date_list.add([start_date]);
		// this.date_list.add([end_date]);
	}
	public static void main(String[] args){
		System.out.println("Hello World!");
	}

	public int getTotalAmount(){
		return this.totalAmount;
	}

	// public String getDate(){
	// 	return this.date;
	// }

	// public int setTotalAmount(int num){ -> setting is determined in withdrawal/deposit class?
	// 	this.totalAmount = num;
	// }

	public String[][] getDateDetails(){
		return this.date_list;
	}
	// public void printDate(){
	// 	for(HashMap.Entry<String, Date> entry: this.full_date.entrySet()){
	// 		System.out.println("Card's start date = " + entry.getKey() + "End date " + entry.getValue());
	// 	}
	// }
	// public HashMap<String, Date> getDate(){
	// 	return this.full_date;
		
	// }



}
