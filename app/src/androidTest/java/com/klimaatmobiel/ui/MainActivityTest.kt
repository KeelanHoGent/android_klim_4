package com.klimaatmobiel.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.projecten3android.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val appCompatButton = onView(
            allOf(
                withId(R.id.webshop_button), withText("naar webshop"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout2),
                        childAtPosition(
                            withId(R.id.myNavHostFragment),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.edit_text_add_pupil),
                childAtPosition(
                    allOf(
                        withId(R.id.add_group_fragment),
                        childAtPosition(
                            withId(R.id.myNavHostFragment),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("So"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.edit_text_add_pupil_name),
                childAtPosition(
                    allOf(
                        withId(R.id.add_group_fragment),
                        childAtPosition(
                            withId(R.id.myNavHostFragment),
                            0
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("Se"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.edit_text_add_pupil), withText("So"),
                childAtPosition(
                    allOf(
                        withId(R.id.add_group_fragment),
                        childAtPosition(
                            withId(R.id.myNavHostFragment),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("Sofie"))

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.edit_text_add_pupil), withText("Sofie"),
                childAtPosition(
                    allOf(
                        withId(R.id.add_group_fragment),
                        childAtPosition(
                            withId(R.id.myNavHostFragment),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(closeSoftKeyboard())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.button_add_pupil), withText("+"),
                childAtPosition(
                    allOf(
                        withId(R.id.add_group_fragment),
                        childAtPosition(
                            withId(R.id.myNavHostFragment),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        val editText = onView(
            allOf(
                withId(R.id.input_pupil_first_name), withText("Sofie"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recycler_groupmembers),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        editText.check(matches(withText("Sofie")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
