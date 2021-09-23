package R18_G2_ASM1;

import org.junit.jupiter.api.Assertions;

import java.util.HashMap;

import R18_G2_ASM1.MoneyStack;
import R18_G2_ASM1.MoneyType;
import org.junit.jupiter.api.Test;

public class MoneyStackTest {

    @Test
    public void testSetup() {
        MoneyStack testMoneyStack = new MoneyStack();
        MoneyStack wdMoneyStack = new MoneyStack();
        Assertions.assertTrue(testMoneyStack.totalMoney().intValue() == 0);
    }
}