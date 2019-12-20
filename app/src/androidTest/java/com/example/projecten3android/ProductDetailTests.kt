package com.example.projecten3android

import android.content.pm.ActivityInfo
import android.os.SystemClock
import android.widget.TextView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.klimaatmobiel.ui.MainActivity
import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProducctDetailTests {

    @get:Rule
    var mActivityTestRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        // navigate from main menu to group menu
        val mainMenuButton = onView(withId(R.id.webshop_button))
        mainMenuButton.perform(click())

        Espresso.closeSoftKeyboard()
        SystemClock.sleep(1000)


        val webshopButton = onView(withId(R.id.button_group_added))
        webshopButton.perform(click())

    }

    @Test
    fun DisplayProductDetailInfo() {
        SystemClock.sleep(1000)



        val textView1 = onView(  allOf(
            withId(R.id.info_text), withText("Plastiek")))
        textView1.perform(click())

        onView(withId(R.id.detail_name_text)).check(matches(withText(startsWith("Plastiek"))))

    }
    @Test
    fun DisplayProductDetailInfoAfterRotation() {
        SystemClock.sleep(1000)
        val textView1 = onView(  allOf(
            withId(R.id.info_text), withText("Plastiek")))
        textView1.perform(click())
        SystemClock.sleep(2000)
        onView(withId(R.id.detail_name_text)).check(matches(withText(startsWith("Plastiek"))))

    }

}