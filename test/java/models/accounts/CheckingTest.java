package models.accounts;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckingTest {
    Checking c;

    @Before
    public void setUp() throws Exception {
        c = new Checking(123456, 500, 100);
    }

    @Test
    public void depositValidAmount(){
        assertTrue(c.deposit(20));
        assertEquals(520, c.getAccountBalance(), 0.001);
    }

    @Test
    public void depositInvalidAmount(){
        assertFalse(c.deposit(-500));
        assertEquals(500, c.getAccountBalance(), 0.001);
    }

    @Test
    public void withdrawValidAmount() {
        assertTrue(c.withdraw(20));
        assertEquals(480, c.getAccountBalance(), 0.001);
    }

    @Test
    public void withdrawInvalidAmount() {
        assertFalse(c.withdraw(-20));
        assertEquals(500, c.getAccountBalance(), 0.001);
    }

    @Test
    public void withdrawWithBalanceFee(){
        assertTrue(c.withdraw(450));
        assertEquals(20, c.getAccountBalance(), 0.001);
    }

    @Test
    public void withdrawInvalidPositiveAmount() {
        assertFalse(c.withdraw(600));
        assertEquals(500, c.getAccountBalance(), 0.001);
    }

    @Test
    public void transferValidAmount() {
        Account checking = new Checking(123456, 500, 100);
        assertTrue(c.transfer(checking, 20));
        assertEquals(480, c.getAccountBalance(), 0.001);
        assertEquals(520, checking.getAccountBalance(), 0.001);
    }

    @Test
    public void transferInvalidAmount() {
        Account checking = new Checking(123456, 500, 100);
        assertFalse(c.transfer(checking, -20));
        assertEquals(500, c.getAccountBalance(), 0.001);
        assertEquals(500, checking.getAccountBalance(), 0.001);
    }

    @Test
    public void transferInvalidPositiveAmount() {
        Account checking = new Checking(123456, 500, 100);
        assertFalse(c.transfer(checking, 501));
        assertEquals(500, c.getAccountBalance(), 0.001);
        assertEquals(500, checking.getAccountBalance(), 0.001);
    }

    @Test
    public void transferValidAmountWithFee() {
        System.out.println(c.getAccountBalance());
        Account checking = new Checking(123456, 500, 100);
        assertTrue(c.transfer(checking, 450));
        assertEquals(20, c.getAccountBalance(), 0.001);
        assertEquals(950, checking.getAccountBalance(), 0.001);
    }
}