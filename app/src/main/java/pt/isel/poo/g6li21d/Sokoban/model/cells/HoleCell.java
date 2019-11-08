package pt.isel.poo.g6li21d.Sokoban.model.cells;

public final class HoleCell extends PlayableCell {

    public static final char TYPE = 'H';

    public HoleCell(int l, int c) {
        super(l, c);
    }

    @Override
    public char getType() { return TYPE; }

}
