package R18_G2_ASM1;

public enum MoneyType {
  // HUNDRED_DOLLARS (100),
  // FIFTY_DOLLARS (50),
  // TWENTY_DOLLARS (20),
  // TEN_DOLLARS (10),
  // FIVE_DOLLARS (5),
  // TWO_DOLLARS (2),
  // ONE_DOLLAR (1),
  // FIFTY_CENTS (0.5),
  // TWENTY_CENTS (0.2),
  // TEN_CENTS (0.1),
  // FIVE_CENTS (0.05);
  
  HUNDRED_DOLLARS (100, 50),
  FIFTY_DOLLARS (50, 50),
  TWENTY_DOLLARS (20, 50),
  TEN_DOLLARS (10, 50),
  FIVE_DOLLARS (5, 100),
  TWO_DOLLARS (2, 100),
  ONE_DOLLAR (1, 100),
  FIFTY_CENTS (0.5, 100),
  TWENTY_CENTS (0.2, 100),
  TEN_CENTS (0.1, 100),
  FIVE_CENTS (0.05, 100);
  
  private final double value;
  protected  int amount;

  MoneyType(double value, int amount) {
    this.value = value;  
    this.amount = amount;
  }

  double getValue() {
    return this.value;
  }
  int getAmount(){
    return this.amount;
  }
}

