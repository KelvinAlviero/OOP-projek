import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//this is where all the ingame stuff happens
public class GamePanel extends Canvas implements Runnable, KeyListener {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    private Thread gameThread;
    private boolean running = false;//toggles running
    private Player player;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    public boolean sprint;//also toggles running
    private boolean attack;//attack thingy
    private BufferedImage backgroundImage;//Background blit
    private List<Level> levels;// Level lists
    public int currentLevelIndex = 0;// current level thingy
    private UI ui;// UI, UIUIUIUUIUIUI
    private static final int BLACK_BOX_HEIGHT = 50;// Black box borders thing
    public boolean interact; //Transition thingy
    private MenuPanel menuPanel;


    public GamePanel() {
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);
        setFocusable(true);
        addKeyListener(this);

        initializeLevels();
        loadLevel(currentLevelIndex);

        ui = new UI(player);
        menuPanel = new MenuPanel();
    }

    //Makes and loads all levels borders
    private void initializeLevels() {
        levels = new ArrayList<>();
        List<BoundingBox> level1Boxes = new ArrayList<>();
        level1Boxes.add(new BoundingBox(60, 30, 800, 300));
        BoundingBox transitionPoint1 = new BoundingBox(60,30,50,50);
        levels.add(new Level("Level 1", "resources/backgrounds/Level1.png", level1Boxes));


        List<BoundingBox> level2Boxes = new ArrayList<>();
        level2Boxes.add(new BoundingBox(0, 0, 1200, 600));
        BoundingBox transitionPoint2 = new BoundingBox(750,350,50,50);
        levels.add(new Level("Level 2", "resources/backgrounds/Level2.png", level2Boxes));

        List<BoundingBox> level3Boxes = new ArrayList<>();
        level2Boxes.add(new BoundingBox(0, 0, 1200, 600));
        levels.add(new Level("Level 3", "resources/backgrounds/Level3.png", level3Boxes));

        List<BoundingBox> level4Boxes = new ArrayList<>();
        level2Boxes.add(new BoundingBox(0, 0, 1200, 600));
        levels.add(new Level("Level 4", "resources/backgrounds/Level4.png", level4Boxes));

        List<BoundingBox> level5Boxes = new ArrayList<>();
        level2Boxes.add(new BoundingBox(0, 0, 1200, 600));
        levels.add(new Level("Level 5", "resources/backgrounds/Level5.png", level5Boxes));

        List<BoundingBox> level6Boxes = new ArrayList<>();
        level2Boxes.add(new BoundingBox(0, 0, 1200, 600));
        levels.add(new Level("Level 6", "resources/backgrounds/Level6.png", level6Boxes));

        List<BoundingBox> level7Boxes = new ArrayList<>();
        level2Boxes.add(new BoundingBox(0, 0, 1200, 600));
        levels.add(new Level("Level 7", "resources/backgrounds/Level7.png", level7Boxes));

        List<BoundingBox> level8Boxes = new ArrayList<>();
        level2Boxes.add(new BoundingBox(0, 0, 1200, 600));
        levels.add(new Level("Level 8", "resources/backgrounds/Level8.png", level7Boxes));
    }

    //Loads levels
    public void loadLevel(int index) {
        Level level = levels.get(index);
        BoundingBox playerBoundingBox = level.getBoundingBoxes().get(0);
        //Player spawn point,borders and levels
        player = new Player(150, 100, playerBoundingBox,levels);
        loadBackgroundImage(level.getBackgroundImagePath());
    }

    //loads actual background image
    private void loadBackgroundImage(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Starts the whole ordeal
    public synchronized void start() {
        if (running) return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    //Unused stop function
    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //FPS counter
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double nsPerUpdate = 1000000000.0 / 60.0;// I dunno why they wanted it like this but it works?
        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerUpdate;
            lastTime = now;

            while (delta >= 1) {
                update();
                delta--;
            }

            render();

                }
                if (System.currentTimeMillis() - lastTimer >= 1000) {
                    lastTimer += 1000;


        }
    }

    //update function for players movement in flipped form
    private void update() {
        player.update(up, down, left, right, sprint, attack,interact);

    }

    //Renders allat stuff
    public void render() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        Graphics2D g2d = (Graphics2D) g;

        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);
        }

        player.render(g2d); // Render the player
        // Render other game elements (UI, menu, etc.)
        renderUI(g2d);

        g2d.dispose();
        bufferStrategy.show();
    }

    //UI rendering with the blackboxes
    private void renderUI(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, WIDTH, BLACK_BOX_HEIGHT); // Top black box
        g2d.fillRect(0, HEIGHT - BLACK_BOX_HEIGHT, WIDTH, BLACK_BOX_HEIGHT); // Bottom black box

        // Render additional UI elements here if needed
        ui.render(g2d);
    }

    //Controls basically
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_SHIFT:
                sprint = true;
                break;
            case KeyEvent.VK_SPACE:
                attack = true;
                break;
            case KeyEvent.VK_E:
                interact =true;
                break;

        }
    }

    //Controls when u stop moving
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_W:
                up = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_SHIFT:
                sprint = false;
                break;
            case KeyEvent.VK_SPACE:
                attack = false;
                break;
            case KeyEvent.VK_E:
                interact= false;
                break;
        }
    }

    //If I delete this everything breaks
    @Override//I DON'T KNOW WHYYY
    public void keyTyped(KeyEvent e) {
        // Not used
    }
}// Start the game loop

