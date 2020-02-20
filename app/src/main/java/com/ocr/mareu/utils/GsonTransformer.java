package com.ocr.mareu.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 13/02/2020
 */
public class GsonTransformer {

    public static String getGsonToString (List<String> pArrayList) {
        return new Gson().toJson(pArrayList);
    }

    public static List<String> getGsonToListString (String pString) {
        //Utilisation du Type uniquement quand il s'agît d'un ArrayList
        Type lListType = new TypeToken<List<String>>() {}.getType();

        return new Gson().fromJson(pString, lListType);
    }

}
