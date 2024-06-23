import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

//Semi-scrapped level
public class Level1 extends GamePanel {
    private String name;
    private BufferedImage backgroundImage;
    private List<BoundingBox> boundingBoxes;

    public Level1(String name, String backgroundImagePath, List<BoundingBox> boundingBoxes) {
        super();
        this.name = name;
        try {
            this.backgroundImage = ImageIO.read(new File(backgroundImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.boundingBoxes = boundingBoxes;
        //Enemy array goes here
    }

    //Render levels
    public void render() {
        super.render();

        // Your specific rendering logic for Level1
        Graphics2D g2d = (Graphics2D) getGraphics();

        // Render the background image
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, null);
        }


    }}
