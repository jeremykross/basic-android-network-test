package androidlib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.nicktate.clientexample.R;

import java.util.List;

import androidlib.BasePageActivity;
import androidlib.view.BBCategoryRow;
import networklib.model.MCategory;

/**
 * Created by danielclayton on 4/13/15.
 */
public class BBCategoryAdapter extends RecyclerView.Adapter<BBCategoryAdapter.ViewHolder> {
    private BasePageActivity mActivity;
    private List<MCategory> mCategories;

    public BBCategoryAdapter(BasePageActivity activity, List<MCategory> categories) {
        this.mActivity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView rowText;

        public ViewHolder(View itemView) {
            super(itemView);
            rowText = (TextView) itemView.findViewById(R.id.row_text);
        }
    }

    @Override
    public BBCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = new BBCategoryRow(mActivity);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final BBCategoryAdapter.ViewHolder holder, int position) {
        final MCategory category = mCategories.get(position);

        holder.rowText.setText(category.title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NavigationAction.builder(mActivity, NavigationAction.METHOD.PUSH)
//                        .target(category.getHref())
//                        .targetClazz(GncCategoryPage.class)
//                        .title(category.getTitle())
//                        .trigger();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategories != null ? mCategories.size() : 0;
    }
}