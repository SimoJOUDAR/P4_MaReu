package fr.joudar.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

import fr.joudar.mareu.model.Meeting;
import fr.joudar.mareu.ui.MeetingsListActivity;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {
    @Rule
    public ActivityScenarioRule<MeetingsListActivity> scenarioRule = new ActivityScenarioRule<MeetingsListActivity>(MeetingsListActivity.class);

    //    @Before
//    public void setup() {
//        ActivityScenario scenario = scenarioRule.getScenario();
//    }
    @Test
    public void meetingsList_shouldNotBeEmpty() {
        onView(allOf(withId(R.id.recycler_view_list), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }
}