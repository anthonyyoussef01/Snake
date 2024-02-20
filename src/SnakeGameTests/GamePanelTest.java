package SnakeGameTests;
import SnakeGame.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GamePanelTest {
    private GamePanel gamePanel;

    @BeforeEach
    public void setup() {
        gamePanel = new GamePanel();
    }

    @Test
    public void startGameShouldInitializeGame() {
        gamePanel.startGame();

        assertTrue(gamePanel.isRunning());
        assertNotNull(gamePanel.getTimer());
        assertEquals(6, gamePanel.getBodyParts());
        assertEquals(0, gamePanel.getApplesEaten());
        assertEquals('R', gamePanel.getDirection());

        for (int i = 0; i < gamePanel.getBodyParts(); i++) {
            assertEquals(0, gamePanel.getSnakeX()[i]);
            assertEquals(0, gamePanel.getSnakeX()[i]);
        }
    }

    @Test
    public void startGameShouldPlaceApple() {
        gamePanel.startGame();

        assertTrue(gamePanel.getAppleX() >= 0);
        assertTrue(gamePanel.getAppleX() < gamePanel.getScreenWidth());
        assertTrue(gamePanel.getAppleY() >= 0);
        assertTrue(gamePanel.getAppleY() < gamePanel.getScreenHeight());
    }

    @Test
    public void newAppleShouldPlaceAppleInValidLocation() {
        gamePanel.newApple();

        assertTrue(gamePanel.getAppleX() >= 0);
        assertTrue(gamePanel.getAppleX() < gamePanel.getScreenWidth());
        assertTrue(gamePanel.getAppleY() >= 0);
        assertTrue(gamePanel.getAppleY() < gamePanel.getScreenHeight());
    }

    @Test
    public void moveShouldChangeSnakePosition() {
        gamePanel.startGame();
        int initialX = gamePanel.getSnakeX()[0];
        int initialY = gamePanel.getSnakeY()[0];

        gamePanel.move();

        assertEquals(initialX + gamePanel.getUnitSize(), gamePanel.getSnakeX()[0]);
        assertEquals(initialY, gamePanel.getSnakeY()[0]);
    }

    @Test
    public void checkAppleShouldIncreaseScoreAndBodyPartsWhenAppleEaten() {
        gamePanel.startGame();
        gamePanel.setAppleX(gamePanel.getSnakeX()[0]);
        gamePanel.setAppleY(gamePanel.getSnakeY()[0]);

        gamePanel.checkApple();

        assertEquals(1, gamePanel.getApplesEaten());
        assertEquals(7, gamePanel.getBodyParts());
    }

    @Test
    public void checkCollisionsShouldStopGameWhenSnakeHitsBorder() {
        gamePanel.startGame();
        gamePanel.getSnakeX()[0] = gamePanel.getScreenWidth() + 1;

        gamePanel.checkCollisions();

        assertFalse(gamePanel.isRunning());
    }

    @Test
    public void checkCollisionsShouldStopGameWhenSnakeHitsItself() {
        gamePanel.startGame();
        gamePanel.setBodyParts(2);
        int[] SnakeX = gamePanel.getSnakeX();
        SnakeX[0] = gamePanel.getSnakeX()[1];
        gamePanel.setSnakeX(SnakeX);
        int[]SnakeY = gamePanel.getSnakeY();
        SnakeX[0] = gamePanel.getSnakeY()[1];
        gamePanel.setSnakeY(SnakeX);

        gamePanel.checkCollisions();

        assertFalse(gamePanel.isRunning());
    }

    @Test
    public void actionPerformedShouldMoveSnakeAndCheckCollisionsWhenRunning() {
        gamePanel.startGame();
        int initialX = gamePanel.getSnakeX()[0];
        int initialY = gamePanel.getSnakeY()[0];

        gamePanel.actionPerformed(null);

        assertEquals(initialX + gamePanel.getUnitSize(), gamePanel.getSnakeX()[0]);
        assertEquals(initialY, gamePanel.getSnakeY()[0]);
        assertTrue(gamePanel.isRunning());
    }

    @Test
    public void saveAndLoadHighScoreShouldPersistHighScore() {
        int expectedHighScore = 37;
        gamePanel.setHighScore(expectedHighScore);
        gamePanel.saveHighScore();
        gamePanel.setHighScore(10);
        gamePanel.loadHighScore();
        assertEquals(expectedHighScore, gamePanel.getHighScore());
    }
}