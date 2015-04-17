package org.nicktate.clientexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidlib.BasePageActivity;
import networklib.model.MCategory;
import org.nicktate.clientexample.model.MCustomCategory;

import java.util.List;

import networklib.BBCommerceApi;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MainActivity extends BasePageActivity {

    private static final String TAG = "RxJavaSample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BBCommerceApi.getInstance()
                .getCategories("1")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<List<MCategory>, Observable<MCategory>>() {
                    @Override
                    public Observable<MCategory> call(List<MCategory> mCategories) {
                        return Observable.from(mCategories);
                    }
                })
                .map(new Func1<MCategory, String>() {
                    @Override
                    public String call(MCategory mCategory) {
                        return mCategory.title;
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Subscriber<MCustomCategory> mCategorySubscriber = new Subscriber<MCustomCategory>() {
        @Override
        public void onCompleted() {
            Log.d(TAG, "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "onError: " + e.getMessage());
        }

        @Override
        public void onNext(MCustomCategory category) {
            Log.d(TAG, "onNext");
            Toast.makeText(MainActivity.this, "Category iD: " + category.href, Toast.LENGTH_SHORT).show();
        }
    };

    private Func1<List<MCategory>, Observable<MCategory>> mCategoryFunc1 = new Func1<List<MCategory>, Observable<MCategory>>() {
        @Override
        public Observable<MCategory> call(List<MCategory> categories) {
            return Observable.from(categories);
        }
    };

}
