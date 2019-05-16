package pt.isel.poo.g6li21d.Sokoban.view;

import android.content.Context;

import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.Sokoban.model.cells.EmptyCell;
import pt.isel.poo.g6li21d.Sokoban.model.cells.WallCell;
import pt.isel.poo.g6li21d.Sokoban.view.TileLib.Tile;

public abstract class CellTile implements Tile {

    protected Context ctx;
    protected Cell cell;

    CellTile(Context ctx, Cell cell) {
        this.ctx = ctx;
        this.cell = cell;
    }

    @Override
    public boolean setSelect(boolean selected) { return false; }

    public static CellTile tileOf(Context ctx, Cell cell) {
        Class<?> clazz = cell.getClass();
        if (clazz == EmptyCell.class) return new EmptyCellTile(ctx, cell);
        if (clazz == WallCell.class) return new WallCellTile(ctx, cell);
        return null;
    }

}
