package com.klimaatmobiel.ui


import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
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
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class NavigationTests {

    @get:Rule
    var mActivityTestRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Test
    fun navigateThroughApp() {
        // navigate from main menu to group menu
        val mainMenuButton = onView(withId(R.id.webshop_button))
        mainMenuButton.perform(click())

        SystemClock.sleep(10000)

        val addGroupFragment = onView(withId(R.id.add_group_fragment))
        addGroupFragment.check(matches(isDisplayed()))

        // navigate from add group to webshop
        val webshopButton = onView(withId(R.id.button_group_added))
        webshopButton.perform(click())

        SystemClock.sleep(3000)

        val viewGroup = onView(withId(R.id.webshop_fragment))
        viewGroup.check(matches(isDisplayed()))

        // navigate to webshop
        val bottomNavigationItemView = onView(withId(R.id.nav_order))
        bottomNavigationItemView.perform(click())

        SystemClock.sleep(3000)

        val shoppingCartFragment = onView(withId(R.id.shopping_cart_fragment))
        shoppingCartFragment.check(matches(isDisplayed()))

        // navigate to project details
        val projectDetailsItemView = onView(withId(R.id.nav_info))
        bottomNavigationItemView.perform(click())

        SystemClock.sleep(3000)

        val projectDetailsFragment = onView(withId(R.id.shopping_cart_fragment))
        shoppingCartFragment.check(matches(isDisplayed()))

        // return to webshop
        val webshopItemView = onView(withId(R.id.nav_webshop))
        bottomNavigationItemView.perform(click())

        SystemClock.sleep(3000)

        val webshopFragment = onView(withId(R.id.webshop_fragment))
        shoppingCartFragment.check(matches(isDisplayed()))
    }

    @Test
    fun addToShoppingCart() {
        val addToWebshopButton = onView(withId(R.id.add_to_cart_image))
        addToWebshopButton.perform(click())


        val viewGroup2 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.order_preview_list),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        viewGroup2.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.product_name), withText("Hout"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Hout")))

        val textView2 = onView(
            allOf(
                withId(R.id.order_item_amount), withText("4"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("1")))
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

