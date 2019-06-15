package pt.isel.poo.g6li21d.Sokoban.view.tiles;

import android.content.Context;

import pt.isel.poo.g6li21d.Sokoban.R;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.TileLib.Img;

public class WallCellTile extends CellTile {

    private static final int RESID = R.drawable.wall;
    private static Img image;

    /**
     * Creates a new WallCellTile object
     * @param ctx Context
     * @param cell associated wall cell
     */
    WallCellTile(Context ctx, Cell cell) {
        super(ctx, cell);
        image = generateImage(image, ctx, RESID);
        setTileBackgroundImage(image);
    }

}
