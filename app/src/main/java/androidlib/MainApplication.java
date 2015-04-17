package androidlib;

import android.app.Application;

import androidlib.model.MCustomCategory;

import networklib.GsonUtils;
import networklib.model.MCategory;

/**
 * Created by Brad on 4/17/2015.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GsonUtils.getInstance().registerTypeSelector(MCategory.class, MCustomCategory.class);
    }
}
