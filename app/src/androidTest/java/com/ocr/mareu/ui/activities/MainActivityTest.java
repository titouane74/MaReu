package com.ocr.mareu.ui.activities;

import android.content.Context;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.ocr.mareu.R;
import com.ocr.mareu.service.FakeMeetingApiService;
import com.ocr.mareu.service.MeetingApiService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Florence LE BOURNOT on 22/02/2020
 */
public class MainActivityTest {

    private MeetingApiService mApi = null;
    private MainActivity mActivity = null;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
        assertNotNull(mActivity);
        assertThat(mActivity, notNullValue());

        mApi = new FakeMeetingApiService();
        assertNotNull(mApi);
    }

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.ocr.mareu", appContext.getPackageName());
    }

    @Test
    public void open() {
        onView(withId(R.id.add_fab)).check(matches(isDisplayed()));

        onView(withId(R.id.action)).check(matches(isEnabled()));
//        onView(withChild(withId(R.id.action_sort))).check(matches(not(isEnabled())));
//        onView(withChild(withId(R.id.action_filter))).check(matches(not(isEnabled())));

        onView(withId(R.id.activity_list_rv))
                .check(matches(hasMinimumChildCount(0)));




    }
}
