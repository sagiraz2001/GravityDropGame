import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class FallingObject {
    private int currentX;
    private int currentY;
    private boolean goodObject;
    private int width;
    private int height;
    private int speed;
    private Image myImage;

    public FallingObject() {
        Random random = new Random();
        this.currentX = random.nextInt(0, GameConfig.SCREEN_WIDTH - GameConfig.OBJECT_WIDTH);
        this.currentY = 0;
        this.goodObject = random.nextBoolean();
        this.width = GameConfig.OBJECT_WIDTH;
        this.height = GameConfig.OBJECT_HEIGHT;
        this.speed = GameConfig.OBJECT_SPEED;

        int imageNumber = random.nextInt(1, 9);

        try {
            String folderPath = "Assets/";
            if (this.goodObject) {
                this.myImage = ImageIO.read(new File(folderPath + "food" + imageNumber + ".png"));
            } else {
                this.myImage = ImageIO.read(new File(folderPath + "enemy" + imageNumber + ".png"));
            }
        } catch (IOException exception) {
            System.out.println("Error while try to load object image: " + imageNumber);
        }

    }

    public void updateLocation() {
        this.currentY += this.speed;
    }

    public void draw(Graphics graphics) {
        if (this.myImage != null) {
            graphics.drawImage(this.myImage, this.currentX, this.currentY, this.width, this.height, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(this.currentX, this.currentY, GameConfig.OBJECT_WIDTH, GameConfig.OBJECT_HEIGHT);
    }

    public boolean isGoodObject() {
        return this.goodObject;
    }

    public int getCurrentY() {
        return this.currentY;
    }

}
