// Diego Alejandro Cintrón Santiago
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    private boolean gameRunning = true;
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = (int)(GAME_WIDTH*(0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    public static final int UNIT_SIZE = 50;
    static final int PROJECTILE_WIDTH = 10;
    static final int PROJECTILE_HEIGHT = 25;
    static final int PROJECTILE_SPEED = 5;
    static final int ENEMY_COUNT = 32;
    Player player;
    Swarm swarm;
    Pews pews;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Score score;


    GamePanel() {
        newPlayer();

        // Creates the enemies and handles methods that affect the enemies
        swarm = new Swarm(ENEMY_COUNT);
        pews = new Pews();

        score = new Score(GAME_WIDTH, GAME_HEIGHT);

        this.setFocusable(true);
        this.addKeyListener(new ActionListener());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    // Creates the player class and assigns their starting
    // coordinates as the bottom-center of the screen.
    public void newPlayer() {
        player = new Player(GAME_WIDTH/2 - UNIT_SIZE, GAME_HEIGHT - UNIT_SIZE);
    }

    // When the player presses the shoot button, it sets their 'shooting' variable to 'true'.
    // This method checks every tick to see if the player wants to shoot. It creates the projectile
    // and then sets their 'shooting' variable back to 'false'.
    public void checkShooting() {
        if (player.shooting) {
            pews.newProjectile(player.x+10, player.y - 25, 1);
            player.shooting = false;
        }

    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0, this);
    }

    public void draw(Graphics g) {
        player.draw(g);
        score.draw(g);
        swarm.draw(g);
        pews.draw(g);
    }

    // Updates the position of the player, the projectiles, and the enemies.
    public void move() {
        player.move();
        swarm.move();
        pews.move();
    }

    // Stops player and projectiles from running off the screen.
    public void checkCollision() {
        int tempX;
        int tempY;

        // Player projectiles colliding with enemies
        for (int i = 0; i < pews.projectiles.size(); i++) {

            // Checks if the projectile is an enemy projectile. If true, skips it.
            if (pews.isEnemy(i)) { continue; }

            tempX = pews.projectiles.get(i).x;
            tempY = pews.projectiles.get(i).y;

            // if PLAYER's projectile collides with an enemy, remove the enemy and the projectile
            // if true, it also creates an enemy projectile
            if (swarm.checkCollision(tempX+GamePanel.PROJECTILE_WIDTH/2,tempY+GamePanel.PROJECTILE_HEIGHT/2)) {

                pews.removePew(i);
                score.player++;
                pews.newProjectile(tempX, tempY, 2);

            }
        }

        // Checks if an enemy projectile hits a player
        for (int i = 0; i < pews.projectiles.size(); i++) {
            // Checks if projectile is an enemy projectile. If false, skips it.
            if (!pews.isEnemy(i)) {
                continue;
            }

            tempX = pews.projectiles.get(i).x;
            tempY = pews.projectiles.get(i).y;
            int xHitbox = tempX + GamePanel.PROJECTILE_WIDTH / 2;
            int yHitbox = tempY + GamePanel.PROJECTILE_HEIGHT / 2;

            // Check if projectile's x-coordinates are in range of enemy
            if (xHitbox >= player.x && xHitbox <= player.x + GamePanel.UNIT_SIZE) {

                // Check if projectile's y-coordinates are in range of enemy
                if (yHitbox >= player.y && xHitbox <= player.y + GamePanel.UNIT_SIZE) {
                    gameOver();
                }
            }
        }


        // Stops projectiles at window edges
        pews.checkCollision();

        // Stops player at window edges
        if (player.x <= 0)
            player.x = 0;

        if (player.x >= GAME_WIDTH - UNIT_SIZE)
            player.x = GAME_WIDTH - UNIT_SIZE;

    }

    // Checks if there are no enemies remaining.
    // If there are none, the game ends.
    public void checkEnemies() {
        if (swarm.enemiesRemaining() == 0) {
            gameOver();
        }
    }

    public void gameOver() {
        gameRunning = false;
    }

    public void run() {
        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;

        while (gameRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                checkEnemies();
                move();
                checkCollision();
                checkShooting();
                repaint();
                delta--;
            }
        }
    }


    // Listens to key presses
    public class ActionListener extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }

}