package pt.isel.poo.g6li21d.Sokoban.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.Sokoban.view.TileLib.Img;

public abstract class ImageableCellTile extends CellTile {

    private static final Paint p = new Paint();
    protected Img img;

    ImageableCellTile(Context ctx, Cell cell, Img img) {
        super(ctx, cell);
        this.img = img;
    }

    @Override
    public void draw(Canvas canvas, int side) { img.draw(canvas, side, side, p); }

}
