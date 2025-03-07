import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Account {
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public Account(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive");
            return;
        }
        balance += amount;
        System.out.println("Deposited: $" + amount);
        System.out.println("New balance: $" + balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds");
            return;
        }
        balance -= amount;
        System.out.println("Withdrawn: $" + amount);
        System.out.println("New balance: $" + balance);
    }

    public void checkBalance() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Current Balance: $" + balance);
    }
}

class ATM {
    private Map<String, Account> accounts;
    private Account currentAccount;
    private Scanner scanner;

    public ATM() {
        accounts = new HashMap<>();
        scanner = new Scanner(System.in);
        // Initialize with some sample accounts
        accounts.put("1001", new Account("1001", "John Doe", 1500.0));
        accounts.put("1002", new Account("1002", "Jane Smith", 2500.0));
        accounts.put("1003", new Account("1003", "Robert Johnson", 750.0));
    }

    public void start() {
        System.out.println("Welcome to the ATM System");
        
        boolean isRunning = true;
        while (isRunning) {
            if (currentAccount == null) {
                displayLoginMenu();
            } else {
                displayMainMenu();
            }
            
            int choice = getUserChoice();
            if (currentAccount == null) {
                isRunning = processLoginChoice(choice);
            } else {
                isRunning = processMainMenuChoice(choice);
            }
        }
        
        System.out.println("Thank you for using the ATM. Goodbye!");
        scanner.close();
    }

    private void displayLoginMenu() {
        System.out.println("\n===== LOGIN MENU =====");
        System.out.println("1. Enter Account Number");
        System.out.println("2. Create New Account");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private void displayMainMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("Welcome, " + currentAccount.getAccountHolderName());
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Logout");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid choice
        }
    }

    private boolean processLoginChoice(int choice) {
        switch (choice) {
            case 1:
                login();
                return true;
            case 2:
                createAccount();
                return true;
            case 0:
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
                return true;
        }
    }

    private boolean processMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                currentAccount.checkBalance();
                return true;
            case 2:
                performDeposit();
                return true;
            case 3:
                performWithdrawal();
                return true;
            case 4:
                currentAccount = null;
                System.out.println("Logged out successfully.");
                return true;
            case 0:
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
                return true;
        }
    }

    private void login() {
        System.out.print("Enter your account number: ");
        String accountNumber = scanner.nextLine();
        
        if (accounts.containsKey(accountNumber)) {
            currentAccount = accounts.get(accountNumber);
            System.out.println("Login successful!");
        } else {
            System.out.println("Account not found. Please try again.");
        }
    }

    private void createAccount() {
        System.out.print("Enter a new account number: ");
        String accountNumber = scanner.nextLine();
        
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account number already exists. Please choose another one.");
            return;
        }
        
        System.out.print("Enter account holder name: ");
        String accountHolderName = scanner.nextLine();
        
        System.out.print("Enter initial deposit amount: $");
        try {
            double initialDeposit = Double.parseDouble(scanner.nextLine());
            if (initialDeposit < 0) {
                System.out.println("Initial deposit cannot be negative.");
                return;
            }
            
            Account newAccount = new Account(accountNumber, accountHolderName, initialDeposit);
            accounts.put(accountNumber, newAccount);
            System.out.println("Account created successfully!");
            currentAccount = newAccount;
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a valid number.");
        }
    }

    private void performDeposit() {
        System.out.print("Enter deposit amount: $");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            currentAccount.deposit(amount);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a valid number.");
        }
    }

    private void performWithdrawal() {
        System.out.print("Enter withdrawal amount: $");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            currentAccount.withdraw(amount);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a valid number.");
        }
    }
}

public class ATMSystem {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
