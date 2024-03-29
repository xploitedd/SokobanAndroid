package pt.isel.poo.g6li21d.Sokoban.model.actors;

import pt.isel.poo.g6li21d.Sokoban.model.Dir;
import pt.isel.poo.g6li21d.Sokoban.model.Level;
import pt.isel.poo.g6li21d.Sokoban.model.cells.Cell;
import pt.isel.poo.g6li21d.Sokoban.model.cells.HoleCell;

public final class Player extends Actor {

    public static final char TYPE = '@';

    public final int playerId;
    private boolean active;
    private boolean dead;

    /**
     * Constructor for the player actor
     * @param playerId playerId of this player
     */
    public Player(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public boolean move(Level level, Dir dir, Cell from, Cell to) {
        if (to.hasActor()) {
            Actor toActor = to.getActor();
            if (toActor.getType() == Player.TYPE)
                return false;

            Cell other = level.getCell(to.line + dir.dl, to.column + dir.dc);
            if (!toActor.move(level, dir, to, other))
                return false;
        }

        if (super.move(level, dir, from, to)) {
            if (to.getType() == HoleCell.TYPE) {
                level.removeActor(to);
                dead = true;
            }

            return true;
        }

        return false;
    }

    @Override
    public char getType() {
        return TYPE;
    }

    public void setActive(boolean active) { this.active = active; }

    public boolean isActive() { return active; }

    public boolean isDead() { return dead; }

}
