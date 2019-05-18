package com.forcetower.positivenews.core.injection

import com.forcetower.dagger.AndroidWorkerInjectionModule
import com.forcetower.positivenews.PositiveApplication
import com.forcetower.positivenews.core.injection.modules.ApplicationModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    AndroidWorkerInjectionModule::class,
    ApplicationModule::class
])
interface AppComponent : AndroidInjector<PositiveApplication> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<PositiveApplication>
}