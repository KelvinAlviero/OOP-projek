import java.awt.*;

//Border for game
public class BoundingBox {
    public int x, y, width, height;
    public int x2,y2,w2,h2;

    //
    public BoundingBox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        //Level 1 borders
        this.x2 = 120;
        this.y2 = 45;
        this.w2 = 900;
        this.h2= 40;
    }

    public boolean contains(int px, int py, int playerWidth, int playerHeight) {
        return px >= x && px + playerWidth <= x + width &&
                py >= y && py + playerHeight <= y + height;
    }
    //scrapped after code rewrite
    public boolean intersects(BoundingBox other) {
        return x2 < other.x2 + other.w2 &&
                x2 + w2 > other.x &&
                y2 < other.y2 + other.h2 &&
                y2 + h2 > other.y2;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }
}
