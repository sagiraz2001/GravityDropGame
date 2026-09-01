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

    /**
     * GamePanel Constructor: Initializes screen settings,
     * registers keyboard inputs, and starts the game loop.
     */
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

    /**
     * Updates the screen graphics. Draws everything layer by layer:
     * Background first, then player and objects, and UI last so it stays on top.
     */
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics); //Erase the previous frame and draw a new one
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

    /**
     * Checks if the player touched any falling object.
     * If hit, updates the score or lives and removes the object.
     * We loop backwards to safely remove objects without causing errors.
     */
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

    /**
     * Handles keyboard input when a key is pressed down.
     * Updates movement flags for smooth control and manages game states.
     */
    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (this.player != null && this.gameState == GameConfig.STATE_PLAYING) {
            if (keyCode == KeyEvent.VK_RIGHT) {
                this.player.setRightPressed(true);
            }
            if (keyCode == KeyEvent.VK_LEFT) {
                this.player.setLeftPressed(true);
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

    /**
     * Starts the main game loop in a separate thread.
     * Runs while the game is running, updating logic when playing,
     * repainting the screen, and sleeping briefly to control the frame rate.
     */
    public void startGame() {
        this.isRunning = true;
        Thread gameThread = new Thread(() -> {
            // If the game is not running (Paused) - the update will stop and the screen will be "frozen"
            while (this.isRunning) {
                if (gameState == GameConfig.STATE_PLAYING) {
                    this.player.updatePlayerLocation();
                    this.update();
                }
                // Request the system to redraw the screen
                this.repaint();
                // A short pause to control game speed and prevent CPU overload
                try {
                    Thread.sleep(10);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        });
        gameThread.start();
    }

    /**
     * Handles the core game logic per frame.
     * Spawns new falling objects, updates their positions, removes out-of-bounds items,
     * checks for collisions, and triggers Game Over if lives drop to zero.
     */
    public void update() {
        // Decides whether to spawn a new falling object based on a random chance per frame
        // For every point the player gets, the chances of more objects falling increases and the difficulty increases.
        double currentSpawnRate = GameConfig.OBJECTS_SPAWN_RATE + (this.player.getScore() * 0.001);
        if (Math.random() < currentSpawnRate) {
            this.objects.add(new FallingObject());
        }
        for (int i = this.objects.size() - 1; i >= 0; i--) {
            FallingObject fallingObject = this.objects.get(i);
            fallingObject.updateLocation();

            if (fallingObject.getCurrentY() > GameConfig.SCREEN_HEIGHT) {
                this.objects.remove(i);
            }
        }
        this.checkCollision();
        if (this.player.isDead()) {
            this.gameState = GameConfig.STATE_GAME_OVER;
        }
    }


}


