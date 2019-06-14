package pt.isel.poo.g6li21d.Sokoban.model.cells;

import pt.isel.poo.g6li21d.Sokoban.model.Dir;
import pt.isel.poo.g6li21d.Sokoban.model.actors.Actor;
import pt.isel.poo.g6li21d.Sokoban.model.actors.Key;

public final class DoorCell extends PlayableCell {

    public static final char TYPE = 'P';

    public DoorCell(int l, int c) {
        super(l, c, TYPE);
    }

    @Override
    public boolean canHaveActor(Dir dir, Actor actor) { return actor.getType() == Key.TYPE; }

}
