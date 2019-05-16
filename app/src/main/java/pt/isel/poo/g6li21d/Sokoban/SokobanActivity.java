package pt.isel.poo.g6li21d.Sokoban;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import pt.isel.poo.g6li21d.Sokoban.model.Game;
import pt.isel.poo.g6li21d.Sokoban.model.Level;
import pt.isel.poo.g6li21d.Sokoban.model.Loader;
import pt.isel.poo.g6li21d.Sokoban.model.cells.EmptyCell;
import pt.isel.poo.g6li21d.Sokoban.view.CellTile;
import pt.isel.poo.g6li21d.Sokoban.view.TileLib.Tile;
import pt.isel.poo.g6li21d.Sokoban.view.TileLib.TilePanel;

public class SokobanActivity extends Activity {

    private Game game;
    private TilePanel tilePanel;
    private TextView levelView;
    private TextView movesView;
    private TextView boxesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sokoban);

        game = new Game(getResources().openRawResource(R.raw.levels));
        tilePanel = findViewById(R.id.tilePanel);
        levelView = findViewById(R.id.levelCount);
        movesView = findViewById(R.id.movesCount);
        boxesView = findViewById(R.id.boxesCount);
        loadNextLevel();
    }

    private void loadNextLevel() {
        try {
            Level level = game.loadNextLevel();

            int lw = level.getWidth(), lh = level.getHeight();
            tilePanel.setSize(lw, lh);
            Tile[][] tiles = new Tile[lw][lh];
            for (int i = 0; i < lw; ++i) {
                for (int j = 0; j < lh; ++j)
                    tiles[i][j] = CellTile.tileOf(this, level.getCell(j, i));
            }

            tilePanel.setAllTiles(tiles);
        } catch (Loader.LevelFormatException e) {
            e.printStackTrace();
        }
    }

}
