import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private MenuPanel menuPanel;
    private GamePanel gamePanel;

    public GameFrame() {
        super("Game Title");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window

        // Initialize MenuPanel
        menuPanel = new MenuPanel();
        add(menuPanel);

        setVisible(true);
    }

    public void startGame() {
        // Remove MenuPanel
        getContentPane().remove(menuPanel);

        // Initialize GamePanel
        gamePanel = new GamePanel(/* Pass necessary parameters */);
        add(gamePanel);

        // Ensure the GamePanel is focused for key events, if needed
        gamePanel.requestFocus();

        // Repaint the frame
        revalidate();
        repaint();

        // Start your game logic if necessary
        // gamePanel.startGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame();
            // Assuming you have a method to start the game when a button is clicked
            // Example: menuPanel.setStartAction(() -> gameFrame.startGame());
        });
    }
}
