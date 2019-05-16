package pt.isel.poo.g6li21d.Sokoban.model.cells.directional;

import pt.isel.poo.g6li21d.Sokoban.model.Dir;

public final class DownCell extends DirectionalCell {

    public static final char TYPE = 'D';
    public static final Dir DIRECTION = Dir.DOWN;

    public DownCell(int l, int c) {
        super(l, c, TYPE, DIRECTION);
    }

}
