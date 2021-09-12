// package R18_G2_ASM1;

public enum SessionStatus {
  IN_PROGRESS("The session is still in progress"),
  INVALID_CARD_NUMBER("The card number does not match a valid card"),
  CARD_NOT_ACTIVE("The card entered is only active from x date"),
  CARD_EXPIRED("The card entered has expired"),
  CARD_LOST("The card entered has been reported as lost/stolen"),
  CARD_BLOCKED("The card entered has been blocked due to too many PIN attempts"),
  SUCCESS("The session was succesfully completed");

  private String description;

  private SessionStatus(String description) {
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
