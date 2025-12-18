import java.awt.*;
import javax.swing.*;

public class GameOverPanel extends JPanel {

    private JLabel reasonLabel;

    public GameOverPanel(CardLayout cardLayout, JPanel cards, GamePanel gamePanel) {
        setLayout(new GridBagLayout());
        JLabel heading = new JLabel("Game Over");
        heading.setFont(heading.getFont().deriveFont(28f));
        reasonLabel = new JLabel("You lost!");
        
        JButton retry = new JButton("Retry");
        retry.addActionListener(e -> {
            gamePanel.startNewGame();
            cardLayout.show(cards, App.GAME);
            gamePanel.requestFocusInWindow();
        });
        
        JButton menu = new JButton("Main Menu");
        menu.addActionListener(e -> cardLayout.show(cards, App.MENU));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(heading, gbc);
        gbc.gridy = 1;
        add(reasonLabel, gbc);
        gbc.gridy = 2;
        add(retry, gbc);
        gbc.gridy = 3;
        add(menu, gbc);
    }

    public void setReason(String r) {
        reasonLabel.setText(r);
    }
}