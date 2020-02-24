package com.ocr.mareu.ui.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.TimePicker;

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
import com.ocr.mareu.utils.DeleteViewAction;
import com.ocr.mareu.utils.SortOrFilterLabel;
import com.ocr.mareu.utils.ToastMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.view.KeyEvent.KEYCODE_ENTER;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.LayoutMatchers.hasEllipsizedText;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.ocr.mareu.utils.ChipValueAssertion.matchesChipTextAtPosition;
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
 * Created by Florence LE BOURNOT on 22/02/2020
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private MeetingApiService mApi = null;
    private MainActivity mActivity = null;
    private static int ITEMS_COUNT = 2;

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
    }

    @After
    public void tearDown() {
        mApi = DI.getMeetingApiServiceNewInstance();
    }

    @Test //OK
    public void givenNothing_whenOpen_thenListIsEmpty() {
        //Contrôle que la liste est vide
        onView(withId(R.id.activity_list_rv)).check(withItemCount(0));
    }

    @Test //KO
    public void givenNewMeeting_whenAddMeeting_thenAddItemInList() {

        //Contrôle que la liste est vide
        onView(withId(R.id.activity_list_rv)).check(withItemCount(0));

        //Ajout une nouvelle réunion
        onView(withId(R.id.add_fab)).perform(click());

        onView(allOf(withId(R.id.add_fragment_layout))).check(matches(isDisplayed()));

        //Saisie de la salle de réunion
        onView(allOf(withId(R.id.room_list))).perform(doubleClick());

        onView(withText("POSEIDON"))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(allOf(withId(R.id.room_list))).check(matches(withText("POSEIDON")));

        //Saisie du sujet
        String lText = "Première réunion";

        onView(allOf(withId(R.id.meeting_topic_et)))
                .perform(replaceText(lText))
                .check(matches(withText(lText)));

        //Saisie de la date du lendemain
        Calendar lCalDate = Calendar.getInstance(Locale.FRANCE);
        DateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        lCalDate.add(Calendar.DATE,1);
        lCalDate.set(Calendar.MINUTE,00);
        lCalDate.set(Calendar.SECOND,0);
        lCalDate.set(Calendar.MILLISECOND,0);

        onView(withId(R.id.meeting_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        lCalDate.get(Calendar.YEAR),
                        lCalDate.get(Calendar.MONTH) + 1,
                        lCalDate.get(Calendar.DAY_OF_MONTH)));
        onView(withText(android.R.string.ok)).perform(click());
        onView(allOf(withId(R.id.meeting_date_et)))
                .check(matches(withText(lDateFormat.format(lCalDate.getTime()))));


        //Saisie de l'heure de début de la réunion
        SimpleDateFormat lSimpleTimeFormat = new SimpleDateFormat("HH:mm", Locale.FRENCH);
        onView(withId(R.id.meeting_start_et)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(
                        lCalDate.get(Calendar.HOUR_OF_DAY),
                        lCalDate.get(Calendar.MINUTE)));
        onView(withText(android.R.string.ok)).perform(click());
        onView(allOf(withId(R.id.meeting_start_et)))
                .check(matches(withText(lSimpleTimeFormat.format(lCalDate.getTime()))));

        //Saisie de l'heure de fin de la réunion
        lCalDate.add(Calendar.HOUR_OF_DAY ,1);
        onView(withId(R.id.meeting_end_et)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(
                        lCalDate.get(Calendar.HOUR_OF_DAY) ,
                        lCalDate.get(Calendar.MINUTE)));
        onView(withText(android.R.string.ok)).perform(click());
        onView(allOf(withId(R.id.meeting_end_et)))
                .check(matches(withText(lSimpleTimeFormat.format(lCalDate.getTime()))));

        //Saisie d'une adresse email
        lText = "toto@gmail.com";
        ViewInteraction lEmailET = onView(allOf(withId(R.id.email_address_et)));
        lEmailET.perform(replaceText(lText))
            .perform(scrollTo(), click())
            .perform(pressKey(KEYCODE_ENTER))
            .check(matches(withText("")));

        //On vérifie que l'adresse email s'est ajoutée au ChipGroup
        onView(withId(R.id.email_group_cg)).check(matchesChipTextAtPosition(0, lText));

        //Saisie d'une deuxième adresse email
        lText = "titi@gmail.com";
        onView(allOf(withId(R.id.email_address_et)))
                .perform(replaceText(lText))
                .perform(scrollTo(), click())
                .perform(pressKey(KEYCODE_ENTER))
                .check(matches(withText("")));

        //On vérifie que l'adresse email s'est ajoutée au ChipGroup
        onView(withId(R.id.email_group_cg)).check(matchesChipTextAtPosition(1, lText));

        //On enregistre la réunion
        onView(allOf(withId(R.id.btn_save))).perform(click());

        //On vérifie que la liste de réunion est affichée
        onView(allOf(withId(R.id.activity_list_rv))).check(matches(isDisplayed()));

        //Contrôle que la liste à un item
        onView(withId(R.id.activity_list_rv)).check(withItemCount(1));

        onView(withText(R.string.action_add_meeting))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test //OK
    public void givenNewMeeting_whenClickCancelButton_thenNoItemAddedInList() {
        //Contrôle que la liste est vide
        onView(withId(R.id.activity_list_rv)).check(withItemCount(0));

        //Ajout une nouvelle réunion
        onView(withId(R.id.add_fab)).perform(click());

        onView(allOf(withId(R.id.add_fragment_layout))).check(matches(isDisplayed()));

        //Saisie de la salle de réunion
        onView(allOf(withId(R.id.room_list))).perform(doubleClick());

        onView(withText("POSEIDON"))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(allOf(withId(R.id.room_list))).check(matches(withText("POSEIDON")));

        //On annule la saisie de la réunion
        onView(allOf(withId(R.id.btn_cancel))).perform(click());

        //On vérifie que la liste de réunion est affichée
        onView(allOf(withId(R.id.activity_list_rv))).check(matches(isDisplayed()));

        //Contrôle que la liste est vide
        onView(withId(R.id.activity_list_rv)).check(withItemCount(0));

    }

    @Test //OK
    public void givenItem_whenClickOnItem_thenDisplayDetail() {
        int idItemToTest = 0;

        mApi.addFakeMeeting();

        onView(allOf(withId(R.id.activity_list_rv), isDisplayed()))
                .perform(actionOnItemAtPosition(idItemToTest, click()));

        onView(allOf(withId(R.id.detail_fragment_layout))).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.room_name),isDisplayed()))
                .check(matches(withText(mApi.getMeetingSelected().getRoom().getNameRoom())));
    }

    @Test //OK
    public void givenItem_whenClickDeleteAction_thenRemoveItem() {

        //Chargement de fausses réunions
        mApi.addFakeMeeting();

        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT));

        onView(withId(R.id.activity_list_rv))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction()));

        onView(allOf(withText(R.string.msg_delete_meeting))).check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test //KO
    public void given2Meeting_whenTextEllipsized_thenSucces() {
        mApi.addFakeMeeting();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(allOf(withId(R.id.activity_list_rv))).check(matches(isDisplayed()));
        onView(withId(R.id.activity_list_rv)).check(withItemCount(2));

        List<Meeting> lMeetings = mApi.getMeetings();

        System.out.println("PARTICIPANTS :" + lMeetings.get(0).toStringParticipants());

        onView(allOf(withId(R.id.item_participant), withText(startsWith("loki"))))
                .check(matches(withText(lMeetings.get(0).toStringParticipants())));

        onView(allOf(withId(R.id.item_participant), withText(startsWith("loki"))))
                .check(matches(hasEllipsizedText()));
    }

    @Test //KO
    public void given10Meeting_whenFilterByRoom_thenShow2MeetingWithSuccess () throws MeetingApiServiceException {


        mApi.addFakeValidMeetingsLongList();

        //Contrôle que la liste est vide
        onView(withId(R.id.activity_list_rv)).check(withItemCount(10));


        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


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

    @Test //KO
    public void given10Meeting_whenFilterByDate_thenShowMeetingWithSameDate () throws MeetingApiServiceException {
        mApi.addFakeValidMeetingsLongList();

        //Contrôle que la liste est vide
        onView(withId(R.id.activity_list_rv)).check(withItemCount(10));

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

    @Test //KO
    public void given10Meeting_whenResetFilter_thenRemoveAllItems () throws MeetingApiServiceException {
        mApi.addFakeValidMeetingsLongList();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.activity_list_rv)).check(withItemCount(10));


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

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


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

        onView(withId(R.id.activity_list_rv)).check(withItemCount(10));



    }


}
