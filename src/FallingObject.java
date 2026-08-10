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
    Thread thread = new Thread(() -> {
        while (this.currentY <= GameConfig.SCREEN_HEIGHT) {
            setCurrentY(this.currentY + this.speed);
        }
    });
    private Image myImage;

    public FallingObject() {
        Random random = new Random();
        this.currentX = random.nextInt(0, GameConfig.SCREEN_WIDTH - GameConfig.OBJECT_WIDTH);
        this.currentY = 0;
        this.goodObject = random.nextBoolean();
        this.width = GameConfig.OBJECT_WIDTH;
        this.height = GameConfig.OBJECT_HEIGHT;
        this.speed = GameConfig.OBJECT_SPEED;

        int imageNumber = random.nextInt(1, 5);

        try {
            if (this.goodObject) {
                switch (imageNumber) {
                    case 1:
                        this.myImage = ImageIO.read(new File("food1.png"));
                        break;
                    case 2:
                        this.myImage = ImageIO.read(new File("food2.png"));
                        break;
                    case 3:
                        this.myImage = ImageIO.read(new File("food3.png"));
                        break;
                    case 4:
                        this.myImage = ImageIO.read(new File("food5.png"));
                        break;
                }
            } else {
                switch (imageNumber) {
                    case 1:
                        this.myImage = ImageIO.read(new File("enemy1.png"));
                        break;
                    case 2:
                        this.myImage = ImageIO.read(new File("enemy2.png"));
                        break;
                    case 3:
                        this.myImage = ImageIO.read(new File("enemy3.png"));
                        break;
                    case 4:
                        this.myImage = ImageIO.read(new File("enemy4.png"));
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error while try to load object image!");
        }
    }

    public void draw(Graphics graphics) {
        if (this.myImage != null) {
            graphics.drawImage(this.myImage, this.currentX, this.currentY, this.width, this.height, null);
        }
    }

    public void setCurrentY(int y) {
        this.currentY = y;
    }

    public Rectangle getBounds() {
        return new Rectangle(this.currentX, this.currentY, GameConfig.OBJECT_WIDTH, GameConfig.OBJECT_HEIGHT);
    }

    public boolean isGoodObject() {
        return this.goodObject;
    }
}
