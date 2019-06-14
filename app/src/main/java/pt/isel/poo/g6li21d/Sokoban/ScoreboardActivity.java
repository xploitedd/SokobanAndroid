package pt.isel.poo.g6li21d.Sokoban;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import pt.isel.poo.g6li21d.Sokoban.model.Scoreboard;
import pt.isel.poo.g6li21d.Sokoban.model.ScoreboardEntry;
import pt.isel.poo.g6li21d.Sokoban.view.ScoreView;

public class ScoreboardActivity extends Activity {

    private static final Scoreboard scoreboard = ScoreboardManager.getInstance();

    private LinearLayout scoreTable;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_scoreboard);

        scoreTable = findViewById(R.id.score_table);

        loadScores();
        renderScores();
    }

    private void loadScores() {
        try {
            InputStream is = openFileInput(ScoreboardManager.SCORE_FILE);
            scoreboard.load(is);
        } catch (FileNotFoundException e) {
            Log.w(SokobanActivity.APP_NAME, "There are no scores to read!");
        }
    }

    private void saveScores() {
        try {
            OutputStream os = openFileOutput(ScoreboardManager.SCORE_FILE, MODE_PRIVATE);
            scoreboard.save(os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void renderScores() {
        scoreTable.removeAllViewsInLayout();
        for (ScoreboardEntry entry : scoreboard) {
            ScoreView sv = new ScoreView(this);
            sv.setName(entry.entryName);
            sv.setMoves(entry.getMoves());
            sv.setMaxLevel(entry.getMaxLevel());

            scoreTable.addView(sv);
        }

        scoreTable.invalidate();
        saveScores();
    }

}
