import java.util.Random;
import java.awt.Image;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class FallingObject {
    private int currentX;
    private int currentY;
    private boolean isGoodObject;
    private int width;
    private int hight;
    private int speed;
    private Image myImage;

    public FallingObject (){
        Random random = new Random();
        this.currentX = random.nextInt(0, GameConfig.SCREEN_WIDTH - GameConfig.OBJECT_WIDTH);
        this.currentY = 0;
        this.isGoodObject = random.nextBoolean();
        this.width = GameConfig.OBJECT_WIDTH;
        this.hight = GameConfig.OBJECT_HEIGHT;
        this.speed = GameConfig.OBJECT_SPEED;

        int imageNumber = random.nextInt(1, 5);

        try {
            if (this.isGoodObject) {
                switch (imageNumber) {
                    case 1: this.myImage = ImageIO.read(new File("food1.png")); break;
                    case 2: this.myImage = ImageIO.read(new File("food2.png")); break;
                    case 3: this.myImage = ImageIO.read(new File("food3.png")); break;
                    case 4: this.myImage = ImageIO.read(new File("food5.png")); break;
                }
            } else {
                switch (imageNumber) {
                    case 1: this.myImage = ImageIO.read(new File("enemy1.png")); break;
                    case 2: this.myImage = ImageIO.read(new File("enemy2.png")); break;
                    case 3: this.myImage = ImageIO.read(new File("enemy3.png")); break;
                    case 4: this.myImage = ImageIO.read(new File("enemy4.png")); break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error while try to load object image!");
        }
    }

    public void draw (Graphics graphics){
        if (this.myImage != null) {
            graphics.drawImage(this.myImage, this.currentX, this.currentY, this.width, this.hight, null);
        }
    }

    public void setCurrentY(int y) {
        this.currentY = y;
    }

    Thread thread = new Thread(() -> {
            while (this.currentY <= GameConfig.SCREEN_HEIGHT){
                setCurrentY(this.currentY + this.speed);
            }
    });



}
