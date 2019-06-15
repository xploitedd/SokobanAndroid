package pt.isel.poo.g6li21d.Sokoban.view.tiles;

import android.content.Context;

import pt.isel.poo.g6li21d.Sokoban.R;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.TileLib.Img;

public class DownCellTile extends PlayableCellTile {

    private static final int DIR_DOWN_RES = R.drawable.dirdown;

    private static Img downImg;

    DownCellTile(Context ctx, Cell cell) {
        super(ctx, cell);
        downImg = generateImage(downImg, ctx, DIR_DOWN_RES);
        setTileBackgroundImage(downImg);
    }

}
