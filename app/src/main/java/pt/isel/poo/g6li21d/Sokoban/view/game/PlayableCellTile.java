package pt.isel.poo.g6li21d.Sokoban.view.game;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;

import pt.isel.poo.g6li21d.Sokoban.R;
import pt.isel.poo.g6li21d.Sokoban.SokobanActivity;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.Sokoban.view.TileLib.Img;

public abstract class PlayableCellTile extends CellTile {

    private static final int PLAYER_RES = R.drawable.man;
    private static final int BOX_RES = R.drawable.box;
    private static final int LIGHT_BOX_RES = R.drawable.light_box;
    private static final int KEY_RES = R.drawable.key;

    private static Img playerImg;
    private static Img boxImg;
    private static Img lightBoxImg;
    private static Img keyImg;

    PlayableCellTile(Context ctx, Cell cell) { super(ctx, cell); }

    PlayableCellTile(Context ctx, Cell cell, int bgColor) { super(ctx, cell, bgColor); }

    {
        playerImg = generateImage(playerImg, ctx, PLAYER_RES);
        boxImg = generateImage(boxImg, ctx, BOX_RES);
        lightBoxImg = generateImage(lightBoxImg, ctx, LIGHT_BOX_RES);
        keyImg = generateImage(keyImg, ctx, KEY_RES);
    }

    @Override
    public void draw(Canvas canvas, int side) {
        super.draw(canvas, side);
        if (!cell.hasActor())
            return;

        String actorName = cell.getActor().getClass().getSimpleName();
        try {
            PlayableCellTile.class
                    .getDeclaredMethod("draw" + actorName, Canvas.class, int.class)
                    .invoke(this, canvas, side);
        } catch (IllegalAccessException |
                InvocationTargetException |
                NoSuchMethodException e) {
            Log.e(SokobanActivity.APP_NAME, "Cannot find method to draw " + actorName);
            e.printStackTrace();
        }
    }

    private void drawPlayer(Canvas canvas, int side) { playerImg.draw(canvas, side, side, paint); }

    private void drawBox(Canvas canvas, int side) { boxImg.draw(canvas, side, side, paint); }

    private void drawLightBox(Canvas canvas, int side) { lightBoxImg.draw(canvas, side, side, paint); }

    private void drawKey(Canvas canvas, int side) { keyImg.draw(canvas, side, side, paint); }

}
