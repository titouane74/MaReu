package com.ocr.mareu.ui.activities;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.ocr.mareu.R;
import com.ocr.mareu.di.DI;
import com.ocr.mareu.service.MeetingApiService;
import com.ocr.mareu.actions.DeleteViewAction;
import com.ocr.mareu.matchers.ToastMatcher;
import com.ocr.mareu.utilstest.InsertGraphicData;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import static android.view.KeyEvent.KEYCODE_ENTER;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.ocr.mareu.assertion.ChipGroupNoAssertion.matchesChipGroupEmpty;
import static com.ocr.mareu.assertion.ChipValueAssertion.matchesChipTextAtPosition;
import static com.ocr.mareu.assertion.RecyclerViewItemCountAssertion.withItemCount;
import static com.ocr.mareu.assertion.TextInputLayoutErrorAssertion.matchesErrorText;
import static com.ocr.mareu.assertion.TextInputLayoutNoErrorAssertion.matchesNoErrorText;
import static com.ocr.mareu.utilstest.FakeDateTime.generateDateTimeFromNow;
import static com.ocr.mareu.utilstest.FakeDateTime.getSimpleDateFormat;
import static com.ocr.mareu.utilstest.FakeDateTime.getSimpleDateOrTimeFormat;
import static com.ocr.mareu.utilstest.InsertGraphicData.addDate;
import static com.ocr.mareu.utilstest.InsertGraphicData.addFakeMeeting;
import static com.ocr.mareu.utilstest.InsertGraphicData.addTime;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


/**
 * Created by Florence LE BOURNOT on 22/02/2020
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private MeetingApiService mApi = null;
    private MainActivity mActivity = null;
    private Calendar mNow ;
    private int mDiffMonth ;
    private int mDiffDay ;
    private int mDiffHour ;
    private Calendar mCalDate;
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

        mNow = Calendar.getInstance(Locale.FRANCE);
        mCalDate = (Calendar) mNow.clone();
        mDiffDay = 1;
        mDiffHour = 2;
        mDiffMonth = 0;
    }

    @After
    public void tearDown() {
        mApi = DI.getMeetingApiServiceNewInstance();
        mNow = Calendar.getInstance(Locale.FRANCE);
    }

    @Test //OK
    public void givenItem_whenClickOnItem_thenDisplayDetail() {
        int idItemToTest = 0;

        addFakeMeeting("ARES", "La guerre des boutons", mCalDate, 2,2,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(allOf(withId(R.id.activity_list_rv), isDisplayed()))
                .perform(actionOnItemAtPosition(idItemToTest, click()));

        onView(allOf(withId(R.id.detail_fragment_layout))).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.room_name),isDisplayed()))
                .check(matches(withText(mApi.getMeetingSelected().getRoom().getNameRoom())));
    }

    @Test //OK
    public void givenItem_whenClickAndValidDeleteAction_thenRemoveItem() {

        addFakeMeeting("ARES", "La guerre des boutons", mCalDate, 2,2,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        addFakeMeeting("PLUTON", "La guerre des étoiles", mCalDate,5,5,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(allOf(withId(R.id.activity_list_rv),isDisplayed())).check(withItemCount(ITEMS_COUNT));

        onView(withId(R.id.activity_list_rv))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction()));

        onView(allOf(withText(R.string.msg_delete_meeting))).check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test //OK - Mentorat - voir si test utilie
    public void givenItem_whenClickAndNoValidDeleteAction_thenRemoveItem() {

        addFakeMeeting("ARES", "La guerre des boutons", mCalDate, 2,2,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        addFakeMeeting("PLUTON", "La guerre des étoiles", mCalDate,5,5,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(allOf(withId(R.id.activity_list_rv),isDisplayed())).check(withItemCount(ITEMS_COUNT));

        //On exécute le DeleteActionView qui appelle la demande de confirmation de suppression
        onView(withId(R.id.activity_list_rv))
                .perform(actionOnItemAtPosition(0, new DeleteViewAction()));

        //On s'assure que le message s'est ouvert
        onView(allOf(withText(R.string.msg_delete_meeting))).check(matches(isDisplayed()));

        //On clique sur annuler pour refuser la suppression
        onView(withId(android.R.id.button2)).perform(click());

        //On met -1 car graphiquement on a déjà supprimé l'item
        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT - 1));
    }

    @Test //OK
    public void givenNothing_whenOpen_thenListIsEmpty() {
        onView(withId(R.id.activity_list_rv)).check(withItemCount(0));
    }

    @Test //OK
    public void givenNewMeeting_whenAddMeeting_thenAddItemInList() {

        //Contrôle que la liste est vide
        onView(withId(R.id.activity_list_rv)).check(withItemCount(0));

        //Ajout une nouvelle réunion
        onView(withId(R.id.add_fab)).perform(click());

        onView(allOf(withId(R.id.add_fragment_layout))).check(matches(isDisplayed()));

        //Saisie de la salle de réunion
        onView(allOf(withId(R.id.room_list))).perform(click());

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
        mDiffDay = 1;
        mCalDate = generateDateTimeFromNow(mCalDate, mDiffMonth, mDiffDay, mDiffHour);

        onView(withId(R.id.meeting_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        mCalDate.get(Calendar.YEAR),
                        mCalDate.get(Calendar.MONTH + mDiffMonth) ,
                        mCalDate.get(Calendar.DAY_OF_MONTH + mDiffDay)));
        onView(withText(android.R.string.ok)).perform(click());
        onView(allOf(withId(R.id.meeting_date_et)))
                .check(matches(withText(getSimpleDateFormat(mCalDate))));


        //Saisie de l'heure de début de la réunion
        onView(withId(R.id.meeting_start_et)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(
                        mCalDate.get(Calendar.HOUR_OF_DAY),
                        mCalDate.get(Calendar.MINUTE)));
        onView(withText(android.R.string.ok)).perform(click());
        onView(allOf(withId(R.id.meeting_start_et)))
                .check(matches(withText(getSimpleDateOrTimeFormat(mCalDate))));

        //Saisie de l'heure de fin de la réunion
        mDiffHour = 1;
        mCalDate.add(Calendar.HOUR_OF_DAY , mDiffHour);
        onView(withId(R.id.meeting_end_et)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(
                        mCalDate.get(Calendar.HOUR_OF_DAY) ,
                        mCalDate.get(Calendar.MINUTE)));
        onView(withText(android.R.string.ok)).perform(click());
        onView(allOf(withId(R.id.meeting_end_et)))
                .check(matches(withText(getSimpleDateOrTimeFormat(mCalDate))));

        //Saisie d'une adresse email
        lText = "toto@gmail.com";
        ViewInteraction lEmailET = onView(allOf(withId(R.id.email_address_et)));
        lEmailET.perform(replaceText(lText))
            .perform(scrollTo(), click())
            .perform(pressKey(KEYCODE_ENTER))
            .check(matches(withText("")));
        onView(allOf(withId(R.id.email_address))).check(matchesNoErrorText());

        //On vérifie que l'adresse email s'est ajoutée au ChipGroup
        onView(withId(R.id.email_group_cg)).check(matchesChipTextAtPosition(0, lText));

        //Saisie d'une deuxième adresse email
        lText = "titi@gmail.com";
        onView(allOf(withId(R.id.email_address_et)))
                .perform(replaceText(lText))
                .perform(scrollTo(), click())
                .perform(pressKey(KEYCODE_ENTER))
                .check(matches(withText("")));
        onView(allOf(withId(R.id.email_address))).check(matchesNoErrorText());

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
    public void given2NewMeeting_whenSave_thenAddItemInList() {
        onView(withId(R.id.activity_list_rv)).check(withItemCount(0));

        addFakeMeeting("ARES", "La guerre des boutons", mCalDate, 2,2,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        addFakeMeeting("PLUTON", "La guerre des étoiles", mCalDate,5,5,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(allOf(withId(R.id.activity_list_rv))).check(matches(isDisplayed()));

        onView(withId(R.id.activity_list_rv)).check(withItemCount(2));
    }

    @Test //OK
    public void givenNewMeeting_whenClickCancelButton_thenNoItemAddedInList() {
        onView(withId(R.id.activity_list_rv)).check(withItemCount(0));

        //Ajout une nouvelle réunion
        onView(withId(R.id.add_fab)).perform(click());

        onView(allOf(withId(R.id.add_fragment_layout))).check(matches(isDisplayed()));

        //Saisie du sujet de la réunion
        String lText = "Première réunion";

        onView(allOf(withId(R.id.meeting_topic_et)))
                .perform(replaceText(lText))
                .check(matches(withText(lText)));

        //On annule la saisie de la réunion
        onView(allOf(withId(R.id.btn_cancel))).perform(click());

        //On vérifie que la liste de réunion est affichée
        onView(allOf(withId(R.id.activity_list_rv))).check(matches(isDisplayed()));

        //Contrôle que la liste est vide
        onView(withId(R.id.activity_list_rv)).check(withItemCount(0));

    }

    @Test //OK
    public void givenNothing_whenSave_thenFail() {
        //Ajout une nouvelle réunion
        onView(withId(R.id.add_fab)).perform(click());

        onView(allOf(withId(R.id.add_fragment_layout))).check(matches(isDisplayed()));

        //On enregistre la réunion
        onView(allOf(withId(R.id.btn_save))).perform(click());

        onView(withId(R.id.room_list_layout))
                .check(matchesErrorText(mActivity.getString(R.string.err_empty_field)));

        onView(withId(R.id.meeting_topic))
                .check(matchesErrorText(mActivity.getString(R.string.err_empty_field)));

        onView(withId(R.id.meeting_date))
                .check(matchesErrorText(mActivity.getString(R.string.err_empty_field)));

        onView(withId(R.id.meeting_start))
                .check(matchesErrorText(mActivity.getString(R.string.err_empty_field)));

        onView(withId(R.id.meeting_end))
                .check(matchesErrorText(mActivity.getString(R.string.err_empty_field)));

        onView(withId(R.id.email_address))
                .check(matchesErrorText(mActivity.getString(R.string.err_list_participants)));

    }

    @Test //OK
    public void givenNothing_whenSave_thenToastImpossibleAdd() {
        //Ajout une nouvelle réunion
        onView(withId(R.id.add_fab)).perform(click());

        onView(allOf(withId(R.id.add_fragment_layout))).check(matches(isDisplayed()));

        //On enregistre la réunion
        onView(allOf(withId(R.id.btn_save))).perform(click());

        onView(withText(R.string.action_add_meeting_missing_field))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test //OK
    public void givenNoDate_whenOnOpening_thenStartAndEndTimeNotEnabled() {
        //Ajout une nouvelle réunion
        onView(withId(R.id.add_fab)).perform(click());

        onView(allOf(withId(R.id.add_fragment_layout))).check(matches(isDisplayed()));

        onView(withId(R.id.meeting_start_et))
                .check(matches(not(isEnabled())));

        onView(withId(R.id.meeting_end_et))
                .check(matches(not(isEnabled())));
    }

    @Test //OK
    public void givenDate_whenAfterUpdate_thenStartAndEndTimeEnabled() {

        //Ajout une nouvelle réunion
        onView(withId(R.id.add_fab)).perform(click());

        onView(allOf(withId(R.id.add_fragment_layout))).check(matches(isDisplayed()));

        mDiffDay = 1;
        mCalDate = generateDateTimeFromNow(mCalDate, mDiffMonth, mDiffDay, mDiffHour);

        onView(withId(R.id.meeting_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        mCalDate.get(Calendar.YEAR),
                        mCalDate.get(Calendar.MONTH + mDiffMonth) ,
                        mCalDate.get(Calendar.DAY_OF_MONTH + mDiffDay)));
        onView(withText(android.R.string.ok)).perform(click());
        onView(allOf(withId(R.id.meeting_date_et)))
                .check(matches(withText(getSimpleDateFormat(mCalDate))));


        onView(withId(R.id.meeting_start_et))
                .check(matches(isEnabled()));

        onView(withId(R.id.meeting_end_et))
                .check(matches(isEnabled()));
    }

    @Test //OK
    public void givenEmailAddress_whenInvalid_thenFail(){
        //Ajout une nouvelle réunion
        onView(withId(R.id.add_fab)).perform(click());

        onView(allOf(withId(R.id.add_fragment_layout))).check(matches(isDisplayed()));

        //Saisie d'une adresse email invalide
        InsertGraphicData.addEmailAddress("toto");
        onView(allOf(withId(R.id.email_address)))
                .check(matchesErrorText(mActivity.getString(R.string.err_invalid_email_address)));

        //On vérifie que l'adresse email s'est pas ajoutée au ChipGroup
        onView(withId(R.id.email_group_cg)).check(matchesChipGroupEmpty());

        InsertGraphicData.addEmailAddress("to>to@777.");
        onView(allOf(withId(R.id.email_address)))
                .check(matchesErrorText(mActivity.getString(R.string.err_invalid_email_address)));

        //On vérifie que l'adresse email s'est pas ajoutée au ChipGroup
        onView(withId(R.id.email_group_cg)).check(matchesChipGroupEmpty());

    }

    @Test //OK
    public void givenTopic_whenMoreThan40Caracters_thenMeesageError() {

        onView(withId(R.id.add_fab)).perform(click());

        onView(allOf(withId(R.id.add_fragment_layout))).check(matches(isDisplayed()));

        //Saisie d'un sujet trop long
        InsertGraphicData.addTopic("Sujet de réunion de plus de quarante caractères impossible");

        onView(allOf(withId(R.id.btn_save))).perform(click());

        onView(allOf(withId(R.id.meeting_topic)))
                .check(matchesErrorText(mActivity.getString(R.string.err_topic_length)));

    }

    @Test //OK
    public void givenDate_whenBeforeNow_thenErrorMessage() {

        onView(withId(R.id.add_fab)).perform(click());

        onView(allOf(withId(R.id.add_fragment_layout))).check(matches(isDisplayed()));

        mDiffDay = -1;
        mCalDate = generateDateTimeFromNow(mCalDate, mDiffMonth, mDiffDay, mDiffHour);

        onView(withId(R.id.meeting_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        mCalDate.get(Calendar.YEAR),
                        mCalDate.get(Calendar.MONTH + mDiffMonth) ,
                        mCalDate.get(Calendar.DAY_OF_MONTH + mDiffDay)));
        onView(withText(android.R.string.ok)).perform(click());

        onView(withId(R.id.meeting_date))
                .check(matchesErrorText(mActivity.getString(R.string.err_anterior_date)));

    }

    @Test //KO - régression de code
    public void givenTimeStart_whenBeforeNow_thenErrorMessage() {

        onView(withId(R.id.add_fab)).perform(click());

        onView(allOf(withId(R.id.add_fragment_layout))).check(matches(isDisplayed()));

        addDate(mCalDate);

        mCalDate.add(Calendar.HOUR_OF_DAY, -2);
        onView(withId(R.id.meeting_start_et)).perform(click());
        addTime(mCalDate);

        onView(allOf(withId(R.id.btn_save))).perform(click());

        onView(withId(R.id.meeting_start))
                .check(matchesErrorText(mActivity.getString(R.string.err_start_before_now)));

    }

    @Test //OK
    public void givenTimeEnd_whenBeforeStart_thenErrorMessage() {

        onView(withId(R.id.add_fab)).perform(click());

        onView(allOf(withId(R.id.add_fragment_layout))).check(matches(isDisplayed()));

        addDate(mCalDate);

        mCalDate.add(Calendar.HOUR_OF_DAY, 4);
        onView(withId(R.id.meeting_start_et)).perform(click());
        addTime(mCalDate);

        mCalDate.add(Calendar.HOUR_OF_DAY, -6);
        onView(withId(R.id.meeting_end_et)).perform(click());
        addTime(mCalDate);

        onView(allOf(withId(R.id.btn_save))).perform(click());

        onView(withId(R.id.meeting_end))
                .check(matchesErrorText(mActivity.getString(R.string.err_end_before_start)));

    }

}
