package pt.isel.poo.g6li21d.Sokoban.view;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import pt.isel.poo.g6li21d.Sokoban.R;

public class MessageView extends LinearLayout {

    private Button button;
    private TextView textView;

    public MessageView(Context context) {
        super(context);
        init(context);
    }

    public MessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context ctx) {
        LayoutInflater li = LayoutInflater.from(ctx);
        li.inflate(R.layout.message_view, this);

        button = findViewById(R.id.msg_button);
        textView = findViewById(R.id.message_area);
    }

    public void setMessage(@StringRes int res) { textView.setText(res); }

    public void setButton(@StringRes int res, View.OnClickListener listener) {
        button.setText(res);
        button.setOnClickListener(listener);
    }

}
