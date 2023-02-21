import java.awt.*;
public class Projectile extends Rectangle {
    int velocity;
    int id;

    Projectile(int x, int y, int width, int height, int id, int velocity) {
        super(x,y,width,height);
        this.id = id;
        this.velocity = velocity;
    }

    public void move() {
        if (id == 1)
            y -= velocity;
        else
            y += velocity;
    }

    public void draw(Graphics g) {
        if (id == 1) // if player's projectile
            g.setColor(Color.yellow);
        else // if enemy projectile
            g.setColor(Color.magenta);

        g.fillOval(x, y, width, height);
    }
}