package pt.isel.poo.g6li21d.Sokoban.view.tiles;

import android.content.Context;

import pt.isel.poo.g6li21d.Sokoban.R;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.TileLib.Img;

public final class LeftCellTile extends FloorCellTile {

    private static final int DIR_LEFT_RES = R.drawable.dirleft;

    private static Img leftImg;

    /**
     * Creates a new LeftCellTile object
     * @param ctx Context
     * @param cell associated left cell
     */
    LeftCellTile(Context ctx, Cell cell) {
        super(ctx, cell);
        leftImg = generateImage(leftImg, ctx, DIR_LEFT_RES);
        setTileBackgroundImage(leftImg);
    }

}
