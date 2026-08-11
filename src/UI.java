import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UI {
    private Font fontSmall;
    private Font fontLarge;
    private BufferedImage fullHeartImage;
    private BufferedImage emptyHeartImage;
    private Player player;


    public UI(Player player) {
        this.fontSmall = new Font("Arial", Font.BOLD, GameConfig.SMALL_FONT_SIZE);
        this.fontLarge = new Font("Arial", Font.BOLD, GameConfig.LARGE_FONT_SIZE);
        try {
            fullHeartImage = ImageIO.read(new File("Assets/full_heart.png"));
        } catch (IOException exception) {
            System.out.println("Error while try to loading full heart image!");
            exception.printStackTrace();
        }
        try {
            emptyHeartImage = ImageIO.read(new File("Assets/empty_heart.png"));
        } catch (IOException exception) {
            System.out.println("Error while try to loading empty heart image!");
            exception.printStackTrace();
        }
        this.player = player;
    }


    public void draw(Graphics graphics, int gameState) {
        switch (gameState) {
            case GameConfig.STATE_START:
                //צריך להשלים פה
                break;

            case GameConfig.STATE_PLAYING:
                graphics.setFont(fontSmall);
                graphics.setColor(Color.WHITE);
                graphics.drawString("Score:" + this.player.getScore(), GameConfig.SCORE_LOCATION_X, GameConfig.SCORE_LOCATION_Y);

                for (int i = 0; i < 3; i++) {
                    int currentX = GameConfig.HEART_START_X + (i * GameConfig.HEART_SPACING);
                    if (i < this.player.getLives()) {
                        graphics.drawImage(fullHeartImage, currentX, GameConfig.HEART_Y, GameConfig.HEART_WIDTH, GameConfig.HEART_HEIGHT, null);
                    } else {
                        graphics.drawImage(emptyHeartImage, currentX, GameConfig.HEART_Y, GameConfig.HEART_WIDTH, GameConfig.HEART_HEIGHT, null);
                    }
                }
                break;

            case GameConfig.STATE_GAME_OVER:
                // צריך להשלים
                break;
        }

    }
}
