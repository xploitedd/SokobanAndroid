package pt.isel.poo.g6li21d.Sokoban;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;

import pt.isel.poo.g6li21d.Sokoban.model.Scoreboard;

public class MenuActivity extends Activity {

    private Scoreboard scoreboard;
    private Button scoreboardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViewById(R.id.play_game).setOnClickListener(this::onPlayGame);

        try {
            // loads the scoreboard instance with all the scores from the saved file
            scoreboardButton = findViewById(R.id.view_scoreboard);
            scoreboard = ScoreboardManager.getInstance();
            scoreboard.load(openFileInput(ScoreboardManager.SCORE_FILE));
            tryShowScoreboardButton();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tryShowScoreboardButton();
    }

    /**
     * Tries to show the scoreboard button
     * that is dependent if there's something or not
     * on the scoreboard
     */
    private void tryShowScoreboardButton() {
        if (scoreboard.hasEntry()) {
            scoreboardButton.setOnClickListener(this::onShowScoreboard);
            scoreboardButton.setVisibility(View.VISIBLE);
            return;
        }

        scoreboardButton.setVisibility(View.GONE);
    }

    /**
     * Handles the play button
     * @param v view
     */
    private void onPlayGame(View v) {
        Intent it = new Intent(this, SokobanActivity.class);
        startActivity(it);
    }

    /**
     * Handles the scoreboard button
     * @param v view
     */
    private void onShowScoreboard(View v) {
        Intent it = new Intent(this, ScoreboardActivity.class);
        startActivity(it);
    }

}
