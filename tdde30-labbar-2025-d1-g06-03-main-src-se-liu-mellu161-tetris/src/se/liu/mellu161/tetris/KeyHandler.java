package se.liu.mellu161.tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class KeyHandler
{
    private TetrisComponent component;
    private Board board;

    public KeyHandler(TetrisComponent component, Board board){
	this.component = component;
	this.board = board;

	final InputMap inputMap = component.getInputMap(TetrisComponent.WHEN_IN_FOCUSED_WINDOW);// -- behöver jag component???
	inputMap.put(KeyStroke.getKeyStroke("LEFT"),"moveLeft");
	inputMap.put(KeyStroke.getKeyStroke("RIGHT"),"moveRight");
	inputMap.put(KeyStroke.getKeyStroke("UP"),"rotateRight");
	inputMap.put(KeyStroke.getKeyStroke("DOWN"),"rotateLeft");
	inputMap.put(KeyStroke.getKeyStroke("SPACE"),"dropDown");

	final ActionMap actionMap = component.getActionMap();
	actionMap.put("moveLeft", new MoveAction(0));
	actionMap.put("moveRight", new MoveAction(1));
	actionMap.put("dropDown", new MoveAction(2));
	actionMap.put("rotateRight", new RotateAction(0));
	actionMap.put("rotateLeft", new RotateAction(1));
    }

    private class MoveAction extends AbstractAction{
	private final int direction;

	public MoveAction(int direction) {
	    this.direction = direction;
	}
	@Override public void actionPerformed(final ActionEvent e) {
	    if (direction == 0){
		board.moveDirection(Directions.LEFT);
	    }
	    else if (direction == 1){
		board.moveDirection(Directions.RIGHT);
	    }
	    else if (direction == 2){
		board.dropDown();
	    }
	}
    }

    private class RotateAction extends AbstractAction{
	private final int direction;

	public RotateAction(int direction) {
	    this.direction = direction;
	}
	@Override public void actionPerformed(final ActionEvent e) {

	     if (direction == 0){
		board.rotate(Directions.RIGHT);
	    }
	    else if (direction == 1){
		board.rotate(Directions.LEFT);
	    }
	}
    }

}
