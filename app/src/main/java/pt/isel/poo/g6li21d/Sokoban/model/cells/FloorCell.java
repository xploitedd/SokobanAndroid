package pt.isel.poo.g6li21d.Sokoban.model.cells;

public class FloorCell extends PlayableCell {

    public static final char TYPE = ' ';

    public FloorCell(int l, int c) { super(l, c, TYPE); }

    protected FloorCell(int l, int c, char type) { super(l, c, type); }

}
