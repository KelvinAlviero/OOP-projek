import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MenuPanel extends JPanel {

    private BufferedImage backgroundImage; // Background image for the menu
    private JButton startButton; // Button to start the game

    public MenuPanel() {
        // Load background image (replace with your image loading logic)
        backgroundImage = loadImage("/resources/Icons/Menuscreen.png");

        // Create start button
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle button click to start the game
                startGame();
            }
        });

        // Set layout (null layout for absolute positioning)
        setLayout(null);
        // Add button to panel
        add(startButton);
        // Set button bounds (x, y, width, height) - adjust as needed
        startButton.setBounds(100, 100, 100, 40);
    }

    // Method to load an image (replace with your image loading logic)
    private BufferedImage loadImage(String path) {
        // Example code to load image from file
        // Replace with your image loading logic
        return null; // Placeholder, replace with actual image loading
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void startGame() {
        // Get the parent JFrame
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        // Remove current MenuPanel from frame
        frame.getContentPane().remove(this);

        // Create and add GamePanel
        GamePanel gamePanel = new GamePanel(/* pass necessary parameters */);
        frame.add(gamePanel);

        // Repaint frame to show GamePanel
        frame.revalidate();
        frame.repaint();

        // Start game logic if needed
        // gamePanel.startGame();
    }

    // Main method to test the menu
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("EPIC SCREEEEEN");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);

                MenuPanel menuPanel = new MenuPanel();
                frame.add(menuPanel);

                frame.setVisible(true);
            }
        });
    }
}
