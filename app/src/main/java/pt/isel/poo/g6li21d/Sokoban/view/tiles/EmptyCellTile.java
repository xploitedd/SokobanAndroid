package pt.isel.poo.g6li21d.Sokoban.view.tiles;

import android.content.Context;

import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;

public final class EmptyCellTile extends CellTile {

    /**
     * Creates a new EmptyCellTile object
     * @param ctx Context
     * @param cell associated empty cell
     */
    EmptyCellTile(Context ctx, Cell cell) {
        super(ctx, cell);
    }

}
