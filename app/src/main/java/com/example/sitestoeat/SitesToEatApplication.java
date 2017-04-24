package com.example.sitestoeat;

import android.app.Application;

import com.example.sitestoeat.Infrastructure.DataAccessManager;

/**
 * Created by w.castiblanco on 19/04/2017.
 */
public class SitesToEatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataAccessManager.init(getApplicationContext());
    }
}
