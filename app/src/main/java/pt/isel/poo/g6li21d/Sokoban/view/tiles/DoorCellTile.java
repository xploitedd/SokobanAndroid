package pt.isel.poo.g6li21d.Sokoban.view.tiles;

import android.content.Context;

import pt.isel.poo.g6li21d.Sokoban.R;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.TileLib.Img;

public final class DoorCellTile extends PlayableCellTile {

    private static final int DIR_DOWN_RES = R.drawable.door;

    private static Img downImg;

    /**
     * Creates a new DoorCellTile object
     * @param ctx Context
     * @param cell associated door cell
     */
    DoorCellTile(Context ctx, Cell cell) {
        super(ctx, cell);
        downImg = generateImage(downImg, ctx, DIR_DOWN_RES);
        setTileBackgroundImage(downImg);
    }

}
