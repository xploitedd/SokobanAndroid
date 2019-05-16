package pt.isel.poo.g6li21d.Sokoban.view;

import android.content.Context;

import pt.isel.poo.g6li21d.Sokoban.R;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.Sokoban.view.TileLib.Img;

public class WallCellTile extends ImageableCellTile {

    private static final int RESID = R.drawable.wall;

    WallCellTile(Context ctx, Cell cell) {
        super(ctx, cell, new Img(ctx, RESID)); // TODO: Optimize image creation later
    }

}
