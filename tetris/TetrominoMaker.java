package se.liu.mellu161.tetris;

import java.util.Random;

public class TetrominoMaker {
    private final static Random RND = new Random();

    public TetrominoMaker(){

    }

    public int getNumberOfTypes() {
        return SquareType.values().length;
    }

    //vill ha 2-8, +2 för att inte få EMPTY eller OUTSIDE
    public SquareType getRandomPoly(){
        return SquareType.values()[RND.nextInt(getNumberOfTypes()-2)+2];
    }

    public Poly getPoly() {
        SquareType type = getRandomPoly();
        String polyStr = Poly.getPolyStr(type);

        int height = (int)Math.sqrt(polyStr.length());
        int width = (int)Math.sqrt(polyStr.length());
        SquareType[][] poly = new SquareType[height][width];

        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if (polyStr.charAt(index) == '1') {
                    poly[x][y] = type;
                } else {
                    poly[x][y] = SquareType.EMPTY;
                }
                index++;
            }
        }
        return new Poly(poly);
    }

    public static void main(String[] args) {
        TetrominoMaker m = new TetrominoMaker();
        for (int i = 0; i < 20; i++) {
            System.out.println(m.getRandomPoly());
        }
    }

}
