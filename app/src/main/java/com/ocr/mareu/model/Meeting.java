package com.ocr.mareu.model;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 16/01/2020
 */
public class Meeting  {

    private static Integer mMaxId = 0;

    private Integer mId;
    private Room mRoom;
    private String mTopic;
    private Calendar mDate;
    private Calendar mStart;
    private Calendar mEnd;
    private List<String> mParticpants;

    /**
     * Constructor d'une réunion
     * @param pRoom : objet : salle de réunion
     * @param pTopic : strinf : sujet de la réunion
     * @param pDate : calendar : date
     * @param pStart : calendar : date et heure de début
     * @param pEnd : calendar : date et heure de fin
     * @param pParticpants : list : liste e participants
     */
    public Meeting(Room pRoom, String pTopic,Calendar pDate, Calendar pStart, Calendar pEnd, List<String> pParticpants) {
        mId = ++mMaxId;
        mRoom = pRoom;
        mTopic = pTopic;
        mDate = pDate;
        mStart = pStart;
        mEnd = pEnd;
        mParticpants = pParticpants;
    }

    /**
     * Récupération de l'id de la réunion
     * @return : integer : id
     */
    public Integer getId() { return mId; }

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

}
