import java.awt.*;
import javax.swing.*;

public class MainMenuPanel extends JPanel {

    public MainMenuPanel(CardLayout cardLayout, JPanel cards, GamePanel gamePanel) {
        setLayout(new GridBagLayout());
        JLabel title = new JLabel("THE J-WALKER");
        title.setFont(title.getFont().deriveFont(28f));

        JButton start = new JButton("Start Game");
        start.addActionListener(e -> {
            gamePanel.startNewGame();
            cardLayout.show(cards, App.GAME);
            gamePanel.requestFocusInWindow();
        });

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> System.exit(0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);
        gbc.gridy = 1;
        add(start, gbc);
        gbc.gridy = 2;
        add(exit, gbc);
    }
}