package com.cr.o.cdc.aprenderdegrandes

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.cr.o.cdc.aprenderdegrandes.analitycs.FirebaseEvent
import com.cr.o.cdc.aprenderdegrandes.mocks.CardEntityMock
import com.cr.o.cdc.aprenderdegrandes.mocks.MockCardsRepository
import com.cr.o.cdc.aprenderdegrandes.mocks.MockMyFirebaseAnalyticsImp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class VolumeActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var scenario: ActivityScenario<VolumeActivity>
    private lateinit var context: Context

    @Before
    fun before() {
        hiltRule.inject()
        scenario = launchActivity(
            VolumeActivity.getIntent(
                ApplicationProvider.getApplicationContext(), 1
            )
        )
        scenario.onActivity {
            this.context = it
        }
        Thread.sleep(1000)
        MockMyFirebaseAnalyticsImp.clean()
    }

    @Test
    fun rateCard() {
        val cardEntity = CardEntityMock.getFirstCardEntity()

    }

    @Test
    fun allViewsAreVisible() {
        scenario.onActivity {
            val constraintLayout = findFirstConstraintLayout(it.window.decorView.rootView)
            checkOverlayOffViews(constraintLayout?.children?.toList()!!)
        }
    }

    @Test
    fun showInitialCard() {
        val cardEntity = CardEntityMock.getFirstCardEntity()
        onView(withId(R.id.txt)).check(matches(ViewMatchers.withText(cardEntity.text)))
        onView(withId(R.id.txt_viewed_n_times)).check(
            matches(ViewMatchers.withSubstring(cardEntity.viewedTimes.inc().toString()))
        )
    }

    @Test
    fun btnAnotherCard() {
        onView(withId(R.id.btn)).perform(ViewActions.click())
        Thread.sleep(500)
        val card = MockCardsRepository.volume.value.data?.cards?.get(
            1
        )
        onView(withId(R.id.txt)).check(matches(ViewMatchers.withText(card?.text)))
        assert(MockMyFirebaseAnalyticsImp.contains(FirebaseEvent.BTN_ANOTHER_CARD))
        assertEquals(
            card?.id,
            MockMyFirebaseAnalyticsImp.viewedCardEntity?.id
        )
    }

    @Test
    fun btnAnotherCardWithoutCards() {
        onView(withId(R.id.btn)).perform(ViewActions.click())
        Thread.sleep(500)
        onView(withId(R.id.btn)).perform(ViewActions.click())
        Thread.sleep(500)
        assertEquals(Lifecycle.State.DESTROYED, scenario.state)
        assert(MockMyFirebaseAnalyticsImp.contains(FirebaseEvent.BTN_FINISH_GAME))
    }

    private fun checkOverlayOffViews(viewList: List<View>) {
        viewList.forEachIndexed { indexA, viewA ->
            val leftA = viewA.left
            val topA = viewA.top
            val rightA = viewA.right
            val bottomA = viewA.bottom

            viewList.filter { viewB -> viewB != viewA }.forEachIndexed { indexB, viewB ->
                val leftB = viewB.left
                val topB = viewB.top
                val rightB = viewB.right
                val bottomB = viewB.bottom

                val isOverlapping =
                    !(rightA < leftB || leftA > rightB || bottomA < topB || topA > bottomB)

                val zeroDp = viewB.height == 0 && viewB.width == 0
                if (zeroDp) {
                    throw Exception("${viewB.javaClass.name} zero dp in height and width")
                }

                if (isOverlapping) {
                    throw Exception("${viewA.javaClass.name} at position $indexA overlapping with ${viewB.javaClass.name} at position $indexB")
                }
            }
        }
    }

    private fun findFirstConstraintLayout(view: View): ConstraintLayout? {
        if (view is ConstraintLayout) {
            return view
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val childView = view.getChildAt(i)
                if (childView is ViewStub && childView.layoutResource != 0) {
                    val inflatedView = childView.inflate()
                    val constraintLayout = findFirstConstraintLayout(inflatedView)
                    if (constraintLayout != null) {
                        return constraintLayout
                    }
                } else {
                    val constraintLayout = findFirstConstraintLayout(childView)
                    if (constraintLayout != null) {
                        return constraintLayout
                    }
                }
            }
        }

        return null
    }
}