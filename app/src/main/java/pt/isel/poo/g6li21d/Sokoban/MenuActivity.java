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
        findViewById(R.id.select_level).setOnClickListener(this::onSelectLevel);
        findViewById(R.id.view_scoreboard).setOnClickListener(this::onShowScoreboard);
    }

    private void onPlayGame(View v) {
        Intent it = new Intent(this, SokobanActivity.class);
        startActivity(it);
    }

    private void onSelectLevel(View v) {

    }

    private void onShowScoreboard(View v) {
        Intent it = new Intent(this, ScoreboardActivity.class);
        startActivity(it);
    }

}
