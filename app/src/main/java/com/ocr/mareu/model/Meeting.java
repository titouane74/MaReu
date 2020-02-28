package com.ocr.mareu.model;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 16/01/2020
 */
public class Meeting  {

    private Room mRoom;
    private String mTopic;
    private Calendar mDate;
    private Calendar mStart;
    private Calendar mEnd;
    private List<String> mParticpants;

    /**
     * Constructor d'une réunion
     * @param pRoom : objet : salle de réunion
     * @param pTopic : string : sujet de la réunion
     * @param pDate : calendar : date
     * @param pStart : calendar : date et heure de début
     * @param pEnd : calendar : date et heure de fin
     * @param pParticpants : list : liste e participants
     */
    public Meeting(Room pRoom, String pTopic,Calendar pDate, Calendar pStart, Calendar pEnd, List<String> pParticpants) {
        mRoom = pRoom;
        mTopic = pTopic;
        mDate = pDate;
        mStart = pStart;
        mEnd = pEnd;
        mParticpants = pParticpants;
    }

    /**
     * Récupération du sujet de la réunion
     * @return : string : sujet
     */
    public String getTopic() { return mTopic; }

    /**
     * Récupération de la date
     * @return : calendar : date
     */
    public Calendar getDate() { return mDate; }

    /**
     * récupération de la date et heure de début
     * @return : calendar : date et heure de début
     */
    public Calendar getStart() { return mStart; }

    /**
     * Récupération de la date et heure de fin
     * @return : calendar : date et heure de fin
     */
    public Calendar getEnd() { return mEnd; }

    /**
     * Récupération de la liste des participants
     * @return : list : liste de participants
     */
    public List<String> getParticpants() { return mParticpants; }

    /**
     * Récupération de la salle de réunion
     * @return : objet : salle de réunion
     */
    public Room getRoom() { return mRoom; }

    /**
     * Récupère la description de la réunion contenant :
     * le nom de la salle, l'heure de début et le sujet sous forme de chaîne de caractères
     * pour l'affichage dans la liste de réunion
     * @return : string : description de la réunion
     */
    public String toStringDescription() {

         return TextUtils.join(" - ", Arrays.asList(
                getRoom().getNameRoom(),
                new SimpleDateFormat("HH:mm").format(getStart().getTime()),
                getTopic()));
    }

    /**
     * Récupération de la liste des participants sous forme de chaîne de caractères
     * pour l'affichage dans la liste des réunions
     * @return : string : liste des participants
     */
    public String toStringParticipants() {
       return TextUtils.join(", ", getParticpants());
    }
}
