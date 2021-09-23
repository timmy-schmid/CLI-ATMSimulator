package R18_G2_ASM1;

import java.math.BigDecimal;


public enum MoneyType {
  HUNDRED_DOLLARS (BigDecimal.valueOf(100)),
  FIFTY_DOLLARS (BigDecimal.valueOf(50)),
  TWENTY_DOLLARS (BigDecimal.valueOf(20)),
  TEN_DOLLARS (BigDecimal.valueOf(10)),
  FIVE_DOLLARS (BigDecimal.valueOf(5)),
  TWO_DOLLARS (BigDecimal.valueOf(2)),
  ONE_DOLLAR (BigDecimal.valueOf(1)),
  FIFTY_CENTS (BigDecimal.valueOf(0.5)),
  TWENTY_CENTS (BigDecimal.valueOf(0.2)),
  TEN_CENTS (BigDecimal.valueOf(0.1)),
  FIVE_CENTS (BigDecimal.valueOf(0.05));
  
  private final BigDecimal value;

  MoneyType(BigDecimal value) {
    this.value = value;  
  }

  BigDecimal getValue() {
    return this.value;
  }
}
