import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }
}

class ATM {
    private BankAccount bankAccount;

    public ATM(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void deposit(double amount) {
        bankAccount.deposit(amount);
    }

    public boolean withdraw(double amount) {
        return bankAccount.withdraw(amount);
    }

    public double checkBalance() {
        return bankAccount.getBalance();
    }
}

public class ATMInterface extends JFrame {
    private ATM atm;
    private JTextField amountField;
    private JTextArea infoArea;

    public ATMInterface(ATM atm) {
        this.atm = atm;

        setTitle("ATM Interface");
        setSize(500, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createUI();

        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void createUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel headingLabel = new JLabel("ATM Interface Task");
        headingLabel.setForeground(new Color(102, 0, 0));
        headingLabel.setFont(new Font("Cambria", Font.BOLD, 30));
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(headingLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Enter Amount field and label in one line
        JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel amountLabel = new JLabel("Enter Amount:");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        amountLabel.setForeground(Color.DARK_GRAY);
        amountField = new JTextField(10);
        amountField.setPreferredSize(new Dimension(150, 30));
        amountPanel.add(amountLabel);
        amountPanel.add(amountField);
        panel.add(amountPanel);

        // Withdraw and Deposit buttons in the next line
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setPreferredSize(new Dimension(120, 30));
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performWithdraw();
            }
        });

        JButton depositButton = new JButton("Deposit");
        depositButton.setPreferredSize(new Dimension(120, 30));
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performDeposit();
            }
        });

        buttonPanel.add(withdrawButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(depositButton);
        panel.add(buttonPanel);

        // Check Balance button
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton balanceButton = new JButton("Check Balance");
        balanceButton.setPreferredSize(new Dimension(150, 30));
        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBalance();
            }
        });

        centerPanel.add(balanceButton);
        panel.add(centerPanel);

        // Centered info area with JScrollPane
        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Cambria", Font.BOLD, 15));
        infoArea.setForeground(Color.RED);
        JScrollPane scrollPane = new JScrollPane(infoArea);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(scrollPane);

        add(panel);
    }

    private void performWithdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (atm.withdraw(amount)) {
                infoArea.setText("Withdrawal successful. New balance: $" + atm.checkBalance());
            } else {
                infoArea.setText("Insufficient funds.");
            }
        } catch (NumberFormatException ex) {
            infoArea.setText("Invalid input. Please enter a valid number.");
        }
    }

    private void performDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            atm.deposit(amount);
            infoArea.setText("Deposit successful. New balance: $" + atm.checkBalance());
        } catch (NumberFormatException ex) {
            infoArea.setText("Invalid input. Please enter a valid number.");
        }
    }

    private void displayBalance() {
        infoArea.setText("Current balance: $" + atm.checkBalance());
    }

    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(1000.0);
        ATM atm = new ATM(bankAccount);
        new ATMInterface(atm);
    }
}
