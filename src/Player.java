import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

//The almighty player class. I hate how long this is
public class Player {
    private int x, y;//X,y stuff
    private int width, height;//Width and height of the thingy
    private int speed;//SPEEDEDDEDEDEEDEDEDED
    private boolean facingRight = true;//Initial facing position
    private int frameIndex = 0;
    private int frameCounter = 0;
    private String currentAction = "idle";//What action u start with
    private Map<String, BufferedImage[]> animations;//animations map for da stuufff
    private BoundingBox boundingBox;//Player box
    private Map<String, Integer> frameDelays;//
    private boolean isAttacking = false;
    private BoundingBox attackHitbox;
    private int comboStage = 0;
    private int maxComboStage = 3; // 4 stages total (0 to 3)
    private boolean hasHit = false;
    private int attackFrame = 2; // Frame in the combo that registers the hit
    private List<Level> levels;
    private int currentLevelIndex=0;
    //Fun fact, if the error of a lists says "Type 'java.awt.List' does not have type parameters", Just add "Import java.util.List" I dunno how it works but it works lol


    public Player(int x, int y, BoundingBox boundingBox, java.util.List<Level> levels) {
        this.x = x;
        this.y = y;
        this.width = 180;
        this.height = 180;
        this.speed = 5;
        this.boundingBox = boundingBox;
        this.animations = new HashMap<>();
        this.frameDelays = new HashMap<>();
        this.levels = levels;

        // create animation arrays
        animations.put("walk", new BufferedImage[0]);
        animations.put("idle", new BufferedImage[0]);
        animations.put("sprint", new BufferedImage[0]);
        animations.put("attack", new BufferedImage[0]);

        //Fun fact, lower is faster while higher is slower
        frameDelays.put("walk", 5);
        frameDelays.put("idle", 5);
        frameDelays.put("sprint", 2);
        frameDelays.put("attack", 5);

        loadImages();

        // make attack hitbox
        this.attackHitbox = new BoundingBox(0, 0, 50, 50); // Example dimensions
    }




    //Loads allat stufff
    private void loadImages() {
        File folder = new File("resources/Latisha");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files)
                    try {
                    BufferedImage image = ImageIO.read(file);
                    //Error check from ages ago because I sucked at doing this
                    if (image == null) {
                        System.err.println("IMAGE GONE: " + file.getAbsolutePath());
                        continue;
                    }
                    //Puts stuff in the lists
                    String name = file.getName().toLowerCase();
                    if (name.startsWith("walk")) {
                        addAnimationFrame("walk", image);
                    } else if (name.startsWith("idle")) {
                        addAnimationFrame("idle", image);
                    } else if (name.startsWith("run")) {
                        addAnimationFrame("sprint", image);
                    } else if (name.startsWith("attack")) {
                        addAnimationFrame("attack", image);
                    }
                } catch (IOException e) {
                        //Error catcher so laptop no go kapoot
                    System.err.println("IMAGE ERROR" + file.getAbsolutePath());
                    e.printStackTrace();
                }
            }
        } else {
            //Error catcher so laptop no go kapootoototot
            System.err.println("ERORORORORO" + folder.getAbsolutePath());
        }
    }


// loads animation frames

    private void addAnimationFrame(String action, BufferedImage image) {
    BufferedImage[] frames = animations.getOrDefault(action, new BufferedImage[0]);
    BufferedImage[] newFrames = new BufferedImage[frames.length + 1];
    System.arraycopy(frames, 0, newFrames, 0, frames.length);
    newFrames[frames.length] = image;
    animations.put(action, newFrames);
}
    //Movement module, Yes it's tedious because I wanna make my guy face diffrent directions
    public void update(boolean up, boolean down, boolean left, boolean right, boolean sprint, boolean attack, boolean interact) {
    int dx = 0, dy = 0;
    //Sprint speed
    int currentSpeed = sprint ? speed * 2 : speed;

    //Movement understandings
    if (up) dy -= currentSpeed;
    if (down) dy += currentSpeed;
    if (left) {
        dx -= currentSpeed;
        facingRight = false;
    }
    if (right) {
        dx += currentSpeed;
        facingRight = true;
    }
    //If func to handle level transitisotins
    if(interact && shouldTranistionToNextLevel()){
        boolean transitioned = transitionToNextLevel();
    }

    //Scrapped attack system, It's hard :(
    if (attack && !isAttacking) {
        currentAction = "attack";
        frameIndex = 0;
        isAttacking = true;
        comboStage = 0;
        hasHit = false;
    } else if (isAttacking) {
        if (currentAction.equals("attack")) {
            // Attack action logic
            if (frameIndex == attackFrame && !hasHit) {
                performAttack();
            }
            if (frameIndex >= 3) {
                if (hasHit && comboStage < maxComboStage) {
                    comboStage++;
                    frameIndex = 0;
                    hasHit = false;
                } else {
                    isAttacking = false;
                    currentAction = "idle";
                }
            }
        }
    } else {
        // Determine the current action
        if (sprint) {
            currentAction = "sprint";
        } else if (up || down || left || right) {
            currentAction = "walk";
        } else {
            currentAction = "idle";
        }
    }

    // VERY IMPORTANT SO THE ARRAYOUTOFINDEX ERROR NO POP UP
    BufferedImage[] actionFrames = animations.get(currentAction);
    if (actionFrames != null && frameIndex >= actionFrames.length) {
        frameIndex = 0;
    }

    int newX = x + dx;
    int newY = y + dy;

    // Check bounds and update position
    if (boundingBox.contains(newX, newY, width, height)) {
        x = newX;
        y = newY;
    }

    frameCounter++;
    int actionFrameDelay = frameDelays.getOrDefault(currentAction, 10);
    if (frameCounter >= actionFrameDelay) {
        frameCounter = 0;
        if (actionFrames != null && actionFrames.length > 0) {
            frameIndex = (frameIndex + 1) % actionFrames.length;
        }
    }
}

//Uses the level class to get level stuff for transitions
private boolean shouldTranistionToNextLevel(){
        Level currentLevel = levels.get(currentLevelIndex);
        BoundingBox transitionPoint = currentLevel.getTransition();
        return transitionPoint != null && transitionPoint.contains(x,y,width,height);
}

private boolean transitionToNextLevel(){
        currentLevelIndex++;
        if (currentLevelIndex < levels.size()){
            loadLevel(currentLevelIndex);
        x = 150;
        y = 150;
        return true;
} else {
            currentLevelIndex = 0;
            loadLevel(currentLevelIndex);
            x = 150;
            y = 150;
            return  false;
        }
    }

private void loadLevel(int index){
        Level level = levels.get(index);
        BoundingBox playerBoundingBox = level.getBoundingBoxes().get(0);
        setBoundingBox(playerBoundingBox);
}
public void setBoundingBox(BoundingBox boundingBox){
    this.boundingBox = boundingBox;
    }

//Attacking stuff
private void performAttack() {
    int attackX = facingRight ? x + width : x - attackHitbox.getWidth();
    int attackY = y;

    attackHitbox.setPosition(attackX, attackY);
}

//RENDERS DAT STUFFFF
public void render(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    BufferedImage[] actionFrames = animations.get(currentAction);
    if (actionFrames != null && actionFrames.length > 0) {
        BufferedImage currentImage = actionFrames[frameIndex];
        if (!facingRight) {
            currentImage = flipImage(currentImage);
        }
        g2d.drawImage(currentImage, x, y, width, height, null);
    }
}

//Image flipper to keep image flipped when facing diffrent directions
private BufferedImage flipImage(BufferedImage img) {
    int w = img.getWidth();
    int h = img.getHeight();
    BufferedImage flipped = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = flipped.createGraphics();
    g.drawImage(img, w, 0, 0, h, 0, 0, w, h, null);
    g.dispose();
    return flipped;
}

// Stuff for AI, scrapped
public int getX() {
    return x;
}

public int getY() {
    return y;
}

public int getWidth() {
    return width;
}

public int getHeight() {
    return height;
}

public boolean isFacingRight() {
    return facingRight;
}

public BoundingBox getBoundingBox() {
    return boundingBox;
}
    //Max HP
    public int getMaxHealth() {
        return 20;
    }
    //Current HP
    public int getHealth() {
        return 20;
    }

    public void takeDamage(int attackDamage) {
    }
}

