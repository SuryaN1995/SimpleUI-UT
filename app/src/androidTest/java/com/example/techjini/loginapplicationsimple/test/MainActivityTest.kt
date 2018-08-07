package com.example.techjini.loginapplicationsimple.test

import android.content.Intent
import android.support.test.espresso.intent.rule.IntentsTestRule
import com.example.techjini.loginapplicationsimple.InfoModel
import com.example.techjini.loginapplicationsimple.MainActivity
import com.example.techjini.loginapplicationsimple.SimpleAPI
import com.example.techjini.loginapplicationsimple.SimpleAPICall
import com.example.techjini.loginapplicationsimple.util.AndroidTestUtils
import com.example.techjini.loginapplicationsimple.util.BaseTest
import com.google.gson.Gson
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import rx.Observable

/**
 * Created by Surya N on 06/08/18.
 */
class MainActivityTest : BaseTest(){

    @JvmField
    @Rule
    var intentsTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java,true,false)

    private var simpleAPI : SimpleAPI ?= context?.let { SimpleAPICall.getSimpleAPI(it) }

    private var response : InfoModel ? = null

    override fun setUp() {
        super.setUp()
        response = Gson().fromJson(context?.let { AndroidTestUtils.getJsonResponse(it,"response",AndroidTestUtils.ResponseStatus.SUCCESS) },InfoModel::class.java)
        Mockito.`when`(simpleAPI?.getInfo()).thenReturn(Observable.just(response))
        intentsTestRule.launchActivity(Intent())
    }

    @Test
    fun testAllIntendedViews(){

    }
}