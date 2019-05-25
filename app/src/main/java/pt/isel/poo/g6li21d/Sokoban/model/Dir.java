package pt.isel.poo.g6li21d.Sokoban.model;

public enum Dir {

    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    public final int dc;
    public final int dl;

    /**
     * Constructor for directions
     * @param dc delta column
     * @param dl delta line
     */
    Dir(int dc, int dl) {
        this.dc = dc;
        this.dl = dl;
    }

    /**
     * Gets the direction that represents the specified vector
     * @param dc delta column
     * @param dl delta line
     * @return Direction if the vector is valid, null otherwise
     */
    public static Dir fromVector(int dc, int dl) {
        for (Dir d : values()) {
            if (d.dc == dc && d.dl == dl)
                return d;
        }

        return null;
    }

}
