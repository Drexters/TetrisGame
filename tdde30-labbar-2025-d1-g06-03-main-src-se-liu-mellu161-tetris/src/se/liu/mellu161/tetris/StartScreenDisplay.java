package se.liu.mellu161.tetris;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class StartScreenDisplay
{
    private JFrame frame;
    private static final String IMAGE_PATH = "images/tetris_startscreen.jpg"; // Bildens resursväg

    public StartScreenDisplay(JFrame frame) {
	this.frame = frame;
    }

    public void showImage(Runnable startGameCallback) {
	// Ladda bilden via ClassLoader
	URL imageUrl = getClass().getClassLoader().getResource(IMAGE_PATH);

	if (imageUrl != null) {
	    // Skapa en ImageIcon från URL:en
	    ImageIcon startImage = new ImageIcon(imageUrl);

	    JLabel startScreen = new JLabel(startImage);
	    frame.getContentPane().add(startScreen, BorderLayout.CENTER);
	    frame.setVisible(true);
	    frame.pack();
	    frame.revalidate();
	    frame.repaint();

	    Timer timer = new Timer(3000, e -> {
		frame.getContentPane().remove(startScreen); // Ta bort bilden
		frame.revalidate();
		frame.repaint();
		startGameCallback.run(); // Starta spelet efteråt (här körs lambda functionen)
	    });

	    timer.setRepeats(false);
	    timer.start();

	} else {
	    System.out.println("Bildresursen kunde inte hittas!");
	    startGameCallback.run(); // Spelat körs även om bilden inte hittas (här körs lambda functionen)
	}
    }

}
