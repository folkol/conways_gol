import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {

    boolean running = true;
    private Map<Coords, Boolean> cells = new HashMap<Coords, Boolean>();
    private final Random random = new Random();

    public void reset() {
        cells.clear();
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
        Map<Coords, Boolean> nextGen = new HashMap<Coords, Boolean>();
        for (Coords c : cells.keySet()) {
            nextGen.put(new Coords(c.x + 1, c.y), true);
        }
        Coords coords = new Coords(0, random.nextInt(getHeight() / 5));
        nextGen.put(coords, true);
        cells = nextGen;
    }

    public void draw(Graphics g) {
        for (int x = 0; x < getWidth() / 5; x++) {
            for (int y = 0; y < getHeight() / 5; y++) {
                if (isAlive(x, y)) {
                    g.fillRect(x * 5, y * 5, 5, 5);
                } else {
                    g.drawRect(x * 5, y * 5, 5, 5);
                }
            }
        }
    }

    private boolean isAlive(int x, int y) {
        return cells.get(new Coords(x, y)) != null;
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