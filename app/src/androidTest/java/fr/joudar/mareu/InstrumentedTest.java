package fr.joudar.mareu;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.TreeIterables;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.StringDescription;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

import static fr.joudar.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;

import android.view.View;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.List;

import fr.joudar.mareu.di.DI;
import fr.joudar.mareu.service.ApiService;
import fr.joudar.mareu.ui.MeetingsListActivity;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {
    @Rule
    public ActivityScenarioRule<MeetingsListActivity> scenarioRule = new ActivityScenarioRule<MeetingsListActivity>(MeetingsListActivity.class);

    private static int ITEMS_COUNT = 8;
    private static int ITEM = 0;
    private ApiService mApiService;

    public static ViewAction actionOnItemView(Matcher<View> matcher, ViewAction action) {

        return new ViewAction() {

            @Override
            public String getDescription() {
                return String.format("performing ViewAction: %s on item matching: %s", action.getDescription(), StringDescription.asString(matcher));
            }

            @Override
            public Matcher<View> getConstraints() {
                return allOf(withParent(isAssignableFrom(RecyclerView.class)), isDisplayed());
            }

            @Override
            public void perform(UiController uiController, View view) {
                List<View> results = new ArrayList<>();
                for (View v : TreeIterables.breadthFirstViewTraversal(view)) {
                    if (matcher.matches(v)) results.add(v);
                }
                if (results.isEmpty()) {
                    throw new RuntimeException(String.format("No view found %s", StringDescription.asString(matcher)));
                } else if (results.size() > 1) {
                    throw new RuntimeException(String.format("Ambiguous views found %s", StringDescription.asString(matcher)));
                }
                action.perform(uiController, results.get(0));
            }
        };
    }

    @Before
    public void setUp() {
        mApiService = DI.getNewInstanceApiService();
        assertThat(mApiService, notNullValue());
    }

    @Test
    public void A_meetingsList_shouldNotBeEmpty() {
        onView(allOf(withId(R.id.recycler_view_list), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void B_meetingList_detailAction_shouldDisplayTheCorrectItem() {
        onView(allOf(withId(R.id.recycler_view_list), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT));

        onView(ViewMatchers.withId(R.id.recycler_view_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(ITEM, click()));
        onView(withId(R.id.activity_meeting_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.meeting_room_name_1)).check(matches(withText(mApiService.getMeetings().get(ITEM).getRoom().getRoomName())));
        onView(withId(R.id.meeting_topic)).check(matches(withText(mApiService.getMeetings().get(ITEM).getTopic())));
        onView(withId(R.id.meeting_date)).check(matches(withText(mApiService.getMeetings().get(ITEM).getDateAsString())));
        onView(withId(R.id.meeting_time)).check(matches(withText(mApiService.getMeetings().get(ITEM).getStartTimeAsString()+" - "+mApiService.getMeetings().get(ITEM).getFinishTimeAsString())));
        onView(withId(R.id.meeting_room_name_2)).check(matches(withText(mApiService.getMeetings().get(ITEM).getRoom().getRoomName())));
        onView(withId(R.id.meeting_participants_list)).check(matches(withText(mApiService.getMeetings().get(ITEM).getParticipantsAsString())));

        Espresso.pressBack();

        onView(allOf(withId(R.id.recycler_view_list), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT));

    }

    @Test
    public void C_filterMeetingsByRoomWithSuccess() {
        onView(withId(R.id.menu_filters)).perform(click());
        onView(withText("Room filter")).perform(click());
        onView(withText("Earth")).perform(click());
        onView(withText("Close")).perform(click());
        onView(allOf(withId(R.id.recycler_view_list), isDisplayed()))
                .check(withItemCount(1));
    }

    @Test
    public void D_filterMeetingsByDateWithSuccess() {
        onView(withId(R.id.menu_filters)).perform(click());
        onView(withText("Date filter")).perform(click());
        onView(withText("OK")).perform(click());

        onView(allOf(withId(R.id.recycler_view_list), isDisplayed()))
                .check(withItemCount(2));

        onView(withId(R.id.menu_filters)).perform(click());
        onView(withText("Date filter")).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2023, 1, 1));
        onView(withText("OK")).perform(click());
        onView(allOf(withId(R.id.recycler_view_list), isDisplayed()))
                .check(withItemCount(0));

    }

    @Test
    public void E_disableMeetingsFilterWithSuccess(){

        onView(withId(R.id.menu_filters)).perform(click());
        onView(withText("Room filter")).perform(click());
        onView(withText("Earth")).perform(click());
        onView(withText("Close")).perform(click());
        onView(allOf(withId(R.id.recycler_view_list), isDisplayed()))
                .check(withItemCount(1));

        onView(withId(R.id.menu_all_filters_off)).perform(click());
        onView(allOf(withId(R.id.recycler_view_list), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT));

        onView(withId(R.id.menu_filters)).perform(click());
        onView(withText("Date filter")).perform(click());
        onView(withText("OK")).perform(click());

        onView(allOf(withId(R.id.recycler_view_list), isDisplayed()))
                .check(withItemCount(2));

        onView(withId(R.id.menu_all_filters_off)).perform(click());
        onView(allOf(withId(R.id.recycler_view_list), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT));
    }

    @Test
    public void F_addNewMeetingWithSuccess() {
        onView(allOf(withId(R.id.recycler_view_list), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT));

        onView(withId(R.id.button_add_meeting)).perform(click());

        onView(withId(R.id.topic_edit_text)).perform(click());
        onView(withId(R.id.topic_edit_text)).perform(typeText("New meeting"));

        onView(withId(R.id.room_auto_complete_text_view)).perform(click());
        onData(anything()).atPosition(2)
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        onView(withId(R.id.date_edit_text)).perform(click());
        onView(withText("OK")).perform(click());

        onView(withId(R.id.start_time_edit_text)).perform(click());
        onView(withText("OK")).perform(click());


        onView(withId(R.id.participants_auto_complete_text_view)).perform(click());
        onView(withId(R.id.participants_auto_complete_text_view)).perform(typeText("mich"));
        onData(anything()).atPosition(0)
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        onView(withId(R.id.create_new_meeting)).perform(click());

        onView(allOf(withId(R.id.recycler_view_list), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT+1));
    }

    @Test
    public void G_deleteMeetingWithSuccess() {
        onView(allOf(withId(R.id.recycler_view_list), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT+1));

        ViewAction deleteItemViewAction = actionOnItemView(withId(R.id.delete_item_button), click());
        onView(ViewMatchers.withId(R.id.recycler_view_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(ITEM, deleteItemViewAction));

        onView(allOf(withId(R.id.recycler_view_list), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT));
    }

}