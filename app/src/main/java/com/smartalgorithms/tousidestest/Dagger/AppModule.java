package com.smartalgorithms.tousidestest.Dagger;

import android.content.Context;
import android.content.Intent;

import com.smartalgorithms.tousidestest.Application;
import com.smartalgorithms.tousidestest.Helpers.TextHelper;
import com.smartalgorithms.tousidestest.Home.HomeContract;
import com.smartalgorithms.tousidestest.Home.HomeInteractor;
import com.smartalgorithms.tousidestest.Home.HomePresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by Ndivhuwo Nthambeleni on 2018/05/04.
 * Updated by Ndivhuwo Nthambeleni on 2018/05/04.
 */
@Module
public class AppModule {
    @Provides
    public Context provideContext(Application application){
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    public Intent providesIntent(){
        return new Intent();
    }


    /*@Provides
    static Scheduler provide*/
}
