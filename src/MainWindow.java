import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainWindow extends JPanel {

    private final Game game;

    public MainWindow() {
        game = new Game();

        JPanel canvas = new JPanel();
        JPanel controls = new JPanel();

        canvas.setBorder(BorderFactory.createEmptyBorder());
        controls.setBorder(BorderFactory.createBevelBorder(0));

        final JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (game.isRunning()) {
                    startButton.setText("Resume");
                } else {
                    startButton.setText("Pause");
                }
            }
        });
        controls.add(startButton);

        final JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                startButton.setText("Start");
                game.reset();
            }
        });
        controls.add(resetButton);

        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);
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