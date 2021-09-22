package R18_G2_ASM1;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import R18_G2_ASM1.Session.InvalidTypeException;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;


public class SessionTest {
    File csvCardTest;
    Session section;
    Card card;
    DateFormat dateFormat;
    SimpleDateFormat simpleDateFormat;

    @BeforeEach
    public void setUp() throws InvalidTypeException, IOException{


        // String s = this.getClass().getResource("/").getPath();
        // System.out.println(s);
        // s = s.substring(0,s.length()-13);
        // System.out.println(s);

        // csvCardTest = new File(s+"app/src/test/datasets/cardTest.csv");
        // csvCard = new File(s+"app/src/main/datasets/card.csv");

        // File directory = new File("");//参数为空 
        // String courseFile = directory.getCanonicalPath() ; 
        // System.out.println(courseFile); 

        // System.out.println(csvCardTest.exists());

        csvCardTest = new File("src/test/datasets/cardTest.csv");
        String pattern = "yyyy-MM-dd";
        simpleDateFormat = new SimpleDateFormat(pattern);

        section = new Session(new ATM("loc"));
        card = section.retrieveCardFromFile(12345, csvCardTest);

    }
    @Test
    void retrieveCardFromFileTest() throws InvalidTypeException, IOException, ParseException{
        assertEquals(12345, card.getCardNumber(),"correctly read the card number");
        assertEquals(simpleDateFormat.parse("2017-01-01"), card.getStart_date(),"correctly read the card start date");
        assertEquals(simpleDateFormat.parse("2027-01-01"), card.getExpiration_date(),"correctly read the expiration date");
        assertEquals(false, card.is_lost(),"correctly read the card information");
        assertEquals(false, card.is_blocked(),"correctly read the card information");
        assertEquals(1234, card.getPin(),"correctly read the card information");
        assertEquals(90.0, card.getbalance(),"correctly read the card balance");
        assertEquals("customer", section.getUserType(),"correctly read the card information");
        Card card2 = section.retrieveCardFromFile(58858, csvCardTest);
        assertEquals(null, card2);
        Card card3 = section.retrieveCardFromFile(11022, csvCardTest);
        assertEquals(true, card3.is_lost(),"correctly read the card information");
        assertEquals(true, card3.is_blocked(),"correctly read the card information");


    }

    // @Test
    // void passwordIsNullThrowsException() throws InvalidTypeException {  
    //     section.retrieveCardFromFile(0, csvCardTest);
    // }  

    // public static void main(String[] args) throws InvalidTypeException, IOException{

    //     SessionTest st = new SessionTest();
    //     st.setUp();
    //     // Session sec = new Session(new ATM("loc"));
    //     // Card card = sec.retrieveCardFromFile(11111, csvCard);
    //     // System.out.println(card);
    //     //System.out.println(card.getAccount());

        
    // }


    
}
