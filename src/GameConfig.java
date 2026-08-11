import java.awt.*;

public class GameConfig {

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    public static final int OBJECT_SPEED = 4;
    public static final int OBJECT_WIDTH = 15;
    public static final int OBJECT_HEIGHT = 15;

    public static final Color SKY_COLOR = new Color(81, 165, 201); //Sky Blue - Can be modified
    public static final Color GRASS_COLOR = new Color(52, 179, 52); //Grass green - Can be modified
    public static final int GRASS_HEIGHT = 50;

    public static final int PLAYER_SPEED = 3;
    public static final int PLAYER_WIDTH = 30;
    public static final int PLAYER_HEIGHT = 30;
    public static final int PLAYER_DEFAULT_POSITION = SCREEN_HEIGHT - GRASS_HEIGHT - PLAYER_HEIGHT; //Need to check this value and modify if needed

    public static final int DEFAULT_SCORE = 1;

    public static final int STATE_START = 0;
    public static final int STATE_PLAYING = 1;
    public static final int STATE_GAME_OVER = 2;

    public static final int SMALL_FONT_SIZE = 24;
    public static final int LARGE_FONT_SIZE = 72;

    public static final int SCORE_LOCATION_X = 20;
    public static final int SCORE_LOCATION_Y = 30;

    public static final int HEART_WIDTH = 20;
    public static final int HEART_HEIGHT = 20;
    public static final int HEART_SPACING = 30;
    public static final int HEART_START_X = (int) (SCREEN_WIDTH * 0.8);
    public static final int HEART_Y = SCREEN_HEIGHT / 20;


}
