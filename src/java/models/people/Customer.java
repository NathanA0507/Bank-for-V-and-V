package models.people;

import models.accounts.Checking;
import models.accounts.Credit;
import models.accounts.Savings;

public class Customer extends Person{
    Checking checkingAccount;
    Savings savingsAccount;
    Credit creditAccount;
    int id;

    public Customer() {}

    public Customer(String firstName, String lastName, String address, String phoneNumber, Checking checkingAccount, Savings savingsAccount, Credit creditAccount, int id) {
        super(firstName, lastName, address, phoneNumber);
        this.checkingAccount = checkingAccount;
        this.savingsAccount = savingsAccount;
        this.creditAccount = creditAccount;
        this.id = id;
    }


    public Checking getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(Checking checkingAccount) {
        this.checkingAccount = checkingAccount;
    }

    public Savings getSavingsAccount() {
        return savingsAccount;
    }

    public void setSavingsAccount(Savings savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    public Credit getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(Credit creditAccount) {
        this.creditAccount = creditAccount;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
