package org.nicktate.networkingsample;

import org.nicktate.networkingsample.BBCommerceApiGroovy;
import org.nicktate.networkingsample.BBCommerceApi;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivityGroovy extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BBCommerceApi.getInstance()
            .getCategories("1")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { x -> Observable.from(x) }
            .subscribe(new Subscriber() {
                @Override
                public void onCompleted() {
                    Toast.makeText(MainActivityGroovy.this, "Completed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(MainActivityGroovy.this, "Error", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNext(category) {
                    Toast.makeText(MainActivityGroovy.this, "Category Id: " + category.id, Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
