import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//I have no clue why I put this
public class Background {
    private BufferedImage image;

    public Background(String path) {
        loadImage(path);
    }

    private void loadImage(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics2D g, int width, int height) {
        if (image != null) {
            double bgAspectRatio = (double) image.getWidth() / image.getHeight();
            int bgWidth = width;
            int bgHeight = (int) (bgWidth / bgAspectRatio);
            if (bgHeight < height) {
                bgHeight = height;
                bgWidth = (int) (bgHeight * bgAspectRatio);
            }
            g.drawImage(image, 0, 0, bgWidth, bgHeight, null);
        }
    }
}
