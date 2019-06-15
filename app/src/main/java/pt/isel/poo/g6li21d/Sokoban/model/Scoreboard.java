package pt.isel.poo.g6li21d.Sokoban.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Scoreboard implements Iterable<ScoreboardEntry> {

    public static final Scoreboard INSTANCE = new Scoreboard();

    private final LinkedList<ScoreboardEntry> entries = new LinkedList<>();

    private Scoreboard() {}

    /**
     * Adds a new ScoreboardEntry to the scoreboard
     * and if the scoreboard holds more than one entry sorts it
     * by using the default sorter
     * @see ScoreboardEntry
     * @param entry ScoreboardEntry to add
     */
    public void add(ScoreboardEntry entry) {
        int idx;
        if ((idx = entries.indexOf(entry)) == -1)
            entries.add(entry);
        else
            entries.set(idx, entry);

        sort();
    }

    /**
     * Sorts the entire scoreboard, if needed
     */
    public void sort() {
        if (entries.size() > 1)
            entries.sort(Comparator.reverseOrder());
    }

    /**
     * Saves all the entries to an output stream
     * @param os OutputStream where to save
     */
    public void save(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        for (ScoreboardEntry entry : entries)
            pw.println(entry);

        pw.close();
    }

    /**
     * Loads all the entries from an input stream
     * @param is InputStream where to load
     */
    public void load(InputStream is) {
        try (Scanner s = new Scanner(is)) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                // name;moves;level
                String[] args = line.split(";");
                ScoreboardEntry entry = new ScoreboardEntry(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                if (!entries.contains(entry))
                    add(entry);
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns true if the scoreboard has more than zero entries
     * @return true if it has at least one entry, false otherwise
     */
    public boolean hasEntry() { return entries.size() != 0; }

    @Override
    public Iterator<ScoreboardEntry> iterator() { return entries.iterator(); }

}
