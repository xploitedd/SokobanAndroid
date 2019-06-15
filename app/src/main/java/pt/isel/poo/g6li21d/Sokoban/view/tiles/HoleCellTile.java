package pt.isel.poo.g6li21d.Sokoban.view.tiles;

import android.content.Context;

import pt.isel.poo.g6li21d.Sokoban.R;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.TileLib.Img;

public class HoleCellTile extends PlayableCellTile {

    private static final int RESID = R.drawable.water;
    private static Img image;

    HoleCellTile(Context ctx, Cell cell) {
        super(ctx, cell);
        image = generateImage(image, ctx, RESID);
        setTileBackgroundImage(image);
    }

}
