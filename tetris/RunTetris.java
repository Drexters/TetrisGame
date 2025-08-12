package se.liu.mellu161.tetris;
import javax.swing.*;
import java.awt.event.ActionEvent;
public class RunTetris  {
    private Timer timer = null;

    public RunTetris(HighscoreList highscoreList){
        Board board = new Board(10, 20);
        TetrisComponent component = new TetrisComponent(board);
        TetrisViewer tetrisViewer = new TetrisViewer(board, component); // skicka inte in board
        KeyHandler keyListener = new KeyHandler(component, board);
        MenuMaker menuMaker = new MenuMaker(tetrisViewer, highscoreList, this);
        StartScreenDisplay startScreenDisplay = new StartScreenDisplay(tetrisViewer.getFrame());
        menuMaker.setBoard(board);
        board.setMenuMaker(menuMaker);

        // Visa startskärmen och starta spelet efter 3 sekunder (lambda function som skickas till showImage)
        startScreenDisplay.showImage(() -> {
            tetrisViewer.show(); // Visa spelet efter startskärmen
            startGame(board);
        });
    }

    public void stopGame() {
        if (timer != null) {
            timer.stop();
        }
        timer = null;
    }

    public  void startGame(Board board) {
        final Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                board.tick();
            }
        };

        if (timer != null) {
            timer.stop();  // Stoppa tidigare timer om den finns
        }

        timer = new Timer(1000, doOneStep);
        timer.setCoalesce(true);
        timer.start();
    }

    public static void main(String[] args) {
        HighscoreList highscoreList = new HighscoreList();
        RunTetris runTetris = new RunTetris(highscoreList);
    }

}
