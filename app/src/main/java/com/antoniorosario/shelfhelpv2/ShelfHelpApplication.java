package com.antoniorosario.shelfhelpv2;

import android.app.Application;

import com.antoniorosario.shelfhelpv2.receiver.ConnectivityReceiver;

/**
 * Created by Focus on 8/21/17.
 */

public class ShelfHelpApplication extends Application {

    private static ShelfHelpApplication applicationInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationInstance = this;
    }

    public static synchronized ShelfHelpApplication getApplicationInstance() {
        return applicationInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}