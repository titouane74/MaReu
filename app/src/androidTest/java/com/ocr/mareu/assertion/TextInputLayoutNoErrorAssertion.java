package com.ocr.mareu.assertion;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import com.google.android.material.textfield.TextInputLayout;

import static org.junit.Assert.assertNull;

/**
 * Created by Florence LE BOURNOT on 25/02/2020
 */
public class TextInputLayoutNoErrorAssertion implements ViewAssertion {

    public static TextInputLayoutNoErrorAssertion matchesNoErrorText() {
        return new TextInputLayoutNoErrorAssertion();
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        assertNull(((TextInputLayout) view).getError());
    }

}
