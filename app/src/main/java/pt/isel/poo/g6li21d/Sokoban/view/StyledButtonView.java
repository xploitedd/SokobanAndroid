package pt.isel.poo.g6li21d.Sokoban.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import pt.isel.poo.g6li21d.Sokoban.R;

@SuppressLint("AppCompatCustomView")
public class StyledButtonView extends Button {

    /**
     * Creates a new StyleButton
     * @param context Context
     */
    public StyledButtonView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Creates a new StyleButton
     * @param context Context
     * @param attrs attributes for the view
     */
    public StyledButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Initializes this StyleButton view
     * @param ctx Context
     */
    private void init(Context ctx) {
        setBackgroundColor(getResources().getColor(R.color.darkBackground, ctx.getTheme()));
        setTextColor(getResources().getColor(R.color.colorPrimary, ctx.getTheme()));
        setPadding(100, 10, 100, 10);
    }

}
