import java.awt.*;

public class Enemy extends Rectangle {

    int xVelocity = 2;
    boolean alive = true;

    Enemy(int x, int y) {
        super(x, y, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);
    }

    // Will change the direction the enemies move in. Will be called in collision (maybe)
    public void setXDirection(int xDirection) {
        xVelocity = xDirection;
    }

    // Only handles horizontal movement
    public void move() {
        x += xVelocity;
    }

    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, width, height);
    }

}