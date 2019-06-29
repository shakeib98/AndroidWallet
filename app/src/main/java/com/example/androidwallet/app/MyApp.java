package com.example.androidwallet.app;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;

import static androidx.lifecycle.Lifecycle.Event.ON_STOP;

public class MyApp extends Application implements ApplicationLifecycleObserver.ApplicationLifecycleListener {

    String TAG = MyApp.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLifeCycle(Lifecycle.Event event) {
        switch (event){
            case ON_STOP:
                Log.d(TAG, "IN ON STOP BEFORE");
                ApplicationLifecycleObserver a = new ApplicationLifecycleObserver();
                a.onStop();

                break;
        }

    }
}
