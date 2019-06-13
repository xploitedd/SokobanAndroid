package pt.isel.poo.g6li21d.Sokoban.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import pt.isel.poo.g6li21d.Sokoban.R;

public class MessageView extends LinearLayout {

    private boolean active;
    private Button button;

    public MessageView(Context context) {
        super(context);
        init(context);
    }

    public MessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

        TextView textView = findViewById(R.id.message_area);
        button = findViewById(R.id.msg_button);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MessageView);
        String message = ta.getString(R.styleable.MessageView_message);
        if (message != null)
            textView.setText(message);

        String buttonText = ta.getString(R.styleable.MessageView_button_msg);
        if (buttonText != null)
            button.setText(buttonText);

        ta.recycle();
    }

    public void setOnClickListener(View.OnClickListener listener) { button.setOnClickListener(listener); }

    public boolean isActive() { return active; }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        active = visibility == VISIBLE;
    }

    private void init(Context ctx) {
        LayoutInflater li = LayoutInflater.from(ctx);
        li.inflate(R.layout.message_view, this);
    }

}
