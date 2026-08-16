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
        try {
            Font pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File(("Assets/Pixel_Font2.ttf")));
            this.fontSmall = pixelFont.deriveFont(Font.PLAIN, GameConfig.SMALL_FONT_SIZE);
            this.fontLarge = pixelFont.deriveFont(Font.BOLD, GameConfig.LARGE_FONT_SIZE);
        } catch (FontFormatException | IOException exception) {
            System.out.println("Error while loading font! Using Arial instead.");
            this.fontSmall = new Font("Arial", Font.BOLD, GameConfig.SMALL_FONT_SIZE);
            this.fontLarge = new Font("Arial", Font.BOLD, GameConfig.LARGE_FONT_SIZE);
            exception.printStackTrace();
        }
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


    public void draw(Graphics graphics, int gameState, int bestScore) {
        switch (gameState) {
            case GameConfig.STATE_START:
                graphics.setColor(new Color(0, 0, 0, 150));
                graphics.fillRect(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
                graphics.setFont(fontLarge);
                graphics.setColor(Color.BLACK);
                graphics.drawString("Gravity Drop", GameConfig.TITLE_LOCATION_X + 4, GameConfig.TITLE_LOCATION_Y + 4);
                graphics.setColor(Color.RED);
                graphics.drawString("Gravity Drop", GameConfig.TITLE_LOCATION_X, GameConfig.TITLE_LOCATION_Y);
                graphics.setFont(fontSmall);
                graphics.setColor(Color.WHITE);
                for (int i = 0; i < GameConfig.INSTRUCTIONS.length; i++) {
                    int currentY = GameConfig.INSTRUCTIONS_START_Y + (i * GameConfig.INSTRUCTIONS_LINE_SPACING);
                    graphics.drawString(GameConfig.INSTRUCTIONS[i], GameConfig.INSTRUCTIONS_X, currentY);
                }
                graphics.setColor(Color.YELLOW);
                graphics.drawString("PRESS 'ENTER' TO START", GameConfig.START_ENTER_MSG_X, GameConfig.START_ENTER_MSG_Y);
                graphics.setColor(Color.WHITE);
                graphics.drawString("Best Score: " + bestScore, GameConfig.BEST_SCORE_LOCATION_X, GameConfig.BEST_SCORE_LOCATION_Y);
                break;

            case GameConfig.STATE_PLAYING:
                graphics.setFont(fontSmall);
                graphics.setColor(Color.WHITE);
                graphics.drawString("Score: " + this.player.getScore(), GameConfig.SCORE_LOCATION_X, GameConfig.SCORE_LOCATION_Y);

                for (int i = 0; i < 3; i++) {
                    int currentX = GameConfig.HEART_START_X + (i * GameConfig.HEART_SPACING);
                    if (i < this.player.getLives()) {
                        graphics.drawImage(fullHeartImage, currentX, GameConfig.HEART_Y, GameConfig.HEART_WIDTH, GameConfig.HEART_HEIGHT, null);
                    } else {
                        graphics.drawImage(emptyHeartImage, currentX, GameConfig.HEART_Y, GameConfig.HEART_WIDTH, GameConfig.HEART_HEIGHT, null);
                    }
                }
                graphics.drawString("Best Score: " + bestScore, GameConfig.BEST_SCORE_LOCATION_X, GameConfig.BEST_SCORE_LOCATION_Y);
                break;

            case GameConfig.STATE_GAME_OVER:
                graphics.setColor(new Color(0, 0, 0, 150));
                graphics.fillRect(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
                graphics.setFont(fontLarge);
                graphics.setColor(Color.RED);
                graphics.drawString("Game Over!", GameConfig.GAME_OVER_TITLE_X, GameConfig.TITLE_LOCATION_Y);
                graphics.setFont(fontSmall);
                graphics.setColor(Color.WHITE);
                if (this.player.getScore() > bestScore && this.player.getScore() > 0) {
                    graphics.setColor(Color.GREEN);
                    graphics.drawString("NEW RECORD: " + this.player.getScore(), GameConfig.GAME_OVER_RECORD_X, GameConfig.GAME_OVER_SCORE_Y);
                    graphics.setColor(Color.WHITE);
                } else {
                    graphics.drawString("Final Score: " + this.player.getScore(), GameConfig.GAME_OVER_SCORE_X, GameConfig.GAME_OVER_SCORE_Y);
                    graphics.drawString("Best Score: " + bestScore, GameConfig.GAME_OVER_BEST_SCORE_X, GameConfig.GAME_OVER_BEST_SCORE_Y);
                }
                graphics.setColor(Color.YELLOW);
                graphics.drawString("Press 'ENTER' to Restart", GameConfig.GAME_OVER_RST_MSG_X, GameConfig.GAME_OVER_RST_MSG_Y);

                break;

            case GameConfig.STATE_PAUSE:
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
                graphics.setFont(fontSmall);
                graphics.drawString("Best Score: " + bestScore, GameConfig.BEST_SCORE_LOCATION_X, GameConfig.BEST_SCORE_LOCATION_Y);
                graphics.setColor(new Color(0, 0, 0, 150));
                graphics.fillRect(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
                graphics.setFont(fontLarge);
                graphics.setColor(Color.WHITE);
                graphics.drawString("Game Paused", GameConfig.TITLE_LOCATION_X, GameConfig.TITLE_LOCATION_Y);

                break;
        }

    }
}
