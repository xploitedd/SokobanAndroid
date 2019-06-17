package pt.isel.poo.g6li21d.Sokoban.view.tiles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import pt.isel.poo.g6li21d.Sokoban.model.actors.Box;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;

public final class ObjectiveCellTile extends PlayableCellTile {

    private static final Paint BOX_IN_OBJECTIVE = new Paint();
    private static final int COLOR_NO_BOX = Color.RED;
    private static final int COLOR_BOX = Color.GREEN;

    static {
        BOX_IN_OBJECTIVE.setColor(Color.argb(200, 0, 0, 0));
    }

    /**
     * Creates a new ObjectiveCellTile object
     * @param ctx Context
     * @param cell associated objective cell
     */
    ObjectiveCellTile(Context ctx, Cell cell) { super(ctx, cell, COLOR_NO_BOX); }

    @Override
    public void draw(Canvas canvas, int side) {
        // verify if the actor of this cell is any box
        if (cell.getActor() instanceof Box)
            setBackgroundColor(COLOR_BOX);
        else
            setBackgroundColor(COLOR_NO_BOX);

        // only draw after setting the background color
        super.draw(canvas, side);
    }

    @Override
    protected void drawBox(Canvas canvas, int side) { boxImg.draw(canvas, side, side, BOX_IN_OBJECTIVE); }

    @Override
    protected void drawLightBox(Canvas canvas, int side) { lightBoxImg.draw(canvas, side, side, BOX_IN_OBJECTIVE); }

}
