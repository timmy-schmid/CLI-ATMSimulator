package R18_G2_ASM1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CardDispensorTest {
  
  private final String invalidLength = "Invalid card number: Not 5 digits";
  private final String invalidChars = "Invalid card number: Not 5 digits";
  String mockUserInput;
  ByteArrayInputStream in;
  CardDispensor cd;
  
  @Test
  void insertCardThatIsLessThanFiveDigits() {

    mockUserInput = "1253";
    in = new ByteArrayInputStream(mockUserInput.getBytes());      
    cd = new CardDispensor(in);

    Exception e = assertThrows(InvalidCardException.class, () -> {
      cd.insertCard();
    });
    assertEquals(e.getMessage(),invalidLength);
  }

  @Test
  void insertCardThatIsGreaterThanFiveDigits() {

    mockUserInput = "1253124";

    in = new ByteArrayInputStream(mockUserInput.getBytes());      
    cd = new CardDispensor(in);

    Exception e = assertThrows(InvalidCardException.class, () -> {
      cd.insertCard();
    });

    assertEquals(e.getMessage(),invalidLength);
  }

  @Test
  void insertCardThatIsFiveCharsButNotInt() {

    mockUserInput = "12531f";
    in = new ByteArrayInputStream(mockUserInput.getBytes());
    cd = new CardDispensor(in);

    Exception e = assertThrows(InvalidCardException.class, () -> {
      cd.insertCard();
    });
    assertEquals(e.getMessage(),invalidChars);
  }

  void insertCardThatIsFiveCharsButDecimal() {
    mockUserInput = "125.31";
    in = new ByteArrayInputStream(mockUserInput.getBytes());
    cd = new CardDispensor(in);  

    Exception e = assertThrows(InvalidCardException.class, () -> {
      cd.insertCard();
    });
    assertEquals(e.getMessage(),invalidChars);
  }

  @Test
  void insertCardThatIsValid() {

    mockUserInput = "12531";
    in = new ByteArrayInputStream(mockUserInput.getBytes());      
    cd = new CardDispensor(in);
    assertEquals(cd.insertCard(),12531);

    mockUserInput = "00031";
    in = new ByteArrayInputStream(mockUserInput.getBytes());
    cd = new CardDispensor(in); 
    assertEquals(cd.insertCard(),31); 
  }

  @Test
  void ejectCard() {

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream outPrint = new PrintStream(out);
    Display mockDisplay = new Display(outPrint);
    in = new ByteArrayInputStream("".getBytes());
    cd = new CardDispensor(in);

    cd.ejectCard(mockDisplay);
    assertEquals(out.toString(), "Your card has been ejected.\n");

  }
}
