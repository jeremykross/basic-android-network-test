package org.nicktate.networkingsample;

import org.nicktate.networkingsample.BBCommerceApiGroovy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.nicktate.networkingsample.model.MCategory;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "RxJavaSample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BBCommerceApi.getInstance()
                .getCategories("1")
                .flatMap(mCategoryFunc1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mCategorySubscriber);
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

    private Subscriber<MCategory> mCategorySubscriber = new Subscriber<MCategory>() {
        @Override
        public void onCompleted() {
            Log.d(TAG, "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "onError: " + e.getMessage());
        }

        @Override
        public void onNext(MCategory category) {
            Log.d(TAG, "onNext");
            Log.d(TAG, "Category iD: " + category.id);
            Toast.makeText(MainActivity.this, "Category iD: " + category.id, Toast.LENGTH_SHORT).show();
        }
    };

    private Func1<List<MCategory>, Observable<MCategory>> mCategoryFunc1 = new Func1<List<MCategory>, Observable<MCategory>>() {
        @Override
        public Observable<MCategory> call(List<MCategory> categories) {
            return Observable.from(categories);
        }
    };

}
