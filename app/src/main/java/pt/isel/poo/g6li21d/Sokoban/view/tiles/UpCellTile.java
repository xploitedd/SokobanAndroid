package pt.isel.poo.g6li21d.Sokoban.view.tiles;

import android.content.Context;

import pt.isel.poo.g6li21d.Sokoban.R;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.TileLib.Img;

public class UpCellTile extends FloorCellTile {

    private static final int DIR_UP_RES = R.drawable.dirup;

    private static Img upImg;

    /**
     * Creates a new UpCellTile object
     * @param ctx Context
     * @param cell associated up cell
     */
    UpCellTile(Context ctx, Cell cell) {
        super(ctx, cell);
        upImg = generateImage(upImg, ctx, DIR_UP_RES);
        setTileBackgroundImage(upImg);
    }

}
