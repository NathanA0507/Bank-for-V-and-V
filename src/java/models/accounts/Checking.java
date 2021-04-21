package models.accounts;

public class Checking extends Account {
    private double minBalance;

    public Checking() {
    }

    public Checking(int accountNumber, double accountBalance) {
        super(accountNumber, accountBalance);
    }

    public Checking(int accountNumber, double accountBalance, double minBalance) {
        super(accountNumber, accountBalance);
        this.minBalance = minBalance;
    }

    @Override
    public boolean withdraw(double amount) {
        boolean result = super.withdraw(amount);

        if(super.getAccountBalance() < this.minBalance){
            this.chargeBalanceFee();
        }

        return result;
    }

    // There is a fee of $30 if your account balance drops below the minimum
    private void chargeBalanceFee(){
        super.setAccountBalance(super.getAccountBalance() - 30.00);
    }


}
