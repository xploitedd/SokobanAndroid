package pt.isel.poo.g6li21d.Sokoban;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

import pt.isel.poo.g6li21d.Sokoban.model.Dir;
import pt.isel.poo.g6li21d.Sokoban.model.Game;
import pt.isel.poo.g6li21d.Sokoban.model.Level;
import pt.isel.poo.g6li21d.Sokoban.model.Loader;
import pt.isel.poo.g6li21d.Sokoban.model.Scoreboard;
import pt.isel.poo.g6li21d.Sokoban.model.ScoreboardEntry;
import pt.isel.poo.g6li21d.Sokoban.model.actors.Actor;
import pt.isel.poo.g6li21d.Sokoban.model.actors.Box;
import pt.isel.poo.g6li21d.Sokoban.model.actors.Player;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;

import pt.isel.poo.g6li21d.Sokoban.view.FieldView;
import pt.isel.poo.g6li21d.Sokoban.view.MessageView;
import pt.isel.poo.g6li21d.Sokoban.view.game.CellTile;
import pt.isel.poo.g6li21d.Sokoban.view.TileLib.OnTileTouchListener;
import pt.isel.poo.g6li21d.Sokoban.view.TileLib.Tile;
import pt.isel.poo.g6li21d.Sokoban.view.TileLib.TilePanel;

public class SokobanActivity extends Activity {

    public static final String APP_NAME = "Sokoban";

    private Level.Observer observer;
    private Game game;
    private Level level;
    private TilePanel tilePanel;
    private FieldView levelView;
    private FieldView movesView;
    private FieldView boxesView;
    private Player activePlayer;
    private ScoreboardEntry currentEntry;

    private MessageView winMessage;
    private MessageView gameOverMessage;
    private LinearLayout statsLayout;
    private LinearLayout gameLayout;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_sokoban);

        // game model
        observer = new LevelObserver();
        game = new Game(getResources().openRawResource(R.raw.levels));

        // game view
        tilePanel = findViewById(R.id.tile_panel);
        tilePanel.setListener(new TouchListener());

        // views for the current player stats
        levelView = findViewById(R.id.level_count);
        movesView = findViewById(R.id.moves_count);
        boxesView = findViewById(R.id.boxes_count);

        // views needed for messages
        winMessage = findViewById(R.id.win_message);
        gameOverMessage = findViewById(R.id.game_over_message);
        statsLayout = findViewById(R.id.stats_layout);
        gameLayout = findViewById(R.id.game_layout);

        Button restart = findViewById(R.id.restart);
        restart.setOnClickListener(v -> restartLevel());

        if (savedState == null) {
            // if this activity was just launched
            loadNextLevel();
            createPlayerOnScoreboard();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("level_number", level.getNumber());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        game.saveState(bos);
        outState.putByteArray("level", bos.toByteArray());
        outState.putString("scentry", currentEntry.toString());
        outState.putBoolean("message_win", winMessage.isActive());
        outState.putBoolean("message_gameOver", gameOverMessage.isActive());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        try {
            currentEntry = ScoreboardEntry.from(Objects.requireNonNull(savedState.getString("scentry")));
            Scoreboard.INSTANCE.add(currentEntry);

            int levelNumber = savedState.getInt("level_number", 1);
            ByteArrayInputStream is = new ByteArrayInputStream(savedState.getByteArray("level"));
            level = game.load(is, levelNumber);
            level.setObserver(observer);

            if (savedState.getBoolean("message_win"))
                showMessage(winMessage, new LevelWinListener());
            else if (savedState.getBoolean("message_gameOver"))
                showMessage(gameOverMessage, new LevelLoseListener());
            else
                loadLevel();
        } catch (Loader.LevelFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the next level, if available
     */
    private void loadNextLevel() {
        try {
            if (level != null) {
                // if it isn't the first level, update the player stats on the scoreboard
                currentEntry.setMoves(level.getMoves());
                currentEntry.setMaxLevel(level.getNumber());
            }

            level = game.loadNextLevel();
            level.setObserver(observer);
            loadLevel();
        } catch (Loader.LevelFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the selected level
     */
    private void loadLevel() {
        if (level == null)
            return;

        int lw = level.getWidth(), lh = level.getHeight();
        tilePanel.setSize(lw, lh);
        Tile[][] tiles = new Tile[lw][lh];
        for (int y = 0; y < lh; ++y) {
            for (int x = 0; x < lw; ++x) {
                Cell cell = level.getCell(y, x);
                tiles[x][y] = CellTile.tileOf(this, cell);
                setActivePlayer(cell.getActor());
            }
        }

        tilePanel.setAllTiles(tiles);
        updateValues();
    }

    /**
     * Restarts the current level
     */
    private void restartLevel() {
        game.restart();
        loadLevel();
    }

    /**
     * Update the game values
     */
    private void updateValues() {
        levelView.setValue(level.getNumber());
        movesView.setValue(level.getMoves());
        boxesView.setValue(level.getRemainingBoxes());
    }

    /**
     * Sets the current Player
     * @param actor Actor to be set as a current player
     */
    private void setActivePlayer(Actor actor) {
        if (actor != null && actor.getType() == Player.TYPE) {
            Player p = (Player) actor;
            if (activePlayer != null)
                activePlayer.setActive(false);

            p.setActive(true);
            activePlayer = p;
        }
    }

    /**
     * Asks for the player name and inserts
     * it into the scoreboard.
     */
    private void createPlayerOnScoreboard() {
        // todo: internationalize
        EditText editText = new EditText(this);
        editText.setHint("Player name");
        new AlertDialog.Builder(this)
                .setTitle("Choose the player name")
                .setView(editText)
                .setPositiveButton("New Game", (dialog, which) -> {
                    String playerName = editText.getText().toString();
                    if (playerName.length() == 0)
                        playerName = "Player";

                    currentEntry = new ScoreboardEntry(playerName, 0, 0);
                    Scoreboard.INSTANCE.add(currentEntry);
                }).setNegativeButton("Go back", (dialog, which) -> finish())
                .setCancelable(false).show();
    }

    private void showMessage(MessageView msg, View.OnClickListener listener) {
        statsLayout.setVisibility(View.GONE);
        gameLayout.setVisibility(View.GONE);
        msg.setOnClickListener(listener);
        msg.setVisibility(View.VISIBLE);
    }

    private void hideMessage(MessageView msg) {
        statsLayout.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        msg.setVisibility(View.GONE);
    }

    /**
     * Calculate a direction based on a vector
     * @param xFrom Vector X starting point
     * @param yFrom Vector Y starting point
     * @param xTo Vector X ending point
     * @param yTo Vector Y ending point
     * @return Direction that corresponds to this vector
     */
    @NonNull
    private static Dir calculateDirection(int xFrom, int yFrom, int xTo, int yTo) throws IllegalArgumentException {
        int difX = xTo - xFrom, difY = yTo - yFrom;
        Dir dir = Dir.fromVector(difX, difY);
        if (dir == null)
            throw new IllegalArgumentException("Error calculating a valid vector");

        return dir;
    }

    /**
     * Handles touch-related events
     */
    private class TouchListener implements OnTileTouchListener {

        @Override
        public boolean onClick(int xTile, int yTile) {
            Cell cell = level.getCell(yTile, xTile);
            setActivePlayer(cell.getActor());
            tilePanel.invalidate(xTile, yTile);
            return true;
        }

        @Override
        public boolean onDrag(int xFrom, int yFrom, int xTo, int yTo) {
            if (activePlayer != null) {
                Dir dir = calculateDirection(xFrom, yFrom, xTo, yTo);
                if (level.moveMan(dir, activePlayer.playerId))
                    updateValues();
            }

            return false;
        }

        @Override
        public void onDragEnd(int x, int y) { }

        @Override
        public void onDragCancel() { }

    }

    /**
     * Handles level-related events
     */
    private class LevelObserver implements Level.Observer {

        @Override
        public void onCellUpdated(Cell cell) { tilePanel.invalidate(cell.column, cell.line); }

        @Override
        public void onCellReplaced(int l, int c, Cell cell) { tilePanel.setTile(c, l, CellTile.tileOf(getBaseContext(), cell)); }

        @Override
        public void onPlayerDead(Player player) { showMessage(gameOverMessage, new LevelLoseListener()); }

        @Override
        public void onPlayerMove(Player player) { }

        @Override
        public void onBoxMove(Box box) { }

        @Override
        public void onLevelWin() { showMessage(winMessage, new LevelWinListener()); }

    }

    private class LevelWinListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            hideMessage(winMessage);
            loadNextLevel();
        }

    }

    private class LevelLoseListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            hideMessage(gameOverMessage);
            restartLevel();
        }

    }

}
