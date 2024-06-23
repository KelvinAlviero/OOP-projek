import java.awt.*;
import java.awt.geom.Rectangle2D;
//Scrapped due to code rewrite
public abstract class Entity {
    protected float x, y;
    protected float width, height;
    protected int health;
    protected boolean active;
    protected Rectangle2D.Float hitbox;

    public Entity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initHitbox();
    }

    protected void drawHitbox(Graphics g){
        //hitbox debug
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }
    private void initHitbox(){
        hitbox = new Rectangle2D.Float((int)x,(int)y,width,height);

    }
    public void updateHitbox(){
        hitbox.x = (int)x;
        hitbox.y = (int)y;
    }
    protected Rectangle2D.Float getHitbox(){
        return hitbox;
    }
}





