package com.tengtonghann.android.movieum

import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tengtonghann.android.movieum.ui.movie.MovieFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieFragmentEspressoTest {

    @Test
    fun testEventFragment() {
        with(launchFragment<MovieFragment>()) {
            onFragment {
            }

            // Assumes that the dialog had a button
            // containing the text "Cancel".
            onView(withId(R.id.popularId)).check(matches(withText("Popular")))
        }
    }
}