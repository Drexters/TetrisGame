package se.liu.mellu161.tetris;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.List;
import java.util.function.Consumer;

public class Board {
    private SquareType[][] squares;
    private int width, height;
    private final static Random RND = new Random();
    private Point fallingPos = new Point(0,0);
    private Poly falling = null;
    private List<BoardListener> listeners = new ArrayList<>();
    private TetrominoMaker tetroMaker = new TetrominoMaker();
    private final static int MARGIN = 2;
    private final static int DOUBLE_MARGIN = MARGIN*2;
    public boolean gameOver = false;
    private  int currentScore = 0;
    private Consumer<Integer> scoreUpdateCallback = value -> {};
    private MenuMaker menuMaker = null;

    private final static Map<Integer, Integer> POINT_MAP = Map.of(1,100,
                                                                  2, 300,
                                                                  3,500,
                                                                  4,800,
                                                                  0,0);

    public Board( int width, int height) {
        this.width = width;
        this.height = height;

        squares = new SquareType[height + DOUBLE_MARGIN][width + DOUBLE_MARGIN];
        for (int y = 0; y < height + DOUBLE_MARGIN; y++) {
            for (int x = 0; x < width + DOUBLE_MARGIN; x++) {
                if ((x >= MARGIN && x < width + MARGIN) && (y >= MARGIN && y < height + MARGIN  )) {
                    squares[y][x] = SquareType.EMPTY;
                }
                else {
                    squares[y][x] = SquareType.OUTSIDE;
                }
            }
        }
    }

    public void setMenuMaker(MenuMaker menuMaker) {
        this.menuMaker = menuMaker;
    }

    public Poly getFalling(){
        return falling;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    private Point getFallingPos() {
        return fallingPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public SquareType getFallingPolyType() {
        for (int y = 0; y < falling.getPolyHeight(); y++) {
            for (int x = 0; x < falling.getPolyWidth(); x++) {
                SquareType type = falling.getSquareType(x, y);
                if (type != SquareType.EMPTY) {
                    return type;
                }
            }
        }
       return SquareType.EMPTY;
    }

    public SquareType getSquareTypeOnBoard( int y, int x){
        return squares[y][x];
    }

    public void addBoardListeners(BoardListener listener ){
        listeners.add(listener);
    }

    private void notifyListeners(){
        for (BoardListener listener : listeners) {
            listener.boardChanged();
        }
    }

    public void setSquares(SquareType type ,int y ,int x) {
        squares[y][x] = type;
        notifyListeners();
    }

    // funkar inte med Margin
    public void setRandomSquares(){
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                // +2 för att inte få EMPTY eller OUTSIDE
                squares[y][x] = SquareType.values()[RND.nextInt(7)+2] ;
            }
        }
        notifyListeners();
    }

    public SquareType getVisibleSquqreAt(int y,int x) {
        if (falling == null) {
            return SquareType.EMPTY;
        }
        y += MARGIN;
        x += MARGIN;
        int x1 = fallingPos.x;
        int y1 = fallingPos.y;
        int x2 = x1+falling.getPolyWidth()-1;
        int y2 = y1+falling.getPolyHeight() - 1;

        if (x1<=x && x2>=x && y1<=y && y2>=y && falling.getSquareType(x-x1,y-y1) != SquareType.EMPTY) {
            return falling.getSquareType(x-x1,y-y1);
        }
        return getSquareTypeOnBoard(y,x);
    }

    public void setFalling(){
        this.falling = tetroMaker.getPoly();
        this.fallingPos = new Point(getWidth()/2-2+MARGIN,MARGIN);
    }

    public void addPolyToBoard(){
        for (int y = 0; y < falling.getPolyHeight(); y++) {
            for (int x = 0; x < falling.getPolyWidth(); x++) {
                SquareType type = falling.getSquareType(x, y);
                if (type != SquareType.EMPTY) {
                    int boardX = fallingPos.x + x;
                    int boardY = fallingPos.y + y;
                    setSquares(getFallingPolyType(), boardY, boardX);
                }
            }
        }
        notifyListeners();
    }

    public void moveFalling(){
        fallingPos.y++;
        if(hasCollision()){
            fallingPos.y--;
            addPolyToBoard();
            notifyListeners();
            this.falling = null;
        }
        notifyListeners();
    }

    public void tick(){
        if (!gameOver) {
            if (falling != null) {
                moveFalling();
            }
            // inte else, ty falling försvinner i ett tick
            if (falling == null){
                removeFullRows();
                setFalling();
                if(hasCollision()){
                    gameOver = true;
                }
            }
            notifyListeners();
        }
        else{
            menuMaker.gameOver();
        }
    }

    public void moveRight(){
        fallingPos.x++;
        if (hasCollision()){
            fallingPos.x--;
        }
        notifyListeners();
    }

    public void moveLeft() {
        fallingPos.x--;
        if (hasCollision()){
            fallingPos.x++;
        }
        notifyListeners();
    }

    public void dropDown(){
        while(!hasCollision()){
            fallingPos.y++;
        }
        fallingPos.y--;

        notifyListeners();
    }

    public void moveDirection(Directions direction){
        if (direction == Directions.RIGHT){
            moveRight();
        }
        else{
            moveLeft();
        }
        notifyListeners();
    }

    public void rotate(Directions direction) {
        if (falling != null) {
            Poly oldFalling = falling;
            if (direction == Directions.RIGHT) {
                falling = falling.rotateRight();
            } else{
                falling = falling.rotateLeft();
            }
            if (hasCollision()){
                falling = oldFalling;
            }
            notifyListeners();
        }
    }

    public boolean hasCollision(){
        // går igenom alla squares i falling, om square inte är tomm kolla om bord square inte är tom på samma kordinater, om inte kollision
        // annars gå vidare till näsata squqre
        for (int y = 0; y < falling.getPolyHeight(); y++) {
            for (int x = 0; x < falling.getPolyWidth(); x++) {
                SquareType type = falling.getSquareType(x, y);
                if (type != SquareType.EMPTY) {
                    int boardX = fallingPos.x + x;
                    int boardY = fallingPos.y + y;
                    if (getSquareTypeOnBoard(boardY, boardX) != SquareType.EMPTY) {
                        return true;  // Kollision upptäckt
                    }
                }
            }
        }
       return false;
   }

   public void removeFullRows(){
       // håller koll på antalet fulla rader
       int numberOfLines = 0;
       for (int y = getHeight() + MARGIN -1; y > MARGIN -1; y--) {
           int rowFilledCount = 0;
           for (int x = MARGIN; x < getWidth() + MARGIN; x++) {
               SquareType type = getSquareTypeOnBoard(y,x);
               if (type != SquareType.EMPTY) {
                   rowFilledCount++;
               }
           }
           // kollar om en hel rad är full
           if (rowFilledCount == getWidth()){
               numberOfLines++;

               // hämta type från raden över, sätt undre raden till samma
               // går upp ett steg och gör samma sak
               for (int row = y; row > MARGIN; row--) {
                   for (int x = MARGIN; x < getWidth() + MARGIN; x++) {
                       setSquares(getSquareTypeOnBoard(row - 1, x), row, x);
                   }
               }

               // Rensar översta raden efter nedflytt
               for (int x = MARGIN; x < getWidth() + MARGIN; x++) {
                   setSquares(SquareType.EMPTY, MARGIN, x);
               }

               // kontorllerar raden vi flyttade ned (ifall mer än en rad är full)
               y++;
           }
       }
       // ändrar score om det finns fulla rader
       changeScore(numberOfLines);
       notifyListeners();
   }

   public void changeScore(int numberOfLines){
        currentScore = getCurrentScore() + POINT_MAP.get(numberOfLines);
       if (scoreUpdateCallback != null) {
           scoreUpdateCallback.accept(getCurrentScore()); // Anropa callbacken för att uppdatera poängen
       }
   }
    // tar ett argument (int) och uppdaterer värdet med hjälp av accept(getCurrentScore())
    public void setScoreUpdateCallback(Consumer<Integer> callback) {
        this.scoreUpdateCallback = callback;
    }

    public static void main(String[] args) {
        Board board = new Board(10,20);
        for (int y = 0; y < board.getHeight()+DOUBLE_MARGIN; y++) {
            for (int x = 0; x < board.getWidth() + DOUBLE_MARGIN; x++) {
                System.out.print(board.squares[y][x] + " ");
            }
            System.out.println();
        }
    }

}
