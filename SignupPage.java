import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class SignupPage extends JPanel {

    CardLayout cardLayout;
    JPanel cards;

    public SignupPage(CardLayout cardLayout, JPanel cards) {
        this.cardLayout = cardLayout;
        this.cards = cards;

        this.setBackground(new Color(30, 30, 30));
        this.setPreferredSize(new Dimension(400, 400));

        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        JLabel name = new JLabel("USERNAME: ");
        name.setForeground(Color.WHITE);
        JLabel email = new JLabel("EMAIL: ");
        email.setForeground(Color.WHITE);
        JLabel password = new JLabel("PASSWORD: ");
        password.setForeground(Color.WHITE);
        JLabel confirm = new JLabel("CONFIRM PASSWORD: ");
        confirm.setForeground(Color.WHITE);
        JLabel error = new JLabel("");
        error.setForeground(Color.WHITE);
        JLabel register = new JLabel("REGISTER");
        register.setFont(new Font("Serif", Font.BOLD, 70));
        register.setForeground(Color.RED);

        JTextField insertName = new JTextField();
        JTextField insertEmail = new JTextField();
        JTextField insertPassword = new JTextField();
        JTextField confirmPassword = new JTextField();

        JButton submit = new JButton("REGISTER");

        // Vertical spacing
        int frameWidth = 690;
        int frameHeight = 800;

        // Common sizes
        int fieldWidth = 200;
        int fieldHeight = 25;
        int labelWidth = 100;
        int spacingY = 60;   
        int startY = 235;    

        int centerX = frameWidth / 2;

        int fieldX = centerX - fieldWidth / 2;
        int labelX = fieldX - labelWidth - 10;  

        layout.putConstraint(SpringLayout.NORTH, register, startY-165, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, register, labelX + labelWidth+265, SpringLayout.WEST, this);

        // Email
        insertEmail.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        layout.putConstraint(SpringLayout.NORTH, email, startY, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, email, labelX + labelWidth, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, insertEmail, startY, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, insertEmail, fieldX, SpringLayout.WEST, this);

        // Name
        insertName.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        layout.putConstraint(SpringLayout.NORTH, name, startY + spacingY, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, name, labelX + labelWidth, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, insertName, startY + spacingY, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, insertName, fieldX, SpringLayout.WEST, this);

        // Password
        insertPassword.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        layout.putConstraint(SpringLayout.NORTH, password, startY + 2 * spacingY, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, password, labelX + labelWidth, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, insertPassword, startY + 2 * spacingY, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, insertPassword, fieldX, SpringLayout.WEST, this);

        // Confirm Password
        confirmPassword.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        layout.putConstraint(SpringLayout.NORTH, confirm, startY + 3 * spacingY, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, confirm, labelX + labelWidth, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, confirmPassword, startY + 3 * spacingY, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, confirmPassword, fieldX, SpringLayout.WEST, this);

        // Error message
        layout.putConstraint(SpringLayout.NORTH, error, startY + 4 * spacingY - 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, error, 0, SpringLayout.HORIZONTAL_CENTER, this);

        // Submit button
        layout.putConstraint(SpringLayout.NORTH, submit, startY + 4 * spacingY + 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, submit, 0, SpringLayout.HORIZONTAL_CENTER, this);

        // Button functionality
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userEmail = insertEmail.getText();
                String userName = insertName.getText();
                String userPassword = insertPassword.getText();
                String confirm = confirmPassword.getText();

                if (confirm.trim().equals(userPassword.trim()) == false) {
                    error.setText("PASSWORDS DO NOT MATCH");
                } else {
                    String information = userEmail.trim() + "," + userName.trim() + "," + userPassword.trim();
                    String content = "Content.txt";
                    try {
                        FileWriter writer = new FileWriter(content, true);
                        writer.write(information + "\n");
                        writer.close();
                        System.out.println("Data has been added to file: " + information);
                    } catch (IOException f) {
                        System.out.println("Error writing to file");
                    }

                    cardLayout.show(cards, App.LOGIN);
                }
            }
        });

        this.add(name);
        this.add(insertName);
        this.add(email);
        this.add(insertEmail);
        this.add(password);
        this.add(insertPassword);
        this.add(confirm);
        this.add(confirmPassword);
        this.add(submit);
        this.add(error);
        this.add(register);
    }
}