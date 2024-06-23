import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


//The UI function
public class UI {
    private Player player;
    private BufferedImage characterIcon;

    public UI(Player player) {
        this.player = player;
        loadCharacterIcon();
    }

    //Player icon loader
    private void loadCharacterIcon() {
        try {
            characterIcon = ImageIO.read(new File("resources/Icons/LatishaIcon.png")); // Replace with your icon path
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Draws da stufufufuf
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Draw health bar
        drawHealthBar(g2d);

        // Draw character icon
        drawCharacterIcon(g2d);

        // Add more UI elements here
    }

    //Your HP bar
    private void drawHealthBar(Graphics2D g2d) {
        int health = player.getHealth();
        int maxHealth = player.getMaxHealth();

        int barWidth = 200;
        int barHeight = 20;
        int barX = 125; // Adjusted to leave space for the icon
        int barY = 20;

        // Draw background
        g2d.setColor(Color.GRAY);
        g2d.fillRect(barX, barY, barWidth, barHeight);

        // Draw health
        g2d.setColor(Color.RED);
        int healthWidth = (int) ((health / (double) maxHealth) * barWidth);
        g2d.fillRect(barX, barY, healthWidth, barHeight);

        // Draw border
        g2d.setColor(Color.BLACK);
        g2d.drawRect(barX, barY, barWidth, barHeight);

        // Draw health text
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.WHITE);
        g2d.drawString(health + "/" + maxHealth, barX + barWidth / 2 - 20, barY + 15);
    }

    //The character icon
    private void drawCharacterIcon(Graphics2D g2d) {
        if (characterIcon != null) {
            int iconX = 100; // X position of the icon
            int iconY = 10; // Y position of the icon
            int iconWidth = 75; // Width of the icon
            int iconHeight = 100; // Height of the icon

            g2d.drawImage(characterIcon, iconX, iconY, iconWidth, iconHeight, null);
        }
    }
}
