package pt.isel.poo.g6li21d.Sokoban.view.game;

import android.content.Context;

import pt.isel.poo.g6li21d.Sokoban.R;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.Sokoban.view.TileLib.Img;

public class LeftCellTile extends PlayableCellTile {

    private static final int DIR_LEFT_RES = R.drawable.dirleft;

    private static Img leftImg;

    LeftCellTile(Context ctx, Cell cell) {
        super(ctx, cell);
        leftImg = generateImage(leftImg, ctx, DIR_LEFT_RES);
        setTileBackgroundImage(leftImg);
    }

}
