package pt.isel.poo.g6li21d.Sokoban.view.game;

import android.content.Context;
import android.graphics.Color;

import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;

public class ObjectiveCellTile extends PlayableCellTile {

    private static final int bgColor = Color.RED;

    ObjectiveCellTile(Context ctx, Cell cell) { super(ctx, cell, bgColor); }

}
