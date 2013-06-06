import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {

    private final int worldWidth = 200;
    private final int worldHeight = 150;
    private final int cellSize = 10;

    boolean running = false;
    private boolean[][] cells = new boolean[worldWidth][worldHeight];
    private boolean[][] savedState = new boolean[worldWidth][worldHeight];
    private boolean stillAlive;

    Game() {
        addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!running) {
                    cells[e.getX() / cellSize][e.getY() / cellSize] = !cells[e.getX() / cellSize][e.getY() / cellSize];
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        setDoubleBuffered(true);
    }

    public void reset() {
        for (int x = 1; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                cells[x][y] = savedState[x][y];
            }
        }
        running = false;
    }

    public boolean isRunning() {
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
        stillAlive = false;
        for (int x = 1; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                int neighbours = nearbyCells(x, y);
                if (cells[x][y] == true && neighbours < 2) {
                    nextGen[x][y] = false;
                } else if (cells[x][y] == true && neighbours > 1 && neighbours < 4) {
                    nextGen[x][y] = true;
                } else if (cells[x][y] == true && neighbours > 3) {
                    nextGen[x][y] = false;
                } else if (cells[x][y] == false && neighbours == 3) {
                    nextGen[x][y] = true;
                }
                if (cells[x][y] != nextGen[x][y]) {
                    stillAlive = true;
                }
            }
        }

        cells = nextGen;
    }

    public boolean isAlive() {
        return stillAlive;
    }

    private int nearbyCells(int x, int y) {
        int nearbyCells = 0;
        if ((x - 1) > 0 && (y - 1) > 0 && cells[x - 1][y - 1] == true) nearbyCells++;
        if ((y - 1) > 0 && cells[x][y - 1] == true) nearbyCells++;
        if ((x + 1 < worldWidth) && (y - 1) > 0 && cells[x + 1][y - 1] == true) nearbyCells++;

        if ((x - 1) > 0 && cells[x - 1][y] == true) nearbyCells++;
        if ((x + 1 < worldWidth) && cells[x + 1][y] == true) nearbyCells++;

        if ((x - 1) > 0 && (y + 1) < worldHeight && cells[x - 1][y + 1] == true) nearbyCells++;
        if ((y - 1) > 0 && (y + 1) < worldHeight && cells[x][y + 1] == true) nearbyCells++;
        if ((x + 1 < worldWidth) && (y + 1) < worldHeight && cells[x + 1][y + 1] == true) nearbyCells++;

        return nearbyCells;
    }

    private boolean isAlive(int x, int y) {
        return cells[x][y];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x < getWidth(); x += cellSize) {
            g.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += cellSize) {
            g.drawLine(0, y, getWidth(), y);
        }
        g.setColor(Color.GREEN);
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                if (isAlive(x, y)) {
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }
    }

    public void clear() {
        savedState = new boolean[worldWidth][worldHeight];
    }

    public void save() {
        for (int x = 1; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                savedState[x][y] = cells[x][y];
            }
        }
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