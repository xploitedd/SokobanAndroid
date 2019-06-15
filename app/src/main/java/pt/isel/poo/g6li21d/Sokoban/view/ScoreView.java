package pt.isel.poo.g6li21d.Sokoban.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import pt.isel.poo.g6li21d.Sokoban.R;

public class ScoreView extends LinearLayout {

    private TextView name;
    private TextView moves;
    private TextView maxLevel;

    /**
     * Creates a new ScoreView
     * @param context Context
     */
    public ScoreView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Creates a new ScoreView
     * @param context Context
     * @param attrs attributes for the view
     */
    public ScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Initializes this ScoreView
     * @param ctx Context
     */
    private void init(Context ctx) {
        LayoutInflater li = LayoutInflater.from(ctx);
        li.inflate(R.layout.score_view, this);

        name = findViewById(R.id.name);
        moves = findViewById(R.id.moves);
        maxLevel = findViewById(R.id.max_level);
    }

    /**
     * Sets the player scoreboard name
     * @param name name of the player
     */
    public void setName(String name) {
        if (name.length() > 15)
            name = name.substring(0, 15);

        this.name.setText(name);
    }

    /**
     * Sets the player moves
     * @param moves moves of the player
     */
    @SuppressLint("SetTextI18n")
    public void setMoves(int moves) { this.moves.setText(Integer.toString(moves)); }

    /**
     * Sets the max level this player has reached
     * @param maxLevel max level of this player
     */
    @SuppressLint("SetTextI18n")
    public void setMaxLevel(int maxLevel) { this.maxLevel.setText(Integer.toString(maxLevel)); }

}
