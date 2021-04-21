package models.accounts;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreditTest {
    Credit c;

    @Before
    public void setUp() throws Exception{
        c = new Credit(123456, -500.00, -750.00, 0.15);
    }

    @Test
    public void depositValidAmount() {
        assertTrue(c.deposit(50));
        assertEquals(-450.0, c.getAccountBalance(), 0.001);
    }

    @Test
    public void depositNegativeAmount(){
        assertFalse(c.deposit(-50));
        assertEquals(-500, c.getAccountBalance(), 0.001);
    }

    @Test
    public void depositInvalidPositiveAmount(){
        assertFalse(c.deposit(501));
        assertEquals(-500, c.getAccountBalance(), 0.001);
    }

    @Test
    public void withdrawValidAmount() {
        assertTrue(c.withdraw(20));
        assertEquals(-520, c.getAccountBalance(), 0.001);
    }

    @Test
    public void withdrawInvalidAmount() {
        assertFalse(c.withdraw(-20));
        assertEquals(-500, c.getAccountBalance(), 0.001);
    }

    @Test
    public void withdrawInvalidPositiveAmount() {
        assertFalse(c.withdraw(500));
        assertEquals(-500, c.getAccountBalance(), 0.001);
    }

    @Test
    public void transferValidAmount() {
        Account credit = new Credit(123456, -500.00, -750.00, 0.15);
        assertTrue(c.transfer(credit, 20));
        assertEquals(-520, c.getAccountBalance(), 0.001);
        assertEquals(-480, credit.getAccountBalance(), 0.001);
    }

    @Test
    public void transferInvalidAmount() {
        Account credit = new Credit(123456, -500.00, -750.00, 0.15);
        assertFalse(c.transfer(credit, -251));
        assertEquals(-500, c.getAccountBalance(), 0.001);
        assertEquals(-500, credit.getAccountBalance(), 0.001);
    }

    @Test
    public void transferInvalidPositiveAmount() {
        Account credit = new Credit(123456, -500.00, -750.00, 0.15);
        assertFalse(c.transfer(credit, 251));
        assertEquals(-500, c.getAccountBalance(), 0.001);
        assertEquals(-500, credit.getAccountBalance(), 0.001);
    }

    @Test
    public void transferOverDepositSecondAccount() {
        Account credit = new Credit(123456, -150.00, -520.00, 0.15);
        assertFalse(c.transfer(credit, 200));
        assertEquals(-500, c.getAccountBalance(), 0.001);
        assertEquals(-150, credit.getAccountBalance(), 0.001);
    }

    @Test
    public void calculateInterest() {
        c.calculateInterest();
        assertEquals(-580.90, c.getAccountBalance(), 0.001);
    }
}