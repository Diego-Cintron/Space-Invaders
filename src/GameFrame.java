// Diego Alejandro Cintrón Santiago
import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame{

    GameFrame() {

        this.add(new GamePanel());
        this.setTitle("Space Invaders   ");
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
