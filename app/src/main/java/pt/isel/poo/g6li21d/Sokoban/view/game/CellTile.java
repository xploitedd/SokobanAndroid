package pt.isel.poo.g6li21d.Sokoban.view.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.lang.reflect.InvocationTargetException;

import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.Sokoban.view.TileLib.Img;
import pt.isel.poo.g6li21d.Sokoban.view.TileLib.Tile;

public abstract class CellTile implements Tile {

    public static final String PACKAGE = "pt.isel.poo.g6li21d.Sokoban.view.game";

    protected static final Paint paint = new Paint();

    protected Context ctx;
    protected Cell cell;
    private int defaultBackground;
    private Img img;

    CellTile(Context ctx, Cell cell) { this(ctx, cell, Color.BLACK); }

    CellTile(Context ctx, Cell cell, int defaultBackground) {
        this.ctx = ctx;
        this.cell = cell;
        this.defaultBackground = defaultBackground;
    }

    @Override
    public void draw(Canvas canvas, int side) {
        canvas.drawColor(defaultBackground);

        if (img != null)
            img.draw(canvas, side, side, paint);
    }

    @Override
    public boolean setSelect(boolean selected) { return false; }

    public void setBackgroundColor(int bgColor) { defaultBackground = bgColor; }

    public void setTileBackgroundImage(Img img) { this.img = img; }

    public Img generateImage(Img cache, Context ctx, int resId) {
        // if there's no cached image then generate a new one
        // and return it so it can be saved in cache for future uses
        return cache == null ? new Img(ctx, resId) : cache;
    }

    public int getX() { return cell.column; }

    public int getY() { return cell.line; }

    public static CellTile tileOf(Context ctx, Cell cell) {
        if (cell == null)
            return null;

        Class<?> clazz = cell.getClass();
        try {
            return (CellTile) Class.forName(PACKAGE + '.' + clazz.getSimpleName() + "Tile")
                    .getDeclaredConstructor(Context.class, Cell.class)
                    .newInstance(ctx, cell);
        } catch (NoSuchMethodException | ClassNotFoundException |
                IllegalAccessException | InstantiationException |
                InvocationTargetException e) {
            e.printStackTrace();
            return new EmptyCellTile(ctx, cell);
        }
    }

}
