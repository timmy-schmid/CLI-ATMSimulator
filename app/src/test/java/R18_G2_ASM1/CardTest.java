package R18_G2_ASM1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

class CardTest {

  @Test
  void Is_blocked() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);//change the is_los, blocked, expired in initialiser???
    assertFalse(c.is_blocked());
  }

  @Test
  void Is_lost() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertFalse(c.is_lost());
  }

  @Test
  void getExpiration_date() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    Date test_date = fmt.parse("2023-05-31");
   //assertTrue(c.getExpiration_date() == test_date);
  }

  @Test
  void getStart_date() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    Date test_date = fmt.parse("2018-06-01");
    //assertTrue(c.getStart_date() == test_date);
  }

  @Test
  void getCardNumber() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertEquals(c.getCardNumber(), 55673);
  }

  @Test
  void getPin() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertEquals(c.getPin(), 888888);
  }

  @Test
  void getbalance() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertEquals(c.getbalance(), 38762.99);
  }

  @Test
  void setBalance() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    c.setBalance(10000);
    assertEquals(c.getbalance(), 10000);
  }

  @Test
  void setCardNumber() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    c.setCardNumber(23232);
    assertEquals(c.getCardNumber(), 23232);
  }

  @Test
  void setExpiration_date() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    Date test_date = fmt.parse("2028-06-01");
    c.setExpiration_date(test_date);
    assertEquals(c.getExpiration_date(), test_date);
  }

  @Test
  void setIs_blocked() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    c.setIs_blocked(true);
    assertTrue(c.is_blocked());
  }

  @Test
  void setIs_lost() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    c.setIs_lost(true);
    assertTrue(c.is_lost());
  }

  @Test
  void setPin() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    c.setPin(999999);
    assertEquals(c.getPin(), 999999);
  }

  @Test
  void setStart_date() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    Date test_date = fmt.parse("2018-07-01");
    c.setStart_date(test_date);
    assertEquals(c.getStart_date(), test_date);
  }

  @Test
  void isExpired() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertFalse(c.isExpired());
  }

  @Test
  void isAfterStartDate() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertTrue(c.isAfterStartDate());
  }

  @Test
  void check_pin() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    assertTrue(c.check_pin(888888));
    assertFalse(c.check_pin(123123));
    c.setPin(999999);
    assertFalse(c.check_pin(888888));
    assertTrue(c.check_pin(999999));
  }

  @Test
  void block_card() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, false, false, 888888);
    c.block_card();
    assertTrue(c.is_blocked());
  }

  @Test
  void getCardDetails() {
  }
}