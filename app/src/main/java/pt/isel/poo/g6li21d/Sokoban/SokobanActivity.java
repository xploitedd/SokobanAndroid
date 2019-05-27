package pt.isel.poo.g6li21d.Sokoban;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import pt.isel.poo.g6li21d.Sokoban.model.Dir;
import pt.isel.poo.g6li21d.Sokoban.model.Game;
import pt.isel.poo.g6li21d.Sokoban.model.Level;
import pt.isel.poo.g6li21d.Sokoban.model.Loader;
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

    public static final int[] sounds = new int[3];
    public static final String APP_NAME = "Sokoban";

    private SoundPool sp;
    private Level.Observer observer;
    private Game game;
    private Level level;
    private TilePanel tilePanel;
    private LinearLayout tilePanelLayout;
    private LinearLayout scoreboard;
    private FieldView levelView;
    private FieldView movesView;
    private FieldView boxesView;
    private MessageView messageView;
    private boolean hasMessage;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_sokoban);

        // initialize the sound pool
        sp = new SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build())
                .build();

        sounds[0] = sp.load(this, R.raw.walk, 2);
        sounds[1] = sp.load(this, R.raw.box_moved, 1);
        sounds[2] = sp.load(this, R.raw.kill, 1);

        sp.play(sounds[3], 1f, 1f, 3, -1, 1f);

        observer = new LevelObserver();
        game = new Game(getResources().openRawResource(R.raw.levels));

        tilePanel = findViewById(R.id.tile_panel);
        tilePanelLayout = findViewById(R.id.tile_panel_layout);

        scoreboard = findViewById(R.id.scoreboard);
        levelView = findViewById(R.id.level_count);
        movesView = findViewById(R.id.moves_count);
        boxesView = findViewById(R.id.boxes_count);

        messageView = findViewById(R.id.message_view);

        Button restart = findViewById(R.id.restart);
        restart.setOnClickListener((view) -> restartLevel());

        tilePanel.setListener(new TouchListener());

        if (savedState == null) {
            loadNextLevel();
            return;
        }

        try {
            boolean hasMessage = savedState.getBoolean("message_shown");
            int levelNumber = savedState.getInt("level_number", 1);
            ByteArrayInputStream is = new ByteArrayInputStream(savedState.getByteArray("level"));
            level = game.load(is, levelNumber);
            if (hasMessage)
                loadNextLevel();
            else
                loadLevel();

            level.setObserver(observer);
        } catch (Loader.LevelFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("level_number", level.getNumber());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        game.saveState(bos);
        outState.putByteArray("level", bos.toByteArray());
        outState.putBoolean("message_shown", hasMessage);
    }

    private void showMessage(@StringRes int messageId, @StringRes int buttonString, View.OnClickListener buttonListener) {
        scoreboard.setVisibility(View.GONE);
        tilePanelLayout.setVisibility(View.GONE);
        messageView.setMessage(messageId);
        messageView.setButton(buttonString, buttonListener);
        messageView.setVisibility(View.VISIBLE);
        hasMessage = true;
    }

    private void hideMessage() {
        messageView.setVisibility(View.GONE);
        scoreboard.setVisibility(View.VISIBLE);
        tilePanelLayout.setVisibility(View.VISIBLE);
        hasMessage = false;
    }

    private void loadNextLevel() {
        try {
            level = game.loadNextLevel();
            level.setObserver(observer);
            loadLevel();
        } catch (Loader.LevelFormatException e) {
            e.printStackTrace();
        }
    }

    private void loadLevel() {
        if (level == null)
            return;

        int lw = level.getWidth(), lh = level.getHeight();
        tilePanel.setSize(lw, lh);
        Tile[][] tiles = new Tile[lw][lh];
        for (int y = 0; y < lh; ++y) {
            for (int x = 0; x < lw; ++x)
                tiles[x][y] = CellTile.tileOf(this, level.getCell(y, x));
        }

        tilePanel.setAllTiles(tiles);
        updateValues();
    }

    private void restartLevel() {
        game.restart();
        loadLevel();
    }

    private void updateValues() {
        levelView.setValue(level.getNumber());
        movesView.setValue(level.getMoves());
        boxesView.setValue(level.getRemainingBoxes());
    }

    private static Dir calculateDirection(int xFrom, int yFrom, int xTo, int yTo) {
        int difX = xTo - xFrom, difY = yTo - yFrom;
        return Dir.fromVector(difX, difY);
    }

    private class TouchListener implements OnTileTouchListener {

        @Override
        public boolean onClick(int xTile, int yTile) { return false; }

        @Override
        public boolean onDrag(int xFrom, int yFrom, int xTo, int yTo) {
            Cell cell = level.getCell(yFrom, xFrom);
            Actor actor = cell.getActor();
            if (actor != null && actor.getType() == Player.TYPE) {
                Dir dir = calculateDirection(xFrom, yFrom, xTo, yTo);
                if (dir == null)
                    return false;

                Player p = (Player) actor;
                if (level.moveMan(dir, p.playerId))
                    updateValues();
            }

            return false;
        }

        @Override
        public void onDragEnd(int x, int y) { }

        @Override
        public void onDragCancel() { }

    }

    private class LevelObserver implements Level.Observer {

        @Override
        public void onCellUpdated(Cell cell) { tilePanel.invalidate(cell.column, cell.line); }

        @Override
        public void onCellReplaced(int l, int c, Cell cell) { tilePanel.setTile(c, l, CellTile.tileOf(getBaseContext(), cell)); }

        @Override
        public void onPlayerDead(Player player) {
            sp.play(sounds[2], 1f, 1f, 1, 0, 1);
            showMessage(R.string.level_lose, R.string.restart, (view) -> {
                hideMessage();
                restartLevel();
            });
        }

        @Override
        public void onPlayerMove(Player player) {
            sp.play(sounds[0], 1f, 1f, 2, 0, 1);
        }

        @Override
        public void onBoxMove(Box box) {
            sp.play(sounds[1], 1f, 1f, 1, 0, 1);
        }

        @Override
        public void onLevelWin() {
            showMessage(R.string.win_message, R.string.next_level, (view) -> {
                hideMessage();
                loadNextLevel();
            });
        }

    }

}
