package com.ocr.mareu.utils;

import android.content.Context;
import android.util.Patterns;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.ocr.mareu.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Created by Florence LE BOURNOT on 23/01/2020
 */
public class Validation {

    public static final String CST_EMAIL = "EMAIL";
    public static final String CST_TOPIC = "TOPIC";
    public static final String CST_DATETIME = "DATETIME";
    public static final String CST_ROOM = "ROOM";

    /**
     * Validation du contenu d'une zone de type TextInputLayout ou affichage du message d'erreur
     * @param pContext : context : context
     * @param pObjectToValide : string : indicateur de l'objet à valider (mail ou sujet ...)
     * @return : boolean : indicateur si la zone est valide ou non
     */
    public static String validationText(Context pContext, String pObjectToValide, String pValue) {
        String lTextToReturn = null;

        if (pValue.isEmpty()  && pObjectToValide != CST_EMAIL) {
            lTextToReturn = pContext.getString(R.string.err_empty_field);
        } else if (pObjectToValide == CST_EMAIL) {
            if (!Patterns.EMAIL_ADDRESS.matcher(pValue).matches()){
                lTextToReturn = pContext.getString(R.string.err_invalid_email_address);
            }
        } else if (pObjectToValide == CST_TOPIC) {
            if (pValue.length() > 40) {
                lTextToReturn = pContext.getString(R.string.err_topic_length);
            }
        }
        return lTextToReturn;
    }

    /**
     * Affiche le message d'erreur sur le textinputlayout
     * @param pTextInputLayout : textinputlayout : textinputlayout concerné par l'erreur
     * @param pMessage : string : message d'erreur à afficher
     * @return : boolean : indicateur si la zone est valide ou non
     */
    public static boolean errorMessageToShow (TextInputLayout pTextInputLayout, String pMessage) {
        if (pMessage != null) {
            pTextInputLayout.setError(pMessage);
            return false;
        } else {
            return true;
        }
    }
    public static boolean errorMessageDateTimeToShow (TextInputLayout pStart, String pMessageStart, TextInputLayout pEnd, String pMessageEnd) {
        boolean isValid = true;
        if (pMessageStart != null ) {
            pStart.setError(pMessageStart);
            isValid =  false;
        }
        if (pMessageEnd != null ) {
            pEnd.setError(pMessageEnd);
            isValid = false;
        }
        return isValid;
    }
    /**
     * Validation du contenu d'une zone de type TextInputLayout contenant une date ou une heure
     * ou affichage du message d'erreur
     * @param pContext : context : context
     * @return : boolean : indicateur si la zone est valide ou non
     */
    public static String validationDateTime (Context pContext, Calendar pStartSaved, Calendar pEndSaved)  {
        Calendar lCalendar = Calendar.getInstance() ;
        List<String> lReturn = new ArrayList<>();

        if (pStartSaved.before(lCalendar)) {
            lReturn.add(pContext.getString(R.string.err_start_before_now));
        } else {
            lReturn.add("");
        }
        if (pStartSaved.after(pEndSaved)) {
            lReturn.add("/");
            lReturn.add(pContext.getString(R.string.err_end_before_start));
        } else {
            lReturn.add("");
        }
        return GsonTransformer.getGsonToString(lReturn);
    }


    /**
     * Validation du contenu  de la liste des particpants et préparation de la liste pour l'ajout dans Meeting
     * ou affichage du message d'erreur
     * @param pContext : context : context
     * @param pEmailGroup : chipgroup : liste des participants
     * @return : list : liste des particiants préparés pour l'ajout dans Meeting
     */
    public static String validationParticipants (Context pContext,ChipGroup pEmailGroup) {

        int lNbPart = pEmailGroup.getChildCount();
        List<String> lParts = new ArrayList<>();
        String lReturn;

        if (lNbPart == 0) {
            lReturn = pContext.getString(R.string.err_list_participants);
        } else {
            for (int i = 0; i < lNbPart; i++) {
                Chip lChildEmail = (Chip) pEmailGroup.getChildAt(i);
                String lEmail = lChildEmail.getText().toString();
                lParts.add(lEmail);
                }
            lReturn = GsonTransformer.getGsonToString(lParts);
            }

        return lReturn;
    }
}
