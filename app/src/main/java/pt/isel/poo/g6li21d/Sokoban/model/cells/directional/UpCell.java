package pt.isel.poo.g6li21d.Sokoban.model.cells.directional;

import pt.isel.poo.g6li21d.Sokoban.model.Dir;

public final class UpCell extends DirectionalCell {

    public static final char TYPE = 'U';
    public static final Dir DIRECTION = Dir.UP;

    public UpCell(int l, int c) {
        super(l, c, DIRECTION);
    }

    @Override
    public char getType() { return TYPE; }

}
