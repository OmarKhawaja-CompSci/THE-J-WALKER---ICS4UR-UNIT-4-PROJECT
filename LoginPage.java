import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;

public class LoginPage extends JPanel {

    private HashMap<String, String> userID = new HashMap<>();
    private HashMap<String, String> emailaddress = new HashMap<>();
    
    CardLayout cardLayout;
    JPanel cards;

    public LoginPage(CardLayout cardLayout, JPanel cards) {
        this.cardLayout = cardLayout;
        this.cards = cards;

        this.setBackground(new Color(30,30,30));
        this.setPreferredSize(new Dimension(300, 300));
        SpringLayout springLayout = new SpringLayout();
        this.setLayout(springLayout);

        JLabel name = new JLabel("USERNAME: ");
        name.setForeground(Color.WHITE);
        JLabel passwordLabel = new JLabel("PASSWORD: ");
        passwordLabel.setForeground(Color.WHITE);
        JTextField enterName = new JTextField();
        JPasswordField enterPassword = new JPasswordField(); 
        JButton submit = new JButton("LOG IN");
        JLabel login = new JLabel("LOG IN");
        login.setFont(new Font("Serif", Font.BOLD, 70));
        login.setForeground(Color.RED);

        int frameWidth = 610;
        int frameHeight = 800;
        int verticalSpacing = 40; 
        int topMargin = 300; 
        int gap = 10; 

        // ---- Determine widths ----
        int nameLabelWidth = name.getPreferredSize().width;
        int passwordLabelWidth = passwordLabel.getPreferredSize().width;
        int maxLabelWidth = Math.max(nameLabelWidth, passwordLabelWidth);

        int uniformFieldWidth = 200;

        enterName.setPreferredSize(new Dimension(uniformFieldWidth, enterName.getPreferredSize().height));
        enterPassword.setPreferredSize(new Dimension(uniformFieldWidth, enterPassword.getPreferredSize().height));

        int totalWidth = maxLabelWidth + gap + uniformFieldWidth;

        int startX = (frameWidth - totalWidth) / 2;
        int currentY = topMargin;

        springLayout.putConstraint(SpringLayout.NORTH, login, currentY-165, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.EAST, login, startX + maxLabelWidth + gap + uniformFieldWidth , SpringLayout.WEST, this);
        
        // ---- NAME ----
        springLayout.putConstraint(SpringLayout.WEST, name, startX, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, name, currentY, SpringLayout.NORTH, this);

        springLayout.putConstraint(SpringLayout.WEST, enterName, startX + maxLabelWidth + gap, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, enterName, currentY, SpringLayout.NORTH, this);

        // ---- PASSWORD ----
        currentY += verticalSpacing;

        springLayout.putConstraint(SpringLayout.WEST, passwordLabel, startX, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, passwordLabel, currentY, SpringLayout.NORTH, this);

        springLayout.putConstraint(SpringLayout.WEST, enterPassword, startX + maxLabelWidth + gap, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, enterPassword, currentY, SpringLayout.NORTH, this);

        // ---- SUBMIT BUTTON ----
        currentY += verticalSpacing + 20; 
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, submit, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.NORTH, submit, currentY, SpringLayout.NORTH, this);

        this.add(name);
        this.add(enterName);
        this.add(passwordLabel);
        this.add(enterPassword);
        this.add(submit);
        this.add(login);

        // Button action
        submit.addActionListener(e -> {
            try {
                File file = new File("Content.txt");
                Scanner input = new Scanner(file);

                while (input.hasNextLine()) {
                    String info = input.nextLine();
                    String[] list = info.split(",");
                    if (list.length >= 3) {
                        userID.put(list[1], list[2]);
                        emailaddress.put(list[0], list[2]);
                    }
                }
                input.close();
            } catch (FileNotFoundException a) {
                System.out.println("FILE NOT FOUND");
            }

            String namefield = enterName.getText().trim();
            String password = new String(enterPassword.getPassword()).trim();

            boolean loggedIn = false;

            if (userID.containsKey(namefield)) {
                String p = userID.get(namefield).trim();
                if (password.equals(p)) {
                    loggedIn = true;
                }
            } else if (emailaddress.containsKey(namefield)) {
                String p = emailaddress.get(namefield).trim();
                if (password.equals(p)) {
                    loggedIn = true;
                }
            }

            if (loggedIn) {
                System.out.println("Logged in successfully");
                cardLayout.show(cards, App.PLAY); 
            } else {
                System.out.println("Login failed: invalid user/email or password");
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
        });
    }
}