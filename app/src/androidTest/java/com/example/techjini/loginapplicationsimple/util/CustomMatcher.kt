package com.example.techjini.loginapplicationsimple.util

import android.support.design.widget.TextInputLayout
import android.support.test.espresso.matcher.BoundedMatcher
import android.view.View
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Description
import org.hamcrest.Matcher

object CustomMatcher {

    private fun withErrorInInputLayout(stringMatcher: Matcher<String>): Matcher<View> {
        checkNotNull(stringMatcher)

        return object : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {
            internal var actualError = ""

            override fun describeTo(description: Description) {
                description.appendText("with error: ")
                stringMatcher.describeTo(description)
                description.appendText("But got: $actualError")
            }

            public override fun matchesSafely(textInputLayout: TextInputLayout): Boolean {
                val error = textInputLayout.error
                if (error != null) {
                    actualError = error.toString()
                    return stringMatcher.matches(actualError)
                }
                return false
            }
        }
    }

    fun withErrorInInputLayout(string: String): Matcher<View> {
        return withErrorInInputLayout(`is`(string))
    }

}