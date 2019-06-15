package pt.isel.poo.g6li21d.Sokoban.view.tiles;

import android.content.Context;
import android.graphics.Color;

import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;

public class FloorCellTile extends PlayableCellTile {

    private static final int bgColor = Color.WHITE;

    FloorCellTile(Context ctx, Cell cell) { super(ctx, cell, bgColor); }

}
