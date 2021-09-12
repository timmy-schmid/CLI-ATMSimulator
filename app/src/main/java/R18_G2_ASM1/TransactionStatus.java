// package R18_G2_ASM1;

public enum TransactionStatus {
  FAIL_WITHDRAWAL("The withdrawal failed"),
  SUCCESS_WITHDRAWAL("The withdrawal was successful"),
  FAIL_DEPOSIT("The deposit failed"),
  SUCCESS_DEPOSIT("The deposit was successful"),
  SUCCESS_BALANCE("The balance query was successful");
;

  private String description;

  private TransactionStatus(String description) {
    this.description = description;
  }

  public void updateDescription (String s) {
    description = s;
  }

  @Override
  public String toString() {
    return description;
  }
}
