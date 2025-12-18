import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

public class Player {

    private int col, row;
    private int maxCols, maxRows;
    private int gridSize;
    private Image sprite;

    public Player(int col, int row, int gridSize, int maxCols, int maxRows) {
        this.col = col;
        this.row = row;
        this.gridSize = gridSize;
        this.maxCols = maxCols;
        this.maxRows = maxRows;

        // Load sprite from a file path (assets/player.png) relative to working dir
        try {
            File f = new File("assets/player.png");
            if (f.exists()) {
                sprite = ImageIO.read(f);
            } else {
                System.out.println("player.png not found at: " + f.getAbsolutePath());
                sprite = null;
            }
        } catch (Exception e) {
            System.out.println("Could not load player sprite: " + e.getMessage());
            e.printStackTrace();
            sprite = null;
        }
    }

    public void move(int dc, int dr) {
        col += dc;
        row += dr;
        col = Math.max(0, Math.min(maxCols - 1, col));
        row = Math.max(0, Math.min(maxRows - 1, row));
    }

    public Rectangle getBounds(int gridSize) {
        return new Rectangle(
            col * gridSize + 4,
            row * gridSize + 4,
            gridSize - 8,
            gridSize - 8
        );
    }

    public int getRow() {
        return row;
    }

    public void draw(Graphics2D g2) {
        int x = col * gridSize;
        int y = row * gridSize;

        if (sprite != null) {
            g2.drawImage(
                sprite,
                x + 4,
                y + 4,
                gridSize - 8,
                gridSize - 8,
                null
            );
        } else {
            // fallback (debug)
            g2.setColor(Color.RED);
            g2.fillOval(x + 4, y + 4, gridSize - 8, gridSize - 8);
        }
    }
}