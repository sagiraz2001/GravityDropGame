import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener {
    private Player player;
    private ArrayList<Object> objects;
    private boolean isRunning;


    public GamePanel() {
        this.setPreferredSize(new Dimension(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT));
        this.setBackground(GameConfig.SKY_COLOR);
        this.setFocusable(true);
        this.setDoubleBuffered(true);

        this.player = new Player();
        this.objects = new ArrayList<>();
        this.isRunning = false;

        this.addKeyListener(this);
    }


    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(GameConfig.GRASS_COLOR);
        graphics.fillRect(0, GameConfig.SCREEN_HEIGHT - GameConfig.GRASS_HEIGHT, GameConfig.SCREEN_WIDTH, GameConfig.GRASS_HEIGHT);
        if (this.player != null) {
            this.player.draw(graphics);
        }
    }


    @Override
    public void keyTyped(KeyEvent event) {

    }

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (this.player != null) {
            if (keyCode == KeyEvent.VK_RIGHT) {
                this.player.setRightPressed(true);
            }
            if (keyCode == KeyEvent.VK_LEFT) {
                this.player.setLeftPressed(true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (this.player != null) {
            if (keyCode == KeyEvent.VK_RIGHT) {
                this.player.setRightPressed(false);
            }
            if (keyCode == KeyEvent.VK_LEFT) {
                this.player.setLeftPressed(false);
            }
        }
    }

    public void startGame() {//עדיין לא סיימתי - צריך להמשיך פה את הלולאה הראשית של המשחק
        this.isRunning = true;
        Thread gameThread = new Thread(() -> {
            while (this.isRunning) {
                this.player.updatePlayerLocation();
            }
        });
    }
}
