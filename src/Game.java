import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {

    private final int worldWidth = 1000;
    private final int worldHeight = 1000;

    boolean running = true;
    private boolean[][] cells = new boolean[worldWidth][worldHeight];
    private final Random random = new Random();

    public void reset() {
        cells = new boolean[worldWidth][worldHeight];
        running = false;
    }

    public boolean isRunning() {
        running = !running;
        return running;
    }

    public void pause() {
        running = false;
    }

    public void resume() {
        running = true;
    }

    public void nextGeneration() {
        boolean[][] nextGen = new boolean[worldWidth][worldHeight];
        for (int x = 1; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                nextGen[x][y] = cells[x - 1][y];
            }
        }
        nextGen[0][random.nextInt(worldHeight)] = true;
        cells = nextGen;
    }

    public void draw(Graphics g) {
        for (int x = 0; x < getWidth(); x+=5) {
            g.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y+=5) {
            g.drawLine(0, y, getWidth(), y);
        }
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                if (isAlive(x, y)) {
                    g.fillRect(x * 5, y * 5, 5, 5);
                }
            }
        }
    }

    private boolean isAlive(int x, int y) {
        return cells[x][y];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
}

class Coords {
    public int x;
    public int y;

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + x;
        hash = hash * 31 + y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coords)) {
            return false;
        }
        Coords other = (Coords) obj;
        return x == other.x && y == other.y;
    }
}