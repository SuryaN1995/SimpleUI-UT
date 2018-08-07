package com.example.techjini.loginapplicationsimple.test

import android.support.test.espresso.IdlingResource

import com.example.techjini.loginapplicationsimple.MainActivity

/**
 * Created by Surya N on 07/08/18.
 */
class MainActivityIdlingResource(internal var activity: MainActivity) : IdlingResource {
    private var callback: IdlingResource.ResourceCallback? = null

    init {
        this.activity.dataListener = object : MainActivity.DataListener {

            override fun onDataLoaded() {
                if (callback != null && activity.isDataReady) {
                    callback!!.onTransitionToIdle()
                }
            }
        }
    }

    override fun getName(): String {
        return "mainActivity"
    }

    override fun isIdleNow(): Boolean {
        return activity.isDataReady
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.callback = callback
    }
}
