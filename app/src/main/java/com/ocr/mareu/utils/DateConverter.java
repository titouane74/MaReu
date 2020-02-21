package com.ocr.mareu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Locale;

/**
 * Created by Florence LE BOURNOT on 06/02/2020
 */
public class DateConverter {

    /**
     * Convertit une date ou une date heure de type string en calendar
     * @param pFormat : string : format à appliquer
     * @param pDate : string : date ou heure à transformer
     * @return : calendar
     * @throws ParseException
     */
    public static Calendar convertDateTimeStringToCalendar(String pFormat,String pDate)  {
        SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(pFormat, Locale.FRENCH);
        Calendar lCalendar  = Calendar.getInstance();
        try {
            lCalendar.setTime(lSimpleDateFormat.parse(pDate));
        } catch (ParseException pE) {
            pE.printStackTrace();
        }
        return lCalendar;
    }
}
