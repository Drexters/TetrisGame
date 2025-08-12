package se.liu.mellu161.tetris;

import java.util.Random;

public enum SquareType
{
    EMPTY,OUTSIDE,I,O,T,S,Z,J,L;

    public static void main(String[] args) {
        Random rnd = new Random();
        for (int i = 0; i < 25; i++) {
            System.out.println(SquareType.values()[rnd.nextInt(7)+2] );
        }
    }

}

