package androidlib.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.nicktate.clientexample.R;

import java.util.List;

import androidlib.adapter.CategoryAdapter;
import networklib.BBCommerceApi;
import networklib.model.MCategory;
import networklib.model.MCategoryPage;
import rx.Subscriber;
import rx.functions.Func1;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Brad on 4/17/2015.
 */
public class CategoryPage extends BasePageActivity {
    private RecyclerView vRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_page);
        vRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        vRecyclerView.setLayoutManager(mLayoutManager);
        vRecyclerView.setVerticalScrollBarEnabled(true);
        vRecyclerView.setHasFixedSize(true);
        String route = getIntent().hasExtra(BasePageActivity.ROUTE) ?
                getIntent().getStringExtra(BasePageActivity.ROUTE) : "/";

        BBCommerceApi.getInstance()
                .GET(route, MCategoryPage.class)
                .map(new Func1<MCategoryPage, List<MCategory>>() {
                    public List<MCategory> call(MCategoryPage x) {
                        return x.categories;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MCategory>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(CategoryPage.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<MCategory> mCategories) {
                        if (mCategories == null || mCategories.size() == 0) {
                            Toast.makeText(CategoryPage.this, "No Categories Found!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            vRecyclerView.setAdapter(new CategoryAdapter(CategoryPage.this, mCategories));
                        }

                    }
                });
    }
}
