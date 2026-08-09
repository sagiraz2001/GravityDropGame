import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {

    private int locationX;
    private int locationY;
    private int width;
    private int height;
    private BufferedImage image;
    private int lives;
    private int score;

    public Player(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.width = GameConfig.PLAYER_WIDTH; // בהתאם לגודל התמונה שתוריד (למשל 32 פיקסלים)
        this.height = GameConfig.PLAYER_HEIGHT;
        this.lives = 3;
        this.score = 0;
        try {
            image = ImageIO.read(getClass().getResource("/player2.png"));
        } catch (IOException e) {
            System.out.println("Error while try to loading player image!");
            e.printStackTrace();
        }
    }

    public void moveRight() {
        if (locationX + width < GameConfig.SCREEN_WIDTH) {
            locationX += GameConfig.PLAYER_SPEED;
        }
    }

    public void moveLeft() {
        if (locationX > 0) {
            locationX -= GameConfig.PLAYER_SPEED;
        }
    }

    public void draw(Graphics graphics) {
        if (this.image != null) {
            graphics.drawImage(this.image, locationX, locationY, GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_HEIGHT, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(locationX, locationY, GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_HEIGHT);
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
}

