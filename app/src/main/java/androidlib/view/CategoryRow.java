package androidlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.nicktate.clientexample.R;

import androidlib.interfaces.ICustomView;
import networklib.model.MCategory;

/**
 * Created by Brad on 4/17/2015.
 */
public class CategoryRow extends LinearLayout implements ICustomView<MCategory> {

    private TextView vTextView;


    public CategoryRow(Context context) {
        super(context);
        init(context);
    }

    public CategoryRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CategoryRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CategoryRow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.category_row, this, true);
        vTextView = (TextView) findViewById(R.id.row_text);
        setClickable(true);
    }

    @Override
    public void bindData(final MCategory category) {
        vTextView.setText(category.title);
    }
}
