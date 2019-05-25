package pt.isel.poo.g6li21d.Sokoban.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import pt.isel.poo.g6li21d.Sokoban.R;

public class FieldView extends LinearLayout {

    private TextView label;
    private TextView value;

    public FieldView(Context context) {
        super(context);
        init(context);
    }

    public FieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FieldView);
        String label = ta.getString(R.styleable.FieldView_label);
        if (label != null)
            this.label.setText(label);

        ta.recycle();
    }

    private void init(Context ctx) {
        LayoutInflater li = LayoutInflater.from(ctx);
        li.inflate(R.layout.field_view, this);

        label = findViewById(R.id.label);
        value = findViewById(R.id.value);
    }

    @SuppressLint("SetTextI18n")
    public void setValue(int value) { this.value.setText(Integer.toString(value)); }

}
