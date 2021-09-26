package R18_G2_ASM1;

/**
 * Represents the physical Cash Dispensor of an ATM.
 * @author Tim Schmid
 * @version 1.0
 */
public class CashDispensor {

  /**
   * Deposits money from an ATM
   * @param m the amount of money that is to be deposited into the ATM represented by a MoneyStack object
   * @param balance the amount of money currently in the ATM rpresented by a MoneyStack Object
   */
  public void depositMoney(MoneyStack m, MoneyStack balance) {
    balance.addMoneyStack(m);
  }
  /**
   * Ejects money from an ATM
   * @param m the amount of money that is to be withdrawn into the ATM represented by a MoneyStack object
   * @param balance the amount of money currently in the ATM represented by a MoneyStack Object
   */
  public void ejectMoney(MoneyStack m, MoneyStack balance) {
    balance.withdraw(m);
  }
}
