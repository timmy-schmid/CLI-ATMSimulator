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
        String s = this.getClass().getResource("/").getPath();
        System.out.println(s);
        s = s.substring(0,s.length()-9);
        System.out.println(s);

        csvCardTest = new File(s+"app/src/test/datasets/cardTest.csv");
        csvCard = new File(s+"app/src/main/datasets/card.csv");




        File directory = new File("");//参数为空 
        String courseFile = directory.getCanonicalPath() ; 
        System.out.println(courseFile); 

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
