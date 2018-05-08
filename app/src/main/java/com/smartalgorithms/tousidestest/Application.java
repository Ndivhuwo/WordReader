package com.smartalgorithms.tousidestest;

import android.app.Activity;
import android.content.Context;

import com.smartalgorithms.tousidestest.Dagger.AppComponent;
import com.smartalgorithms.tousidestest.Dagger.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Ndivhuwo Nthambeleni on 2018/05/04.
 * Updated by Ndivhuwo Nthambeleni on 2018/05/04.
 */

public class Application extends android.app.Application implements HasActivityInjector{

    private static Context context;
    public static boolean LOGS_ENABLED = true;
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    private static AppComponent daggerAppComponent;

    @Override
    public void onCreate(){
        super.onCreate();
        daggerAppComponent = DaggerAppComponent.builder()
                .application(this)
                .build();
        daggerAppComponent.inject(this);

        context = getApplicationContext();

    }

    public static Context getAppContext(){
        if(context == null){
            context =getAppContext();
        }
        return context;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
