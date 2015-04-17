package org.nicktate.clientexample;

import android.os.Bundle;

import org.nicktate.clientexample.model.MCustomCategory;

import androidlib.BBApplication;
import networklib.GsonUtils;
import networklib.model.MCategory;

/**
 * Created by Brad on 4/16/2015.
 */
public class MainApplication extends BBApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        GsonUtils.getInstance().registerTypeSelector(MCategory.class, MCustomCategory.class);
    }
}
