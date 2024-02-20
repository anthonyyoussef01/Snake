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

        assertTrue(gamePanel.running);
        assertNotNull(gamePanel.timer);
        assertEquals(6, gamePanel.bodyParts);
        assertEquals(0, gamePanel.applesEaten);
        assertEquals('R', gamePanel.direction);

        for (int i = 0; i < gamePanel.bodyParts; i++) {
            assertEquals(0, gamePanel.x[i]);
            assertEquals(0, gamePanel.y[i]);
        }
    }

    @Test
    public void startGameShouldPlaceApple() {
        gamePanel.startGame();

        assertTrue(gamePanel.appleX >= 0);
        assertTrue(gamePanel.appleX < GamePanel.SCREEN_WIDTH);
        assertTrue(gamePanel.appleY >= 0);
        assertTrue(gamePanel.appleY < GamePanel.SCREEN_HEIGHT);
    }

    @Test
    public void newAppleShouldPlaceAppleInValidLocation() {
        gamePanel.newApple();

        assertTrue(gamePanel.appleX >= 0);
        assertTrue(gamePanel.appleX < GamePanel.SCREEN_WIDTH);
        assertTrue(gamePanel.appleY >= 0);
        assertTrue(gamePanel.appleY < GamePanel.SCREEN_HEIGHT);
    }

    @Test
    public void moveShouldChangeSnakePosition() {
        gamePanel.startGame();
        int initialX = gamePanel.x[0];
        int initialY = gamePanel.y[0];

        gamePanel.move();

        assertEquals(initialX + GamePanel.UNIT_SIZE, gamePanel.x[0]);
        assertEquals(initialY, gamePanel.y[0]);
    }

    @Test
    public void checkAppleShouldIncreaseScoreAndBodyPartsWhenAppleEaten() {
        gamePanel.startGame();
        gamePanel.appleX = gamePanel.x[0];
        gamePanel.appleY = gamePanel.y[0];

        gamePanel.checkApple();

        assertEquals(1, gamePanel.applesEaten);
        assertEquals(7, gamePanel.bodyParts);
    }

    @Test
    public void checkCollisionsShouldStopGameWhenSnakeHitsBorder() {
        gamePanel.startGame();
        gamePanel.x[0] = GamePanel.SCREEN_WIDTH + 1;

        gamePanel.checkCollisions();

        assertFalse(gamePanel.running);
    }

    @Test
    public void checkCollisionsShouldStopGameWhenSnakeHitsItself() {
        gamePanel.startGame();
        gamePanel.bodyParts = 2;
        gamePanel.x[0] = gamePanel.x[1];
        gamePanel.y[0] = gamePanel.y[1];

        gamePanel.checkCollisions();

        assertFalse(gamePanel.running);
    }

    @Test
    public void actionPerformedShouldMoveSnakeAndCheckCollisionsWhenRunning() {
        gamePanel.startGame();
        int initialX = gamePanel.x[0];
        int initialY = gamePanel.y[0];

        gamePanel.actionPerformed(null);

        assertEquals(initialX + GamePanel.UNIT_SIZE, gamePanel.x[0]);
        assertEquals(initialY, gamePanel.y[0]);
        assertTrue(gamePanel.running);
    }
}