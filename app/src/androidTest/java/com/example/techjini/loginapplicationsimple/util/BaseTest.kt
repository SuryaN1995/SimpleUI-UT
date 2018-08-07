package com.example.techjini.loginapplicationsimple.util

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiObjectNotFoundException
import android.support.test.uiautomator.UiSelector
import org.junit.Before
import org.junit.Ignore
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4::class)
@Ignore
open class BaseTest {

    protected var context: Context? = null


    @Before
    @Throws(Exception::class)
    open fun setUp() {
        MockitoAnnotations.initMocks(this)
        context = InstrumentationRegistry.getTargetContext()
    }


    /**
     * [tapTurnOnGpsBtn] will perform the action
     *
     */
    @Throws(UiObjectNotFoundException::class)
    fun tapTurnOnGpsBtn() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val allowGpsBtn = device.findObject(UiSelector()
                .className("android.widget.Button").packageName("com.google.android.gms")
                .resourceId("android:id/button1")
                .clickable(true).checkable(false))
        device.pressDelete() // just in case to turn ON blur screen (not a wake up) for some devices like HTC and some other
        if (allowGpsBtn.exists() && allowGpsBtn.isEnabled) {
            do {
                allowGpsBtn.click()
            } while (allowGpsBtn.exists())
        }
    }

    fun enableGPS() {
        try {
            tapTurnOnGpsBtn()
        } catch (e: UiObjectNotFoundException) {
            e.printStackTrace()
        }

    }


}
