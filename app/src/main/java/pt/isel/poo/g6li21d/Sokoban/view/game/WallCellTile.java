package pt.isel.poo.g6li21d.Sokoban.view.game;

import android.content.Context;

import pt.isel.poo.g6li21d.Sokoban.R;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.Sokoban.view.TileLib.Img;

public class WallCellTile extends CellTile {

    private static final int RESID = R.drawable.wall;
    private static Img image;

    WallCellTile(Context ctx, Cell cell) {
        super(ctx, cell);
        image = generateImage(image, ctx, RESID);
        setTileBackgroundImage(image);
    }

}
