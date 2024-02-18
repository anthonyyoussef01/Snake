import javax.swing.*;

public class GameFrame extends JFrame {
    // This is the constructor for the GameFrame class
    // It adds a new GamePanel to the frame, sets the title of the frame to "Snake",
    // sets the default close operation to exit on close, sets the frame to not be resizable,
    // packs the frame, sets the frame to be visible, and sets the location of the frame to the center of the screen
    public GameFrame() {
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new GameFrame();
    }
}
