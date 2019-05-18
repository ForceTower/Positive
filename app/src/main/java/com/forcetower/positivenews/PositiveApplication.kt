package com.forcetower.positivenews

import androidx.work.Worker
import com.forcetower.dagger.HasWorkerInjector
import com.forcetower.positivenews.core.injection.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class PositiveApplication : DaggerApplication(), HasWorkerInjector {
    @Inject
    lateinit var workerAndroidInjector: DispatchingAndroidInjector<Worker>

    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun workerInjector(): AndroidInjector<Worker> {
        return workerAndroidInjector
    }
}