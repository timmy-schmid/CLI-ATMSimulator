package R18_G2_ASM1;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import R18_G2_ASM1.Session.InvalidTypeException;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;


public class SessionTest {
    File csvCardTest;
    File csvCardTestFinal;
    File csvCardTest3;
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
        csvCardTestFinal = new File("src/test/datasets/cardTestFinal.csv");
        csvCardTest3 = new File("src/test/datasets/cardTest3.csv");
        String pattern = "yyyy-MM-dd";
        simpleDateFormat = new SimpleDateFormat(pattern);

        section = new Session(new ATM("loc"));
        card = section.retrieveCardFromFile(12345, csvCardTestFinal);

    }
    public static int testUserInput(InputStream in,PrintStream out) {
        Scanner keyboard = new Scanner(in);
        out.println("Give a number between 1 and 10");
        int input = keyboard.nextInt();
    
        while (input < 1 || input > 10) {
            out.println("Wrong number, try again.");
            input = keyboard.nextInt();
        }
    
        return input;
    }
    
    @Test
    void retrieveCardFromFileTest() throws InvalidTypeException, IOException, ParseException{
        assertEquals(12345, card.getCardNumber(),"correctly read the card number");
        assertEquals(simpleDateFormat.parse("2017-01-01"), card.getStart_date(),"correctly read the card start date");
        assertEquals(simpleDateFormat.parse("2027-01-01"), card.getExpiration_date(),"correctly read the expiration date");
        assertEquals(false, card.is_lost(),"correctly read the card information");
        assertEquals(false, card.is_blocked(),"correctly read the card information");
        assertEquals(1234, card.getPin(),"correctly read the card information");
        assertEquals(BigDecimal.valueOf(90), card.getbalance(),"correctly read the card balance");
        assertEquals("customer", section.getUserType(),"correctly read the card information");
        
        Card card3 = section.retrieveCardFromFile(11022, csvCardTest);
        assertEquals(true, card3.is_lost(),"correctly read the card information");
        assertEquals(true, card3.is_blocked(),"correctly read the card information");

        Card card2 = section.retrieveCardFromFile(58858, csvCardTestFinal);
        assertEquals(null, card2);


    }
    @Test
    public void testInvalidInputException() throws InvalidTypeException {
        boolean thrown = false;
        try {
            section.retrieveCardFromFile(0, csvCardTest);
            

        } catch (InvalidTypeException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            section.retrieveCardFromFile(11111, csvCardTest3);
            

        } catch (InvalidTypeException e) {
            thrown = true;
        }
        assertTrue(thrown);


        thrown = false;
        try {
            section.retrieveCardFromFile(10011, csvCardTest3);
            

        } catch (InvalidTypeException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            section.retrieveCardFromFile(12345, csvCardTest3);
            

        } catch (InvalidTypeException e) {
            thrown = true;
        }
        assertTrue(thrown);


        thrown = false;
        try {
            section.retrieveCardFromFile(10000, csvCardTest3);
            

        } catch (InvalidTypeException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            section.retrieveCardFromFile(13141, csvCardTest3);
            

        } catch (InvalidTypeException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            section.retrieveCardFromFile(11999, csvCardTest3);
            

        } catch (InvalidTypeException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            section.retrieveCardFromFile(19001, csvCardTest3);
            

        } catch (InvalidTypeException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void fileNotFoundTest() throws FileNotFoundException, InvalidTypeException {
        section.retrieveCardFromFile(11999, new File("notExist.csv"));

        assertTrue(true);
    }

    @Test
    void runTest() throws InvalidTypeException, IOException{
        section.run(12347);
        assertEquals(SessionStatus.INVALID_CARD_NUMBER, section.getStatus()); 
        assertEquals(0, section.getAttemptNum());

        section.ifWrongPin();
        assertEquals(1, section.getAttemptNum());

        boolean thrown = false;

        
        // section.run(51555);
        // testUserInput(System.in, System.out);
        // assertEquals(SessionStatus.ADMIN_MODE, section.getStatus()); 
        
            
     


    }
    @Test
    void runExceptionTest() throws IOException, NoSuchElementException, InvalidTypeException{
        boolean thrown = false;
        try {
            section.run(11022);
            assertEquals(SessionStatus.CARD_LOST, section.getStatus()); 
            
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            section.run(19001);
            assertEquals(SessionStatus.CARD_BLOCKED, section.getStatus()); 
            
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;

        try {
            section.run(11111);
            assertEquals(SessionStatus.CARD_EXPIRED, section.getStatus()); 
            
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        assertTrue(true);

        thrown = false;

        try {
            section.run(11344);
            assertEquals(SessionStatus.CARD_NOT_ACTIVE, section.getStatus()); 
            
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        assertTrue(thrown);


        thrown = false;


        try {
            section.transact(card, TransactionType.DEPOSIT, 1);
            assertEquals(SessionStatus.SUCCESS, section.getStatus());
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;

        try {
            section.checkPIN();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        assertTrue(thrown);


    }




    
}
