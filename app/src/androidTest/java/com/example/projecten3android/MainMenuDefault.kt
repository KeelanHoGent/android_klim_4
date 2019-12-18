package com.example.projecten3android

import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.klimaatmobiel.ui.MainActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainMenuDefault {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        onView(withId(R.id.project_code_text)).perform(clearText())
    }

    @Test
    fun testLoginButtonClickable() {
        onView(withId(R.id.webshop_button)).check(matches(isClickable()))
    }

    @Test
    fun testTextFieldHint() {
        onView(withId(R.id.project_code_text)).check(matches(withHint(R.string.edit_text_hint)))
    }

    @Test
    fun testSnackbarShowOnLoadingError() {
        onView(withId(R.id.project_code_text)).perform(typeText("give-error"))
        onView(withId(R.id.webshop_button)).perform(click())

        // Wait for the api to return an error.
        SystemClock.sleep(10000)

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.project_code_error)))
    }

    @Test
    fun testCheckIfRedirectedToWebshopCorrectProjectCode() {
        onView(withId(R.id.project_code_text)).perform(typeText("212345"))
        onView(withId(R.id.webshop_button)).perform(click())

        // Wait while the shop is loading
        SystemClock.sleep(10000)

        onView(withId(R.id.text_group_name)).check(matches(isDisplayed()))
        //onView(withId(R.id.constraintLayout_order)).check(matches(isDisplayed()))
    }
}
