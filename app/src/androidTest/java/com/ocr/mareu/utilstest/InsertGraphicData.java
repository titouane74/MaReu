package com.ocr.mareu.utilstest;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;

import com.ocr.mareu.R;

import org.hamcrest.Matchers;

import java.util.Calendar;
import java.util.List;

import static android.view.KeyEvent.KEYCODE_ENTER;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Florence LE BOURNOT on 25/02/2020
 */
public class InsertGraphicData {

    public static void toto(String pText){
        onView(withText(pText))
                .inRoot(isPlatformPopup())
                .perform(click());

    }

    public  static void addRoom(String pRoom) {
        onView(withText(pRoom))
                .inRoot(isPlatformPopup())
                .perform(click());
/*
        onData(allOf(is(instanceOf(String.class)), is(pRoom)))
                .inAdapterView(withId(R.id.room_list))
                .perform(click());
*/
    }


    public static void addTopic(String pText) {
        ViewInteraction lTextET = onView(allOf(withId(R.id.meeting_topic_et)));
        lTextET.perform(replaceText(pText));
    }


    public static void addDate(Calendar pCalDate, int pDiffMonth, int pDiffDay) {
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        pCalDate.get(Calendar.YEAR),
                        pCalDate.get(Calendar.MONTH + pDiffMonth) ,
                        pCalDate.get(Calendar.DAY_OF_MONTH + pDiffDay)));
        onView(withText(android.R.string.ok)).perform(click());

    }

    public  static void addTime(Calendar pCalDate) {
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(
                        pCalDate.get(Calendar.HOUR_OF_DAY),
                        pCalDate.get(Calendar.MINUTE)));
        onView(withText(android.R.string.ok)).perform(click());

    }

    public static void addEmailAddress(String pText) {
        ViewInteraction lEmailET = onView(allOf(withId(R.id.email_address_et)));
        lEmailET.perform(replaceText(pText))
                .perform(scrollTo(),click())
                .perform(pressKey(KEYCODE_ENTER));
    }

    public static void addFakeMeeting(String pRoom, String pTopic, Calendar pDateCal,
                                      int pDiffMonth, int pDiffDay, int pDiffHour, List<String> pParticipants) {
        addRoom(pRoom);
        addTopic(pTopic);
        onView(withId(R.id.meeting_date)).perform(click());
        addDate(pDateCal, pDiffMonth, pDiffDay);
        onView(withId(R.id.meeting_start_et)).perform(click());
        addTime(pDateCal);
        pDateCal.add(Calendar.HOUR_OF_DAY , pDiffHour);
        onView(withId(R.id.meeting_end_et)).perform(click());
        addTime(pDateCal);
            for (String lParticipants : pParticipants) {
                addEmailAddress(lParticipants);
            }
    }

    public static void insertFakeMetingRecyclerView(String pDescription, String pParticipants) {
/*

        insertFakeMeting("CRONOS - 16:00 - Réunion DG",
                "loki@gmail.com, thor@gmail.com, captainamerica@gamail.com");

        insertFakeMeting("POSEIDON - 10:00 - Réunion Commerciale",
                "loki@gmail.com, thor@gmail.com, captainamerica@gamail.com");
*/


        ViewInteraction lDescription = onView(allOf(withId(R.id.item_description)));
        lDescription.perform(replaceText(pDescription));

        ViewInteraction lParticipants = onView(allOf(withId(R.id.item_participant)));
        lParticipants.perform(replaceText(pParticipants));
    }
}
