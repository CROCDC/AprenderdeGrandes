package com.cr.o.cdc.aprenderdegrandes

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.cr.o.cdc.aprenderdegrandes.mocks.MockCardsRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun before() {
        launchActivity<MainActivity>()
        Thread.sleep(1000)
    }

    @Test
    fun allViewsAreVisible() {
        onView(withId(R.id.img_icon)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_title)).check(matches(isDisplayed()))
        onView(withId(R.id.txt)).check(matches(isDisplayed()))
        onView(withId(R.id.btn)).check(matches(isDisplayed()))
    }

    @Test
    fun showInitialCard() {
        onView(withId(R.id.txt))
            .check(
                matches(
                    ViewMatchers.withText(
                        MockCardsRepository.getCards.value.data?.cards?.get(
                            0
                        )?.text
                    )
                )
            )
    }

    @Test
    fun btnAnotherCard() {
        Espresso.onView(ViewMatchers.withId(R.id.btn)).perform(ViewActions.click())
        Thread.sleep(500)
        Espresso.onView(ViewMatchers.withId(R.id.txt)).check(
            matches(
                ViewMatchers.withText(
                    MockCardsRepository.getCards.value.data?.cards?.get(
                        1
                    )?.text
                )
            )
        )
    }
}