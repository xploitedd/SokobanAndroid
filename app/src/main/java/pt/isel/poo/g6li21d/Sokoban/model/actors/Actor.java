package pt.isel.poo.g6li21d.Sokoban.model.actors;

import pt.isel.poo.g6li21d.Sokoban.model.Dir;
import pt.isel.poo.g6li21d.Sokoban.model.Level;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;

public abstract class Actor {

    /**
     * Moves the actor into a desired cell
     * @param level Level where the actor will be moved
     * @param dir Direction to move the actor
     * @param from Where the actor is currently
     * @param to Where the actor will go
     * @return true if the actor moves, false otherwise
     */
    public boolean move(Level level, Dir dir, Cell from, Cell to) {
        Actor actor = from.getActor();
        if (to.canHaveActor(dir, actor)) {
            to.setActor(actor);
            from.setActor(null);

            level.updateCell(from);
            level.updateCell(to);

            return true;
        }

        return false;
    }

    /**
     * Gets the actor type
     * @return actor type
     */
    public abstract char getType();

}
