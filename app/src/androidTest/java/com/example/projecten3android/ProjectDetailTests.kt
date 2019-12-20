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
class ProjectDetailTests {

    @get:Rule
    var mActivityTestRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        // navigate from main menu to group menu
        val mainMenuButton = onView(withId(R.id.webshop_button)).perform(typeText("212345"))
        mainMenuButton.perform(click())

        Espresso.closeSoftKeyboard()
        SystemClock.sleep(1000)


        val webshopButton = onView(withId(R.id.button_group_added))
        webshopButton.perform(click())
        val projectDetailButton = onView((withId(R.id.nav_info)))
        projectDetailButton.perform(click())
    }

    @Test
    fun DisplayProjectDetailInfo() {
        SystemClock.sleep(1000)

        onView(withId(R.id.detail_projectname_text)).check(matches(withText(startsWith("Ontdekdozen "))))

    }
    @Test
    fun DisplayProjectDetailInfoAfterRotation() {
        SystemClock.sleep(1000)
        mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        SystemClock.sleep(2000)
        onView(withId(R.id.detail_projectname_text)).check(matches(withText(startsWith("Ontdekdozen "))))

    }

}