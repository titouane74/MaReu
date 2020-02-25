package com.ocr.mareu.utilstest;

import android.graphics.Color;

import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.ocr.mareu.service.FakeMeetingApiService.CST_FORMAT_DATE;
import static com.ocr.mareu.service.FakeMeetingApiService.CST_FORMAT_DATE_TIME;
import static com.ocr.mareu.utils.DateConverter.convertDateTimeStringToCalendar;

/**
 * Created by Florence LE BOURNOT on 25/02/2020
 */
public class FakeDateTime {


    public static  Calendar generateDateTimeFromNow(Calendar pCal, int pDiffMonth, int pDiffDay, int pDiffHour) {
        if (pDiffMonth != 0)
            pCal.add(Calendar.MONTH,pDiffMonth);
        if (pDiffDay != 0)
            pCal.add(Calendar.DAY_OF_MONTH, pDiffDay);
        if (pDiffHour != 0)
            pCal.add(Calendar.HOUR_OF_DAY,pDiffHour);
        pCal.set(Calendar.MINUTE,00);
        pCal.set(Calendar.SECOND,0);
        pCal.set(Calendar.MILLISECOND,0);

        return pCal;
    }

    public static String getSimpleDateFormat(Calendar pCalendar) {
        DateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        return lDateFormat.format(pCalendar.getTime());
    }

    public static String getSimpleDateOrTimeFormat(Calendar pCalendar) {
        DateFormat lDateFormat = new SimpleDateFormat("HH:mm", Locale.FRENCH);
        return lDateFormat.format(pCalendar.getTime());
    }


}
