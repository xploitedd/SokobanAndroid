package pt.isel.poo.g6li21d.Sokoban.model;

public class ScoreboardEntry implements Comparable<ScoreboardEntry> {

    public final String entryName;
    private int moves;
    private int maxLevel;

    public ScoreboardEntry(String entryName, int moves, int maxLevel) {
        this.entryName = entryName;
        this.moves = moves;
        this.maxLevel = maxLevel;
    }

    public void setMoves(int moves) { this.moves = moves; }

    public int getMoves() { return moves; }

    public void setMaxLevel(int maxLevel) { this.maxLevel = maxLevel; }

    public int getMaxLevel() { return maxLevel; }

    public double getScore() { return maxLevel * 0.80 + (1.0 / moves) * 0.20; }

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
    public int compareTo(ScoreboardEntry o) { return (int) (getScore() - o.getScore()); }

    @Override
    public String toString() { return entryName + ';' + moves + ';' + maxLevel; }

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
