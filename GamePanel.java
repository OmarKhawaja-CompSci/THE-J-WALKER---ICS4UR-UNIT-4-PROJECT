import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private final int PANEL_W = 640;
    private final int PANEL_H = 800;
    private final int GRID_SIZE = 40; // size of a cell
    private final int COLS = PANEL_W / GRID_SIZE; // 16
    private final int ROWS = PANEL_H / GRID_SIZE; // 20

    private Timer timer; // game loop
    private int tick = 0;

    private Player player;
    private ArrayList<Car> cars = new ArrayList<>();
    private Random rand = new Random();

    private int score = 0;
    private int highScore = 0;

    private final int[] laneY; // y positions for lanes
    private final int lanesCount = 7; // number of car lanes
    private boolean scoredTop;
    private boolean scoredBottom;

    private CardLayout cardLayout;
    private JPanel cards;
    static MusicManager music = new MusicManager();
    String fileName = "highscore.txt";
    ArrayList<Integer> scores = new ArrayList<>();
  

    public GamePanel(CardLayout cardLayout, JPanel cards) {
        this.cardLayout = cardLayout;
        this.cards = cards;

        setPreferredSize(new Dimension(PANEL_W, PANEL_H));
        setBackground(new Color(135, 206, 235)); // sky blue
        setFocusable(true);
        addKeyListener(this);

        laneY = new int[lanesCount];
        for (int i = 0; i < lanesCount; i++) {
            laneY[i] = GRID_SIZE * (3 + i * 2); // evenly spaced starting a bit down
        }

        timer = new Timer(30, this); // ~33 FPS
    }

    public void startNewGame() {
        cars.clear();
        tick = 0;
        score = 0;
        player = new Player(COLS / 2, ROWS - 1, GRID_SIZE, COLS, ROWS);
        scoredTop = false;
        scoredBottom = true;
        timer.start();
        requestFocusInWindow();
        music.play("menu.wav");
        
    }

    void endGame(String reason) {
        timer.stop();
        music.stop();
         scores.add(score);
            

        // Try-with-resources ensures the file closes automatically
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            
            // convert int to string and write
            writer.write(String.valueOf("your scores are "+scores));
            
            System.out.println("Saved " + highScore + " to text file.");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (score > highScore) {
            highScore = score;
        
        }
       
        
        // Find the GameOverPanel in the cards container to update the reason text
        for (Component comp : cards.getComponents()) {
            if (comp instanceof GameOverPanel) {
                ((GameOverPanel) comp).setReason(reason + " Score: " + score);
                break;
            }
        }
        
        cardLayout.show(cards, App.GAMEOVER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tick++;
        // spawn cars occasionally based on tick
        if (tick % 20 == 0) {
            spawnCars();
        }
        // update cars
        Iterator<Car> it = cars.iterator();
        while (it.hasNext()) {
            Car c = it.next();
            c.update();
            if (c.isOffscreen(PANEL_W, GRID_SIZE)) {
                it.remove();
            }
        }
        // collision detection
        for (Car c : cars) {
            if (c.collidesWith(player, GRID_SIZE)) {
                endGame("Hit by car!");
                return;
            }
        }
        // update score by player's y (the higher you go, the more points)
        if (player.getRow() == 0 && !scoredTop) {
            score++;
            scoredTop = true;
            scoredBottom = false; // allow scoring bottom again
        }

        // score when reaching bottom
        if (player.getRow() == ROWS - 1 && !scoredBottom) {
            score++;
            scoredBottom = true;
            scoredTop = false; // allow scoring top again
        }

        repaint();
    }

    private void spawnCars() {
        // randomly choose some lanes to spawn
        for (int i = 0; i < lanesCount; i++) {
            if (rand.nextDouble() < 0.1) {
                int lane = i;
                boolean leftToRight = rand.nextBoolean();
                int y = laneY[lane];
                int speed = 1 + rand.nextInt(2) + lane / 5; // harder on higher lanes
                int length = 1 + rand.nextInt(2) / 2;
                cars.add(new Car(leftToRight ? -length * GRID_SIZE : PANEL_W, y, speed * (leftToRight ? 1 : -1), length, GRID_SIZE));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        // draw ground / lanes
        drawLanes(g2);
        // draw player
        if (player != null) player.draw(g2);
       
        // draw cars
        for (Car c : cars) {
            c.draw(g2);
        }
        // draw HUD (score)
        g2.setColor(Color.BLACK);
        g2.setFont(g2.getFont().deriveFont(18f));
        g2.drawString("Score: " + score, 12, 20);
        g2.drawString("High: " + highScore, 12, 40);

        g2.dispose();
    }

    private void drawLanes(Graphics2D g2) {
        // draw grass and road bands
        for (int r = 0; r < ROWS; r++) {
            int y = r * GRID_SIZE;
            if (r < ROWS - lanesCount * 2 - 4) {
                // grass
                g2.setColor(new Color(100, 200, 100));
                g2.fillRect(0, y, PANEL_W, GRID_SIZE);
            } else if (r > ROWS + 3 - lanesCount) {
                // grass
                g2.setColor(new Color(100, 200, 100));
                g2.fillRect(0, y, PANEL_W, GRID_SIZE);
            } else {
                // road area for lanes
                g2.setColor(new Color(60, 60, 60));
                g2.fillRect(0, y, PANEL_W, GRID_SIZE);
            }
        }
        // optional lane dividers
        g2.setColor(Color.WHITE);
        for (int i = 0; i < lanesCount; i++) {
            int y = laneY[i] + GRID_SIZE / 2 - 2;
            for (int x = 0; x < PANEL_W; x += 40) {
                g2.fillRect(x, y, 20, 4);
            }
        }
    }

    // Input handling
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (player == null) return;
        
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_A) {
            player.move(-1, 0);
        }
        if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D) {
            player.move(1, 0);
        }
        if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W) {
            player.move(0, -1);
        }
        if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) {
            player.move(0, 1);
        }
        // restart on R
        if (k == KeyEvent.VK_R && !timer.isRunning()) {
            startNewGame();
        }
        // back to menu on ESC
        if (k == KeyEvent.VK_ESCAPE) {
            cardLayout.show(cards, App.MENU);
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}