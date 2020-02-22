package com.ocr.mareu.ui.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.ocr.mareu.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest3 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest3() {
        ViewInteraction viewGroup = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.activity_list_rv),
                                childAtPosition(
                                        withId(R.id.frame_list),
                                        0)),
                        1),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.item_description), withText("ARES - 10:00 - Sujet2"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_list_rv),
                                        1),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("ARES - 10:00 - Sujet2")));

        ViewInteraction constraintLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.activity_list_rv),
                                childAtPosition(
                                        withId(R.id.frame_list),
                                        0)),
                        1),
                        isDisplayed()));
        constraintLayout.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.room_name), withText("ARES"),
                        childAtPosition(
                                allOf(withId(R.id.detail_fragment_layout),
                                        childAtPosition(
                                                withId(R.id.frame_list),
                                                0)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("ARES")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.info_topic), withText("Sujet2"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("Sujet2")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.info_date_time), withText("samedi 15 août 2020\n10:00 - 11:00"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("samedi 15 août 2020 10:00 - 11:00")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.item_participant_rv), withText("toto@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_part),
                                        0),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("toto@gmail.com")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.item_participant_rv), withText("titi@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_part),
                                        1),
                                0),
                        isDisplayed()));
        textView6.check(matches(withText("titi@gmail.com")));

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.main_layout),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());
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
