package R18_G2_ASM1;
public enum KeypadButton {
  ONE(1,false),
  TWO(2,false),
  THREE(3,false),
  FOUR(4,false),
  FIVE(5,false),
  SIX(6,false),
  SEVEN(7,false),
  EIGHT(8,false),
  NINE(9,false),
  CANCEL(10,false),
  ENTER(11,false);

  private final int code;
  private boolean active;

  private KeypadButton(int code, boolean active) {
    this.code = code;
    this.active = active;
  }

  public int getCode() {
    return code;
  }
  public boolean isActive() {
    return active;
  }
  public void activate() {
    active = true;
  }
  public void deactivate() {
    active = false;
  } 
  public static void activateAll() {
    for (KeypadButton k: KeypadButton.values()) {
      k.active = true;
    }
  }
  public static void deactivateAll() {
    for (KeypadButton k: KeypadButton.values()) {
      k.active = false;
    }
  } 

}

