package com.ocr.mareu.utils;

import android.content.Context;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.ocr.mareu.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Florence LE BOURNOT on 23/01/2020
 */
public class Validation {

    public static final String CST_EMAIL = "EMAIL";
    public static final String CST_TOPIC = "TOPIC";
    public static final String CST_DATETIME = "DATETIME";
    public static final String CST_ROOM = "ROOM";


    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(

            "^[a-zA-Z0-9À-ÿ_!#$%&'*+/=?`{|}~^.-]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
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
            if (!EMAIL_ADDRESS_PATTERN.matcher(pValue).matches()){

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

    /**
     * Affiche le message d'erreur sur le textinput correspondant pour les heures de réunion
     * @param pStart : textinputlayout : textinputlayout concerné par l'erreur
     * @param pMessageStart : string : message d'erreur rattaché à l'heure de début
     * @param pEnd : textinputlayout : textinputlayout concerné par l'erreur
     * @param pMessageEnd : string : message d'erreur rattaché à l'heure de fin
     * @return : boolean : indicateur si une des zones est en erreur
     */
    public static boolean errorMessageDateTimeToShow (TextInputLayout pStart, String pMessageStart, TextInputLayout pEnd, String pMessageEnd) {
        boolean isValid = true;
        if (!pMessageStart.equals("") ) {
            pStart.setError(pMessageStart);
            isValid =  false;
        }
        if (!pMessageEnd.equals("")) {
            pEnd.setError(pMessageEnd);
            isValid = false;
        }
        return isValid;
    }
    /**
     * Validation du contenu d'une zone de type TextInputLayout contenant une date ou une heure
     * ou affichage du message d'erreur
     * @param pContext : context : context
     * @return : List<String> : indicateur si la zone est valide ou non
     */
    public static List<String> validationDateTime (Context pContext, Calendar pStartSaved, Calendar pEndSaved)  {
        Calendar lCalendar = Calendar.getInstance() ;
        List<String> lReturn = new ArrayList<>();

        if (pStartSaved.before(lCalendar)) {
            lReturn.add(pContext.getString(R.string.err_start_before_now));
        } else {
            lReturn.add("");
        }
        if (pStartSaved.after(pEndSaved)) {
            lReturn.add(pContext.getString(R.string.err_end_before_start));
        } else {
            lReturn.add("");
        }
        return lReturn;
    }

    /**
     * Transforme les items du ChipGroup en en chaîne de caractères
     * @param pEmailGroup : chipgroup : items des participants
     * @return : string : liste des participants
     */
    public static String transformChipGroupInString(ChipGroup pEmailGroup) {
        int lNbPart = pEmailGroup.getChildCount();
        List<String> lParts = new ArrayList<>();
        if (lNbPart == 0) {
            return "";
        } else {
            for (int i = 0; i < lNbPart; i++) {
                Chip lChildEmail = (Chip) pEmailGroup.getChildAt(i);
                String lEmail = lChildEmail.getText().toString();
                lParts.add(lEmail);
            }
            return GsonTransformer.getGsonToString(lParts);
        }
    }

    /**
     * Validation du contenu  de la liste des particpants et préparation de la liste pour l'ajout dans Meeting
     * ou affichage du message d'erreur
     * @param pContext : context : context
     * @param pParts : string : liste des participants
     * @return : list : liste des particiants préparés pour l'ajout dans Meeting
     */
    public static String validationParticipants (Context pContext,String pParts) {

        if (pParts.isEmpty()) {
            return pContext.getString(R.string.err_list_participants);
        } else {
            return pParts;
        }
    }
}
