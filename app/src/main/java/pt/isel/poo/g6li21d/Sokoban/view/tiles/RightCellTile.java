package pt.isel.poo.g6li21d.Sokoban.view.tiles;

import android.content.Context;

import pt.isel.poo.g6li21d.Sokoban.R;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.TileLib.Img;

public class RightCellTile extends FloorCellTile {

    private static final int DIR_RIGHT_RES = R.drawable.dirright;

    private static Img rightImg;

    /**
     * Creates a new RightCellTile object
     * @param ctx Context
     * @param cell associated right cell
     */
    RightCellTile(Context ctx, Cell cell) {
        super(ctx, cell);
        rightImg = generateImage(rightImg, ctx, DIR_RIGHT_RES);
        setTileBackgroundImage(rightImg);
    }

}
