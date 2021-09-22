package R18_G2_ASM1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class KeypadTest {
  @Test
  public void askForIntButUserDoesNotGiveInt() {
    String mockUserInput = "asaf\n"
                 + "0.2415\n"
                 + "asfasf042\n"
                 + "1\n";

    ByteArrayInputStream in = new ByteArrayInputStream(mockUserInput.getBytes());
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream outPrint = new PrintStream(out);
    Keypad k = new Keypad(in, outPrint);

    k.enterInt();
    assertEquals(out.toString(), "Invalid selection. Please enter a number.\n" +
                                 "Invalid selection. Please enter a number.\n" +
                                 "Invalid selection. Please enter a number.\n");
  }

  @Test
  public void askForIntAndUserGivesInt() {
    String mockUserInput = "4124";

    ByteArrayInputStream in = new ByteArrayInputStream(mockUserInput.getBytes());
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream outPrint = new PrintStream(out);
    Keypad k = new Keypad(in, outPrint);

    assertEquals(k.enterInt(),4124);
  }

  @Test
  public void askForIntAndUserGivesPaddedInt() {
    String mockUserInput = "00003";

    ByteArrayInputStream in = new ByteArrayInputStream(mockUserInput.getBytes());
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream outPrint = new PrintStream(out);
    Keypad k = new Keypad(in, outPrint);

    assertEquals(k.enterInt(),3);
  }

  @Test
  public void askForCashAmountAndUserGivesInt() {
    String mockUserInput = "5";

    ByteArrayInputStream in = new ByteArrayInputStream(mockUserInput.getBytes());
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream outPrint = new PrintStream(out);
    Keypad k = new Keypad(in, outPrint);

    assertEquals(k.enterCashAmount(),new BigDecimal(5).setScale(2,RoundingMode.HALF_UP));
  }

  @Test
  public void askForCashAmountAndUserGivesNonDouble() {
    String mockUserInput = "sdfsdf\n"
                         + "$24.04\n"
                         + "sdgsdg24.04\n"
                         + "4.0\n";

    ByteArrayInputStream in = new ByteArrayInputStream(mockUserInput.getBytes());
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream outPrint = new PrintStream(out);
    Keypad k = new Keypad(in, outPrint);
    k.enterCashAmount();
    assertEquals(out.toString(), "Invalid selection. Please enter dollar amount.\n" +
                                 "Invalid selection. Please enter dollar amount.\n" +
                                 "Invalid selection. Please enter dollar amount.\n");
  }

  @Test
  public void askForCashAmountAndUserGivesDoubleMoreThanTwoDecimalPlacesShouldRoundDown() {
    String mockUserInput = "4.134\n";

    ByteArrayInputStream in = new ByteArrayInputStream(mockUserInput.getBytes());
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream outPrint = new PrintStream(out);
    Keypad k = new Keypad(in, outPrint);
    assertEquals(k.enterCashAmount(),new BigDecimal(4.13).setScale(2,RoundingMode.HALF_UP));

  }

  @Test
  public void askForCashAmountAndUserGivesDoubleMoreThanTwoDecimalPlacesShouldRoundUp() {
    String mockUserInput = "4.5999995\n";

    ByteArrayInputStream in = new ByteArrayInputStream(mockUserInput.getBytes());
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream outPrint = new PrintStream(out);
    Keypad k = new Keypad(in, outPrint);
    assertEquals(k.enterCashAmount(),new BigDecimal(4.6).setScale(2,RoundingMode.HALF_UP));
  }


}
