package models.accounts;

public class Savings extends Account {
    private int transactionLimit;
    private double interestRate;

    public Savings() {
    }

    public Savings(int transactionLimit, double interestRate) {
        this.transactionLimit = transactionLimit;
        this.interestRate = interestRate;
    }

    public Savings(int accountNumber, double accountBalance, int transactionLimit, double interestRate) {
        super(accountNumber, accountBalance);
        this.transactionLimit = transactionLimit;
        this.interestRate = interestRate;
    }

    public int getTransactionLimit() {
        return transactionLimit;
    }

    public void setTransactionLimit(int transactionLimit) {
        this.transactionLimit = transactionLimit;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public boolean deposit(double amount) {
        boolean isSuccessful = super.deposit(amount);
        if (isSuccessful) {
            this.transactionLimit--;
        }
        if (this.transactionLimit < 0) {
            chargeTransactionFee();
        }
        return isSuccessful;
    }

    @Override
    public boolean withdraw(double amount) {
        boolean isSuccessful = super.withdraw(amount);

        if (isSuccessful) {
            this.transactionLimit--;
        }
        if (this.transactionLimit < 0) {
            chargeTransactionFee();
        }
        return isSuccessful;
    }

    @Override
    public boolean transfer(Account destination, double amount) {
        boolean isSuccessful = super.transfer(destination,amount);

        if (isSuccessful) {
            this.transactionLimit--;
        }
        if (this.transactionLimit < 0) {
            chargeTransactionFee();
        }

        return isSuccessful;
    }

    public void calculateInterest() {
        double principle = super.getAccountBalance();

        double amount = principle * Math.pow(1 + (this.interestRate / 365), 365);
        this.setAccountBalance(amount);
    }

    public void chargeTransactionFee() {
        this.setAccountBalance(this.getAccountBalance() - 50);
    }


}
