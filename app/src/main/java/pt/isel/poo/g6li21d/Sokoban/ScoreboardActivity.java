package pt.isel.poo.g6li21d.Sokoban;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

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
        renderScores();
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
    }

}
