package R18_G2_ASM1;
public class CashDispensor extends ATMComponent {

  public void depositMoney(MoneyStack m, MoneyStack balance) {
    balance.addMoneyStack(m);
  }

  public void ejectMoney(MoneyStack m, MoneyStack balance) {
    balance.withdraw(m);
  }
  
}
