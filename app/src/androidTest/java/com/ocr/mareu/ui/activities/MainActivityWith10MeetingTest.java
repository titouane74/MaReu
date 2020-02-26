package com.ocr.mareu.ui.activities;

import android.view.View;
import android.widget.DatePicker;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.ocr.mareu.R;
import com.ocr.mareu.di.DI;
import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.service.MeetingApiService;
import com.ocr.mareu.service.MeetingApiServiceException;
import com.ocr.mareu.ui.activities.MainActivity;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.LayoutMatchers.hasEllipsizedText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.ocr.mareu.matchers.ChildAtPositionMatcher.childAtPosition;
import static com.ocr.mareu.assertion.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Florence LE BOURNOT on 24/02/2020
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityWith10MeetingTest {

    private MeetingApiService mApi = null;
    private MainActivity mActivity = null;
    private List<Meeting> mMeetings = new ArrayList<>();
    private static int ITEMS_COUNT = 10;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {
            @Override
            protected void beforeActivityLaunched() {
                mApi = DI.getMeetingApiServiceNewInstance();
                try {
                    mApi.addFakeValidMeetingsLongList();
                } catch (MeetingApiServiceException pE) {
                    pE.printStackTrace();
                }
            }
    };

    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
        assertNotNull(mActivity);
        assertThat(mActivity, notNullValue());

        mMeetings = mApi.getMeetings();
    }

    @After
    public void tearDown() {
        mApi = DI.getMeetingApiServiceNewInstance();
    }

    @Test //OK
    public void given10Meeting_whenTextEllipsizedAndGoodFormat_thenSuccess() {

        onView(allOf(withId(R.id.activity_list_rv))).check(matches(isDisplayed()));
        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT));

        onView(allOf(withId(R.id.item_description), withText(startsWith("POSEIDON"))))
                .check(matches(withText(mMeetings.get(0).toStringDescription())));

        onView(allOf(withId(R.id.item_participant), withText(startsWith("loki"))))
                .check(matches(withText(mMeetings.get(0).toStringParticipants())));

        onView(allOf(withId(R.id.item_participant), withText(startsWith("loki"))))
                .check(matches(hasEllipsizedText()));

    }

    @Test //OK
    public void given10Meeting_whenFilterByRoom_thenShow2MeetingWithSuccess () throws MeetingApiServiceException {

        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT));

        onView(allOf(withId(R.id.action_filter))).perform(click());

        ViewInteraction appCompatTextView = onView(
                Matchers.allOf(withId(R.id.title), withText("Salle sélectionnée"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());


        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(Matchers.allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                        childAtPosition(
                                withClassName(is("android.widget.FrameLayout")),
                                0)))
                .atPosition(3);
        appCompatCheckedTextView.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(Matchers.allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                        childAtPosition(
                                withClassName(is("android.widget.FrameLayout")),
                                0)))
                .atPosition(7);
        appCompatCheckedTextView2.perform(click());

        ViewInteraction checkedTextView = onView(
                Matchers.allOf(withId(android.R.id.text1),
                        childAtPosition(
                                Matchers.allOf(IsInstanceOf.<View>instanceOf(android.widget.ListView.class),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                                0)),
                                3),
                        isDisplayed()));
        checkedTextView.check(matches(isDisplayed()));

        ViewInteraction checkedTextView2 = onView(
                Matchers.allOf(withId(android.R.id.text1),
                        childAtPosition(
                                Matchers.allOf(IsInstanceOf.<View>instanceOf(android.widget.ListView.class),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                                0)),
                                7),
                        isDisplayed()));
        checkedTextView2.check(matches(isDisplayed()));

        ViewInteraction appCompatButton = onView(
                Matchers.allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                Matchers.allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());


        onView(allOf(withId(R.id.activity_list_rv))).check(matches(isDisplayed()));

        //Contrôle que la liste est vide
        onView(withId(R.id.activity_list_rv)).check(withItemCount(2));

        List<Meeting> lMeetings = mApi.getMeetings();
        onView(allOf(withId(R.id.item_description),withText(startsWith("DEMETER"))))
                .check(matches(withText(lMeetings.get(7).toStringDescription())));
        onView(allOf(withId(R.id.item_description), withText(startsWith("POSEIDON"))))
                .check(matches(withText(lMeetings.get(0).toStringDescription())));

    }

    @Test //OK
    public void given10Meeting_whenFilterByDate_thenShowMeetingWithSameDate () throws MeetingApiServiceException {

        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT));

        //Saisie de la date de filtrage
        Calendar lCalDate = Calendar.getInstance(Locale.FRANCE);
        DateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        lCalDate.set(2020,8,01);
        lCalDate.set(Calendar.MINUTE,00);
        lCalDate.set(Calendar.SECOND,0);
        lCalDate.set(Calendar.MILLISECOND,0);


        onView(allOf(withId(R.id.action_filter))).perform(click());

        ViewInteraction appCompatTextView = onView(
                Matchers.allOf(withId(R.id.title), withText("Date sélectionnée"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        lCalDate.get(Calendar.YEAR),
                        lCalDate.get(Calendar.MONTH),
                        lCalDate.get(Calendar.DAY_OF_MONTH)));
        onView(withText(android.R.string.ok)).perform(click());


        onView(allOf(withId(R.id.activity_list_rv))).check(matches(isDisplayed()));

        onView(withId(R.id.activity_list_rv)).check(withItemCount(3));

        List<Meeting> lMeetings = mApi.getMeetings();
        onView(allOf(withId(R.id.item_description),withText(startsWith("VENUS"))))
                .check(matches(withText(lMeetings.get(6).toStringDescription())));
        onView(allOf(withId(R.id.item_description), withText(startsWith("HADES"))))
                .check(matches(withText(lMeetings.get(8).toStringDescription())));
        onView(allOf(withId(R.id.item_description), withText(startsWith("GAIA"))))
                .check(matches(withText(lMeetings.get(9).toStringDescription())));

    }

    @Test //OK
    public void given10Meeting_whenResetFilter_thenRemoveAllItems () throws MeetingApiServiceException {

        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT));

        //Saisie de la date de filtrage
        Calendar lCalDate = Calendar.getInstance(Locale.FRANCE);
        DateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        lCalDate.set(2020,8,01);
        lCalDate.set(Calendar.MINUTE,00);
        lCalDate.set(Calendar.SECOND,0);
        lCalDate.set(Calendar.MILLISECOND,0);


        onView(allOf(withId(R.id.action_filter))).perform(click());
        ViewInteraction appCompatTextView = onView(
                Matchers.allOf(withId(R.id.title), withText("Date sélectionnée"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        lCalDate.get(Calendar.YEAR),
                        lCalDate.get(Calendar.MONTH),
                        lCalDate.get(Calendar.DAY_OF_MONTH)));
        onView(withText(android.R.string.ok)).perform(click());


        onView(allOf(withId(R.id.activity_list_rv))).check(matches(isDisplayed()));

        onView(withId(R.id.activity_list_rv)).check(withItemCount(3));

        List<Meeting> lMeetings = mApi.getMeetings();
        onView(allOf(withId(R.id.item_description),withText(startsWith("VENUS"))))
                .check(matches(withText(lMeetings.get(6).toStringDescription())));
        onView(allOf(withId(R.id.item_description), withText(startsWith("HADES"))))
                .check(matches(withText(lMeetings.get(8).toStringDescription())));
        onView(allOf(withId(R.id.item_description), withText(startsWith("GAIA"))))
                .check(matches(withText(lMeetings.get(9).toStringDescription())));

        onView(allOf(withId(R.id.action_filter))).perform(click());

        ViewInteraction appCompatTextView1 = onView(
                Matchers.allOf(withId(R.id.title), withText("Enlever le filtre"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView1.perform(click());

        onView(allOf(withId(R.id.activity_list_rv))).check(matches(isDisplayed()));

        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT));
    }


}
