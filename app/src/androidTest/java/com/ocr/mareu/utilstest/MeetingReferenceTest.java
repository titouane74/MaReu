package com.ocr.mareu.utilstest;

import android.graphics.Color;

import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;
import com.ocr.mareu.service.MeetingApiService;
import com.ocr.mareu.service.MeetingApiServiceException;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by Florence LE BOURNOT on 26/02/2020
 */
public class MeetingReferenceTest {

   public static MeetingApiService addReferenceMeeting(MeetingApiService mApi,
           Calendar pCalDate, int pDiffDay, int pDiffHourStart, int pDiffHourEnd) throws MeetingApiServiceException {

        pCalDate.add(Calendar.DAY_OF_MONTH, pDiffDay);
        pCalDate.set(Calendar.MINUTE,00);
        pCalDate.set(Calendar.SECOND,0);
        pCalDate.set(Calendar.MILLISECOND,0);

        Calendar lCalStart = (Calendar) pCalDate.clone();
        Calendar lCalEnd = (Calendar) pCalDate.clone();

        lCalStart.add(Calendar.HOUR_OF_DAY,pDiffHourStart);
        lCalEnd.add(Calendar.HOUR_OF_DAY,pDiffHourEnd);

        mApi.addMeeting( new Meeting(new Room("ARES", Color.argb(100,0,150,135)),
                "Sujet Référence",
                pCalDate,
                lCalStart,
                lCalEnd,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com")));
        return mApi;
    }
}
