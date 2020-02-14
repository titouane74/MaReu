package com.ocr.mareu.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 13/02/2020
 */
public class GsonTransformer {

    /**
     * Trannsforme un ArrayList en String avec la méthode Gson
     * @param pArrayList : ArrayList : Liste des utilisateurs
     * @return : String : retourne la liste des utilisateurs au format String
     */
    public static String getGsonToString (Meeting pArrayList) {
        return new Gson().toJson(pArrayList);
    }

    /**
     * Transforme un String en ArrayList avec la méthode Gson
     * @param pString : String : châine à transformer en ArrayList
     * @return : ArrayList : retourne la chaîne au format ArrayList
     */
    public static Meeting getGsonToMeeting (String pString) {
        //Utilisation du Type uniquement quand il s'agît d'un ArrayList
        Type lListType = new TypeToken<Meeting>() {}.getType();

        return new Gson().fromJson(pString,lListType);
    }

    public static String getGsonListRoomsToString (List<Room> pArrayList) {
        return new Gson().toJson(pArrayList);
    }

    public static ArrayList<Room> getGsonToListRooms (String pString) {
        //Utilisation du Type uniquement quand il s'agît d'un ArrayList
        Type lListType = new TypeToken<ArrayList<Room>>() {}.getType();
        return new Gson().fromJson(pString,lListType);
    }
}
