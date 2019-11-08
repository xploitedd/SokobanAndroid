package pt.isel.poo.g6li21d.Sokoban.model.cells;

import java.lang.reflect.InvocationTargetException;

import pt.isel.poo.g6li21d.Sokoban.model.Dir;
import pt.isel.poo.g6li21d.Sokoban.model.actors.Actor;
import pt.isel.poo.g6li21d.Sokoban.model.cells.directional.DownCell;
import pt.isel.poo.g6li21d.Sokoban.model.cells.directional.LeftCell;
import pt.isel.poo.g6li21d.Sokoban.model.cells.directional.RightCell;
import pt.isel.poo.g6li21d.Sokoban.model.cells.directional.UpCell;

public abstract class Cell {

    private static final Class[] cells = {FloorCell.class
            , WallCell.class
            , ObjectiveCell.class
            , HoleCell.class
            , EmptyCell.class
            , UpCell.class
            , DownCell.class
            , RightCell.class
            , LeftCell.class
            , DoorCell.class};
    public final int line;
    public final int column;

    protected Actor actor;

    /**
     * Constructor for each cell
     * @param l line where the cell is
     * @param c column where the cell is
     */
    public Cell(int l, int c) {
        line = l;
        column = c;
    }

    /**
     * Moves the actor to this cell if the action
     * is allowed by the cell
     * @param dir Direction that the actor is taking
     * @param actor Actor to move
     * @return true if allowed to move, false otherwise
     */
    public boolean canHaveActor(Dir dir, Actor actor) { return false; }

    /**
     * Sets the actor
     * @param actor Actor to be set
     */
    public final void setActor(Actor actor) { this.actor = actor; }

    /**
     * Gets the current actor
     * @return current actor, null if none
     */
    public final Actor getActor() { return actor; }

    /**
     * Checks if there's an actor
     * @return true if there is one, false otherwise
     */
    public final boolean hasActor() { return actor != null; }

    /**
     * Gets the cell type
     * @return type of the cell
     */
    public abstract char getType();

    /**
     * Transforms a cell type into a Cell-like object
     * @param l Line of the cell
     * @param c Column of the cell
     * @param type Type of the cell
     * @return Cell-like object or null if type is invalid
     */
    public static Cell getCellByType(int l, int c, char type) {
        try {
            for (Class curr : cells) {
                char currType = curr.getField("TYPE").getChar(null);
                if(currType == type) {
                    return (Cell) curr.getConstructor(int.class,int.class).newInstance(l,c);
                }
            }
        }catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e ){
            e.printStackTrace();
            throw new RuntimeException("GetCellByType Error");
        }
        return null;
    }

}
