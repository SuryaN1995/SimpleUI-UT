package com.example.techjini.loginapplicationsimple.utility

import android.content.Context

import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@Ignore
open class BaseTest {

    @JvmField
    @Rule
    protected var rule = RxAndroidSchedulersRule()


    @Mock
    protected var context: Context? = null


    @Before
    open fun setUp() {
        MockitoAnnotations.initMocks(this)
    }
}
