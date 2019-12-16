package com.klimaatmobiel.ui


import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.projecten3android.R
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddGroupTests {

    @get:Rule
    var mActivityTestRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        // navigate from main menu to group menu
        val mainMenuButton = onView(withId(R.id.webshop_button))
        mainMenuButton.perform(click())

        SystemClock.sleep(10000)
        Espresso.closeSoftKeyboard()
    }

    @Test
    fun AddGroupMember() {
        // add the pupil
        onView(withId(R.id.edit_text_add_pupil)).perform(ViewActions.typeText("Sofie"))
        onView(withId(R.id.edit_text_add_pupil_name)).perform(ViewActions.typeText("Seru"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.button_add_pupil)).perform(click())

        // check if is added
        val input_first_name = onView(allOf(
                withId(R.id.input_pupil_first_name), withText("Sofie")))

        input_first_name.check(matches(isDisplayed()))

        val input_name = onView(allOf(
            withId(R.id.input_pupil_name), withText("Seru")))

        input_name.check(matches(isDisplayed()))
    }

    @Test
    fun afterInsertFieldsAreEmpty() {
        // add the pupil
        val inputFirstName = onView(withId(R.id.edit_text_add_pupil))
        val inputName = onView(withId(R.id.edit_text_add_pupil_name))
        Espresso.closeSoftKeyboard()
        inputFirstName.perform(ViewActions.typeText("Florian"))
        inputName.perform(ViewActions.typeText("Landuyt"))

        onView(withId(R.id.button_add_pupil)).perform(click())

        inputFirstName.check(matches(withText("")))
        inputName.check(matches(withText("")))
    }

    @Test
    fun AddMultiplePupils() {
        // add the pupil
        val inputFirstName = onView(withId(R.id.edit_text_add_pupil))
        val inputName = onView(withId(R.id.edit_text_add_pupil_name))
        inputFirstName.perform(ViewActions.typeText("Florian"))
        inputName.perform(ViewActions.typeText("Landuyt"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.button_add_pupil)).perform(click())

        // add next pupil
        inputFirstName.perform(ViewActions.typeText("Keelan"))
        inputName.perform(ViewActions.typeText("Savat"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.button_add_pupil)).perform(click())

        // check if is added
        val input_first_name = onView(allOf(withId(R.id.input_pupil_first_name), withText("Florian")))

        input_first_name.check(matches(isDisplayed()))

        val input_name = onView(allOf(withId(R.id.input_pupil_name), withText("Landuyt")))

        input_name.check(matches(isDisplayed()))

        val input_first_name2 = onView(allOf(
            withId(R.id.input_pupil_first_name), withText("Keelan")))
        input_first_name2.check(matches(isDisplayed()))

        val input_name2 = onView(allOf(
            withId(R.id.input_pupil_name), withText("Savat")))

        input_name2.check(matches(isDisplayed()))
    }


    @Test
    fun LeavePupilFieldsEmpty_ShowsSnackbar() {
        // add the pupil
        onView(withId(R.id.edit_text_add_pupil)).perform(ViewActions.clearText())
        onView(withId(R.id.edit_text_add_pupil_name)).perform(ViewActions.clearText())
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.button_add_pupil)).perform(click())

        // check if shows snackbar
        val snackBarItem = onView(withId(com.google.android.material.R.id.snackbar_text))
        snackBarItem.check(matches(withText("naam invullen")))
    }

    @Test
    fun LeavePupilFirstNameEmpty_ShowsSnackbar() {
        // add the pupil
        onView(withId(R.id.edit_text_add_pupil)).perform(ViewActions.clearText())
        onView(withId(R.id.edit_text_add_pupil_name)).perform(ViewActions.replaceText("Schuddinck"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.button_add_pupil)).perform(click())

        // check if shows snackbar
        val snackBarItem = onView(withId(com.google.android.material.R.id.snackbar_text))
        snackBarItem.check(matches(withText("naam invullen")))
    }

    @Test
    fun LeavePupilNameEmpty_ShowsSnackbar() {
        // add the pupil
        onView(withId(R.id.edit_text_add_pupil)).perform(ViewActions.replaceText("Thomas"))
        onView(withId(R.id.edit_text_add_pupil_name)).perform(ViewActions.clearText())
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.button_add_pupil)).perform(click())

        // check if shows snackbar
        val snackBarItem = onView(withId(com.google.android.material.R.id.snackbar_text))
        snackBarItem.check(matches(withText("naam invullen")))
    }
}

