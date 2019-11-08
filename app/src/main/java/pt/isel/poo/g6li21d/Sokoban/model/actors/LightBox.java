package pt.isel.poo.g6li21d.Sokoban.model.actors;

import pt.isel.poo.g6li21d.Sokoban.model.Dir;
import pt.isel.poo.g6li21d.Sokoban.model.Level;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;

public final class LightBox extends Box {

    public static final char TYPE = '#';

    public LightBox(Level lvl, Cell cell) { super(lvl,cell); }

    @Override
    public char getType() {
        return TYPE;
    }

    @Override
    public boolean move(Level level, Dir dir, Cell from, Cell to) {
        if (to.hasActor() && to.getActor().getType() == TYPE) {
            Cell other = level.getCell(to.line + dir.dl, to.column + dir.dc);
            if (!super.move(level, dir, to, other))
                return false;
        }

        return super.move(level, dir, from, to);
    }

}
