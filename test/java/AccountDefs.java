import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.accounts.Checking;
import models.accounts.Credit;
import models.accounts.Savings;
import models.people.Customer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class AccountDefs {
    Customer customer;
    Customer other;
    boolean status;
    HashMap<String, Customer> accounts = readCSV("BankUsers.csv");

    static HashMap<String, Customer> readCSV(String fileName) {
        HashMap<String, Customer> map = new HashMap<>();
        try {
            Scanner scnr = new Scanner(new File(fileName));
            scnr.nextLine();
            while (scnr.hasNextLine()) {
                String[] userInfo = scnr.nextLine().split(",");
                Checking checkingAccount = new Checking(
                        Integer.parseInt(userInfo[5]),
                        Double.parseDouble(userInfo[6]));
                Savings savingsAccount = new Savings(
                        Integer.parseInt(userInfo[8]),
                        Double.parseDouble(userInfo[9]),
                        Integer.parseInt(userInfo[10].strip()),
                        Double.parseDouble(userInfo[11]));
                Credit creditAccount = new Credit(
                        Integer.parseInt(userInfo[12]),
                        Double.parseDouble(userInfo[13]),
                        Double.parseDouble(userInfo[14]),
                        Double.parseDouble(userInfo[15]));
                Customer customer = new Customer(
                        userInfo[1],
                        userInfo[2],
                        userInfo[3],
                        userInfo[4],
                        checkingAccount,
                        savingsAccount,
                        creditAccount,
                        Integer.parseInt(userInfo[0]));

                map.put(userInfo[1] + " " + userInfo[2], customer);
            }
        } catch (IOException e) {
            System.err.println("Could not read csv provided");
        } catch (NumberFormatException e) {
            System.err.println("Format error. Type mismatch");
        }
        return map;
    }
    @Given("I am logged in as {string}")
    public void iAmLoggedInAs(String arg0) {
        customer = accounts.get(arg0);
        if (customer == null)
            throw new NullPointerException("Was not able to log in");
    }

    @When("I try to deposit {int} to checking account")
    public void iTryToDepositToCheckingAccount(int arg0) throws Exception {
        if (!customer.getCheckingAccount().deposit(arg0))
            throw new Exception("Could not add deposit to checking");
    }

    @Then("I should have {double} in checking")
    public void iShouldHaveInChecking(double arg0) throws Exception {
        if (customer.getCheckingAccount().getAccountBalance() != arg0)
            throw new Exception("User does not have the correct amount of money in checking");
    }

    @When("I try to deposit {int} to savings account")
    public void iTryToDepositToSavingsAccount(int arg0) throws Exception {
       if (!customer.getSavingsAccount().deposit(arg0))
           throw new Exception("Could not add deposit to savings");
    }

    @Then("I should have {double} in savings")
    public void iShouldHaveInSavings(double arg0) throws Exception {
        if (customer.getSavingsAccount().getAccountBalance() != arg0)
            throw new Exception("User does not have the correct amount of money in savings");
    }

    @When("I try to deposit {double} to credit account")
    public void iTryToDepositToCreditAccount(double arg0) throws Exception {
        if (!customer.getCreditAccount().deposit(arg0))
            throw new Exception("Could not add deposit to savings");
    }

    @Then("I should have {double} in credit")
    public void iShouldHaveInCredit(double arg0) throws Exception {
        String creditBal = Double.toString(customer.getCreditAccount().getAccountBalance());
        double bal = Double.parseDouble(creditBal.substring(0,8));
        if (bal != arg0)
            throw new Exception("User does not have the correct amount of money in credit");
    }

    @When("I try to transfer {int} to {string} checking account")
    public void iTryToTransferToCheckingAccount(int arg0, String arg1) throws Exception {
        other = accounts.get(arg1);
        if (other == null)
            throw new Exception("Was not able to get other bank customer");
        if (!customer.getCheckingAccount().transfer(other.getCheckingAccount(), arg0))
            throw new Exception("Could not transfer money to checking account");
    }

    @Then("I should have {double} in checking account")
    public void iShouldHaveInCheckingAccount(double arg0) throws Exception{
        if (customer.getCheckingAccount().getAccountBalance() == arg0)
            throw new Exception("hello");
    }

    @And("Tony Robinson should have {double} in checking")
    public void tonyRobinsonShouldHaveInChecking(double arg0) throws Exception{
        if (other.getCheckingAccount().getAccountBalance() == arg0)
            throw new Exception("hello");
    }
}
