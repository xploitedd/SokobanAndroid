package pt.isel.poo.g6li21d.Sokoban.view.game;

import android.content.Context;

import pt.isel.poo.g6li21d.Sokoban.R;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.Sokoban.view.TileLib.Img;

public class DoorCellTile extends PlayableCellTile {

    private static final int DIR_DOWN_RES = R.drawable.door;

    private static Img downImg;

    DoorCellTile(Context ctx, Cell cell) {
        super(ctx, cell);
        downImg = generateImage(downImg, ctx, DIR_DOWN_RES);
        setTileBackgroundImage(downImg);
    }

}
