package com.smartalgorithms.tousidestest.Dagger;

import com.smartalgorithms.tousidestest.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Ndivhuwo Nthambeleni on 2018/05/04.
 * Updated by Ndivhuwo Nthambeleni on 2018/05/04.
 */

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        BuildersModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        Builder appModule(AppModule appModule);
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
    void inject(Application app);
}
