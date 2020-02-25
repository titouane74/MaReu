package com.ocr.mareu.utilstest;

import androidx.test.espresso.ViewInteraction;

import com.ocr.mareu.R;
import com.ocr.mareu.ui.activities.MainActivity;

import static android.view.KeyEvent.KEYCODE_ENTER;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Florence LE BOURNOT on 25/02/2020
 */
public class InsertGraphicData {


    public static void addInvalidEmailAddress(String pText) {

    ViewInteraction lEmailET = onView(allOf(withId(R.id.email_address_et)));
        lEmailET.perform(replaceText(pText))
        .perform(scrollTo(),click())
        .perform(pressKey(KEYCODE_ENTER));
    }


    public static void insertFakeMeting(String pDescription, String pParticipants) {
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
