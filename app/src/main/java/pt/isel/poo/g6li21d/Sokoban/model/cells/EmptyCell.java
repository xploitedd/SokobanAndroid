package pt.isel.poo.g6li21d.Sokoban.model.cells;

public final class EmptyCell extends Cell {

    public static final char TYPE = '.';

    public EmptyCell(int l, int c) {
        super(l, c, TYPE);
    }

}
