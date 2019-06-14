package pt.isel.poo.g6li21d.Sokoban.model;

public class ScoreboardEntry implements Comparable<ScoreboardEntry> {

    public final String entryName;
    private int moves;
    private int maxLevel;

    /**
     * Creates a new ScoreboardEntry
     * @param entryName the scoreboard name (aka the player name)
     * @param moves the moves that this entry has
     * @param maxLevel the max level that this entry has reached
     */
    public ScoreboardEntry(String entryName, int moves, int maxLevel) {
        this.entryName = entryName;
        this.moves = moves;
        this.maxLevel = maxLevel;
    }

    /**
     * Adds to the amount of moves a quantity
     * @param moves quantity of moves to add
     */
    public void addMoves(int moves) { this.moves += moves; }

    /**
     * Gets this entry moves
     * @return entry moves
     */
    public int getMoves() { return moves; }

    /**
     * Sets the max level this entry has reached
     * @param maxLevel max level to be set
     */
    public void setMaxLevel(int maxLevel) { this.maxLevel = maxLevel; }

    /**
     * Gets the max level this entry has reached
     * @return max level
     */
    public int getMaxLevel() { return maxLevel; }

    /**
     * Calculates the score for this entry based on the moves
     * and on the max level
     * @return score for this entry
     */
    public double getScore() { return maxLevel * 0.80 + (100.0 / moves) * 0.20; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof ScoreboardEntry))
            return false;

        ScoreboardEntry entry = (ScoreboardEntry) obj;
        return entryName.equals(entry.entryName);
    }

    @Override
    public int compareTo(ScoreboardEntry o) {
        double scoreDif = getScore() - o.getScore();
        return scoreDif >= 0 ? 1 : -1;
    }

    @Override
    public String toString() { return entryName + ';' + moves + ';' + maxLevel; }

    /**
     * Generates a new ScoreboardEntry from a String
     * @param line String to generate from
     * @return a new ScoreboardEntry
     */
    public static ScoreboardEntry from(String line) {
        try {
            String[] properties = line.split(";");
            return new ScoreboardEntry(properties[0], Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Parsing an invalid line!");
            e.printStackTrace();
        }

        return null;
    }

}
