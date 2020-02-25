package com.ocr.mareu.assertion;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ChipValueAssertion implements ViewAssertion {
    private final String mExpectedText;
    private final int mAtPosition;

    private ChipValueAssertion(int pAtPosition, String pExpectedText) {
        mAtPosition = pAtPosition ;
        mExpectedText = pExpectedText;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        int lCount = ((ChipGroup) view).getChildCount();

        assertNotEquals(0, lCount);
        assert(mAtPosition >= 0 && mAtPosition <= lCount);

        Chip lTmpEmail = (Chip) ((ChipGroup) view).getChildAt(mAtPosition);
        String lEmail = lTmpEmail.getText().toString();

        assertEquals(mExpectedText, lEmail);
    }

    public static ChipValueAssertion matchesChipTextAtPosition(int pAtPosition, String pExpectedText) {
        return new ChipValueAssertion(pAtPosition, pExpectedText);
    }
}
