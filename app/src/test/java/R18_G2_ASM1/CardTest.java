package R18_G2_ASM1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

class CardTest {

  @Test
  void isIs_blocked() throws ParseException {
    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    Date start_date = dateFormat1.parse("2018-06-01");
    Date expiration_date = dateFormat1.parse("2023-05-31");
    Card c = new Card(38762.99, 55673, start_date, expiration_date,
        false, true, false, 888888);
    assertTrue(c.is_blocked());
  }

  @Test
  void is_lost() {
  }

  @Test
  void getExpiration_date() {
  }

  @Test
  void getStart_date() {
  }

  @Test
  void getCardNumber() {
  }

  @Test
  void getPin() {
  }

  @Test
  void getbalance() {
  }

  @Test
  void setBalance() {
  }

  @Test
  void setCardNumber() {
  }

  @Test
  void setExpiration_date() {
  }

  @Test
  void setIs_blocked() {
  }

  @Test
  void setIs_lost() {
  }

  @Test
  void setPin() {
  }

  @Test
  void setStart_date() {
  }

  @Test
  void isExpired() {
  }

  @Test
  void isAfterStartDate() {
  }

  @Test
  void check_pin() {
  }

  @Test
  void block_card() {
  }

  @Test
  void getCardDetails() {
  }
}