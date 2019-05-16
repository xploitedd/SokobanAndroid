package pt.isel.poo.g6li21d.Sokoban.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;

public final class EmptyCellTile extends CellTile {

    EmptyCellTile(Context ctx, Cell cell) {
        super(ctx, cell);
    }

    @Override
    public void draw(Canvas canvas, int side) { canvas.drawColor(Color.BLACK); }

    @Override
    public boolean setSelect(boolean selected) { return false; }

}
