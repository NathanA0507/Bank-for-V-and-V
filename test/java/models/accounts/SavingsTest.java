package models.accounts;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SavingsTest {
    Savings s;

    @Before
    public void setUp() throws Exception {
        s = new Savings(123456, 200.00, 2, 0.004);
    }

    @Test
    public void depositValidAmount() {
        assertTrue(s.deposit(50));
        assertEquals(250, s.getAccountBalance(), 0.001);
    }

    @Test
    public void depositInvalidAmount() {
        assertFalse(s.deposit(-5));
        assertEquals(200, s.getAccountBalance(), 0.001);
    }

    @Test
    public void depositTransactionLimitFee() {
        s.setTransactionLimit(0);
        assertTrue(s.deposit(100));
        assertEquals(250, s.getAccountBalance(), 0.001);
    }

    @Test
    public void withdrawValidAmount() {
        assertTrue(s.withdraw(50));
        assertEquals(150, s.getAccountBalance(), 0.001);
    }

    @Test
    public void withdrawInvalidAmount() {
        assertFalse(s.withdraw(-500));
        assertEquals(200, s.getAccountBalance(), 0.001);
    }

    @Test
    public void withdrawValidAmountWithFee() {
        s.setTransactionLimit(0);
        assertTrue(s.withdraw(50));
        assertEquals(100, s.getAccountBalance(), 0.001);
    }

    @Test
    public void transferValidAmount() {
        Account a = new Savings(123456, 200, 5, 0.04);
        assertTrue(s.transfer(a, 100));
        assertEquals(100, s.getAccountBalance(), 0.001);
        assertEquals(300, a.getAccountBalance(), 0.001);
    }

    @Test
    public void transferInvalidAmount() {
        Account a = new Savings(123456, 200, 5, 0.04);
        assertFalse(s.transfer(a, -251));
        assertEquals(200, s.getAccountBalance(), 0.001);
        assertEquals(200, a.getAccountBalance(), 0.001);
    }

    @Test
    public void calculateInterest() {
        s.calculateInterest();
        assertEquals(200.80, s.getAccountBalance(), 0.01);
    }

}