package R18_G2_ASM1;

import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import R18_G2_ASM1.MoneyStack;
import R18_G2_ASM1.MoneyType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyStackTest {

    @Test
    public void testSetup() {
        MoneyStack testMoneyStack = new MoneyStack();
        MoneyStack wdMoneyStack = new MoneyStack();
        Assertions.assertTrue(testMoneyStack.totalMoney().intValue() == 0);
    }

    @Test
    public void testAddMoney(){
        MoneyStack testMoneyStack = new MoneyStack();
        MoneyStack wdMoneyStack = new MoneyStack();
        try{
            testMoneyStack.addMoney(MoneyType.TEN_DOLLARS,10);
        }catch(Exception e){
            System.out.println("No Moneytype match");
        }
        Assertions.assertEquals(testMoneyStack.totalMoney().intValue(),100);
    }

    @Test
    public void testfreezeMoney(){
        MoneyStack testMoneyStack = new MoneyStack();
        testMoneyStack.freezeMoney();
        Assertions.assertFalse(testMoneyStack.getstatusOfMoney());
    }

    @Test
    public void testActivateCash(){
        MoneyStack testMoneyStack = new MoneyStack();
        testMoneyStack.activateCash();
        Assertions.assertTrue(testMoneyStack.getstatusOfMoney());
    }

    @Test
    public void testStatusOfMoney(){
        MoneyStack testMoneyStack = new MoneyStack();
        testMoneyStack.freezeMoney();
        testMoneyStack.activateCash();
        Assertions.assertTrue(testMoneyStack.getstatusOfMoney());
    }

    @Test
    public void testaddMoneyStacknull(){
        MoneyStack testMoneyStack = new MoneyStack();
        testMoneyStack.addMoneyStack(null);
        Assertions.assertEquals(testMoneyStack.totalMoney().intValue(),0);
    }

    @Test
    public void testaddMoneyStack(){
        MoneyStack testMoneyStack = new MoneyStack();
        MoneyStack wdMoneyStack= new MoneyStack();
        try{
            testMoneyStack.addMoney(MoneyType.TEN_DOLLARS,10);
            testMoneyStack.addMoney(MoneyType.HUNDRED_DOLLARS,10);
            testMoneyStack.addMoney(MoneyType.TEN_CENTS,8);
        }catch (IOException e){
            assertEquals(IOException.class, e.getClass());
        }
        wdMoneyStack.addMoneyStack(testMoneyStack);
        Assertions.assertTrue(testMoneyStack.totalMoney().compareTo(wdMoneyStack.totalMoney()) == 0);
    }

    @Test
    public void testwithdrawStackFalse(){
        MoneyStack testMoneyStack = new MoneyStack();
        MoneyStack wdMoneyStack= new MoneyStack();
        try{
            testMoneyStack.addMoney(MoneyType.TEN_DOLLARS,10);
            testMoneyStack.addMoney(MoneyType.HUNDRED_DOLLARS,10);
            testMoneyStack.addMoney(MoneyType.TEN_CENTS,8);
        }catch (IOException e){
            assertEquals(IOException.class, e.getClass());
        }
        Assertions.assertFalse(wdMoneyStack.withdrawStack(testMoneyStack));
    }

    @Test
    public void testwithdrawStackTrue(){
        MoneyStack testMoneyStack = new MoneyStack();
        MoneyStack wdMoneyStack= new MoneyStack();
        try{
            testMoneyStack.addMoney(MoneyType.TEN_DOLLARS,10);
            testMoneyStack.addMoney(MoneyType.HUNDRED_DOLLARS,10);
            testMoneyStack.addMoney(MoneyType.TEN_CENTS,8);
        }catch(Exception e){
            System.out.println("No Moneytype match");
        }
        try{
            wdMoneyStack.addMoney(MoneyType.TEN_DOLLARS,4);
            wdMoneyStack.addMoney(MoneyType.HUNDRED_DOLLARS,3);
            wdMoneyStack.addMoney(MoneyType.TEN_CENTS,2);
        }catch (IOException e){
            assertEquals(IOException.class, e.getClass());
        }
        Assertions.assertTrue(testMoneyStack.withdrawStack(wdMoneyStack));
    }

    @Test
    public void testQuery(){
        MoneyStack testMoneyStack = new MoneyStack();
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        try{
            testMoneyStack.addMoney(MoneyType.TEN_DOLLARS,3);
            testMoneyStack.addMoney(MoneyType.HUNDRED_DOLLARS,10);
            testMoneyStack.addMoney(MoneyType.TEN_CENTS,8);
            a = testMoneyStack.query(MoneyType.TEN_DOLLARS);
            b = testMoneyStack.query(MoneyType.HUNDRED_DOLLARS);
            c = testMoneyStack.query(MoneyType.TEN_CENTS);
            d = testMoneyStack.query(MoneyType.FIFTY_CENTS);
        }catch (IOException e){
            assertEquals(IOException.class, e.getClass());
        }
        Assertions.assertEquals(3,a);
        Assertions.assertEquals(10,b);
        Assertions.assertEquals(8,c);
        Assertions.assertEquals(0,d);

    }

    @Test
    public void testCanWithdralCoins(){
        MoneyStack testMoneyStack = new MoneyStack();
        try{
            testMoneyStack.addMoney(MoneyType.FIFTY_CENTS,10);
            testMoneyStack.addMoney(MoneyType.TWENTY_CENTS,10);
            testMoneyStack.addMoney(MoneyType.TEN_CENTS,10);
        }catch (IOException e){
            assertEquals(IOException.class, e.getClass());
        }
        MoneyStack wdMoneyStack = new MoneyStack();
        try{
            testMoneyStack.addMoney(MoneyType.TWENTY_CENTS,3);
        }catch (IOException e){
            assertEquals(IOException.class, e.getClass());
        }
        Assertions.assertTrue(testMoneyStack.canWithdraw(wdMoneyStack));
    }

    @Test
    public void testWithdralNotEnoughMoney(){
        MoneyStack testMoneyStack = new MoneyStack();
        try{
            testMoneyStack.addMoney(MoneyType.TEN_CENTS,10);
        }catch (IOException e){
            assertEquals(IOException.class, e.getClass());
        }
        MoneyStack wdMoneyStack = new MoneyStack();
        try{
            wdMoneyStack.addMoney(MoneyType.TWENTY_CENTS,7);
        }catch (IOException e){
            assertEquals(IOException.class, e.getClass());
        }
        Assertions.assertFalse(testMoneyStack.canWithdraw(wdMoneyStack));
        Assertions.assertFalse(testMoneyStack.withdraw(wdMoneyStack));
    }

    @Test
    public void testCanWithdralNotes(){
        MoneyStack testMoneyStack = new MoneyStack();
        try{
            testMoneyStack.addMoney(MoneyType.HUNDRED_DOLLARS,100);
            testMoneyStack.addMoney(MoneyType.FIFTY_DOLLARS,100);
            testMoneyStack.addMoney(MoneyType.TWENTY_DOLLARS,100);
            testMoneyStack.addMoney(MoneyType.TEN_DOLLARS,100);
            testMoneyStack.addMoney(MoneyType.FIVE_DOLLARS,100);
            testMoneyStack.addMoney(MoneyType.TWO_DOLLARS,100);
            testMoneyStack.addMoney(MoneyType.ONE_DOLLAR,100);
        }catch (IOException e){
            assertEquals(IOException.class, e.getClass());
        }
        MoneyStack wdMoneyStack = new MoneyStack();
        try{
            wdMoneyStack.addMoney(MoneyType.HUNDRED_DOLLARS,2);
            wdMoneyStack.addMoney(MoneyType.FIFTY_DOLLARS,1);
            wdMoneyStack.addMoney(MoneyType.TWENTY_DOLLARS,1);
            wdMoneyStack.addMoney(MoneyType.TEN_DOLLARS,1);
            wdMoneyStack.addMoney(MoneyType.FIVE_DOLLARS,1);
            wdMoneyStack.addMoney(MoneyType.TWO_DOLLARS,1);
            wdMoneyStack.addMoney(MoneyType.ONE_DOLLAR,1);
        }catch (IOException e){
            assertEquals(IOException.class, e.getClass());
        }
        Assertions.assertTrue(testMoneyStack.canWithdraw(wdMoneyStack));
    }

    @Test
    public void testWithdralAllMoney(){
        MoneyStack testMoneyStack = new MoneyStack();
        try{
            testMoneyStack.addMoney(MoneyType.HUNDRED_DOLLARS,100);
            testMoneyStack.addMoney(MoneyType.FIFTY_DOLLARS,100);
            testMoneyStack.addMoney(MoneyType.TWENTY_DOLLARS,100);
            testMoneyStack.addMoney(MoneyType.TEN_DOLLARS,100);
            testMoneyStack.addMoney(MoneyType.FIVE_DOLLARS,100);
            testMoneyStack.addMoney(MoneyType.TWO_DOLLARS,100);
            testMoneyStack.addMoney(MoneyType.ONE_DOLLAR,100);
        }catch (IOException e){
            assertEquals(IOException.class, e.getClass());
        }
        MoneyStack wdMoneyStack = new MoneyStack();
        try{
            wdMoneyStack.addMoney(MoneyType.HUNDRED_DOLLARS,100);
            wdMoneyStack.addMoney(MoneyType.FIFTY_DOLLARS,100);
            wdMoneyStack.addMoney(MoneyType.TWENTY_DOLLARS,100);
            wdMoneyStack.addMoney(MoneyType.TEN_DOLLARS,100);
            wdMoneyStack.addMoney(MoneyType.FIVE_DOLLARS,100);
            wdMoneyStack.addMoney(MoneyType.TWO_DOLLARS,100);
            wdMoneyStack.addMoney(MoneyType.ONE_DOLLAR,100);
        }catch (IOException e){
            assertEquals(IOException.class, e.getClass());
        }
        Assertions.assertTrue(testMoneyStack.withdraw(wdMoneyStack));
        Assertions.assertTrue(testMoneyStack.totalMoney().compareTo(BigDecimal.ZERO) == 0);
    }

    @Test
    public void testWithdralMoney(){
        MoneyStack testMoneyStack = new MoneyStack();
        try{
            testMoneyStack.addMoney(MoneyType.HUNDRED_DOLLARS,100);
        }catch (IOException e){
            assertEquals(IOException.class, e.getClass());
        }
        MoneyStack wdMoneyStack = new MoneyStack();
        try{
            wdMoneyStack.addMoney(MoneyType.HUNDRED_DOLLARS,2);
        }catch (IOException e){
            assertEquals(IOException.class, e.getClass());
        }
        BigDecimal wdMoneyAmout = wdMoneyStack.totalMoney();
        BigDecimal nowMoneyAmount = testMoneyStack.totalMoney();
        Assertions.assertTrue(testMoneyStack.withdraw(wdMoneyStack));
    }

    @Test
    public void testWithdralMoneyFalse(){
        MoneyStack testMoneyStack = new MoneyStack();
        try{
            testMoneyStack.addMoney(MoneyType.HUNDRED_DOLLARS,100);
        }catch (IOException e){
            assertEquals(IOException.class, e.getClass());
        }
        MoneyStack wdMoneyStack = new MoneyStack();
        try{
            wdMoneyStack.addMoney(MoneyType.FIFTY_DOLLARS,1);
        }catch (IOException e){
            assertEquals(IOException.class, e.getClass());
        }
        Assertions.assertFalse(testMoneyStack.withdraw(wdMoneyStack));
    }

}