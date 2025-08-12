package se.liu.mellu161.tetris;

public class BoardToTextConverter {
    public  String convertToText(Board board){
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < 10; y++) {
            builder.append("\n");
            for (int x = 0; x < 10; x++) {
                switch (board.getVisibleSquqreAt(y,x)){
                    case SquareType.EMPTY:
                        builder.append("-");
                        break;
                    case SquareType.I:
                        builder.append("I");
                        break;
                    case SquareType.J:
                        builder.append("J");
                        break;
                    case SquareType.L:
                        builder.append("L");
                        break;
                    case SquareType.O:
                        builder.append("O");
                        break;
                    case SquareType.S:
                        builder.append("S");
                        break;
                    case SquareType.T:
                        builder.append("T");
                        break;
                    case SquareType.Z:
                        builder.append("Z");
                        break;
                }
            }
        }
        return builder.toString();
    }

}
