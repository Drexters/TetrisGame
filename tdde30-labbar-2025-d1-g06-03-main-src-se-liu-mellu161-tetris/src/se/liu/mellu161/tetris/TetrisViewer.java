package se.liu.mellu161.tetris;

import javax.swing.*;
import java.awt.*;

public class TetrisViewer {
    private JFrame frame = new JFrame("Tetris");
    private Component component;
    private JLabel scoreLabel;

    public TetrisViewer(Board board, Component component) {
        this.component = component;
        scoreLabel = new JLabel("Score: " + board.getCurrentScore(), SwingConstants.CENTER);
        board.setScoreUpdateCallback(this::updateScore);// tar emot värdet från board
    }

    public JFrame getFrame() {
        return frame;
    }

    public void updateScore(int newScore) {
        // lambda funktion som skickas till board
        SwingUtilities.invokeLater(() -> {
            scoreLabel.setText( "Score: " + newScore);  // Uppdatera texten
        });
    }

    public void show(){
        frame.setLayout(new BorderLayout());
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 25));
        frame.add(scoreLabel, BorderLayout.NORTH);
        frame.add(component, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
