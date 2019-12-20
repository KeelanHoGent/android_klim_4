package com.example.projecten3android

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.projecten3android.CustomMatchers.Companion.withItemCount
import com.klimaatmobiel.ui.MainActivity
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShoppingCartTests {

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

        // go to shopping cart
        val toShoppingCardButton = onView((withId(R.id.nav_order)))
        toShoppingCardButton.perform(click())

        // clear all products
        val clearShoppingCardButton = onView((withId(R.id.btn_empty_shopping_cart)))
        clearShoppingCardButton.perform(click())
        var clearButton = onView(withText("Ja"))
        clearButton.perform(click())

        // go back to webshop
        val toWebshopButton = onView((withId(R.id.nav_webshop)))
        toWebshopButton.perform(click())


    }

    // USERSTORY:
    // Als een leerling wil ik een product toevoegen aan mijn winkelmand
    // zodat ik hiervan een bestelling kan plaatsen
    @Test
    fun AddProductToCart() {

        onView(allOf(withId(R.id.add_to_cart_image),hasSibling(withText("Plastiek"))))
            .perform(click());


        val toShoppingCardButton = onView((withId(R.id.nav_order)))
        toShoppingCardButton.perform(click())
        val aantal1 = 1
        onView(withId(R.id.order_preview_list)).check(matches(withItemCount(aantal1)))

    }

    // USERSTORY:
    // Als een leerling Wil ik mijn winkelmandje bekijken
    // zodat ik een overzicht heb van de aanwezige producten
    @Test
    fun DisplayShoppingCart() {
        val toShoppingCardButton = onView((withId(R.id.nav_order)))
        toShoppingCardButton.perform(click())
        onView(withId(R.id.btn_empty_shopping_cart)).check(matches(isDisplayed()))



    }

    // USERSTORY:
    // Als een leerling wil ik de hoeveelheid van een product kiezen
    // zodat ik dit niet meerdere keren in het winkelmandje moet toevoegen
    @Test
    fun ChangesAmountInShoppingCart() {
        onView(allOf(withId(R.id.add_to_cart_image),hasSibling(withText("Plastiek"))))
            .perform(click());


        val toShoppingCardButton = onView((withId(R.id.nav_order)))
        toShoppingCardButton.perform(click())
        val aantal = 1
        val amount = 3;
        onView(allOf(withId(R.id.btn_plus),hasSibling(withText("Plastiek"))))
            .perform(click()).perform(click());
        val amountView = onView(allOf(withId(R.id.order_item_amount),hasSibling(withText("Plastiek"))))

        amountView.check(ViewAssertions.matches(withText(startsWith(amount.toString()))))
        onView(withId(R.id.order_preview_list)).check(matches(withItemCount(aantal)))


    }

    // USERSTORY:
    // Als een leerling wil ik een product verwijderen
    // uit mijn winkelmand
    @Test
    fun RemoveProductFromCart() {

        onView(allOf(withId(R.id.add_to_cart_image),hasSibling(withText("Plastiek"))))
            .perform(click());


        val toShoppingCardButton = onView((withId(R.id.nav_order)))
        toShoppingCardButton.perform(click())
        var aantal1 = 1
        onView(withId(R.id.order_preview_list)).check(matches(withItemCount(aantal1)))
        onView(allOf(withId(R.id.cart_remove_order_item),hasSibling(withText("Plastiek"))))
            .perform(click());
        aantal1 = 0
        onView(withId(R.id.order_preview_list)).check(matches(withItemCount(aantal1)))

    }

    // USERSTORY:
    // Als een leerling wil ik de volledige winkelmand verwijderen
    // zodat ik snel opnieuw kan beginnen
    @Test
    fun ClearCart() {

        onView(allOf(withId(R.id.add_to_cart_image),hasSibling(withText("Plastiek"))))
            .perform(click());
        onView(allOf(withId(R.id.add_to_cart_image),hasSibling(withText("Plakband"))))
            .perform(click());


        val toShoppingCardButton = onView((withId(R.id.nav_order)))
        toShoppingCardButton.perform(click())
        var aantal1 = 2
        onView(withId(R.id.order_preview_list)).check(matches(withItemCount(aantal1)))

        // clear all products
        val clearShoppingCardButton = onView((withId(R.id.btn_empty_shopping_cart)))
        clearShoppingCardButton.perform(click())
        var clearButton = onView(withText("Ja"))
        clearButton.perform(click())

        aantal1 = 0
        onView(withId(R.id.order_preview_list)).check(matches(withItemCount(aantal1)))

    }


    //USERSTORY:
    //Als een leerling
    //Wil ik een bestelling doorgeven
    //Zodat ik de producten kan afhalen
    @Test
    fun confirmOrder() {
        onView(allOf(withId(R.id.add_to_cart_image),hasSibling(withText("Plastiek"))))
            .perform(click());
        onView(allOf(withId(R.id.add_to_cart_image),hasSibling(withText("Plakband"))))
            .perform(click());


        onView(withId(R.id.order_status_text)).check(matches(withText("Bestelling bezig")))
        onView(withId(R.id.complete_order_button)).perform(click())
        onView(withId(R.id.order_status_text)).check(matches(withText("Ingediend")))

    }

    //USERSTORY:
    //Als een leerling wil ik een ingediend winkelkarretje aanpassen
    // zodat ik producten die ik vergeten ben nog kan toevoegen
    @Test
    fun resumeOrder() {
        onView(allOf(withId(R.id.add_to_cart_image),hasSibling(withText("Plastiek"))))
            .perform(click());
        onView(allOf(withId(R.id.add_to_cart_image),hasSibling(withText("Plakband"))))
            .perform(click());

        val toShoppingCardButton = onView((withId(R.id.nav_order)))
        toShoppingCardButton.perform(click())

        onView(withId(R.id.order_status_text)).check(matches(withText("Bestelling bezig")))
        onView(withId(R.id.complete_order_button)).perform(click())
        onView(withId(R.id.order_status_text)).check(matches(withText("Ingediend")))

        val toWebShopButton = onView((withId(R.id.nav_webshop)))
        toWebShopButton.perform(click())
        onView(allOf(withId(R.id.add_to_cart_image),hasSibling(withText("Plastiek"))))
            .perform(click());

        toShoppingCardButton.perform(click())
        onView(withId(R.id.order_status_text)).check(matches(withText("Bestelling bezig")))
    }






}