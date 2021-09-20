package R18_G2_ASM1;
import org.junit.jupiter.api.Test;

import R18_G2_ASM1.Session.InvalidTypeException;


import java.io.*;
import org.junit.jupiter.api.BeforeEach;


public class SessionTest {
    @BeforeEach
    public void setUp() throws InvalidTypeException, IOException{

        File csvCardTest;
        csvCardTest = new File("app/src/test/datasets/cardTest.csv");
        System.out.println(csvCardTest.exists());

        Session sec = new Session(new ATM("loc"));
        Card card = sec.retrieveCardFromFile(11111, csvCardTest);
        System.out.println(card.getCardNumber());
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
