package pt.isel.poo.g6li21d.Sokoban.model.cells.directional;

import pt.isel.poo.g6li21d.Sokoban.model.Dir;
import pt.isel.poo.g6li21d.Sokoban.model.actors.Actor;
import pt.isel.poo.g6li21d.Sokoban.model.cells.FloorCell;
import pt.isel.poo.g6li21d.Sokoban.model.cells.PlayableCell;

public abstract class DirectionalCell extends PlayableCell {

    private Dir direction;

    DirectionalCell(int l, int c, Dir direction) {
        super(l, c);
        this.direction = direction;
    }

    /**
     * Returns which direction an actor can move
     * @return allowed direction
     */
    public final Dir getDirection() { return direction; }

    @Override
    public boolean canHaveActor(Dir dir, Actor actor) {
        if (dir != direction)
            return false;

        return super.canHaveActor(dir, actor);
    }

}
