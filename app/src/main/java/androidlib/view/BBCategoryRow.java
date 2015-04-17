package androidlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.nicktate.clientexample.R;

import networklib.model.MCategory;

/**
 * Created by Brad on 4/17/2015.
 */
public class BBCategoryRow extends LinearLayout {

    TextView vTextView;

    public BBCategoryRow(Context context) {
        super(context);
        init(context);
    }

    public BBCategoryRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BBCategoryRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public BBCategoryRow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.bb_category_row, this, true);
        vTextView = (TextView) findViewById(R.id.row_text);
    }

    public void bindData(MCategory category) {
        vTextView.setText(category.title);
    }
}
