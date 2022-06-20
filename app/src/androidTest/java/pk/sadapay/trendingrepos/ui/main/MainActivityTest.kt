package pk.sadapay.trendingrepos.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pk.sadapay.trendingrepos.R
import pk.sadapay.trendingrepos.utils.UIState

@RunWith(AndroidJUnit4::class)
@LargeTest
internal class MainActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun assert_multiStateView_Is_Displayed() {
        onView(ViewMatchers.withId(R.id.multiStateView)).check(matches(isDisplayed()))
    }

    @Test
    fun assert_Retry_Button_Click() {
        activityScenarioRule.scenario.onActivity {
            it.onUiStateChange(UIState.Error(""))
        }
        onView(ViewMatchers.withId(R.id.buttonRetry)).perform(click())
    }

    @Test
    fun assert_Recyclerview_is_visible_when_ui_state_is_content() {
        activityScenarioRule.scenario.onActivity {
            it.onUiStateChange(UIState.Content)
        }

        onView(ViewMatchers.withId(R.id.multiStateView)).check(
            matches(
                ViewMatchers.withEffectiveVisibility(
                    ViewMatchers.Visibility.VISIBLE
                )
            )
        )
    }

    @Test
    fun assert_Shimmer_is_visible_when_ui_state_is_loading() {
        activityScenarioRule.scenario.onActivity {
            it.onUiStateChange(UIState.Loading)
        }
        onView(ViewMatchers.withId(R.id.shimmerFrameLayout)).check(
            matches(
                ViewMatchers.withEffectiveVisibility(
                    ViewMatchers.Visibility.VISIBLE
                )
            )
        )
    }

    @Test
    fun assert_Error_layout_is_visible_when_ui_state_is_loading() {
        activityScenarioRule.scenario.onActivity {
            it.onUiStateChange(UIState.Error(""))
        }
        onView(ViewMatchers.withId(R.id.layoutError)).check(
            matches(
                ViewMatchers.withEffectiveVisibility(
                    ViewMatchers.Visibility.VISIBLE
                )
            )
        )
    }
}