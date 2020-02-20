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

import static com.ocr.mareu.di.DI.sMeetingApiService;

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
     * @param pTextInputLayout : textinputlayout : textinputlayout qui reçoit le message d'erreur
     * @return : boolean : indicateur si la zone est valide ou non
     */
    public static boolean validationTextInputLayout(Context pContext, String pObjectToValide, TextInputLayout pTextInputLayout) {
        boolean isValid = true;

        String lValue = Objects.requireNonNull(pTextInputLayout.getEditText()).getText().toString().trim();
        pTextInputLayout.setError(null);

        if (lValue.isEmpty()  && pObjectToValide != CST_EMAIL) {
            pTextInputLayout.setError(pContext.getString(R.string.err_empty_field));
            isValid = false;
        } else if (pObjectToValide == CST_EMAIL) {
            if (!Patterns.EMAIL_ADDRESS.matcher(lValue).matches()){
                isValid = errorMessage(pTextInputLayout,pContext.getString(R.string.err_invalid_email_address));
            }
        } else if (pObjectToValide == CST_TOPIC) {
            if (lValue.length() > 40) {
                isValid = errorMessage(pTextInputLayout, pContext.getString(R.string.err_topic_length));
            }
        }
        return isValid;
    }

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
    public static boolean errorMessage (TextInputLayout pTextInputLayout, String pMessage) {
        pTextInputLayout.setError(pMessage);
        return false ;
    }
    public static void errorMessageToShow (TextInputLayout pTextInputLayout, String pMessage) {
        pTextInputLayout.setError(pMessage);
    }

    /**
     * Validation du contenu d'une zone de type TextInputLayout contenant une date ou une heure
     * ou affichage du message d'erreur
     * @param pContext : context : context
     * @param pStart : textinpulayout : textinputlayout : textinputlayout pouvant recevoir le message d'erreur
     * @param pEnd : textinputlayout : textinputlayout pouvant recevoir le message d'erreur
     * @return : boolean : indicateur si la zone est valide ou non
     */
    public static boolean validationDateTime (Context pContext, TextInputLayout pStart, TextInputLayout pEnd,
    Calendar pStartSaved, Calendar pEndSaved)  {

        Calendar lCalendar = Calendar.getInstance() ;
        boolean isValid = true;

        Calendar lStart = sMeetingApiService.getStartMeeting();
        Calendar lEnd = sMeetingApiService.getEndMeeting();

        if (pStartSaved.before(lCalendar)) {
            pStart.setError(pContext.getString(R.string.err_start_before_now));
            isValid = false;
        }
        if (pStartSaved.after(pEndSaved)) {
            pEnd.setError(pContext.getString(R.string.err_end_before_start));
            isValid = false;
        }
        return isValid;
    }

    /**
     * Validation du contenu  de la liste des particpants et préparation de la liste pour l'ajout dans Meeting
     * ou affichage du message d'erreur
     * @param pContext : context : context
     * @param pEmail : textinpulayout : textinputlayout : textinputlayout affichant le message d'erreur
     * @param pEmailGroup : chipgroup : liste des participants
     * @return : list : liste des particiants préparés pour l'ajout dans Meeting
     */
    public static List<String> validationParticipants (Context pContext, TextInputLayout pEmail,ChipGroup pEmailGroup) {

        int lNbPart = pEmailGroup.getChildCount();
        List<String> lParts = new ArrayList<>();

        if (lNbPart == 0) {
            pEmail.setError(pContext.getString(R.string.err_list_participants));
        } else {
            for (int i = 0; i < lNbPart; i++) {
                Chip lChildEmail = (Chip) pEmailGroup.getChildAt(i);
                String lEmail = lChildEmail.getText().toString();
                lParts.add(lEmail);
                }
            }
        return lParts;
    }
}
