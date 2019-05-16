package pt.isel.poo.g6li21d.Sokoban.view;

import android.content.Context;

import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.Sokoban.view.TileLib.Img;

public abstract class PlayableCellTile extends ImageableCellTile {
    PlayableCellTile(Context ctx, Cell cell, Img img) {
        super(ctx, cell, img);
    }
}
