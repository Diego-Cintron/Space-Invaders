// Diego Alejandro Cintrón Santiago
import java.awt.*;
import java.util.*;

public class Swarm {
    ArrayList<Enemy> enemies;
    int enemyCount;

    public Swarm(int enemyCount) {
        this.enemyCount = enemyCount;

        newEnemies(GamePanel.UNIT_SIZE);
    }

    public void newEnemies(int UNIT_SIZE) {
        enemies = new ArrayList<>(enemyCount);
        int x = 100; // Starting distance from the left border
        int y = 60; // Starting distance from the top border

        for (int i=0; i < enemyCount; i++) {
            enemies.add(new Enemy(x,y));
            x += 110;

            // Stops module from giving an error
            if (i == 0)
                continue;

            // After 8 enemies, starts the next line of enemies
            if (i % 8 == 7) {
                x = 100;
                y += 30 + UNIT_SIZE;
            }
        }
    }

    // Updates enemy positions.
    public void move() {

        // if enemies have reached the corner of the screen, then switch direction
        if (enemies.get(7).x + GamePanel.UNIT_SIZE >= GamePanel.GAME_WIDTH || enemies.get(0).x <= 0) {
            System.out.println("SWITCHING SIDES");
            for (Enemy baddie : enemies) {
                baddie.setXDirection(-1 * baddie.xVelocity);
                baddie.move();
            }
        }
        else {
            for (Enemy baddie : enemies) {
                baddie.move();
            }
        }
    }

    public void draw(Graphics g) {
        for (Enemy baddie : enemies) {
            if (baddie.alive)
                baddie.draw(g);
        }
    }

    // Checks if player projectiles collides with an enemy. If true, remove enemy and return True.
    public boolean checkCollision(int xHitbox, int yHitbox) {
        for (Enemy baddie : enemies) {
            if (baddie.alive) {

                // Check if projectile's x-coordinates are in range of enemy
                if (xHitbox >= baddie.x && xHitbox <= baddie.x+GamePanel.UNIT_SIZE) {

                    // Check if projectile's y-coordinates are in range of enemy
                    if (yHitbox >= baddie.y && yHitbox <= baddie.y+GamePanel.UNIT_SIZE) {
                        baddie.alive = false;
                        return true;
                    }
                }

            }
        }
        // If projectile didn't collide with an enemy
        return false;
    }

    // Counts and returns how many enemies are alive.
    public int enemiesRemaining() {
        int enemyCount = 0;

        for (Enemy baddie : enemies) {
            if (baddie.alive)
                enemyCount++;
        }

        return enemyCount;
    }
}
