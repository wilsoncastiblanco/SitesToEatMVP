package com.example.sitestoeat.Infrastructure;

import android.content.ContentResolver;
import android.content.Context;

/**
 * Created by wilsoncastiblanco on 4/23/17.
 */

public class DataAccessManager {

    private static DataAccessManager instance;
    public ContentResolver contentResolver;

    public DataAccessManager(Context context) {
        contentResolver = context.getContentResolver();
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new DataAccessManager(context);
        }
    }

    public static DataAccessManager getInstance() {
        return instance;
    }

    public ContentResolver getContentResolver(){
        return contentResolver;
    }

}
