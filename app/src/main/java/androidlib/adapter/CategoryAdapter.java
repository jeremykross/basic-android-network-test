package androidlib.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.nicktate.clientexample.R;

import java.util.List;

import androidlib.activity.BasePageActivity;
import androidlib.activity.CategoryPage;
import androidlib.view.CategoryRow;
import networklib.model.MCategory;

/**
 * Created by danielclayton on 4/13/15.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private BasePageActivity mActivity;
    private List<MCategory> mCategories;

    public CategoryAdapter(BasePageActivity activity, List<MCategory> categories) {
        this.mActivity = activity;
        this.mCategories = categories;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView rowText;

        public ViewHolder(View itemView) {
            super(itemView);
            rowText = (TextView) itemView.findViewById(R.id.row_text);
        }
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = new CategoryRow(mActivity);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CategoryAdapter.ViewHolder holder, int position) {
        final MCategory category = mCategories.get(position);
        CategoryRow row = (CategoryRow)holder.itemView;
        row.bindData(category);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(BasePageActivity.ROUTE, category.href);
                i.setClass(mActivity, CategoryPage.class);
                mActivity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategories != null ? mCategories.size() : 0;
    }
}