//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import javax.imageio.ImageIO;
//
////The most basic enemy type, 1 attack low damage low hp
//public class Recruit {
//    private int x, y; //X y stuff
//    private int width, height;// widht hieght
//    private int speed;//speeeeeeed
//    private int health;//HP
//    private boolean facingRight = true;//Facing right check
//    private int frameIndex = 0;
//    private int frameCounter = 0;
//    private String currentAction = "idle";
//    private Map<String, BufferedImage[]> animations;
//    private BoundingBox boundingBox;
//    private Player player;
//    private boolean active = false;
//    private long activationTime;
//    private long delay = 3000; // 3 seconds delay before activation
//    private Map<String, Integer> frameDelays;
//    private int attackRange = 50; // Recruit attack range
//    private Rectangle hitbox;
//    private long lastAttackTime;
//    private final int attackCooldown = 1000; // 1 second cooldown for attack
//    private final int attackDamage = 10; // Damage output
//
//    public Recruit(int x, int y, Player player, BoundingBox boundingBox) {
//        this.x = x;
//        this.y = y;
//        this.width = 200;
//        this.height = 200;
//        this.speed = 3; //Speed
//        this.health = 10; //Health
//        this.player = player; //Declared so the recruit can use the player class
//        this.boundingBox = boundingBox;
//        this.animations = new HashMap<>(); //Frame animation holders
//        this.frameDelays = new HashMap<>();
//        this.hitbox = new Rectangle(x, y, width, height);
////        initAnimations();
//        loadImages();
//
//        this.activationTime = System.currentTimeMillis(); //Timer for activation
//    }
//
//    private void initAnimations() {
//        animations.put("idle", new BufferedImage[0]);
//        animations.put("walk", new BufferedImage[0]);
//        animations.put("attack", new BufferedImage[0]);
//        animations.put("pain", new BufferedImage[0]);
//        animations.put("dead", new BufferedImage[1]); // For death sprite
//
//        frameDelays.put("idle", 10);
//        frameDelays.put("walk", 5);
//        frameDelays.put("attack", 10);
//        frameDelays.put("pain", 10);
//    }
//
//    private void loadImages() {
//        File folder = new File("resources/recruit");
//        if (folder.exists() && folder.isDirectory()) {
//            File[] files = folder.listFiles();
//            if (files != null) {
//                for (File file : files) {
//                    try {
//                        BufferedImage image = ImageIO.read(file);
//                        if (image == null) {
//                            System.err.println("Failed to load image: " + file.getAbsolutePath());
//                            continue;
//                        }
//                        String name = file.getName().toLowerCase();
//                        if (name.startsWith("idle")) {
//                            addAnimationFrame("idle", image);
//                        } else if (name.startsWith("walk")) {
//                            addAnimationFrame("walk", image);
//                        } else if (name.startsWith("punch")) {
//                            addAnimationFrame("attack", image);
//                        } else if (name.startsWith("pain")) {
//                            addAnimationFrame("pain", image);
//                        } else if (name.startsWith("death")) {
//                            animations.get("dead")[0] = image;
//                        }
//                    } catch (IOException e) {
//                        System.err.println("Error loading image: " + file.getAbsolutePath());
//                        e.printStackTrace();
//                    }
//                }
//            } else {
//                System.err.println("No files found in directory: " + folder.getAbsolutePath());
//            }
//        } else {
//            System.err.println("Image directory does not exist: " + folder.getAbsolutePath());
//        }
//    }
//
//    private void addAnimationFrame(String action, BufferedImage image) {
//        BufferedImage[] frames = animations.getOrDefault(action, new BufferedImage[0]);
//        BufferedImage[] newFrames = new BufferedImage[frames.length + 1];
//        System.arraycopy(frames, 0, newFrames, 0, frames.length);
//        newFrames[frames.length] = image;
//        animations.put(action, newFrames);
//    }
//
//    public void update(Integer p_x, Integer p_y) {
//        long currentTime = System.currentTimeMillis();
//
//        int playerX = p_x;
//        int playerY = p_y;
//
//        if (x < playerX) {
//            x += speed;
//            facingRight = true;
//        } else if (x > playerX) {
//            x -= speed;
//            facingRight = false;
//        }
//
//        if (y < playerY) {
//            y += speed;
//        } else if (y > playerY) {
//            y -= speed;
//        }
//
//        // Activate the recruit after the delay
//        if (!active && currentTime - activationTime >= delay) {
//            active = true;
//        }
//
//        if (active) {
//            // Print player and recruit coordinates
//            System.out.println("Player coordinates: (" + this.player.getX() + ", " + this.player.getY() + ")");
//            System.out.println("Recruit coordinates: (" + x + ", " + y + ")");
//
//            if (isPlayerInRange()) {
//                if (currentTime - lastAttackTime >= attackCooldown) {
//                    this.player.takeDamage(attackDamage);
//                    lastAttackTime = currentTime;
//                    currentAction = "attack";
//                }
//            } else {
//                currentAction = "walk"; // Set walking animation if moving
//            }
//
//            // Update the hitbox position
//            hitbox.setLocation(x, y);
//            boundingBox.setPosition(x, y);
//        }
//
//        updateAnimationFrame();
//    }
//
//
//
//    private boolean isPlayerInRange() {
//        int playerX = player.getX();
//        int playerY = player.getY();
//        int playerWidth = (int) player.getWidth();
//        int playerHeight = (int) player.getHeight();
//
//        int recruitCenterX = x + width / 2;
//        int recruitCenterY = y + height / 2;
//        int playerCenterX = playerX + playerWidth / 2;
//        int playerCenterY = playerY + playerHeight / 2;
//
//        return Math.hypot(recruitCenterX - playerCenterX, recruitCenterY - playerCenterY) <= attackRange;
//    }
//
//    private void updateAnimationFrame() {
//        frameCounter++;
//        int actionFrameDelay = frameDelays.getOrDefault(currentAction, 10);
//        if (frameCounter >= actionFrameDelay) {
//            frameCounter = 0;
//            BufferedImage[] actionFrames = animations.get(currentAction);
//            if (actionFrames != null && actionFrames.length > 0) {
//                frameIndex = (frameIndex + 1) % actionFrames.length;
//            }
//        }
//    }
//
//    public void render(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//
//        BufferedImage[] actionFrames = animations.get(currentAction);
//        if (actionFrames != null && actionFrames.length > 0) { //CRUCIAL IF STATEMENT TO PREVENT AN ARRAYOUTOFINDEX
//            BufferedImage currentImage = actionFrames[frameIndex];
//        }
//        if (actionFrames != null && actionFrames.length > 0) {
//            BufferedImage currentImage = actionFrames[frameIndex];
//            if (!facingRight) {
//                currentImage = flipImage(currentImage);
//            }
//            g2d.drawImage(currentImage, x, y, width, height, null);
//        }
//        g2d.setColor(Color.RED);
//        g2d.drawRect((int) hitbox.getX(), (int) hitbox.getY(), (int) hitbox.getWidth(), (int) hitbox.getHeight());
//    }
//
//    private BufferedImage flipImage(BufferedImage img) {
//        int w = img.getWidth();
//        int h = img.getHeight();
//        BufferedImage flipped = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g = flipped.createGraphics();
//        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
//        g.dispose();
//        return flipped;
//    }
//
//    public void takeDamage(int damage) {
//        health -= damage;
//        if (health <= 0) {
//            currentAction = "dead";
//            active = false;
//        } else {
//            currentAction = "pain";
//        }
//    }
//
//    public Rectangle getHitbox() {
//        return hitbox;
//    }
//
//    public boolean isDead() {
//        return health <= 0;
//    }
//
//    // Getter methods for position and bounding box
//    public int getX() {
//        return x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public BoundingBox getBoundingBox() {
//        return boundingBox;
//    }
//}
