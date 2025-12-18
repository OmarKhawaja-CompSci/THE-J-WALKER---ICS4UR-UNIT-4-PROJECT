import java.awt.*;
import javax.swing.*;

public class App {

    // Constants for CardLayout screen names
    public static final String LOGIN = "LOGIN";
    public static final String MENU = "MENU";
    public static final String GAME = "GAME";
    public static final String GAMEOVER = "GAMEOVER";
    public static final String SIGN = "SIGN";
    public static final String PLAY = "PLAY";

    private JFrame frame;
    private JPanel cards;
    private CardLayout cardLayout;
    private GamePanel gamePanel;
  

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().createAndShowGui());
    }

    public void createAndShowGui() {
        frame = new JFrame("THE J-WALKER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 800);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Initialize GamePanel first so other panels can reference it
        gamePanel = new GamePanel(cardLayout, cards);
        
        // Initialize GameOverPanel
        GameOverPanel gameOverPanel = new GameOverPanel(cardLayout, cards, gamePanel);

        // Add screens to the CardLayout
        cards.add(new MainPanel(cardLayout, cards), MENU);
        cards.add(new LoginPage(cardLayout, cards), LOGIN);
        cards.add(new MainMenuPanel(cardLayout, cards, gamePanel), PLAY);
        cards.add(new SignupPage(cardLayout, cards), SIGN);
        cards.add(gamePanel, GAME);
        cards.add(gameOverPanel, GAMEOVER);

        frame.setContentPane(cards);
        frame.setVisible(true);
        
    }
}