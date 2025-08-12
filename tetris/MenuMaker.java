package se.liu.mellu161.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuMaker
{
    private JFrame frame;
    private HighscoreList highscoreList;
    private Board board;
    private RunTetris runTetris;

    public MenuMaker(TetrisViewer viewer, HighscoreList highscoreList, RunTetris runTetris){
	this.frame = viewer.getFrame();
	this.highscoreList = highscoreList;
	this.board = null;
	this.runTetris = runTetris;

	JMenuBar menuBar = new JMenuBar();
	final JMenu fileMenu = new JMenu("File");
	fileMenu.setFont(new Font("Arial", Font.BOLD, 20));

	// skapar menyknapp avsluta som ett "menuItem"
	final JMenuItem exit = new JMenuItem("Avsluta");
	exit.setFont(new Font("Arial", Font.BOLD, 15));
	exit.addActionListener(new ActionListener() {
	    @Override
	    // sägar vad som ska hända när "avsluta" trycks på
	    public void actionPerformed(ActionEvent e) {
		 if (JOptionPane.showConfirmDialog(frame, "avsluta Tetris?", "Avsluta",
					      JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
		     System.exit(0); // Stänger programmet
		 }
	    }
	});

	final JMenuItem restart = new JMenuItem("Omstart");
	restart.setFont(new Font("Arial", Font.BOLD, 15));
	restart.addActionListener(new ActionListener() {
	    @Override
	    // sägar vad som ska hända när "Omstart" trycks på
	    public void actionPerformed(ActionEvent e) {
		if (JOptionPane.showConfirmDialog(frame, "Spela igen?", "omstart",
						  JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){

		    // om gameOver == true så har vi redan sparat highscoer ty gameOver() har körts
		    // Detta stoppar spelat från att spara higscore två gånger istället för en
		    if (board.gameOver){
		       restartApplication();
		    }
		    else{
		       restartGame();
		    }
		}
	    }
	});

	// läggar till allt där det ska vara
	fileMenu.add(exit);
	fileMenu.add(restart);
	menuBar.add(fileMenu);
	frame.setJMenuBar(menuBar);
    }

    public void addHighscore(){
	String name = JOptionPane.showInputDialog(frame, "Namn", "Namn", JOptionPane.PLAIN_MESSAGE);
	int score = board.getCurrentScore();
	Highscore highscore = new Highscore(name, score);
	highscoreList.addHighscore(highscore);
	highscoreList.sort(new ScoreComparator());
    }

    public void restartGame(){

	gameOver();

	// startar om spelet
	restartApplication();
    }

    public void gameOver() {

	runTetris.stopGame();

	// hämtar highscore från fil
	loadHighscoreList();

	// lägger till spelaren och dens poäng i HighscoreList
	addHighscore();

	// lägger till highscore i fil
	saveHighscoreList();

	// visar HighscoreList på skärmen och väntar på att starta nästa spelomgång
	showHighscoreList();
    }

    public void loadHighscoreList() {
	// fångar upp fel som skickas hit från loadHighscoreToFile i HighscoreList
	boolean loadedToFile = false;
	while (!loadedToFile){
	    try {
		highscoreList.loadHighscoreFromFile();
		loadedToFile = true;
	    } catch (IOException je) {
		int choice =
			JOptionPane.showConfirmDialog(frame, "Kunde inte lada ner highscore!\n" + je.getMessage() + "vill du försöka ingen",
						      "Fel",JOptionPane.YES_NO_OPTION ,JOptionPane.ERROR_MESSAGE);
		if (choice == JOptionPane.NO_OPTION) {
		    break; // Avbryt om användaren inte vill försöka igen
		}
	    }
	}
    }

    public void saveHighscoreList()  {
	// fångar upp fel som skickas hit från saveHighscoreToFile i HighscoreList
	boolean savedToFile = false;
	while(!savedToFile) {
	    try {
		highscoreList.saveHighscoreToFile();
		savedToFile = true;
	    } catch (IOException ex) {
		int choice =
			JOptionPane.showConfirmDialog(frame, "Kunde inte spara highscore!\n" + ex.getMessage() + "vill du försöka ingen",
						      "Fel", JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
		if (choice == JOptionPane.NO_OPTION) {
		    break; // Avbryt om användaren inte vill försöka igen
		}
	    }
	}
    }

    public void showHighscoreList() {
	String highscores = highscoreList.toString();
	JOptionPane.showMessageDialog(frame, highscores, "Tetris", JOptionPane.PLAIN_MESSAGE);
    }


    public void restartApplication() {
	//runTetris.stopGame();
	frame.dispose(); // Stänger det nuvarande fönstret
	RunTetris runTetris = new RunTetris(highscoreList);
    }

    public void setBoard(Board board){
	this.board = board;
    }

}
