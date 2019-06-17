package pt.isel.poo.g6li21d.Sokoban.model.actors;

import pt.isel.poo.g6li21d.Sokoban.model.Dir;
import pt.isel.poo.g6li21d.Sokoban.model.Level;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.Sokoban.model.cells.DoorCell;
import pt.isel.poo.g6li21d.Sokoban.model.cells.FloorCell;
import pt.isel.poo.g6li21d.Sokoban.model.cells.HoleCell;

public final class Key extends Actor {

    public static final char TYPE = 'K';

    public Key() {
        super(TYPE);
    }

    @Override
    public boolean move(Level level, Dir dir, Cell from, Cell to) {
        if (to.hasActor())
            return false;

        if (super.move(level, dir, from, to)) {
            if (to.getType() == DoorCell.TYPE)
                level.replaceCell(to, new FloorCell(to.line, to.column));
            else if (to.getType() == HoleCell.TYPE)
                level.removeActor(to);

            return true;
        }

        return false;
    }

}
