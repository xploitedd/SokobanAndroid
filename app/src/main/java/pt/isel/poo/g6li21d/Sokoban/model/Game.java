package pt.isel.poo.g6li21d.Sokoban.model;

import java.io.*;
import java.util.Scanner;

public class Game {

    public static final int MAX_PLAYERS = 2;

    private final InputStream input;
    private int levelNumber = 0;
    private Level curLevel = null;

    // --- Methods to be use by Controller ---

    public Game(InputStream levelsFile) {
        input = levelsFile.markSupported() ? levelsFile : new BufferedInputStream(levelsFile);
        input.mark(40*1024);
    }

    public Level loadNextLevel() throws Loader.LevelFormatException {
        curLevel = new Loader(createScanner()).load(++levelNumber);
        if (curLevel!=null)
            curLevel.init(this);
        return curLevel;
    }

    public void restart() {
        new Loader(createScanner()).reload(curLevel);
        curLevel.init(this);
    }

    private Scanner createScanner() {
        try {
            input.reset();
            return new Scanner(input);
        } catch (IOException e) {
            throw new RuntimeException("IOException",e);
        }
    }
}
