package com.example.techjini.loginapplicationsimple.utility

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

import rx.Scheduler
import rx.android.plugins.RxAndroidPlugins
import rx.android.plugins.RxAndroidSchedulersHook
import rx.plugins.RxJavaPlugins
import rx.plugins.RxJavaSchedulersHook
import rx.schedulers.Schedulers


class RxAndroidSchedulersRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                //Replace any Schedulers.io() with Schedulers.immediate()
                RxJavaPlugins.getInstance().registerSchedulersHook(object : RxJavaSchedulersHook() {
                    override fun getIOScheduler(): Scheduler {
                        return Schedulers.immediate()
                    }
                })

                //Replace any AndroidSchedulers.mainThread() with Schedulers.immediate()
                RxAndroidPlugins.getInstance().registerSchedulersHook(object : RxAndroidSchedulersHook() {
                    override fun getMainThreadScheduler(): Scheduler {
                        return Schedulers.immediate()
                    }

                })
                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.getInstance().reset()
                    RxAndroidPlugins.getInstance().reset()
                }
            }
        }
    }
}
