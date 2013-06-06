import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class MainWindow extends JPanel {

    private final Game game;
    private final Timer evolveTimer;
    private final Timer paintTimer;
    private final int SPEED_MIN = 0;
    private final int SPEED_MAX = 500;
    private final int SPEED_INIT = 25;

    public MainWindow() {
        game = new Game();

        JPanel controlPanel = new JPanel();

        game.setBorder(BorderFactory.createEmptyBorder());
        controlPanel.setBorder(BorderFactory.createBevelBorder(0));

        final JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.isRunning()) {
                    game.pause();
                    startButton.setText("Resume");
                    evolveTimer.stop();
                } else {
                    game.resume();
                    startButton.setText("Pause");
                    evolveTimer.start();
                }
            }
        });
        controlPanel.add(startButton);

        final JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setText("Start");
                game.reset();
                evolveTimer.stop();
                repaint();
            }
        });
        controlPanel.add(resetButton);

        setLayout(new BorderLayout());
        add(game, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        evolveTimer = new Timer(1000 / SPEED_INIT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.nextGeneration();
                if (!game.isAlive()) {
                    evolveTimer.stop();
                    game.reset();
                    startButton.setText("Start");
                }
            }
        });
        
        paintTimer = new Timer(1000 / 30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                repaint();
            }
        });
        paintTimer.start();

        JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL, SPEED_MIN, SPEED_MAX, SPEED_INIT);
        framesPerSecond.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                int fps = source.getValue();
                if (fps == 0) {
                    evolveTimer.stop();
                } else {
                    int speed = 1000 / fps;
                    evolveTimer.setDelay(speed);
                    if (game.running) {
                        evolveTimer.start();
                    }
                }
            }
        });
        controlPanel.add(framesPerSecond);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("");
        int windowWidth = 800;
        int windowHeight = 600;
        frame.setSize(windowWidth, windowHeight);
        int posX = (Toolkit.getDefaultToolkit().getScreenSize().width / 2) - (windowWidth / 2);
        int posY = (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - (windowHeight / 2);
        frame.setLocation(posX, posY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new MainWindow();
        frame.add(panel);
        frame.setVisible(true);
    }
}
