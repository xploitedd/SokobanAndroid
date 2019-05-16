package pt.isel.poo.g6li21d.Sokoban.model.cells;

import pt.isel.poo.g6li21d.Sokoban.model.Dir;
import pt.isel.poo.g6li21d.Sokoban.model.actors.Actor;

public abstract class PlayableCell extends Cell {

    public PlayableCell(int l, int c, char type) {
        super(l, c, type);
    }

    @Override
    public boolean canHaveActor(Dir dir, Actor actor) { return true; }

}
