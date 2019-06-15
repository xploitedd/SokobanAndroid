package pt.isel.poo.g6li21d.Sokoban.view.tiles;

import android.content.Context;

import pt.isel.poo.g6li21d.Sokoban.R;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.TileLib.Img;

public class UpCellTile extends PlayableCellTile {

    private static final int DIR_UP_RES = R.drawable.dirup;

    private static Img upImg;

    UpCellTile(Context ctx, Cell cell) {
        super(ctx, cell);
        upImg = generateImage(upImg, ctx, DIR_UP_RES);
        setTileBackgroundImage(upImg);
    }

}
