package com.smartalgorithms.tousidestest.Dagger;

import com.smartalgorithms.tousidestest.Home.HomeActivity;
import com.smartalgorithms.tousidestest.Home.HomeModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Ndivhuwo Nthambeleni on 2018/05/04.
 * Updated by Ndivhuwo Nthambeleni on 2018/05/04.
 */
@Module
public abstract class BuildersModule {
    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeActivity bindHomeActivity();
}
