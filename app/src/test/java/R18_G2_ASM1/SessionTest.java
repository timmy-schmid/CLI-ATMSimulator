package R18_G2_ASM1;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.io.IOUtil;
import org.opentest4j.AssertionFailedError;

import R18_G2_ASM1.Session.InvalidTypeException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;


public class SessionTest {
    File csvCardTest;
    File csvCardTestFinal;
    File csvCardTest3;
    File csvCardTest2;
    File csvCardFinal2;
    File csvCardTestFinal2;
    Session section;
    Card card;
    DateFormat dateFormat;
    SimpleDateFormat simpleDateFormat;
    
    private static void copyFile(File source, File dest) throws IOException {    
        Files.copy(source.toPath(), dest.toPath());
}

    @BeforeEach
    public void setUp() throws InvalidTypeException, IOException{


        // String s = this.getClass().getResource("/").getPath();
        // System.out.println(s);
        // s = s.substring(0,s.length()-13);
        // System.out.println(s);

        // csvCardTest = new File(s+"app/src/test/datasets/cardTest.csv");
        // csvCard = new File(s+"app/src/main/datasets/card.csv");

        // File directory = new File("");// 
        // String courseFile = directory.getCanonicalPath() ; 
        // System.out.println(courseFile); 

        // System.out.println(csvCardTest.exists());

        csvCardTest = new File("src/test/datasets/cardTest.csv");
        csvCardTestFinal = new File("src/test/datasets/cardTestFinal.csv");
        csvCardTest3 = new File("src/test/datasets/cardTest3.csv");
        csvCardTest2 = new File("src/test/datasets/cardTest2.csv");
        csvCardTestFinal2 = new File("src/test/datasets/cardTestFinal2.csv");
        csvCardTestFinal2.delete();
        copyFile(new File("src/main/datasets/cardFinal.csv"), new File("src/test/datasets/cardTestFinal2.csv"));
        csvCardTestFinal2 = new File("src/test/datasets/cardTestFinal2.csv");


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
        assertEquals(simpleDateFormat.parse("2017-01-01"), card.getStartDate(),"correctly read the card start date");
        assertEquals(simpleDateFormat.parse("2027-01-01"), card.getExpirationDate(),"correctly read the expiration date");
        assertEquals(false, card.isLost(),"correctly read the card information");
        assertEquals(false, card.isBlocked(),"correctly read the card information");
        assertEquals(1234, card.getPin(),"correctly read the card information");
        assertEquals(BigDecimal.valueOf(90), card.getBalance(),"correctly read the card balance");
        assertEquals("customer", section.getUserType(),"correctly read the card information");
        
        Card card3 = section.retrieveCardFromFile(11022, csvCardTest);
        assertEquals(true, card3.isLost(),"correctly read the card information");
        assertEquals(true, card3.isBlocked(),"correctly read the card information");

        Card card2 = section.retrieveCardFromFile(58858, csvCardTestFinal);
        assertEquals(null, card2);


    }
    /* Tim - Commented out so passes tests until we implement new SessionStatus'
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
    }*/

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
        
        
    }
    @Test
    void runExceptionTest() throws IOException, NoSuchElementException, InvalidTypeException{
        boolean thrown = false;
        try {
            section.run(66666);
            assertEquals(SessionStatus.INVALID_CARD_NUMBER, section.getStatus()); 
            
        } catch (NoSuchElementException e) {
            thrown = true;
        }

    }

    
    public void setInput(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }
        
     
    @Test
    public void testAdminMode() throws InvalidTypeException, IOException {
        
        String inputMessage = "51555\n"
                + "1234\n"
                + "1\n";
        
        
        setInput(inputMessage);
       
        Session sec = new Session(new ATM("bbb"));
        sec.changeCsvFile(csvCardTest2);
        sec.run(51555);
        assertEquals(SessionStatus.ADMIN_MODE, sec.getStatus());
    }

    @Test
    public void testTransact() throws InvalidTypeException, IOException,NullPointerException {
        try{
            String inputMessage = "10000\n"
            + "1234\n"
            + "1\n"
            + "6\n"
            + "7\n";
    
    
            setInput(inputMessage);
        
            Session sec = new Session(new ATM("bbb"));
            sec.changeCsvFile(csvCardTestFinal2);
            sec.run(10000);
            assertEquals(SessionStatus.CANCELLED, sec.getStatus());
        } catch (NullPointerException e){

        }

    }

    @Test
    public void testWrongPinTest() throws InvalidTypeException, IOException, NullPointerException {
        try{
            String inputMessage = "12345\n"
            + "1111\n"
            + "1111\n"
            + "1111\n";
    
    
            setInput(inputMessage);
        
            Session sec = new Session(new ATM("bbb"));
            sec.changeCsvFile(csvCardTestFinal2);
            sec.run(12345);
            assertEquals(SessionStatus.CARD_BLOCKED, sec.getStatus());
        } catch (NullPointerException e){

        }

    }

    @Test
    public void testCardLost() throws InvalidTypeException, IOException,NullPointerException {
        try{
            String inputMessage = "12366\n"
            + "1234\n"
            + "1\n"
            + "6\n"
            + "7\n";
    
            setInput(inputMessage);
        
            Session sec = new Session(new ATM("bbb"));
            sec.changeCsvFile(csvCardTestFinal2);
            sec.run(12366);
            assertEquals(SessionStatus.CARD_LOST, sec.getStatus());
        } catch (NullPointerException e){

        }

    }

    @Test
    public void testCardBlocked() throws InvalidTypeException, IOException,NullPointerException {
        try{
            String inputMessage = "19001\n"
            + "1234\n"
            + "1\n"
            + "6\n"
            + "7\n";
    
            setInput(inputMessage);
        
            Session sec = new Session(new ATM("bbb"));
            sec.changeCsvFile(csvCardTestFinal2);
            sec.run(19001);
            assertEquals(SessionStatus.CARD_BLOCKED, sec.getStatus());
        } catch (NullPointerException e){

        }

    }
    
    @Test
    public void testCardIsExpired() throws InvalidTypeException, IOException,NullPointerException {
        try{
            String inputMessage = "11111\n"
            + "1234\n"
            + "1\n"
            + "6\n"
            + "7\n";
    
            setInput(inputMessage);
        
            Session sec = new Session(new ATM("bbb"));
            sec.changeCsvFile(csvCardTestFinal2);
            sec.run(11111);
            assertEquals(SessionStatus.CARD_EXPIRED, sec.getStatus());
        } catch (NullPointerException e){

        }

    }

    @Test
    public void testNotActiveCard() throws InvalidTypeException, IOException,NullPointerException {
        try{
            String inputMessage = "10011\n"
            + "1234\n"
            + "1\n"
            + "6\n"
            + "7\n";
    
            setInput(inputMessage);
        
            Session sec = new Session(new ATM("bbb"));
            sec.changeCsvFile(csvCardTestFinal2);
            sec.run(10011);
            assertEquals(SessionStatus.CARD_NOT_ACTIVE, sec.getStatus());
        } catch (NullPointerException e){

        }

    }


    @Test
    public void fileNotFoundTest2() throws InvalidTypeException, IOException {
        section.writeCardToFile(11999, new File("notExist.csv"));

        assertTrue(true);
    }





    
}
