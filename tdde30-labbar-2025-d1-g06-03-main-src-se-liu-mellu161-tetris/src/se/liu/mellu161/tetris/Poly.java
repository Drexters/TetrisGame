package se.liu.mellu161.tetris;

public class Poly {
    private SquareType[][] squares;

    public Poly(SquareType[][] squares) {
        this.squares = squares;
    }

    public int getPolyWidth(){
        return squares.length;
    }

    public int getPolyHeight() {
        return (getPolyWidth()> 0) ? squares[0].length : 0;
    }

    public SquareType getSquareType(int x, int y) {
        return squares[x][y];
    }

    public Poly rotateRight() {
        Poly newPoly = new Poly(new SquareType[getPolyHeight()][getPolyWidth()]);

        for (int r = 0; r < getPolyHeight(); r++) {
            for (int c = 0; c < getPolyWidth(); c++){
                newPoly.squares[c][getPolyWidth()-1-r] = this.squares[r][c];
            }
        }

        return newPoly;
    }

    public Poly rotateLeft() {
        Poly newPoly = new Poly(new SquareType[getPolyHeight()][getPolyWidth()]);

        for (int r = 0; r < getPolyHeight(); r++) {
            for (int c = 0; c < getPolyWidth(); c++){
                newPoly.squares[getPolyWidth()-1-c][r] = this.squares[r][c];
            }
        }

        return newPoly;
    }

    public static String getPolyStr(SquareType type){
        switch (type) {
            case SquareType.I:
                return "0000111100000000";
            case SquareType.J:
                return "100111000";
            case SquareType.L:
                return "001111000";
            case SquareType.O:
                return "1111";
            case SquareType.S:
                return "011110000";
            case SquareType.T:
                return "010111000";
            case SquareType.Z:
                return "110011000";
            default:
                return "";
        }
    }

}
