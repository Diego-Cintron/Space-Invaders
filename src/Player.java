// Diego Alejandro Cintrón Santiago
import java.awt.*;
import java.awt.event.*;

public class Player extends Rectangle {

    int xVelocity;
    int speed = 10;
    boolean shooting = false;

    Player(int x, int y) {
        super(x, y, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);
    }

    // Starts the player's directional movement depending on what key they pressed.
    // Also handles shooting.
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_A) { // If someone presses A
            setXDirection(-speed);
            move();
        }

        if (e.getKeyCode() == KeyEvent.VK_D) { // If someone presses D
            setXDirection(speed);
            move();
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            shooting = true;
        }

    }

    // Stops the player's movement once they let go of the key.
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_A) {
            setXDirection(0);
            move();
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            setXDirection(0);
            move();
        }

    }

    public void setXDirection(int xDirection) {
        xVelocity = xDirection;
    }

    public void move() {
        x += xVelocity;
    }

    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(x, y, width, height);
    }
}