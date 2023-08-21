package com.cr.o.cdc.aprenderdegrandes

import android.widget.TextView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.cr.o.cdc.aprenderdegrandes.mocks.MockCardsRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun before() {
    }

    @Test
    fun showInitialCard() {
        val scenario = launchActivity<MainActivity>()
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.txt))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.withText(
                        MockCardsRepository.getCards.value.data?.cards?.get(
                            0
                        )?.text
                    )
                )
            )
    }
}