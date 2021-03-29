import models.accounts.Checking;
import models.accounts.Credit;
import models.accounts.Savings;
import models.people.Customer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        HashMap<String, Customer> accounts = readCSV("BankUsers.csv");
        Scanner scnr = new Scanner(System.in);
        int choice = 0;
        do {
            Customer user = null;
            System.out.println("The banking app main menu\n1. log in with full name\n2. list accounts\n3. exit");
            try {
                choice = Integer.parseInt(scnr.nextLine().strip());
            } catch (NumberFormatException e) {
                System.err.println("Format error: please input an integer");
                continue;
            }
            System.out.println("---------------------");
            if (choice == 1) {
                while (true) {
                    System.out.println("Login page: (input nothing to go back to the main menu)");
                    String accountName = scnr.nextLine().strip();
                    if (accountName.isEmpty())
                        break;
                    user = findAccount(accounts, accountName);
                    if (user == null) {
                        System.out.println("Account doesn't exist. Please try again");
                        continue;
                    }
                    break;
                }

                if (user == null)
                    continue;
                System.out.println("---------------------\nWelcome " + user.getFirstName() + ".");

                while (true) {
                    System.out.println("1. Checking\n2. Savings\n3. Credit\n4. Exit");
                    int accountChoice;
                    try{
                        accountChoice = Integer.parseInt(scnr.nextLine().strip());
                    } catch (NumberFormatException e) {
                        System.err.println("Format error: please input an integer");
                        continue;
                    }

                    System.out.println("---------------------");
                    // checking
                    if (accountChoice == 1) {
                        while (true) {
                            System.out.println("1. View balance\n2. Deposit\n3.Withdraw\n4.Transfer money\n5. Exit");
                            int actionChoice;
                            try {
                                actionChoice = Integer.parseInt(scnr.nextLine().strip());
                            } catch (NumberFormatException e) {
                                System.err.println("Format error: please input an integer");
                                continue;
                            }
                            System.out.println("---------------------");
                            if (actionChoice == 1) {
                                displayCheckingBalance(user);
                                System.out.println("---------------------");
                            }
                            else if (actionChoice == 2) {
                                while (true) {
                                    System.out.println("Amount to deposit:");
                                    double amount;
                                    try {
                                        amount = Double.parseDouble(scnr.nextLine().strip());
                                    } catch (NumberFormatException e) {
                                        System.err.println("Format error: please input an integer");
                                        continue;
                                    }
                                    if (depositChecking(user, amount)) {
                                        System.out.println("Deposit was successful");
                                        break;
                                    }
                                    else {
                                        System.out.println("Unable to deposit.");
                                    }
                                }
                                System.out.println("---------------------");
                            }
                            else if (actionChoice == 3) {
                                while (true) {
                                    System.out.println("Amount to withdraw:");
                                    double amount;
                                    try {
                                        amount = Double.parseDouble(scnr.nextLine().strip());
                                    } catch (NumberFormatException e) {
                                        System.err.println("Format error: please input an integer");
                                        continue;
                                    }
                                    if (withdrawChecking(user, amount)) {
                                        System.out.println("Withdraw was successful");
                                        break;
                                    }
                                    else {
                                        System.out.println("Unable to withdraw.");
                                    }
                                }
                                System.out.println("---------------------");
                            }
                            else if (actionChoice == 4) {
                                displayCustomers(accounts);
                                while (true) {
                                    System.out.println("Please input person's full name you wish to transfer: (input nothing to exit)");
                                    String accountName = scnr.nextLine().strip();
                                    if (accountName.isEmpty())
                                        break;
                                    Customer other = findAccount(accounts, accountName);
                                    if (other == null) {
                                        System.out.println("Account doesn't exist. Please try again");
                                        continue;
                                    }
                                    System.out.println("Their account type: \n1. Checking\n2. Savings\n3. Credit");
                                    int accountType;
                                    try {
                                        accountType = Integer.parseInt(scnr.nextLine());
                                    } catch (NumberFormatException e) {
                                        System.err.println("Format error: please input an integer");
                                        continue;
                                    }
                                    System.out.println("Amount to transfer:");
                                    double amount;
                                    try {
                                        amount = Double.parseDouble(scnr.nextLine().strip());
                                    } catch (NumberFormatException e) {
                                        System.err.println("Format error: please input an integer");
                                        continue;
                                    }
                                    if (accountType == 1) {
                                        if (transferCheckingToChecking(user, other, amount)) {
                                            System.out.println("Amount has been transferred");
                                            break;
                                        }
                                        else
                                            System.out.println("Invalid transfer. Please try again");
                                    }
                                    else if (accountType == 2) {
                                        if (transferCheckingToSavings(user, other, amount)) {
                                            System.out.println("Amount has been transferred");
                                            break;
                                        }
                                        else
                                            System.out.println("Invalid transfer. Please try again");
                                    }
                                    else if (accountType == 3) {
                                        if (transferCheckingToCredit(user, other, amount)) {
                                            System.out.println("Amount has been transferred");
                                            break;
                                        }
                                        else
                                            System.out.println("Invalid transfer. Please try again");
                                    }
                                }
                                System.out.println("---------------------");
                            }
                            else if (actionChoice == 5) {
                                break;
                            }
                            else {
                                System.out.println("Invalid option selected. Please try again.");
                            }
                        }
                    }
                    // savings
                    else if (accountChoice == 2) {
                        while (true) {
                            System.out.println("1. View balance\n2. Deposit\n3.Withdraw\n4.Transfer money\n5. Exit");
                            int actionChoice;
                            try {
                                actionChoice = Integer.parseInt(scnr.nextLine().strip());
                            } catch (NumberFormatException e) {
                                System.err.println("Format error: please input an integer");
                                continue;
                            }
                            System.out.println("---------------------");
                            if (actionChoice == 1) {
                                displaySavingsBalance(user);
                                System.out.println("---------------------");
                            }
                            else if (actionChoice == 2) {
                                while (true) {
                                    System.out.println("Amount to deposit:");
                                    double amount;
                                    try {
                                        amount = Double.parseDouble(scnr.nextLine().strip());
                                    } catch (NumberFormatException e) {
                                        System.err.println("Format error: please input an integer");
                                        continue;
                                    }
                                    if (depositSavings(user, amount)) {
                                        System.out.println("Deposit was successful");
                                        break;
                                    }
                                    else {
                                        System.out.println("Unable to deposit.");
                                    }
                                }
                                System.out.println("---------------------");
                            }
                            else if (actionChoice == 3) {
                                while (true) {
                                    System.out.println("Amount to withdraw:");
                                    double amount;
                                    try {
                                        amount = Double.parseDouble(scnr.nextLine().strip());
                                    } catch (NumberFormatException e) {
                                        System.err.println("Format error: please input an integer");
                                        continue;
                                    }
                                    if (withdrawSavings(user, amount)) {
                                        System.out.println("Withdraw was successful");
                                        break;
                                    }
                                    else {
                                        System.out.println("Unable to withdraw.");
                                    }
                                }
                                System.out.println("---------------------");
                            }
                            else if (actionChoice == 4) {
                                displayCustomers(accounts);
                                while (true) {
                                    System.out.println("Please input person's full name you wish to transfer: (input nothing to exit)");
                                    String accountName = scnr.nextLine().strip();
                                    if (accountName.isEmpty())
                                        break;
                                    Customer other = findAccount(accounts, accountName);
                                    if (other == null) {
                                        System.out.println("Account doesn't exist. Please try again");
                                        continue;
                                    }
                                    System.out.println("Their account type: \n1. Checking\n2. Savings\n3. Credit");
                                    int accountType;
                                    try {
                                        accountType = Integer.parseInt(scnr.nextLine());
                                    } catch (NumberFormatException e) {
                                        System.err.println("Format error: please input an integer");
                                        continue;
                                    }
                                    System.out.println("Amount to transfer:");
                                    double amount;
                                    try {
                                        amount = Double.parseDouble(scnr.nextLine().strip());
                                    } catch (NumberFormatException e) {
                                        System.err.println("Format error: please input an integer");
                                        continue;
                                    }
                                    if (accountType == 1) {
                                        if (transferSavingsToChecking(user, other, amount)) {
                                            System.out.println("Amount has been transferred");
                                            break;
                                        }
                                        else
                                            System.out.println("Invalid transfer. Please try again");
                                    }
                                    else if (accountType == 2) {
                                        if (transferSavingsToSavings(user, other, amount)) {
                                            System.out.println("Amount has been transferred");
                                            break;
                                        }
                                        else
                                            System.out.println("Invalid transfer. Please try again");
                                    }
                                    else if (accountType == 3) {
                                        if (transferSavingsToCredit(user, other, amount)) {
                                            System.out.println("Amount has been transferred");
                                            break;
                                        }
                                        else
                                            System.out.println("Invalid transfer. Please try again");
                                    }
                                }
                                System.out.println("---------------------");
                            }
                            else if (actionChoice == 5) {
                                break;
                            }
                            else {
                                System.out.println("Invalid option selected. Please try again.");
                            }
                        }
                    }
                    // credit
                    else if (accountChoice == 3) {
                        while (true) {
                            System.out.println("1. View balance\n2. Deposit\n3.Withdraw\n4.Transfer money\n5. Exit");
                            int actionChoice;
                            try {
                                actionChoice = Integer.parseInt(scnr.nextLine().strip());
                            } catch (NumberFormatException e) {
                                System.err.println("Format error: please input an integer");
                                continue;
                            }
                            System.out.println("---------------------");
                            if (actionChoice == 1) {
                                displayCreditBalance(user);
                                System.out.println("---------------------");
                            }
                            else if (actionChoice == 2) {
                                while (true) {
                                    System.out.println("Amount to deposit:");
                                    double amount;
                                    try {
                                        amount = Double.parseDouble(scnr.nextLine().strip());
                                    } catch (NumberFormatException e) {
                                        System.err.println("Format error: please input an integer");
                                        continue;
                                    }
                                    if (depositCredit(user, amount)) {
                                        System.out.println("Deposit was successful");
                                        break;
                                    }
                                    else {
                                        System.out.println("Unable to deposit.");
                                    }
                                }
                                System.out.println("---------------------");
                            }
                            else if (actionChoice == 3) {
                                while (true) {
                                    System.out.println("Amount to withdraw:");
                                    double amount;
                                    try {
                                        amount = Double.parseDouble(scnr.nextLine().strip());
                                    } catch (NumberFormatException e) {
                                        System.err.println("Format error: please input an integer");
                                        continue;
                                    }
                                    if (withdrawCredit(user, amount)) {
                                        System.out.println("Withdraw was successful");
                                        break;
                                    }
                                    else {
                                        System.out.println("Unable to withdraw.");
                                    }
                                }
                                System.out.println("---------------------");
                            }
                            else if (actionChoice == 4) {
                                displayCustomers(accounts);
                                while (true) {
                                    System.out.println("Please input person's full name you wish to transfer: (input nothing to exit)");
                                    String accountName = scnr.nextLine().strip();
                                    if (accountName.isEmpty())
                                        break;
                                    Customer other = findAccount(accounts, accountName);
                                    if (other == null) {
                                        System.out.println("Account doesn't exist. Please try again");
                                        continue;
                                    }
                                    System.out.println("Their account type: \n1. Checking\n2. Savings\n3. Credit");
                                    int accountType;
                                    try {
                                        accountType = Integer.parseInt(scnr.nextLine());
                                    } catch (NumberFormatException e) {
                                        System.err.println("Format error: please input an integer");
                                        continue;
                                    }
                                    System.out.println("Amount to transfer:");
                                    double amount;
                                    try {
                                        amount = Double.parseDouble(scnr.nextLine().strip());
                                    } catch (NumberFormatException e) {
                                        System.err.println("Format error: please input an integer");
                                        continue;
                                    }
                                    if (accountType == 1) {
                                        if (transferCreditToChecking(user, other, amount)) {
                                            System.out.println("Amount has been transferred");
                                            break;
                                        }
                                        else
                                            System.out.println("Invalid transfer. Please try again");
                                    }
                                    else if (accountType == 2) {
                                        if (transferCreditToSavings(user, other, amount)) {
                                            System.out.println("Amount has been transferred");
                                            break;
                                        }
                                        else
                                            System.out.println("Invalid transfer. Please try again");
                                    }
                                    else if (accountType == 3) {
                                        if (transferCreditToCredit(user, other, amount)) {
                                            System.out.println("Amount has been transferred");
                                            break;
                                        }
                                        else
                                            System.out.println("Invalid transfer. Please try again");
                                    }
                                }
                                System.out.println("---------------------");
                            }
                            else if (actionChoice == 5) {
                                break;
                            }
                            else {
                                System.out.println("Invalid option selected. Please try again.");
                            }
                        }
                    }
                    // exit
                    else if (accountChoice == 4) break;
                }
            }

            else if (choice == 2) {
                displayCustomers(accounts);
                System.out.println("---------------------");
            }

            else if (choice != 3)
                System.out.println("Error: please select a valid choice");

        } while (choice != 3);
        System.out.println("See ya later alligator!");
    }

    static Customer findAccount(HashMap<String, Customer> accounts, String accountName) {
        return accounts.getOrDefault(accountName, null);
    }

    static void displayCustomers(HashMap<String, Customer> accounts) {
        for (Map.Entry<String,Customer> entry : accounts.entrySet())
            System.out.println(entry.getKey());
    }
    static boolean displayCheckingBalance(Customer customer) {
        if (customer == null)
            return false;
        System.out.println("Balance: $" + customer.getCheckingAccount().getAccountBalance());
        return true;
    }

    static boolean displaySavingsBalance(Customer customer) {
        if (customer == null)
            return false;
        System.out.println("Balance: $" + customer.getSavingsAccount().getAccountBalance());
        return true;
    }

    static boolean displayCreditBalance(Customer customer) {
        if (customer == null)
            return false;
        System.out.println("Balance: $" + customer.getSavingsAccount().getAccountBalance());
        return true;
    }

    static boolean depositChecking(Customer customer, double amount) {
        return customer.getCheckingAccount().deposit(amount);
    }

    static boolean depositSavings(Customer customer, double amount) {
        return customer.getSavingsAccount().deposit(amount);
    }

    static boolean depositCredit(Customer customer, double amount) {
        return customer.getCreditAccount().deposit(amount);
    }

    static boolean withdrawChecking(Customer customer, double amount) {
        return customer.getCheckingAccount().withdraw(amount);
    }

    static boolean withdrawSavings(Customer customer, double amount) {
        return customer.getSavingsAccount().withdraw(amount);
    }

    static boolean withdrawCredit(Customer customer, double amount) {
        return customer.getCreditAccount().withdraw(amount);
    }

    static boolean transferCheckingToChecking(Customer user, Customer other, double amount) {
        return user.getCheckingAccount().transfer(other.getCheckingAccount(), amount);
    }

    static boolean transferCheckingToSavings(Customer user, Customer other, double amount) {
        return user.getCheckingAccount().transfer(other.getSavingsAccount(), amount);
    }

    static boolean transferCheckingToCredit(Customer user, Customer other, double amount) {
        return user.getCheckingAccount().transfer(other.getCreditAccount(), amount);
    }

    static boolean transferSavingsToChecking(Customer user, Customer other, double amount) {
        return user.getSavingsAccount().transfer(other.getCheckingAccount(), amount);
    }

    static boolean transferSavingsToSavings(Customer user, Customer other, double amount) {
        return user.getSavingsAccount().transfer(other.getSavingsAccount(), amount);
    }

    static boolean transferSavingsToCredit(Customer user, Customer other, double amount) {
        return user.getSavingsAccount().transfer(other.getCreditAccount(), amount);
    }

    static boolean transferCreditToChecking(Customer user, Customer other, double amount) {
        return user.getCreditAccount().transfer(other.getCheckingAccount(), amount);
    }

    static boolean transferCreditToSavings(Customer user, Customer other, double amount) {
        return user.getCreditAccount().transfer(other.getSavingsAccount(), amount);
    }

    static boolean transferCreditToCredit(Customer user, Customer other, double amount) {
        return user.getCreditAccount().transfer(other.getCreditAccount(), amount);
    }


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
}
