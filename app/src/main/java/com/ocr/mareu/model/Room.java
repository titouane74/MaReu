package com.ocr.mareu.model;

import androidx.annotation.NonNull;

/**
 * Created by Florence LE BOURNOT on 17/01/2020
 */
public class Room  {

    private String mNameRoom;
    private Integer mColorRoom;

    /**
     * Constructor d'une salle de réunion
     * @param pNameRoom : string : nom de la salle
     * @param pColorRoom : integer : couleur de la salle
     */
    public Room(String pNameRoom, Integer pColorRoom) {
        mNameRoom = pNameRoom;
        mColorRoom = pColorRoom;
    }

    /**
     * Récupération du nom de la salle de réunion
     * @return : string : nom de la salle
     */
    public String getNameRoom() { return mNameRoom; }

    /**
     * Récupère la couleur de la salle
     * @return : integer : couleur de la salla
     */
    public Integer getColorRoom() { return mColorRoom; }


    /**
     * Récupère le nom de la salle de réunion sous forme de chaîne de caractères
     * @return
     */
    @NonNull
    @Override
    public String toString() {
        return mNameRoom;
    }

}
