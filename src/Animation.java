import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
//Scrapped due to code rewrite
public class Animation {
    private BufferedImage[] frames;
    private int currentFrame;
    private int speed; // How fast to cycle through frames
    private long lastTime, timer;

    public Animation(int speed, String[] framePaths) {
        this.speed = speed;
        this.frames = new BufferedImage[framePaths.length];
        this.currentFrame = 0;
        this.timer = 0;
        this.lastTime = System.currentTimeMillis();

        for (int i = 0; i < framePaths.length; i++) {
            try {
                frames[i] = ImageIO.read(getClass().getResource(framePaths[i]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void update() {
        long now = System.currentTimeMillis();
        timer += now - lastTime;
        lastTime = now;

        if (timer > speed) {
            currentFrame = (currentFrame + 1) % frames.length;
            timer = 0;
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[currentFrame];
    }
}

