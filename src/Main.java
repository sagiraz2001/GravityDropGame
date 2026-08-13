import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Gravity Drop Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.startGame();
    }
}