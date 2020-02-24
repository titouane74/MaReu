package com.ocr.mareu.ui.activities;

import android.content.Context;
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
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.ocr.mareu.utils.FilterRoomMatcher.childAtPosition;
import static com.ocr.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
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

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
        assertNotNull(mActivity);
        assertThat(mActivity, notNullValue());

        mApi = DI.getMeetingApiService();
        assertNotNull(mApi);

        mApi.addFakeMeeting();
        onView(withId(R.id.activity_list_rv)).check(withItemCount(2));

        mMeetings = mApi.getMeetings();
    }

    @After
    public void tearDown() {
        mApi = DI.getMeetingApiServiceNewInstance();
    }

    @Test
    public void useAppContext() {
        Context appContext = getInstrumentation().getTargetContext();
        assertEquals("com.ocr.mareu", appContext.getPackageName());
    }

    @Test //KO
    public void given2Meeting_whenTextEllipsized_thenSucces() {

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(withId(R.id.activity_list_rv))).check(matches(isDisplayed()));
        onView(withId(R.id.activity_list_rv)).check(withItemCount(2));

        System.out.println("PARTICIPANTS :" + mMeetings.get(0).toStringParticipants());

/*
        onView(allOf(withId(R.id.item_description), withText(startsWith("POSEIDON"))))
                .check(matches(withText(lMeetings.get(0).toStringDescription())));
*/

    }


}
