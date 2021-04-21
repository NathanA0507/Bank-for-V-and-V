package models.accounts;

public class Credit extends Account {
    private double creditLimit;
    private double interestRate;

    public Credit() {
    }

    public Credit(double creditLimit, double interestRate) {
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public Credit(int accountNumber, double accountBalance, double creditLimit, double interestRate) {
        super(accountNumber, accountBalance);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    @Override
    public boolean deposit(double amount) {
        if(this.getAccountBalance() + amount < 0){
            return super.deposit(amount);
        }
        return false;
    }

    @Override
    public boolean withdraw(double amount) {
        if (this.creditLimit < this.getAccountBalance() - amount) {
            if(amount > 0){
                this.setAccountBalance(this.getAccountBalance() - amount);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean transfer(Account destination, double amount) {
        if (this.creditLimit < this.getAccountBalance() - amount){
            if(destination != null){
                if(amount > 0){
                    boolean result = this.withdraw(amount);
                    if(!result){
                        return false;
                    }

                    result &= destination.deposit(amount);
                    if(!result){
                        this.deposit(amount);
                    }

                    return result;
                }
            }
            return false;
        }
        return false;
    }

    public void calculateInterest() {
        double principle = super.getAccountBalance();

        double amount = principle * Math.pow(1 + (this.interestRate / 365), 365);
        this.setAccountBalance(amount);
    }

}
