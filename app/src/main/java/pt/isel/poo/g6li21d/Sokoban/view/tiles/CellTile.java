package pt.isel.poo.g6li21d.Sokoban.view.tiles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.DrawableRes;

import java.lang.reflect.InvocationTargetException;

import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.TileLib.Img;
import pt.isel.poo.g6li21d.TileLib.Tile;

public abstract class CellTile implements Tile {

    public static final String PACKAGE = "pt.isel.poo.g6li21d.Sokoban.view.tiles";

    protected static final Paint paint = new Paint();

    protected Context ctx;
    protected Cell cell;

    private int defaultBackground;
    private Img img;

    /**
     * Creates a new CellTile with the default background
     * color as black
     * @param ctx Context
     * @param cell Cell that this CellTile represents
     * @see Cell
     */
    CellTile(Context ctx, Cell cell) { this(ctx, cell, Color.BLACK); }

    /**
     * Creates a new CellTile with the specified
     * background color
     * @param ctx Context
     * @param cell Cell that this CellTile represents
     * @param defaultBackground default background for this cell
     * @see Cell
     */
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

    /**
     * Sets the background color
     * @param bgColor background color to be set
     */
    public void setBackgroundColor(int bgColor) { defaultBackground = bgColor; }

    /**
     * Sets the background image
     * @param img image to be set
     */
    public void setTileBackgroundImage(Img img) { this.img = img; }

    /**
     * Gets the X position of the tile
     * @return tile X
     */
    public int getX() { return cell.column; }

    /**
     * Gets the Y position of the tile
     * @return tile Y
     */
    public int getY() { return cell.line; }

    /**
     * Generates a new image or uses one that already exists
     * This method is used to prevent creation of Img objects for
     * every single CellTile, since they can occupy some memory
     * @param cache Cache for the Image object (null if the cache is empty)
     * @param ctx Context
     * @param resId an Android drawable resource
     * @see Img
     * @return a new Image object (that can be saved on the cache), or the cache
     */
    static Img generateImage(Img cache, Context ctx, @DrawableRes int resId) {
        // if there's no cached image then generate a new one
        // and return it so it can be saved in cache for future uses
        return cache == null ? new Img(ctx, resId) : cache;
    }

    /**
     * Creates a new CellTile object based on the Cell it comes from
     * For this purpose it uses java reflection so new cells can be added
     * without changing this code.
     * Because of the reflection used Cell-derived classes need to use a
     * pre-established name, e.g if we have a SomeCell class the CellTile of
     * this cell will be named SomeCellTile
     * @param ctx Context
     * @param cell Cell object to parse into a new CellTile
     * @see Cell
     * @return a new CellTile object
     */
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
