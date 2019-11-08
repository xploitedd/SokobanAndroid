package pt.isel.poo.g6li21d.Sokoban.model.actors;

import pt.isel.poo.g6li21d.Sokoban.model.Dir;
import pt.isel.poo.g6li21d.Sokoban.model.Level;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.Sokoban.model.cells.FloorCell;
import pt.isel.poo.g6li21d.Sokoban.model.cells.HoleCell;
import pt.isel.poo.g6li21d.Sokoban.model.cells.ObjectiveCell;

public class Box extends Actor {

    public static final char TYPE = 'B';

    public Box(Level lvl,Cell cell) {
        lvl.addBox(cell);
    }

    @Override
    public boolean move(Level level, Dir dir, Cell from, Cell to) {
        if (to.hasActor())
            return false;

        if (super.move(level, dir, from, to)) {
            if (to.getType() == ObjectiveCell.TYPE) {
                if (from.getType() != ObjectiveCell.TYPE)
                    level.incrementBoxCount(-1);
            } else if (to.getType() == HoleCell.TYPE) {
                level.replaceCell(to, new FloorCell(to.line, to.column));
            } else if (from.getType() == ObjectiveCell.TYPE)
                level.incrementBoxCount(1);

            level.boxMoved(this);
            return true;
        }

        return false;
    }

    @Override
    public char getType() {
        return TYPE;
    }

}
