package com.example.projecten3android

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.klimaatmobiel.ui.MainActivity
import org.hamcrest.Matchers.*
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WebshopTests {

    @get:Rule
    var mActivityTestRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        // navigate from main menu to group menu
        val mainMenuButton = onView(withId(R.id.webshop_button))
        mainMenuButton.perform(click())

        Espresso.closeSoftKeyboard()
        SystemClock.sleep(10000)


        val webshopButton = onView(withId(R.id.button_group_added))
        webshopButton.perform(click())
    }

    @Test
    fun FilterOnProductName() {
        SystemClock.sleep(3000)
        val nameFilterText = onView(withId(R.id.filter_text))

        val tobeTyped = "Pla"
        nameFilterText.perform(replaceText(tobeTyped), closeSoftKeyboard())

        val filterName = onView(withId(R.id.filter_text))
        filterName.perform(replaceText("pla"), closeSoftKeyboard())

        val textView1 = onView(  allOf(
            withId(R.id.info_text), withText("Plastiek")))
        val textView2 = onView(  allOf(
            withId(R.id.info_text), withText("Plakband")))



        textView1.check(ViewAssertions.matches(withText(startsWith(tobeTyped))))
        textView1.check(ViewAssertions.matches(withText(startsWith(tobeTyped))))

    }


}