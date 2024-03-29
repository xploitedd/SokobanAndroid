package pt.isel.poo.g6li21d.Sokoban;

import pt.isel.poo.g6li21d.Sokoban.model.Scoreboard;

public class ScoreboardManager {

    public static final String SCORE_FILE = "scores.txt";
    private static final Scoreboard scoreboard = Scoreboard.INSTANCE;

    /**
     * Gets the current instance of the scoreboard
     * @return Scorebaord instance
     */
    public static Scoreboard getInstance() { return scoreboard; }

}
