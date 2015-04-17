package androidlib;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import org.nicktate.clientexample.model.MCustomCategory;

import networklib.GsonUtils;
import networklib.model.MCategory;

/**
 * Created by Brad on 4/17/2015.
 */
public class BBApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GsonUtils.getInstance().registerTypeSelector(MCategory.class, MCustomCategory.class);
    }
}
