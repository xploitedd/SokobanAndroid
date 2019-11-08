package pt.isel.poo.g6li21d.Sokoban.model.cells.directional;

import pt.isel.poo.g6li21d.Sokoban.model.Dir;

public final class RightCell extends DirectionalCell {

    public static final char TYPE = 'R';
    public static final Dir DIRECTION = Dir.RIGHT;

    public RightCell(int l, int c) {
        super(l, c, DIRECTION);
    }

    @Override
    public char getType() { return TYPE; }

}
