package com.ocr.mareu.utilstest;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;

import com.ocr.mareu.R;

import org.hamcrest.Matchers;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static android.view.KeyEvent.KEYCODE_ENTER;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Florence LE BOURNOT on 25/02/2020
 */
public class InsertGraphicData {


    public static void addFakeMeeting(String pRoom, String pTopic,
                                      Calendar pDateCal, int pDiffDay, int pDiffHourStart, int pDiffHourEnd, List<String> pParticipants) {

        onView(withId(R.id.add_fab)).perform(click());

        onView(allOf(withId(R.id.add_fragment_layout))).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.room_list))).perform(click());
        addRoom(pRoom);

        addTopic(pTopic);

        pDateCal.add(Calendar.DAY_OF_MONTH, pDiffDay);
        addDate(pDateCal);
        System.out.println("REFERENCE DATE : " + pDateCal.getTime());

        Calendar lCalStart = (Calendar) pDateCal.clone();
        lCalStart.add(Calendar.HOUR_OF_DAY, pDiffHourStart);
        onView(withId(R.id.meeting_start_et)).perform(click());
        addTime(lCalStart);
        System.out.println("REFERENCE START : " + lCalStart.getTime());

        Calendar lCalEnd = (Calendar) pDateCal.clone();
        lCalEnd.add(Calendar.HOUR_OF_DAY, pDiffHourEnd);
        onView(withId(R.id.meeting_end_et)).perform(click());
        addTime(lCalEnd);
        System.out.println("REFERENCE END : " + lCalEnd.getTime());

        for (String lParticipants : pParticipants) {
            addEmailAddress(lParticipants);
        }

        onView(allOf(withId(R.id.btn_save))).perform(click());

    }


    public static void addRoom(String pRoom) {
        onView(withText(pRoom))
                .inRoot(isPlatformPopup())
                .perform(click());
    }


    public static void addTopic(String pText) {
        ViewInteraction lTextET = onView(allOf(withId(R.id.meeting_topic_et)));
        lTextET.perform(replaceText(pText));
    }


    public static void addDate(Calendar pCalDate) {

        onView(withId(R.id.meeting_date)).perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        pCalDate.get(Calendar.YEAR),
                        pCalDate.get(Calendar.MONTH) + 1,
                        pCalDate.get(Calendar.DAY_OF_MONTH)));
        onView(withText(android.R.string.ok)).perform(click());

    }

    public static void addTime(Calendar pCalDate) {

        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(
                        pCalDate.get(Calendar.HOUR_OF_DAY),
                        pCalDate.get(Calendar.MINUTE)));
        onView(withText(android.R.string.ok)).perform(click());

    }

    public static void addEmailAddress(String pText) {
        ViewInteraction lEmailET = onView(allOf(withId(R.id.email_address_et)));
        lEmailET.perform(replaceText(pText))
                .perform(scrollTo(), click())
                .perform(pressKey(KEYCODE_ENTER));
    }


/*
    public static void add10FakeMeeting() {
        addFakeMeeting("ARES", "La guerre des boutons",
                mCalDate, 2,2,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

    }
*/
}

