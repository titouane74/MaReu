package com.ocr.mareu.ui.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.ocr.mareu.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() {
        ViewInteraction imageButton = onView(
                allOf(withId(R.id.add_fab),
                        childAtPosition(
                                allOf(withId(R.id.main_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.add_fab),
                        childAtPosition(
                                allOf(withId(R.id.main_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatAutoCompleteTextView = onView(
                allOf(withId(R.id.room_list),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.room_list_layout),
                                        0),
                                0)));
        appCompatAutoCompleteTextView.perform(scrollTo(), click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.room_list), withText("Sélectionnez une salle de réunion"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.room_list_layout),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("Sélectionnez une salle de réunion")));

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(7);
        appCompatTextView.perform(click());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.room_list), withText("POSEIDON"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.room_list_layout),
                                        0),
                                0),
                        isDisplayed()));
        editText3.check(matches(withText("POSEIDON")));

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.meeting_topic_et),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meeting_topic),
                                        0),
                                0)));
        textInputEditText.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

/*
        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.meeting_topic_et), withText("test"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meeting_topic),
                                        0),
                                0)));

        textInputEditText2.perform(pressImeActionButton());
*/
        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.email_address_et),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_address),
                                        0),
                                0)));
        textInputEditText3.perform(scrollTo(), replaceText("toto@gmail.com"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.email_address_et), withText("toto@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_address),
                                        0),
                                0)));
        textInputEditText4.perform(scrollTo(), click());


        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.email_address_et), withText("toto@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_address),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText6.perform(closeSoftKeyboard());

        //pressBack();

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.meeting_date_et),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meeting_date),
                                        0),
                                0)));
        textInputEditText8.perform(scrollTo(), click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.meeting_start_et),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meeting_start),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText9.perform(click());

         ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        appCompatButton2.perform(click());

/*        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        appCompatButton3.perform(click());*/

        ViewInteraction textInputEditText11 = onView(
                allOf(withId(R.id.meeting_end_et),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meeting_end),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText11.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.meeting_topic_et), withText("test"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meeting_topic),
                                        0),
                                0),
                        isDisplayed()));
        editText4.check(matches(withText("test")));

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.meeting_date_et), withText("26/02/2020"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meeting_date),
                                        0),
                                0),
                        isDisplayed()));
        editText5.check(matches(withText("26/02/2020")));

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.meeting_start_et), withText("09:00"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meeting_start),
                                        0),
                                0),
                        isDisplayed()));
        editText6.check(matches(withText("09:00")));

        ViewInteraction editText7 = onView(
                allOf(withId(R.id.meeting_end_et), withText("11:00"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.meeting_end),
                                        0),
                                0),
                        isDisplayed()));
        editText7.check(matches(withText("11:00")));

        ViewInteraction chip = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.email_group_cg),
                                childAtPosition(
                                        withId(R.id.email_address),
                                        2)),
                        0),
                        isDisplayed()));
        chip.check(matches(isDisplayed()));

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
