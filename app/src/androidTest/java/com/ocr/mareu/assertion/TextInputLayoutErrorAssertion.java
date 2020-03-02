package com.ocr.mareu.assertion;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

/**
 * Created by Florence LE BOURNOT on 25/02/2020
 */
public class TextInputLayoutErrorAssertion implements ViewAssertion {

    private final String mExpectedText;

    //Appel méthode pour préparer le check et retourne le résultat
    public static TextInputLayoutErrorAssertion matchesErrorText(String pExpectedText) {
        return new TextInputLayoutErrorAssertion(pExpectedText);
    }

    //Initialise la valeur attendue passée en paramètre
    private TextInputLayoutErrorAssertion(String pExpectedText) {
        mExpectedText = pExpectedText;
    }

    //Réalise la vérification souhaitée
    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        //Récupère le texte du textInputLayout de la vue
        String text;
        try {
            text = Objects.requireNonNull(((TextInputLayout) view).getError()).toString();
        } catch (Exception e) {
            text = "";
        }

        //Fait la vérification
        assertEquals(mExpectedText, text);
    }
}
