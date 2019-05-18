package com.forcetower.dagger

import androidx.work.Worker

object AndroidWorkerInjection {
    @JvmStatic
    fun inject(worker: Worker) {
        val application = worker.applicationContext

        if (application !is HasWorkerInjector) {
            throw RuntimeException(
                String.format(
                    "%s does not implement %s",
                    application.javaClass.canonicalName,
                    HasWorkerInjector::class.java.canonicalName
                )
            )
        }

        val workerInjector = (application as HasWorkerInjector).workerInjector()
        workerInjector.inject(worker)
    }
}