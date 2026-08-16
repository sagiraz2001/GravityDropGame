import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Player {

    private int locationX;
    private int locationY;
    private int width;
    private int height;
    private BufferedImage image;
    private int lives;
    private int score;
    private boolean rightPressed;
    private boolean leftPressed;


    public Player() {
        Random random = new Random();
        this.locationX = random.nextInt(GameConfig.SCREEN_WIDTH - GameConfig.PLAYER_WIDTH);
        this.locationY = GameConfig.PLAYER_DEFAULT_POSITION;
        this.width = GameConfig.PLAYER_WIDTH;
        this.height = GameConfig.PLAYER_HEIGHT;
        this.lives = 3;
        this.score = 0;
        try {
            image = ImageIO.read(new File("Assets/player2.png"));
        } catch (IOException exception) {
            System.out.println("Error while try to loading player image!");
            exception.printStackTrace();
        }
    }

    public void draw(Graphics graphics) {
        if (this.image != null) {
            graphics.drawImage(this.image, locationX, locationY, GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_HEIGHT, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(this.locationX, this.locationY, GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_HEIGHT);
    }

    public int getLives() {
        return this.lives;
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void loseLife() {
        this.lives--;
    }

    public boolean isDead() {
        return this.lives <= 0;
    }

    // Updates the flag when the right arrow key is pressed or released
    public void setRightPressed(boolean pressed) {
        this.rightPressed = pressed;
    }

    // Updates the flag when the right arrow key is pressed or released
    public void setLeftPressed(boolean pressed) {
        this.leftPressed = pressed;
    }

    /**
     * Updates the player X locations according to the current keyboard input.
     * Prevent the player from moving out of the screen bounds.
     */
    public void updatePlayerLocation() {
        if (this.rightPressed && (this.locationX + this.width < GameConfig.SCREEN_WIDTH)) {
            this.locationX += GameConfig.PLAYER_SPEED;
        }
        if (this.leftPressed && (this.locationX > 0)) {
            this.locationX -= GameConfig.PLAYER_SPEED;
        }
    }

    public void resetScore() {
        this.score = 0;
    }

    public void resetLives() {
        this.lives = 3;
    }

    public void resetLocation() {
        Random random = new Random();
        this.locationX = random.nextInt(GameConfig.SCREEN_WIDTH - GameConfig.PLAYER_WIDTH);
        this.locationY = GameConfig.PLAYER_DEFAULT_POSITION;
        this.width = GameConfig.PLAYER_WIDTH;
        this.height = GameConfig.PLAYER_HEIGHT;
    }
}
