import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener {
    private Player player;
    private ArrayList<FallingObject> objects;
    private boolean isRunning;
    private UI ui;
    private int gameState;
    private int bestScore = 0;


    public GamePanel() {
        this.setPreferredSize(new Dimension(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT));
        this.setBackground(GameConfig.SKY_COLOR);
        this.setFocusable(true);
        this.setDoubleBuffered(true);

        this.player = new Player();
        this.objects = new ArrayList<>();
        this.isRunning = false;
        this.ui = new UI(this.player);

        this.addKeyListener(this);
        this.requestFocusInWindow();
    }


    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(GameConfig.GRASS_COLOR);
        graphics.fillRect(0, GameConfig.SCREEN_HEIGHT - GameConfig.GRASS_HEIGHT, GameConfig.SCREEN_WIDTH, GameConfig.GRASS_HEIGHT);
        if (this.player != null) {
            this.player.draw(graphics);
        }
        for (FallingObject object : this.objects) {
            object.draw(graphics);
        }
        this.ui.draw(graphics, this.gameState, this.bestScore);
    }

    private void checkCollision() {
        for (int i = this.objects.size() - 1; i >= 0; i--) {
            FallingObject object = this.objects.get(i);
            if (this.player.getBounds().intersects(object.getBounds())) {
                if (object.isGoodObject()) {
                    this.player.addScore(GameConfig.DEFAULT_SCORE);
                } else {
                    this.player.loseLife();
                }
                this.objects.remove(i);
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (this.player != null && this.gameState == GameConfig.STATE_PLAYING) {
            if (keyCode == KeyEvent.VK_RIGHT) {
                this.player.setRightPressed(true);
            }
            if (this.player != null && this.gameState == GameConfig.STATE_PLAYING) {
                if (keyCode == KeyEvent.VK_LEFT) {
                    this.player.setLeftPressed(true);
                }
            }

        }
        if (keyCode == KeyEvent.VK_ENTER) {
            if (this.gameState == GameConfig.STATE_START) {
                this.gameState = GameConfig.STATE_PLAYING;
            } else if (this.gameState == GameConfig.STATE_GAME_OVER) {
                if (this.player.getScore() > this.bestScore) {
                    this.bestScore = this.player.getScore();
                }
                this.player.resetLives();
                this.player.resetScore();
                this.player.resetLocation();
                this.objects.clear();
                this.gameState = GameConfig.STATE_PLAYING;
            }
        }

        if (keyCode == KeyEvent.VK_P) {
            if (this.gameState == GameConfig.STATE_PLAYING) {
                this.gameState = GameConfig.STATE_PAUSE;
            } else if (this.gameState == GameConfig.STATE_PAUSE) {
                this.gameState = GameConfig.STATE_PLAYING;
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
                if (gameState == GameConfig.STATE_PLAYING) {
                    this.player.updatePlayerLocation();
                    this.update();
                }
                this.repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        });
        gameThread.start();
    }

    public void update() {
        if (Math.random() < GameConfig.OBJECTS_SPAWN_RATE) {
            this.objects.add(new FallingObject());
        }
        for (int i = this.objects.size() - 1; i >= 0; i--) {
            FallingObject fallingObject = this.objects.get(i);
            fallingObject.updateLocation();

            if (fallingObject.getCurrentY() > GameConfig.SCREEN_HEIGHT) {
                this.objects.remove(i);
                continue;
            }
        }
        this.checkCollision();
        if (this.player.getLives() <= 0) {
            this.gameState = GameConfig.STATE_GAME_OVER;
        }
    }


}


