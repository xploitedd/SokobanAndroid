package pt.isel.poo.g6li21d.Sokoban.model.cells.directional;

import pt.isel.poo.g6li21d.Sokoban.model.Dir;

public final class LeftCell extends DirectionalCell {

    public static final char TYPE = 'L';
    public static final Dir DIRECTION = Dir.LEFT;

    public LeftCell(int l, int c) {
        super(l, c, TYPE, DIRECTION);
    }

}
