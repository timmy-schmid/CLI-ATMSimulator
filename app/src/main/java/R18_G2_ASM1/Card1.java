// package R18_G2_ASM1;

import java.util.*;

public class Card1 {
	protected String start_date;
	protected String end_date;
	protected double totalAmount;
	protected String name;
	protected int id;

	//where toBeDetermined represents how much Card1 currently holds (totalamount)
	public Card1 (String name, int id, double toBeDetermined, String start_date, String end_date){
		this.totalAmount = toBeDetermined;
		this.start_date= start_date;
		this.end_date = end_date;
		this.name = name;
		this.id = id;
	}

	public double getTotalAmount(){
		return this.totalAmount;
	}

	public int getID(){
		return this.id;
	}
	public void getCardDetails(){
		System.out.println("\nPrinting card details below!!!");

		System.out.println("Card name " + name + ", amount stored  = " + this.getTotalAmount() + ", expires on: "  + this.end_date);
	}
}
