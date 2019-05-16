package pt.isel.poo.g6li21d.Sokoban;

import pt.isel.poo.g6li21d.Sokoban.view.TileLib.OnTileTouchListener;

public class TouchListener implements OnTileTouchListener {

    @Override
    public boolean onClick(int xTile, int yTile) {
        return false;
    }

    @Override
    public boolean onDrag(int xFrom, int yFrom, int xTo, int yTo) {
        return false;
    }

    @Override
    public void onDragEnd(int x, int y) {

    }

    @Override
    public void onDragCancel() {

    }

}
