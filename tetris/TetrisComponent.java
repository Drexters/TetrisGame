package se.liu.mellu161.tetris;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class TetrisComponent extends JComponent implements BoardListener
{
    private final Board board;
    private static final int TILE_SIZE = 30;
    private final static Map<SquareType, Color> SQUARE_COLORS = new EnumMap<>(SquareType.class);

    static {
        SQUARE_COLORS.putAll(Map.ofEntries(Map.entry(SquareType.EMPTY, Color.getHSBColor(0,0,0.15f)), Map.entry(SquareType.I, Color.CYAN),
                                           Map.entry(SquareType.J, Color.BLUE), Map.entry(SquareType.L, Color.ORANGE),
                                           Map.entry(SquareType.O, Color.YELLOW), Map.entry(SquareType.S, Color.GREEN),
                                           Map.entry(SquareType.T, Color.magenta), Map.entry(SquareType.Z, Color.RED),
                                           Map.entry(SquareType.OUTSIDE, Color.WHITE)));
    }

    public TetrisComponent(Board board) {
        this.board = board;
        board.addBoardListeners(this);
    }

    @Override public Dimension getPreferredSize() {
        return new Dimension(board.getWidth() * TILE_SIZE, board.getHeight() * TILE_SIZE);
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {// -- går igenom alla rutor
                SquareType poly = board.getVisibleSquqreAt(y, x); // -- hämtar squaretype
                g2d.setColor(SQUARE_COLORS.get(poly)); // -- sätter färg till alla squaretypes
                g2d.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE); // -- fyller i/ riter alla rektanglar

                g2d.setColor(Color.DARK_GRAY); // Borderfärg
                g2d.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE); // skappar border
            }
        }
    }

    @Override public void boardChanged() {
        this.repaint();
    }

}
