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
  void IsBlocked() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal("38762.99"), 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertFalse(c.isBlocked());
    c.blockCard();
    assertTrue(c.isBlocked());
  }

  @Test
  void IsLost() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal("38762.99"), 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertFalse(c.isLost());
    c.setIsLost(true);
    assertTrue(c.isLost());

  }

  @Test
  void getExpirationDate() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal("38762.99"), 55673, start_date, expiration_date,
        false, false, false, 888888);
    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    Date test_date = fmt.parse("2023-05-31");
    // assertTrue(c.getExpiration_date() == test_date);
    assertEquals(0, c.getExpirationDate().compareTo(test_date));
    Date test_date_2 = fmt.parse("2023-04-23");
    assertNotEquals(0, c.getExpirationDate().compareTo(test_date_2));
  }

  @Test //test failed???
  void getStartDate() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal("38762.99"), 55673, start_date, expiration_date,
        false, false, false, 888888);
    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    Date test_date = fmt.parse("2018-06-01");
    // assertTrue(c.getStart_date() == test_date);
    assertEquals(0, c.getStartDate().compareTo(test_date));
    Date test_date_2 = fmt.parse("2023-04-23");
    assertNotEquals(0, c.getStartDate().compareTo(test_date_2));
  }

  @Test
  void getCardNumber() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal("38762.99"), 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertEquals(c.getCardNumber(), 55673);
    c.setCardNumber(11111);
    assertNotEquals(c.getCardNumber(), 55673);
    assertEquals(c.getCardNumber(), 11111);
  }

  @Test
  void getPin() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal("38762.99"), 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertEquals(c.getPin(), 888888);
    c.setPin(222222);
    assertNotEquals(c.getPin(), 888888);
    assertEquals(c.getPin(), 222222);
  }

  @Test
  void getBalance() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal("38762.99"), 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertEquals(c.getBalance(), new BigDecimal("38762.99"));
    BigDecimal nb = new BigDecimal("888.88");
    c.setBalance(nb);
    assertNotEquals(c.getBalance(), new BigDecimal("38762.99"));
    assertEquals(c.getBalance(), nb);
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
  void setIsBlocked() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    c.setIsBlocked(true);
    assertTrue(c.isBlocked());
  }

  @Test
  void setIsLost() throws ParseException {
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
  void setStartDate() throws ParseException {
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
    Date expiration_date_2 = dateFormat1.parse("2020-05-31");
    Card c_2 = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date_2,
        false, false, false, 888888);
    assertTrue(c_2.isExpired());
  }

  @Test
  void isAfterStartDate() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertTrue(c.isAfterStartDate());
    Date start_date_2 = dateFormat1.parse("2022-05-31");
    Card c_2 = new Card(new BigDecimal(38762.99), 55673, start_date_2, expiration_date,
        false, false, false, 888888);
    assertFalse(c_2.isAfterStartDate());
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
  void blockCard() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(new BigDecimal(38762.99), 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertFalse(c.isBlocked());
    c.blockCard();
    assertTrue(c.isBlocked());
  }
}