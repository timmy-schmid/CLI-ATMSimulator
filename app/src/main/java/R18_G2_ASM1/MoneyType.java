package R18_G2_ASM1;

public enum MoneyType {
  HUNDRED_DOLLARS (100),
  FIFTY_DOLLARS (50),
  TWENTY_DOLLARS (20),
  TEN_DOLLARS (10),
  FIVE_DOLLARS (5),
  TWO_DOLLARS (2),
  ONE_DOLLAR (1),
  FIFTY_CENTS (0.5),
  TWENTY_CENTS (0.2),
  TEN_CENTS (0.1),
  FIVE_CENTS (0.05);
  
  private final double value;

  MoneyType(double value) {
    this.value = value;  
  }

  double getValue() {
    return this.value;
  }
}
