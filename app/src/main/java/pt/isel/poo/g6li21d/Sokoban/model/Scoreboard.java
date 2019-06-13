package pt.isel.poo.g6li21d.Sokoban.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Scoreboard implements Iterable<ScoreboardEntry> {

    public static final Scoreboard INSTANCE = new Scoreboard();

    private final LinkedList<ScoreboardEntry> entries = new LinkedList<>();

    private Scoreboard() {}

    public void add(ScoreboardEntry entry) {
        int idx;
        if ((idx = entries.indexOf(entry)) == -1)
            entries.add(entry);
        else
            entries.set(idx, entry);

        if (entries.size() > 1)
            entries.sort(Comparator.reverseOrder());
    }

    public void save(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        for (ScoreboardEntry entry : entries)
            pw.println(entry);

        pw.close();
    }

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

    @Override
    public Iterator<ScoreboardEntry> iterator() { return entries.iterator(); }

}
