package com.ocr.mareu.ui.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.ocr.mareu.R;
import com.ocr.mareu.di.DI;
import com.ocr.mareu.service.MeetingApiService;
import com.ocr.mareu.utils.DeleteViewAction;
import com.ocr.mareu.utils.RoomViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.view.KeyEvent.KEYCODE_ENTER;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.ocr.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;
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

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.ocr.mareu", appContext.getPackageName());
    }

    @Test
    public void givenNothing_whenOpen_thenListIsEmpty() {
        //Contrôle que la liste est vide
        onView(withId(R.id.activity_list_rv)).check(withItemCount(0));
    }

    @Test
    public void givenNewMeeting_whenAddMeeting_thenAddItemInList() {

        //Contrôle que la liste est vide
        onView(withId(R.id.activity_list_rv)).check(withItemCount(0));

        //Ajout une nouvelle réunion
        onView(withId(R.id.add_fab)).perform(click());

        onView(allOf(withId(R.id.add_fragment_layout))).check(matches(isDisplayed()));

        //Saisie de la salle de réunion
        onView(allOf(withId(R.id.room_list))).perform(doubleClick());

        onView(withText("POSEIDON"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        onView(allOf(withId(R.id.room_list))).check(matches(withText("POSEIDON")));

        //Saisie du sujet
        String lText = "Première réunion";

        onView(allOf(withId(R.id.meeting_topic_et)))
                .perform(replaceText(lText))
                .check(matches(withText(lText)));

        //saisie d'une adresse email
        lText = "toto@gmail.com";
        ViewInteraction lEmailET = onView(allOf(withId(R.id.email_address_et)));
        lEmailET.perform(replaceText(lText))
            .perform(scrollTo(), click())
            .perform(pressKey(KEYCODE_ENTER))
            .check(matches(withText("")));



/*
        //Contrôle que la liste à un item
        onView(withId(R.id.activity_list_rv)).check(withItemCount(1));
*/

    }

    @Test
    public void givenItem_whenClickOnItem_thenDisplayDetail() {
        int idItemToTest = 0;

        mApi.addFakeMeeting();

        onView(allOf(withId(R.id.activity_list_rv), isDisplayed()))
                .perform(actionOnItemAtPosition(idItemToTest, click()));

        onView(allOf(withId(R.id.detail_fragment_layout))).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.room_name),isDisplayed()))
                .check(matches(withText(mApi.getMeetingSelected().getRoom().getNameRoom())));
    }

    @Test
    public void givenItem_whenClickDeleteAction_thenRemoveItem() {

        //Chargement de fausses réunions
        mApi.addFakeMeeting();

        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT));

        onView(withId(R.id.activity_list_rv))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction()));

        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT-1));
    }




    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}
