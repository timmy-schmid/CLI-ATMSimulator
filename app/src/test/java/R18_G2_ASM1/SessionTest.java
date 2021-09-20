package R18_G2_ASM1;
import org.junit.jupiter.api.Test;

import R18_G2_ASM1.Session.InvalidTypeException;


import java.io.*;
import org.junit.jupiter.api.BeforeEach;


public class SessionTest {
    @BeforeEach
    public void setUp() throws InvalidTypeException, IOException{

        File csvCardTest;
        File csvCard;
        csvCardTest = new File("app/src/test/datasets/cardTest.csv");
        csvCard = new File("app/src/main/datasets/card.csv");
        System.out.println(csvCardTest.exists());

        Session sec = new Session(new ATM("loc"));
        Card card = sec.retrieveCardFromFile(12345, csvCard);
        System.out.println(card.getCardNumber());
        System.out.println(card.getPin());
        System.out.println(card.getbalance());
    }

    public static void main(String[] args) throws InvalidTypeException, IOException{

        SessionTest st = new SessionTest();
        st.setUp();


        // Session sec = new Session(new ATM("loc"));
        // Card card = sec.retrieveCardFromFile(11111, csvCard);
        // System.out.println(card);
        //System.out.println(card.getAccount());

        
    }


    
}
