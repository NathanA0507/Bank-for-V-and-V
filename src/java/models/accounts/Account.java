package models.accounts;

public abstract class Account {
    private int accountNumber;
    private double accountBalance;

    public Account(){}


    public Account(int accountNumber, double accountBalance) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public boolean deposit(double amount){
        if(amount > 0){
            this.accountBalance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount){
        if (amount > 0 && accountBalance > amount){
            accountBalance -= amount;
            return true;
        }
        return false;
    }

    public boolean transfer(Account destination, double amount){
        if(destination != null){
            if(amount > 0 && accountBalance > amount){
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

}
