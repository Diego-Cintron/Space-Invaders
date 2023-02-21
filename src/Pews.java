// Diego Alejandro Cintrón Santiago
import java.awt.*;
import java.util.ArrayList;

public class Pews {

    ArrayList<Projectile> projectiles;

    public Pews() {
        projectiles = new ArrayList<>();
    }

    // Adds new projectiles at the given position
    public void newProjectile (int x, int y, int id) {
        projectiles.add(new Projectile(x, y, GamePanel.PROJECTILE_WIDTH, GamePanel.PROJECTILE_HEIGHT, id, GamePanel.PROJECTILE_SPEED));
    }

    // Removes a projectile from pews
    public void removePew(int projectileIndex) { projectiles.remove(projectileIndex); }

    public void move() {
        for (Projectile projectile : projectiles) {
            projectile.move();
        }
    }

    public void draw(Graphics g) {
        for (Projectile projectile : projectiles) {
            projectile.draw(g);
        }
    }

    public void checkCollision() {

        // If projectiles reach the top or bottom of the screen, remove them.
        for (int i=0; i < projectiles.size(); i++) {
            if (projectiles.get(i).y <= 0) {
                projectiles.remove(projectiles.get(i));
                i--;
                continue;
            }

            if (projectiles.get(i).y >= GamePanel.GAME_HEIGHT) {
                projectiles.remove(projectiles.get(i));
                i--;
            }
        }
    }

    // Returns true if the Index given corresponds to an enemy projectile.
    public boolean isEnemy(int projectileIndex) {
        return projectiles.get(projectileIndex).id == 2;
    }
}
