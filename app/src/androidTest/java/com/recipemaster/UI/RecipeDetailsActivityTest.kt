package com.recipemaster.UI

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.recipemaster.R
import com.recipemaster.view.RecipeDetailsActivity
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecipeDetailsActivityTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(RecipeDetailsActivity::class.java)

    @Test
    fun test_isActivityInView() {
        onView(withId(R.id.activity_details_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_recipeTitle() {
        onView(withId(R.id.recipe_title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_ingredientsTitle() {
        onView(withId(R.id.ingredients_title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_isIngredientsTextDisplayed() {
        onView(withId(R.id.ingredients_title))
            .check(matches(withText(R.string.ingredients)))
    }

    @Test
    fun test_visibility_ingredientsField() {
        onView(withId(R.id.recipe_ingredients))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_preparingTitle() {
        onView(withId(R.id.preparing_title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_isPreparingTextDisplayed() {
        onView(withId(R.id.preparing_title))
            .check(matches(withText(R.string.preparing)))
    }

    @Test
    fun test_visibility_preparingField() {
        onView(withId(R.id.recipe_preparing))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_ImagesTitle() {
        onView(withId(R.id.images_title))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun test_isImagesTextDisplayed() {
        onView(withId(R.id.images_title))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun test_visibility_image0() {
        onView(withId(R.id.recipe_image0))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun test_visibility_image1() {
        onView(withId(R.id.recipe_image1))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun test_visibility_image2() {
        onView(withId(R.id.recipe_image2))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun test_visibility_footer(){
        onView(withId(R.id.footer))
            .check(matches(not(isDisplayed())))
    }
    @Test
    fun test_visibility_loggedAsTextField(){
        onView(withId(R.id.logged_as))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun test_visibility_profilePicture(){
        onView(withId(R.id.profile_picture))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun test_isClickable_image0(){
        onView(withId(R.id.recipe_image0))
            .check(matches(isClickable()))
    }

    @Test
    fun test_isClickable_image1(){
        onView(withId(R.id.recipe_image1))
            .check(matches(isClickable()))
    }
    @Test
    fun test_isClickable_image2(){
        onView(withId(R.id.recipe_image2))
            .check(matches(isClickable()))
    }


}
