package R18_G2_ASM1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.math.BigDecimal;

class CardTest {

  @Test
  void Is_blocked() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);//change the is_los, blocked, expired in initialiser???
    assertFalse(c.isBlocked());
  }

  @Test
  void Is_lost() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertFalse(c.isLost());
  }

  @Test
  void getExpiration_date() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    Date test_date = fmt.parse("2023-05-31");
    // assertTrue(c.getExpiration_date() == test_date);
    assertTrue(c.getExpirationDate().compareTo(test_date) == 0); // A - changed to this for date comparison!!!

  }

  @Test //test failed???
  void getStart_date() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    Date test_date = fmt.parse("2018-06-01");
    // assertTrue(c.getStart_date() == test_date);
    assertTrue(c.getStartDate().compareTo(test_date) == 0); // A - changed to this for date comparison!!!
  }

  @Test
  void getCardNumber() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertEquals(c.getCardNumber(), 55673);
  }

  @Test
  void getPin() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertEquals(c.getPin(), 888888);
  }

  @Test
  void getBalance() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertEquals(c.getBalance(), new BigDecimal(38762.99));
  }

  @Test
  void setBalance() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    c.setBalance(new BigDecimal(10000));
    assertEquals(c.getBalance(), new BigDecimal(10000));
  }

  @Test
  void setCardNumber() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    c.setCardNumber(23232);
    assertEquals(c.getCardNumber(), 23232);
  }

  @Test
  void setExpiration_date() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    Date test_date = fmt.parse("2028-06-01");
    c.setExpirationDate(test_date);
    assertEquals(c.getExpirationDate(), test_date);
  }

  @Test
  void setIs_blocked() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    c.setIsBlocked(true);
    assertTrue(c.isBlocked());
  }

  @Test
  void setIs_lost() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    c.setIsLost(true);
    assertTrue(c.isLost());
  }

  @Test
  void setPin() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    c.setPin(999999);
    assertEquals(c.getPin(), 999999);
  }

  @Test
  void setStart_date() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    Date test_date = fmt.parse("2018-07-01");
    c.setStartDate(test_date);
    assertEquals(c.getStartDate(), test_date);
  }

  @Test
  void isExpired() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertFalse(c.isExpired());
  }

  @Test
  void isAfterStartDate() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertTrue(c.isAfterStartDate());
  }

  @Test
  void checkPin() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertTrue(c.checkPin(888888));
    assertFalse(c.checkPin(123123));
    c.setPin(999999);
    assertFalse(c.checkPin(888888));
    assertTrue(c.checkPin(999999));
  }

  @Test
  void block_card() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    c.blockCard();
    assertTrue(c.isBlocked());
  }
}