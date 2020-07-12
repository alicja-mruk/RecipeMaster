package com.recipemaster.UI

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.recipemaster.R
import com.recipemaster.view.HomeActivity
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.recipemaster", appContext.packageName)
    }

    @Test
    fun test_isActivityInView() {
        val activityScenario = ActivityScenario.launch(HomeActivity::class.java)
        onView(withId(R.id.home_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_homePicture() {
        val activityScenario = ActivityScenario.launch(HomeActivity::class.java)
        onView(withId(R.id.main_image))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_mainText() {
        val activityScenario = ActivityScenario.launch(HomeActivity::class.java)
        onView(withId(R.id.title_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_isTitleTextDisplayed() {
        val activityScenario = ActivityScenario.launch(HomeActivity::class.java)
        onView(withId(R.id.title_text))
            .check(matches(withText(R.string.title)))
    }

    @Test
    fun test_visibility_facebookFabButton() {
        val activityScenario = ActivityScenario.launch(HomeActivity::class.java)
        onView(withId(R.id.login_facebook_btn))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testVisibility_getRecipeFabButton() {
        val activityScenario = ActivityScenario.launch(HomeActivity::class.java)
        onView(withId(R.id.get_recipe_btn))
            .check(matches(isDisplayed()))
    }
}
