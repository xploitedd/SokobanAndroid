package pt.isel.poo.g6li21d.Sokoban.model.cells;

public final class ObjectiveCell extends PlayableCell {

    public static final char TYPE = '*';

    public ObjectiveCell(int l, int c) {
        super(l, c);
    }

    @Override
    public char getType() { return TYPE; }

}
