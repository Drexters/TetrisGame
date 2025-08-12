package se.liu.mellu161.tetris;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Highscore>
{
    @Override
    public int compare(Highscore o1, Highscore o2) {
	if (o1.getScore() > o2.getScore()){
	    return -1;
	}
	else if (o1.getScore() < o2.getScore()){
	    return 1;
	}
	else{
	    return 0;
	}
    }

}
