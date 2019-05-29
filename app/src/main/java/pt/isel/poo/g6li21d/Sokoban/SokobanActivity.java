package pt.isel.poo.g6li21d.Sokoban;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

    public static final String APP_NAME = "Sokoban";

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
    private Player activePlayer;
    private boolean hasMessage;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_sokoban);

        observer = new LevelObserver();
        game = new Game(getResources().openRawResource(R.raw.levels));

        tilePanel = findViewById(R.id.tile_panel);
        tilePanelLayout = findViewById(R.id.tile_panel_layout);

        scoreboard = findViewById(R.id.score);
        levelView = findViewById(R.id.level_count);
        movesView = findViewById(R.id.moves_count);
        boxesView = findViewById(R.id.boxes_count);

        messageView = findViewById(R.id.message_view);

        Button restart = findViewById(R.id.restart);
        restart.setOnClickListener((view) -> restartLevel());

        tilePanel.setListener(new TouchListener());

        if (savedState == null)
            loadNextLevel();
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

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        try {
            // TODO: handle message
            boolean hasMessage = savedState.getBoolean("message_shown");
            int levelNumber = savedState.getInt("level_number", 1);
            ByteArrayInputStream is = new ByteArrayInputStream(savedState.getByteArray("level"));
            level = game.load(is, levelNumber);
            loadLevel();
            level.setObserver(observer);
        } catch (Loader.LevelFormatException e) {
            e.printStackTrace();
        }
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
            for (int x = 0; x < lw; ++x) {
                Cell cell = level.getCell(y, x);
                tiles[x][y] = CellTile.tileOf(this, cell);
                setActivePlayer(cell.getActor());
            }
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

    private void setActivePlayer(Actor a) {
        if (a != null && a.getType() == Player.TYPE) {
            Player p = (Player) a;
            if (activePlayer != null)
                activePlayer.setActive(false);

            p.setActive(true);
            activePlayer = p;
        }
    }

    private static Dir calculateDirection(int xFrom, int yFrom, int xTo, int yTo) {
        int difX = xTo - xFrom, difY = yTo - yFrom;
        return Dir.fromVector(difX, difY);
    }

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
                if (dir == null)
                    return false;

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

    private class LevelObserver implements Level.Observer {

        @Override
        public void onCellUpdated(Cell cell) { tilePanel.invalidate(cell.column, cell.line); }

        @Override
        public void onCellReplaced(int l, int c, Cell cell) { tilePanel.setTile(c, l, CellTile.tileOf(getBaseContext(), cell)); }

        @Override
        public void onPlayerDead(Player player) {
            showMessage(R.string.level_lose, R.string.restart, (view) -> {
                hideMessage();
                restartLevel();
            });
        }

        @Override
        public void onPlayerMove(Player player) { }

        @Override
        public void onBoxMove(Box box) { }

        @Override
        public void onLevelWin() {
            showMessage(R.string.win_message, R.string.next_level, (view) -> {
                hideMessage();
                loadNextLevel();
            });
        }

    }

}
