package pt.isel.poo.g6li21d.Sokoban;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViewById(R.id.play_game).setOnClickListener(this::onPlayGame);
        findViewById(R.id.view_scoreboard).setOnClickListener(this::onShowScoreboard);
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
