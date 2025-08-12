package se.liu.mellu161.tetris;

import javax.swing.*;
import java.awt.*;

public class OldTetrisViewer {
    private JFrame frame = new JFrame("Tetris");
    private JTextArea textArea;

    public OldTetrisViewer(Board board) {
        JTextArea textArea = new JTextArea(board.getWidth(), board.getHeight());
        BoardToTextConverter boardToTextConverter = new BoardToTextConverter();
        String textBoard = boardToTextConverter.convertToText(board);
        textArea.setText(textBoard);
        this.textArea = textArea;

    }

    public void show(){
        frame.setLayout(new BorderLayout());
        frame.add(textArea, BorderLayout.CENTER);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
        frame.pack();
        frame.setVisible(true);
    }
}
