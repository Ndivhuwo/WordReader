package com.smartalgorithms.tousidestest.Home;

import com.smartalgorithms.tousidestest.Helpers.TextHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Provider;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ndivhuwo Nthambeleni on 2018/05/04.
 * Updated by Ndivhuwo Nthambeleni on 2018/05/04.
 */

@Module
public abstract class HomeModule {
    @Provides
    static HomePresenter provideHomePresenter(HomeContract.View viewContract, Provider<HomeInteractor> homeInteractor, RxPermissions rxPermissions){
        return new HomePresenter(viewContract, homeInteractor, rxPermissions);
    }

    @Provides
    static HomeContract.Presenter providePresenterContract(HomePresenter homePresenter){
        return homePresenter;
    }

    @Provides
    static HomeInteractor provideHomeInteractor(HomeContract.Presenter listener, TextHelper textHelper, Provider<Scheduler> newThreadScheduler){
        return new HomeInteractor(listener, AndroidSchedulers.mainThread(), newThreadScheduler, textHelper);
    }

    @Provides
    static Scheduler provideNewThreadScheduler(){
        return Schedulers.computation();
    }

    @Provides
    static RxPermissions providesRxPermissions(HomeActivity homeActivity){
        return new RxPermissions(homeActivity);
    }

    @Binds
    abstract HomeContract.View provideViewContract(HomeActivity homeActivity);
}
