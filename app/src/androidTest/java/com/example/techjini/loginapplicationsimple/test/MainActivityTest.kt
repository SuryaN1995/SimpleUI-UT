package com.example.techjini.loginapplicationsimple.test

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import com.example.techjini.loginapplicationsimple.InfoModel
import com.example.techjini.loginapplicationsimple.MainActivity
import com.example.techjini.loginapplicationsimple.R
import com.example.techjini.loginapplicationsimple.SimpleAPI
import com.example.techjini.loginapplicationsimple.util.AndroidTestUtils
import com.example.techjini.loginapplicationsimple.util.BaseTest
import com.example.techjini.loginapplicationsimple.util.CustomMatcher
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response
import retrofit2.adapter.rxjava.HttpException
import rx.Observable

/**
 * Created by Surya N on 06/08/18.
 */
class MainActivityTest : BaseTest() {

    @JvmField
    @Rule
    var intentsTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java, true, false)

    @Mock
    private lateinit var simpleAPI: SimpleAPI

    private var response: InfoModel? = null

    private var idlingResource: MainActivityIdlingResource? = null

    private val httpException = HttpException(Response.error<Any>(409, Mockito.mock<ResponseBody>(ResponseBody::class.java)))

    override fun setUp() {
        super.setUp()
        response = Gson().fromJson(context?.let { AndroidTestUtils.getJsonResponse(it, "response", AndroidTestUtils.ResponseStatus.SUCCESS) }, InfoModel::class.java)
        intentsTestRule.launchActivity(Intent())
        idlingResource = MainActivityIdlingResource(intentsTestRule.activity)
    }

    @Test
    fun testAllIntendedViews() {
        onView(withId(R.id.username)).check(matches(isDisplayed()))
        onView(withId(R.id.email)).check(matches(isDisplayed()))
        onView(withId(R.id.submit)).check(matches(isDisplayed()))
    }

    @Test
    fun test_TwoField_EmptyError() {
        onView(withId(R.id.username)).perform(ViewActions.typeText(""))
        onView(withId(R.id.email)).perform(ViewActions.typeText(""))
        onView(withId(R.id.submit)).perform(click())
        onView(withId(R.id.nametil)).check(matches(context?.getString(R.string.name_empty)?.let { CustomMatcher.withErrorInInputLayout(it) }))
        onView(withId(R.id.emailtil)).check(matches(context?.getString(R.string.email_empty)?.let { CustomMatcher.withErrorInInputLayout(it) }))
    }

    @Test
    fun test_EmailEmpty_Error() {
        onView(withId(R.id.username)).perform(ViewActions.typeText("Hello"))
        onView(withId(R.id.email)).perform(ViewActions.typeText(""))
        onView(withId(R.id.submit)).perform(click())
        onView(withId(R.id.emailtil)).check(matches(context?.getString(R.string.email_empty)?.let { CustomMatcher.withErrorInInputLayout(it) }))
    }

    @Test(expected = NullPointerException::class)
    fun test_Fields_WithNull_Exception() {
        onView(withId(R.id.username)).perform(ViewActions.typeText(null))
        onView(withId(R.id.email)).perform(ViewActions.typeText(null))
    }

    @Test(expected = NullPointerException::class)
    fun test_EmailInvalid_Exception() {
        onView(withId(R.id.username)).perform(ViewActions.typeText(null))
        onView(withId(R.id.email)).perform(ViewActions.typeText("google"))
    }

    @Test(expected = NullPointerException::class)
    fun test_EmailValid_NameException() {
        onView(withId(R.id.username)).perform(ViewActions.typeText(null))
        onView(withId(R.id.email)).perform(ViewActions.typeText("google@gmail.com"))
    }

    @Test
    fun test_EmailInvalid_Error() {
        onView(withId(R.id.username)).perform(ViewActions.typeText(""))
        onView(withId(R.id.email)).perform(ViewActions.typeText("google"))
        onView(withId(R.id.submit)).perform(click())
        onView(withId(R.id.nametil)).check(matches(context?.getString(R.string.name_empty)?.let { CustomMatcher.withErrorInInputLayout(it) }))
        onView(withId(R.id.emailtil)).check(matches(context?.getString(R.string.email_error)?.let { CustomMatcher.withErrorInInputLayout(it) }))
    }


    @Test
    fun test_EmailInvalid_NameValid_Error() {
        onView(withId(R.id.username)).perform(ViewActions.typeText("Hello"))
        onView(withId(R.id.email)).perform(ViewActions.typeText("google"))
        onView(withId(R.id.submit)).perform(click())
        onView(withId(R.id.emailtil)).check(matches(context?.getString(R.string.email_error)?.let { CustomMatcher.withErrorInInputLayout(it) }))
    }

    @Test
    fun test_EmailValid_NameEmpty_Error() {
        onView(withId(R.id.username)).perform(ViewActions.typeText(""))
        onView(withId(R.id.email)).perform(ViewActions.typeText("google@gmail.com"))
        onView(withId(R.id.submit)).perform(click())
        onView(withId(R.id.nametil)).check(matches(context?.getString(R.string.name_empty)?.let { CustomMatcher.withErrorInInputLayout(it) }))
    }

    @Test
    fun test_ValidFields_WithAPIFailure() {
        Mockito.`when`(simpleAPI.getInfo()).thenAnswer {
            Observable.error<HttpException>(httpException)
        }
        onView(withId(R.id.username)).perform(ViewActions.typeText("Hello"))
        onView(withId(R.id.email)).perform(ViewActions.typeText("google@gmail.com"))
        intentsTestRule.activity?.setSimpleAPI(simpleAPI)
        onView(withId(R.id.submit)).perform(click())
        Espresso.closeSoftKeyboard()
        IdlingRegistry.getInstance().register(idlingResource)
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("HTTP 409 null")))
                .check(matches(isDisplayed()))
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun test_ValidFields_WithAPISuccess() {
        Mockito.`when`(simpleAPI.getInfo()).thenReturn(Observable.just(response))
        onView(withId(R.id.username)).perform(ViewActions.typeText("Hello"))
        onView(withId(R.id.email)).perform(ViewActions.typeText("google@gmail.com"))
        intentsTestRule.activity?.setSimpleAPI(simpleAPI)
        onView(withId(R.id.submit)).perform(click())
        Espresso.closeSoftKeyboard()
        IdlingRegistry.getInstance().register(idlingResource)
        onView(withId(R.id.lead_title)).check(matches(isDisplayed()))
        onView(withId(R.id.lead_img)).check(matches(isDisplayed()))
        onView(withId(R.id.ok_button)).check(matches(isDisplayed())).perform(click())
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

}