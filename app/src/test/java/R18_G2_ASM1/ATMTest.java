package R18_G2_ASM1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;


public class ATMTest {
  
  private static String welcomeMsg;
  private static String insertCardMsg;

  private static ByteArrayOutputStream actualOut;
  private static PrintStream actualOutPrint;

  //Mock ATM components
  private static CardDispensor mockCardDisp;
  private static CashDispensor mockCashDisp;
  private static Keypad mockKeypad;
  private static Display mockDisplay;
  private static MoneyStack mockBalance;
  private static Session mockSession;
  private static ATM_logger mockLogger;

  private static ATM testAtm;

  @BeforeAll static void setUpTestsWithMocks() {
    welcomeMsg = "--------------------------\n" +
                 "---WELCOME TO XYZ BANK ---\n" +
                 "--------------------------\n\n";

    insertCardMsg = "Please insert your card (5 digits): ";
    actualOut = new ByteArrayOutputStream();
    actualOutPrint = new PrintStream(actualOut);

    mockDisplay = new Display(actualOutPrint);
    mockCardDisp = mock(CardDispensor.class);
    mockCashDisp = mock(CashDispensor.class);
    mockKeypad = mock(Keypad.class);
    mockBalance = mock(MoneyStack.class);
    mockSession = mock(Session.class);
    mockLogger = mock(ATM_logger.class);

    testAtm = new ATM("TestLocation",mockCashDisp,mockCardDisp,
                      mockDisplay, mockKeypad, mockBalance, mockLogger);
  }

  @Test void testADMINModeAddMoneyCoinsAndNotes() throws IOException {

    String adminInstructions = "Please select from the following:\n" +
                               "  1. Deposit money into ATM\n" +
                               "  2. Change Location of ATM\n" +
                               "  3. Cancel\n\n";

    String addCashInstruction = "ADMIN MODE - DEPOSIT MONEY - Choose From the Below:\n" +
                                "  1. Deposit notes into ATM\n" +
                                "  2. Deposit coins into ATM\n" +
                                "  3. FINISH\n" +
                                "  4. CANCEL\n" +
                                "  TOTAL TO DEPOSIT: $0.00\n\n";

    String addNoteInstructions = "DEPOSIT MONEY - NOTES - Please select a denomination to deposit:\n" +
                                 "  1. $100\n" +
                                 "  2. $50\n" +
                                 "  3. $20\n" +
                                 "  4. $10\n" +
                                 "  5. $5\n" +
                                 "  6. FINISH\n" +
                                 "  7. CANCEL\n" +
                                 "  TOTAL TO DEPOSIT: $0.00\n\n";

  String addCoinInstructions = "DEPOSIT MONEY - COINS - Please select a denomination to deposit:\n" +
                               "  1. $2\n" +
                               "  2. $1\n" +
                               "  3. 50c\n" +
                               "  4. 20c\n" +
                               "  5. 10c\n" +
                               "  6. 5c\n" +
                               "  7. FINISH\n" +
                               "  8. CANCEL\n" +
                               "  TOTAL TO DEPOSIT: $0.00\n\n";
 
    String expectedOut = welcomeMsg + insertCardMsg +
                         SessionStatus.ADMIN_MODE.toString() + adminInstructions +
                         addCashInstruction + addNoteInstructions +
                         addCashInstruction + addCoinInstructions +
                         addCashInstruction;

    MoneyStack mockMoneyToAdd = mock(MoneyStack.class);

    actualOut.reset(); //reset the display buffer

    when(mockCardDisp.insertCard()).thenReturn(12345);
    when(mockSession.getStatus()).thenReturn(SessionStatus.ADMIN_MODE);

    when(mockKeypad.pressButton())
      .thenReturn(KeypadButton.ONE) // Choose to deposit Money
      .thenReturn(KeypadButton.ONE) // Then choose to deposit notes   
      .thenReturn(KeypadButton.SIX) // Then choose to finish depositing notes
      .thenReturn(KeypadButton.TWO) // Then choose to deposit coins
      .thenReturn(KeypadButton.SEVEN) // Then choose to finish depositing coins  
      .thenReturn(KeypadButton.THREE); // Then choose to finish depositing money

    testAtm.run(mockSession);
    assertEquals(expectedOut, actualOut.toString()); // Check display buffer against expected   
    verify(mockCardDisp,times(1)).ejectCard(any());
  }

  @Test
  public void askForPinTooManyDigitsShouldPrintError() {
  
    String expectedOut = "Please enter your 4 digit PIN: " +
                         "PIN is not 4 positive digits. Try again:\n";
    actualOut.reset(); //reset the display buffer
    reset(mockKeypad); //reset verify
    when(mockKeypad.enterInt()).thenReturn(14525).thenReturn(1424);
    
    testAtm.askForPIN();
    verify(mockKeypad,times(2)).enterInt();
    assertEquals(expectedOut,actualOut.toString());
  }

  @Test
  public void askForPinNegativeIntegerShouldPrintError() {
    String expectedOut = "Please enter your 4 digit PIN: " +
                         "PIN is not 4 positive digits. Try again:\n";
    actualOut.reset(); //reset the display buffer
    reset(mockKeypad); //reset verify
    when(mockKeypad.enterInt()).thenReturn(-42).thenReturn(1424);
    
    testAtm.askForPIN();
    verify(mockKeypad,times(2)).enterInt();
    assertEquals(expectedOut,actualOut.toString()); 
  }



}
