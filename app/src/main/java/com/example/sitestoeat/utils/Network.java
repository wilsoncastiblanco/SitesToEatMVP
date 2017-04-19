package com.example.sitestoeat.utils;

import android.util.Log;

import java.io.IOException;

/**
 * Created by wilsoncastiblanco on 8/6/16.
 */
public class Network {

    private static final String TAG = Network.class.getName();

    public static boolean isOnline(){
        Runtime runtime = Runtime.getRuntime();
        try{
            Process process = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = process.waitFor();
            return ( exitValue == 0);
        }catch (IOException | InterruptedException e){
            Log.e(TAG, e.getMessage());
            return false;
        }
    }
}
