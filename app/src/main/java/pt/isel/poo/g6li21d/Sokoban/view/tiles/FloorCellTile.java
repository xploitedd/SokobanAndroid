package pt.isel.poo.g6li21d.Sokoban.view.tiles;

import android.content.Context;
import android.graphics.Color;

import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;

public class FloorCellTile extends PlayableCellTile {

    private static final int BACKGROUND_COLOR = Color.WHITE;

    /**
     * Creates a new FloorCellTile object
     * @param ctx Context
     * @param cell associated floor cell
     */
    FloorCellTile(Context ctx, Cell cell) { super(ctx, cell, BACKGROUND_COLOR); }

}
