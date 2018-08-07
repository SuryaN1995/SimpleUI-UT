package com.example.techjini.loginapplicationsimple.util

import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.ViewAssertion
import android.support.v7.widget.RecyclerView
import android.view.View

import org.junit.Assert.assertTrue

class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {
    private val errorMsg = "Expected count and actual item count do not match"

    override fun check(view: View, noViewFoundException: NoMatchingViewException) {
        if (view !is RecyclerView) {
            throw noViewFoundException
        }
        val itemCount = view.adapter.itemCount
        assertTrue(errorMsg, itemCount == expectedCount)
    }
}
