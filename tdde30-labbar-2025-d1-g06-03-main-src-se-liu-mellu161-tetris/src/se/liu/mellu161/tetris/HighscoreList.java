package se.liu.mellu161.tetris;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HighscoreList
{
    private List<Highscore> highscoreList = new ArrayList<>();

    public HighscoreList() {

    }

    public List<Highscore> getAllHighscores() {
	return new ArrayList<>(highscoreList);
    }

    public List<Highscore> getHighscoreList() {
	return highscoreList;
    }

    public void addHighscore(Highscore hs){
	highscoreList.add(hs);
    }

    public void sort(ScoreComparator scorer){
	highscoreList.sort( new ScoreComparator());
    }

    public void saveHighscoreToFile() throws FileNotFoundException, IOException {
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	String highscoreListAsJson = gson.toJson(highscoreList);
	File file = new File("src/se/liu/mellu161/tetris/TetrisHighscores");

	try (FileWriter myWriter = new FileWriter(file)) {
	    myWriter.write(highscoreListAsJson);
	}
    }

    public void loadHighscoreFromFile() throws FileNotFoundException,IOException {
	Gson gson = new Gson();
	String filePath = "src/se/liu/mellu161/tetris/TetrisHighscores";
	File file = new File(filePath);

	try (Reader reader = new FileReader(file)) {
	    Type highscoreListType = new TypeToken<List<Highscore>>() {}.getType();// skapar exakt typbeskrivning av List<Highscore>
	    highscoreList = gson.fromJson(reader, highscoreListType);
	}
    }

    @Override
    public String toString() {
	int index = 0;
	int topScores = 10;
	StringBuilder sb = new StringBuilder();
	for (Highscore hs : highscoreList) {
	    if (index < topScores) {
		sb.append(hs).append("\n");  // Lägger till varje highscore + ny rad
	    }
	    index++;
	}
	return sb.toString();
    }

}
