package R18_G2_ASM1;

public enum SessionStatus {
  INVALID_CARD_NUMBER("The card you have entered is not a card from our bank.\n"+
                      "This ATM only accepts bank cards from XYZ\n" +
                      "We apologise for any inconvienience.",StatusType.ERROR),

  CARD_NOT_ACTIVE("The card you have entered is only active from a later date and cannot be used.\n" +
                  "We apologise for any inconvienience.\n",StatusType.ERROR),

  CARD_EXPIRED("The card you have entered has expired.\n" +
               "Please contact XYZ Bank to issue a new card.\n" +
               "We apologise for any inconvienience.\n",StatusType.ERROR),

  CARD_LOST("The card you have entered has been reported as Lost or Stolen.\n" +
            "Your card has been confiscated. Please contact XYZ Bank to issue a new card.\n" +
            "We apologise for any inconvienience.\n",StatusType.ERROR),

  CARD_BLOCKED("The card you have entered has been blocked due to too many PIN attempts.\n" +
               "Please contact XYZ Bank to unblock your card.\n" +
               "We apologise for any inconvienience.\n",StatusType.ERROR),

  ADMIN_MODE("The ATM is now in ADMIN MODE\n",StatusType.INFO),
  CANCELLED("The session was cancelled.",StatusType.INFO),

  SUCCESS("The Transaction was successfully completed.\n" +
          "Thank-you for using XYZ Bank :)\n",StatusType.INFO);

  private String description;
  private final StatusType type;

  private SessionStatus(String description, StatusType type) {
    this.description = description;
    this.type = type;
  }

  public void updateDescription (String s) {
    description = s;
  }

  public StatusType getType () {
    return type;
  }

  @Override
  public String toString() {
    return description;
  }
}
