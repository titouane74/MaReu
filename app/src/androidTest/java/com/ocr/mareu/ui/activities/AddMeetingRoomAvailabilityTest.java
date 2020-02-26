package com.ocr.mareu.ui.activities;

import androidx.test.rule.ActivityTestRule;

import com.ocr.mareu.R;
import com.ocr.mareu.di.DI;
import com.ocr.mareu.matchers.ToastMatcher;
import com.ocr.mareu.service.MeetingApiService;
import com.ocr.mareu.service.MeetingApiServiceException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.ocr.mareu.assertion.RecyclerViewItemCountAssertion.withItemCount;
import static com.ocr.mareu.utilstest.InsertGraphicData.addFakeMeeting;
import static com.ocr.mareu.utilstest.MeetingReferenceTest.addReferenceMeeting;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Florence LE BOURNOT on 26/02/2020
 */
public class AddMeetingRoomAvailabilityTest {
    private MeetingApiService mApi = null;
    private MainActivity mActivity = null;
    private Calendar mNow ;
    private int mDiffDay ;
    private Calendar mCalDate;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws MeetingApiServiceException {
        mActivity = mActivityTestRule.getActivity();
        assertNotNull(mActivity);
        assertThat(mActivity, notNullValue());

        mApi = DI.getMeetingApiService();
        assertNotNull(mApi);

        mNow = Calendar.getInstance(Locale.FRANCE);
        mCalDate = (Calendar) mNow.clone();
        mDiffDay = 2;
        mCalDate.add(Calendar.DAY_OF_MONTH,mDiffDay);

        //Meeting de référence
        mApi = addReferenceMeeting(mApi,mCalDate,mDiffDay,0,3);
        onView(withId(R.id.activity_list_rv)).check(withItemCount(1));

        mDiffDay = 0;
    }

    @After
    public void tearDown() {
        mApi = DI.getMeetingApiServiceNewInstance();
        mNow = Calendar.getInstance(Locale.FRANCE);
    }

    //("Case 1 : Meeting Start = Reference start")
    @Test //OK
    public void givenNewMeeting_whenSameStart_thenFail() {

        addFakeMeeting("ARES", "Sujet 1 KO", mCalDate, mDiffDay,0,1,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(withText(R.string.err_meeting_room_not_free))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

    }

    //("Case 2 : Meeting end = Reference End")
    @Test //OK
    public void  givenNewMeeting_whenSameEnd_thenFail() {

        addFakeMeeting("ARES", "Sujet 2 KO", mCalDate, mDiffDay,2,3,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(withText(R.string.err_meeting_room_not_free))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

    }

    //("Case 3 : Meeting start before Reference start and Meeting end before Reference start")
    @Test //OK
    public void givenNewMeeting_whenBeforeReference_thenCreateMeeting() {

        addFakeMeeting("ARES", "Sujet 3 OK", mCalDate, mDiffDay,-2,-1,
            Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(withText(R.string.action_add_meeting))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    //("Case 4 : Meeting start before Reference start and Meeting end same Reference end")
    @Test //OK
    public void givenNewMeeting_whenEndSameReferenceStart_thenCreateMeeting() {

        addFakeMeeting("ARES", "Sujet 4 OK", mCalDate, mDiffDay,-1,0,
            Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(withText(R.string.action_add_meeting))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

    }

    //("Case 5 : Meeting start before Reference start and " +
    //        "Meeting end before Reference end and after Reference start")
    @Test //OK
    public void givenNewMeeting_whenEndDuringReference_thenFail() {

        addFakeMeeting("ARES", "Sujet 5 KO", mCalDate, mDiffDay,-1,1,
            Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(withText(R.string.err_meeting_room_not_free))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

    }

    //("Case 6 : Meeting start before Reference start and Meeting end after Reference end")
    @Test //OK
    public void givenNewMeeting_whenReferenceDuringNewMeeting_thenFail() {

        addFakeMeeting("ARES", "Sujet 6 KO", mCalDate, mDiffDay,-1,+4,
            Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(withText(R.string.err_meeting_room_not_free))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

    }

    //("Case 7 : Meeting start after Reference start and Meeting end before Reference end")
    @Test //OK
    public void givenNewMeeting_whenNewMeetingDuringReference_thenFail() {

        addFakeMeeting("ARES", "Sujet 7 KO", mCalDate, mDiffDay,1,2,
            Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(withText(R.string.err_meeting_room_not_free))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

    }

    //("Case 8 : Meeting start after Reference start and before Reference end and" +
    //        "Meeting end after Reference end")
    @Test //OK
    public void givenNewMeeting_whenReferenceEndDuringNewMeeting_thenFail() {

        addFakeMeeting("ARES", "Sujet 8 KO", mCalDate, mDiffDay,2,4,
            Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));


        onView(withText(R.string.err_meeting_room_not_free))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

    }

    //("Case 9 : Meeting start = Reference end and Meeting end after Reference end")
    @Test //OK
    public void givenNewMeeting_whenStartSameReferenceEnd_thenCreateMeeting() {

        addFakeMeeting("ARES", "Sujet 9 OK", mCalDate, mDiffDay,3,4,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(withText(R.string.action_add_meeting))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

    }

    //("Case 10 : Meeting start and end after Reference end")
    @Test //OK
    public void givenNewMeeting_whenAfterReference_thenCreateMeeting() {

        addFakeMeeting("ARES", "Sujet 10 OK", mCalDate, mDiffDay,4,5,
        Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(withText(R.string.action_add_meeting))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

    }
}
