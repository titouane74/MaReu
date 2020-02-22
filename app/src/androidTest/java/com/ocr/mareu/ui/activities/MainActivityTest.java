package com.ocr.mareu.ui.activities;

import android.content.Context;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.ocr.mareu.R;
import com.ocr.mareu.service.FakeMeetingApiService;
import com.ocr.mareu.service.MeetingApiService;
import com.ocr.mareu.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.ocr.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.not;
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

        mApi = new FakeMeetingApiService();
        assertNotNull(mApi);
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


/*
        //Contrôle qie la liste à un item
        onView(withId(R.id.activity_list_rv)).check(withItemCount(1));
*/

    }

    @Test
    public void givenItem_whenClickOnItem_thenDisplayDetail() {
        int idItemToTest = 0;
        //Click sur un item de la liste des voisins
//        onView(allOf(withId(R.id.activity_list_rv), isDisplayed()))
//                .perform(actionOnItemAtPosition(idItemToTest, click()));

        //Ouverture du détail du voisin
//        onView(withId(R.id.detail_fragment_layout).check(matches(isDisplayed()));

        //Compare que le nom de la salle affiché est celui de l'item sélectionné
//        onView(withId(R.id.room_name))
//                .check(matches(withText(mApi.getMeetingSelected().getRoom().getNameRoom())));

    }

    @Test
    public void givenItem_whenClickDeleteAction_thenRemoveItem() {

        mApi.addFakeMeeting();

        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT));

        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.activity_list_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));

        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT-1));

    }
}
