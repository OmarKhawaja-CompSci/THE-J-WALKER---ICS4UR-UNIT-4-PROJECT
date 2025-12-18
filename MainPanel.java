import java.awt.*;
import javax.swing.*;



public class MainPanel extends JPanel {

    JButton signup = new JButton("SIGN UP");
    JButton login = new JButton("LOG IN");
    JLabel title = new JLabel("THE J-WALKER");
    
    CardLayout cardLayout;
    JPanel cards;

    public MainPanel(CardLayout cardLayout, JPanel cards) {
        this.cardLayout = cardLayout;
        this.cards = cards;

        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(30, 30, 30)); // dark background
        this.setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        // Title styling
        title.setFont(new Font("SansSerif ", Font.BOLD, 67));
        title.setForeground(Color.RED);

        signup.setBackground(Color.WHITE);
        login.setBackground(Color.WHITE);

        // Button styling
        Font buttonFont = new Font("Monospaced", Font.BOLD, 30);
        Dimension buttonSize = new Dimension(250, 80); // width x height

        signup.setFont(buttonFont);
        signup.setPreferredSize(buttonSize);
        login.setFont(buttonFont);
        login.setPreferredSize(buttonSize);

        // Add components
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 50, 0); // space below title
        this.add(title, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0); // space between buttons
        this.add(signup, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(login, gbc);

        // Action listeners
        signup.addActionListener(e -> cardLayout.show(cards, App.SIGN));
        login.addActionListener(e -> cardLayout.show(cards, App.LOGIN));
    }
}