package org.nicktate.networkingsample;

import org.nicktate.networkingsample.BBCommerceApiGroovy;

import rx.Observable;
import rx.Subscriber;
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

        BBCommerceApiGroovy.getInstance()
            .get("/categories/1")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Object>() {
                @Override
                public void onCompleted() {
                    Toast.makeText(MainActivityGroovy.this, "Completed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(MainActivityGroovy.this, "Error", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNext(Object object) {
                    Toast.makeText(MainActivityGroovy.this, "Resp: " + object, Toast.LENGTH_SHORT).show();
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
