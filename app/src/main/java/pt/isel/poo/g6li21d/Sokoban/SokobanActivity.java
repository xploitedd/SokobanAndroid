package pt.isel.poo.g6li21d.Sokoban;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
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

    private Scoreboard scoreboard;
    private ScoreboardEntry currentEntry;

    private MessageView winMessage;
    private MessageView gameOverMessage;
    private MessageView finalMessage;

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
        winMessage.setOnClickListener(new LevelWinListener());
        gameOverMessage = findViewById(R.id.game_over_message);
        gameOverMessage.setOnClickListener(new LevelLoseListener());
        finalMessage = findViewById(R.id.final_message);
        finalMessage.setOnClickListener(v -> finish());

        statsLayout = findViewById(R.id.stats_layout);
        gameLayout = findViewById(R.id.game_layout);

        // get a scoreboard instance
        scoreboard = ScoreboardManager.getInstance();

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
        // save current level
        outState.putInt("level_number", level.getNumber());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        game.saveState(bos);
        outState.putByteArray("level", bos.toByteArray());
        outState.putInt("moves", level.getMoves());

        // save current scoreboard player
        outState.putString("scentry", currentEntry.toString());

        // save messages status
        outState.putBoolean("message_win", winMessage.isActive());
        outState.putBoolean("message_gameOver", gameOverMessage.isActive());
        outState.putBoolean("message_final", finalMessage.isActive());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        try {
            currentEntry = ScoreboardEntry.from(Objects.requireNonNull(savedState.getString("scentry")));
            scoreboard.add(currentEntry);

            int levelNumber = savedState.getInt("level_number", 1);
            ByteArrayInputStream is = new ByteArrayInputStream(savedState.getByteArray("level"));
            level = game.load(is, levelNumber);
            level.setObserver(observer);
            level.setMoves(savedState.getInt("moves"));

            // load message or level if message is not available
            if (savedState.getBoolean("message_win"))
                showMessage(winMessage);
            else if (savedState.getBoolean("message_gameOver"))
                showMessage(gameOverMessage);
            else if (savedState.getBoolean("message_final"))
                showMessage(finalMessage);
            else
                loadLevel();
        } catch (Loader.LevelFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the next level, if available
     */
    private boolean loadNextLevel() {
        try {
            if (level != null) {
                // if it isn't when the game starts, update the player stats on the scoreboard
                currentEntry.addMoves(level.getMoves());
                currentEntry.setMaxLevel(level.getNumber());
                scoreboard.save(openFileOutput(ScoreboardManager.SCORE_FILE, MODE_PRIVATE));
            }

            Level temp = game.loadNextLevel();
            if (temp == null) {
                showMessage(finalMessage);
                return false;
            }

            level = temp;
            level.setObserver(observer);
            loadLevel();
            return true;
        } catch (Loader.LevelFormatException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Loads the selected level
     */
    private void loadLevel() {
        if (level == null)
            return;

        int lw = level.getWidth(), lh = level.getHeight();
        tilePanel.setSize(lw, lh);
        for (int y = 0; y < lh; ++y) {
            for (int x = 0; x < lw; ++x) {
                Cell cell = level.getCell(y, x);
                tilePanel.setTile(x, y, CellTile.tileOf(this, cell));
                setActivePlayer(cell.getActor());
            }
        }

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
     * @return true if set, false if error occurred
     */
    private boolean setActivePlayer(Actor actor) {
        if (actor != null && actor.getType() == Player.TYPE) {
            Player p = (Player) actor;
            if (activePlayer != null)
                activePlayer.setActive(false);

            p.setActive(true);
            activePlayer = p;
            return true;
        }

        return false;
    }

    /**
     * Moves the player in the specified direction if possible
     * @param xFrom From x
     * @param yFrom From y
     * @param xTo To x
     * @param yTo To y
     * @return true if moved, false otherwise
     */
    private boolean moveActivePlayer(int xFrom, int yFrom, int xTo, int yTo) {
        Dir dir = calculateDirection(xFrom, yFrom, xTo, yTo);
        if (dir != null) {
            if (level.moveMan(dir, activePlayer.playerId)) {
                updateValues();
                return true;
            }
        }

        return false;
    }

    /**
     * Asks for the player name and inserts
     * it into the scoreboard.
     */
    private void createPlayerOnScoreboard() {
        EditText editText = new EditText(this);
        editText.setHint(R.string.player_name);
        new AlertDialog.Builder(this)
                .setTitle(R.string.pick_player_name)
                .setView(editText)
                .setPositiveButton(R.string.new_game, (dialog, which) -> {
                    String playerName = editText.getText().toString();
                    if (playerName.length() == 0)
                        playerName = "Player";

                    currentEntry = new ScoreboardEntry(playerName, 0, 0);
                    scoreboard.add(currentEntry);
                }).setNegativeButton(R.string.go_back, (dialog, which) -> finish())
                .setCancelable(false).show();
    }

    /**
     * Shows a message, hiding the game and stats panels
     * @param msg Message to show
     */
    private void showMessage(MessageView msg) {
        statsLayout.setVisibility(View.GONE);
        gameLayout.setVisibility(View.GONE);
        msg.setVisibility(View.VISIBLE);
    }

    /**
     * Hides a message, showing the game and stats panels again
     * @param msg Message to hide
     */
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
    private static Dir calculateDirection(int xFrom, int yFrom, int xTo, int yTo) throws IllegalArgumentException {
        int difX = xTo - xFrom, difY = yTo - yFrom;
        return Dir.fromVector(difX, difY);
    }

    /**
     * Handles touch-related events
     */
    private class TouchListener implements OnTileTouchListener {

        @Override
        public boolean onClick(int xTile, int yTile) {
            Cell cell = level.getCell(yTile, xTile);
            Cell from = level.getPlayerCell(activePlayer.playerId);
            if (setActivePlayer(cell.getActor()) || moveActivePlayer(from.column, from.line, cell.column, cell.line)) {
                tilePanel.invalidate(xTile, yTile);
                return true;
            }

            return false;
        }

        @Override
        public boolean onDrag(int xFrom, int yFrom, int xTo, int yTo) {
            moveActivePlayer(xFrom, yFrom, xTo, yTo);
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
        public void onPlayerDead(Player player) { showMessage(gameOverMessage); }

        @Override
        public void onPlayerMove(Player player) { }

        @Override
        public void onBoxMove(Box box) { }

        @Override
        public void onLevelWin() {
            if (loadNextLevel())
                showMessage(winMessage);
        }

    }

    /**
     * Handles when a player wins the level
     * and clicks the button to proceed
     */
    private class LevelWinListener implements View.OnClickListener {

        @Override
        public void onClick(View v) { hideMessage(winMessage); }

    }

    /**
     * Handles when a player loses a level
     * and clicks the button to restart
     */
    private class LevelLoseListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            hideMessage(gameOverMessage);
            restartLevel();
        }

    }

}
