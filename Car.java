import java.awt.*;
import java.util.Random;

public class Car {

    private double x;
    private int y;
    private int speed;
    private int lengthCells;
    private int gridSize;
    private Color color;
    private Random rand = new Random();

    public Car(double x, int y, int speed, int lengthCells, int gridSize) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.lengthCells = lengthCells;
        this.gridSize = gridSize;
        this.color = new Color(rand.nextInt(180), rand.nextInt(180), rand.nextInt(180));
    }

    public void update() {
        x += speed;
    }

    public boolean isOffscreen(int w, int gridSize) {
        return x < -lengthCells * gridSize - 50 || x > w + 50;
    }

    public boolean collidesWith(Player p, int gridSize) {
        Rectangle rcar = new Rectangle((int) x, y - gridSize / 2, lengthCells * gridSize, gridSize);
        Rectangle rplayer = p.getBounds(gridSize);
        return rcar.intersects(rplayer);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color.darker());
        int drawY = y - gridSize / 2 + 6;
        g2.fillRoundRect((int) x, drawY, lengthCells * gridSize, gridSize - 12, 8, 8);
        // wheels
        g2.setColor(Color.BLACK);
        g2.fillOval((int) x + 6, drawY + gridSize - 18, 8, 8);
        g2.fillOval((int) x + lengthCells * gridSize - 14, drawY + gridSize - 18, 8, 8);
    }
}