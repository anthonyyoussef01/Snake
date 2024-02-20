package SnakeGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.*;

public class GamePanel extends JPanel implements ActionListener {
    // Global variables
    static int SCREEN_WIDTH = 600;
    static int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    int highScore = 0;

    /*
        This is the constructor for the GamePanel class
        It sets the preferred size of the panel to the screen width and height, sets the background color to black,
        sets the panel to be focusable, adds a key listener to the panel, and starts the game
    */
    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        loadHighScore();
        startGame();
    }

    // GETTERS:
    // Global variables
    public int getScreenWidth() {
        return SCREEN_WIDTH;
    }
    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }
    public int getUnitSize() {
        return UNIT_SIZE;
    }
    public int getGameUnits() {
        return GAME_UNITS;
    }
    public int getDelay() {
        return DELAY;
    }
    // Local variables
    public int[] getSnakeX() {
        return x.clone();
    }
    public int[] getSnakeY() {
        return y.clone();
    }
    public int getBodyParts() {
        return bodyParts;
    }
    public int getApplesEaten() {
        return applesEaten;
    }
    public int getAppleX() {
        return appleX;
    }
    public int getAppleY() {
        return appleY;
    }
    public char getDirection() {
        return direction;
    }
    public boolean isRunning() {
        return running;
    }
    public Timer getTimer() {
        return new Timer(timer.getDelay(), new GamePanel());
    }
    // Do we even need this?
    public Random getRandom() {
        return random;
    }
    public int getHighScore() {
        return highScore;
    }
    // SETTERS:
    public void setBodyParts(int bodyParts) {
        this.bodyParts = bodyParts;
    }
    public void setSnakeX(int[] x) {
        if (x.length != this.x.length) {
            throw new IllegalArgumentException("Array length must be " + this.x.length);
        }
        for (int i = 0; i < x.length; i++) {
            this.x[i] = x[i];
        }
    }
    public void setSnakeY(int[] y) {
        if (y.length != this.y.length) {
            throw new IllegalArgumentException("Array length must be " + this.y.length);
        }
        for (int i = 0; i < y.length; i++) {
            this.y[i] = y[i];
        }
    }
    public void setAppleX(int appleX) {
        this.appleX = appleX;
    }
    public void setAppleY(int appleY) {
        this.appleY = appleY;
    }
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }


    /*
        This method starts the game by setting the running variable to true, creating a new apple, and starting the timer
    */
    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        bodyParts = 6;
        applesEaten = 0;
        direction = 'R';
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 0;
            y[i] = 0;
        }
    }

    /*
        This method draws the grid on the panel
    */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /*
        This method draws a grid on the panel to help visualize the game
    */
    public void draw(Graphics g) {
        if (running) {
            /*
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
             */
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255),
                            random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        } else {
            gameOver(g);
        }
    }

    /*
        This method creates a new apple at a random location on the panel
    */
    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    /*
        This method checks if the snake has eaten an apple
    */
    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    /*
        This method checks if the snake has collided with itself or the borders of the panel
    */
    public void checkCollisions() {
        // Check if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

        // Check if head touches left border
        if (x[0] < 0) {
            running = false;
        }

        // Check if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }

        // Check if head touches top border
        if (y[0] < 0) {
            running = false;
        }

        // Check if head touches bottom border
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        // If the game is over, stop the timer
        if (!running) {
            timer.stop();
        }

        // If the game is over, stop the timer
        if (!running) {
            timer.stop();
            // Update high score if current score is greater
            if (applesEaten > highScore) {
                highScore = applesEaten;
            }
            saveHighScore();
        }
    }

    /*
        This method displays the game over text and the score
    */
    public void gameOver(Graphics g) {
        // Game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
        // Score text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten)) / 2,
                g.getFont().getSize());
        // High score text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics4 = getFontMetrics(g.getFont());
        g.drawString("High Score: " + highScore, (SCREEN_WIDTH - metrics4.stringWidth("High Score: " + highScore)) / 2,
                SCREEN_HEIGHT / 2 - 210);
        // Add a restart message
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Press SPACE to RESTART", (SCREEN_WIDTH - metrics3.stringWidth("Press SPACE to RESTART")) / 2,
                SCREEN_HEIGHT / 2 + 100);
    }

    public void saveHighScore() {
        try {
            FileWriter writer = new FileWriter("highscore.txt");
            writer.write(String.valueOf(highScore));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHighScore() {
        try {
            File file = new File("highscore.txt");
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                highScore = Integer.parseInt(reader.readLine());
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    /*
        This is the inner class for the key adapter
        It listens for key presses and changes the direction of the snake accordingly
    */
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                // if space is pressed, the game will restart
                case KeyEvent.VK_SPACE:
                    if (!running) {
                        startGame();
                    }
                    break;
                // if p is pressed, the game will pause or resume
                case KeyEvent.VK_P:
                    if (running) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                    running = !running;
                    break;
                // if escape is pressed, the game will exit
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
                // if s is pressed, the snake will slow down
                case KeyEvent.VK_S:
                    if (DELAY < 150) {
                        timer.setDelay(DELAY + 15);
                    }
                    break;
                // if d is pressed, the snake will speed up
                case KeyEvent.VK_D:
                    if (DELAY > 20) {
                        timer.setDelay(DELAY - 15);
                    }
                    break;
                // if f is pressed, the game will go full screen
                case KeyEvent.VK_F:
                    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                    if (gd.getFullScreenWindow() == null) {
                        gd.setFullScreenWindow((Window) SwingUtilities.getRoot(GamePanel.this));
                        SCREEN_WIDTH = gd.getDisplayMode().getWidth();
                        SCREEN_HEIGHT = gd.getDisplayMode().getHeight();
                    } else {
                        gd.setFullScreenWindow(null);
                        SCREEN_WIDTH = 600;
                        SCREEN_HEIGHT = 600;
                        // Ensure the snake's position is within the screen bounds
                        for (int i = 0; i < bodyParts; i++) {
                            x[i] = Math.min(x[i], SCREEN_WIDTH - UNIT_SIZE);
                            y[i] = Math.min(y[i], SCREEN_HEIGHT - UNIT_SIZE);
                        }
                        // Ensure the apple's position is within the screen bounds
                        appleX = Math.min(appleX, SCREEN_WIDTH - UNIT_SIZE);
                        appleY = Math.min(appleY, SCREEN_HEIGHT - UNIT_SIZE);
                    }
                    break;
            }
        }
    }
}
