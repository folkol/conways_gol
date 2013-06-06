import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class MainWindow extends JPanel {

    private final Game game;
    private final Timer timer;

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
                    startButton.setText("Resume");
                    timer.stop();
                } else {
                    startButton.setText("Pause");
                    timer.start();
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
                timer.stop();
                repaint();
            }
        });
        controlPanel.add(resetButton);

        setLayout(new BorderLayout());
        add(game, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.nextGeneration();
                if (!game.isAlive()) {
                    timer.stop();
                    game.reset();
                    startButton.setText("Start");
                }
                repaint();
            }
        });
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
