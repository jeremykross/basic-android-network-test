package org.nicktate.networkingsample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import rx.functions.Action1;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("\n\n\n Before before BEFORE \n\n\n");

        BBCommerceApi.getInstance().getCategory("1")
                .forEach(new Action1<MCategory>() {
                    @Override
                    public void call(MCategory category) {
                        System.out.println("Title: " + category.title);
                        System.out.println("ID: " + category.id);
                        System.out.println("Href: " + category.href);
                    }
                });

        System.out.println("\n\n\n After after AFTER \n\n\n");
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
}
