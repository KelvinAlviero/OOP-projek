import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Main runnerrrrdd
public class Main {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clean Slate MK2");
        GamePanel gamePanel = new GamePanel();

        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        gamePanel.start();
    }
}
