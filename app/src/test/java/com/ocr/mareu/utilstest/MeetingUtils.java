package com.ocr.mareu.utilstest;

import android.graphics.Color;

import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;
import com.ocr.mareu.service.MeetingApiService;
import com.ocr.mareu.service.MeetingApiServiceException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ocr.mareu.service.FakeMeetingApiService.CST_FORMAT_DATE;
import static com.ocr.mareu.service.FakeMeetingApiService.CST_FORMAT_DATE_TIME;
import static com.ocr.mareu.utils.DateConverter.convertDateTimeStringToCalendar;

/**
 * Created by Florence LE BOURNOT on 18/02/2020
 */
public class MeetingUtils {

    public static Meeting generate1Meeting() throws ParseException {
        Meeting lMeeting = null;
        lMeeting = new Meeting(new Room("HADES", Color.argb(100,3,169,244)),
                "Sujet3",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 14:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 15:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));
        return lMeeting;
    }

    public static List<Room> generate3Rooms() {
        List<Room> lRooms = new ArrayList<>();
        lRooms.add(new Room("HADES", Color.argb(100,3,169,244)));
        lRooms.add(new Room("ARES", Color.argb(100,103,58,183)));
        lRooms.add(new Room("POSEIDON", Color.argb(100,244,67,54)));
        return lRooms;
    }

    public static List<Meeting> add2FakeMeetingsExpected() throws ParseException {
        List<Meeting> lMeetings = new ArrayList<>();

            lMeetings.add(
                new Meeting(new Room("POSEIDON", Color.argb(100,244,67,54)),
                "Sujet",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"30/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"30/08/2020 14:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"30/08/2020 15:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com")));

            lMeetings.add(
                new Meeting(new Room("ARES", Color.argb(100,103,58,183)),
                "Sujet2",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 10:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 11:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com")));
        return lMeetings;
    }

    public static MeetingApiService add2FakeMeetings(MeetingApiService mApi) throws MeetingApiServiceException, ParseException {

        mApi.addMeeting(
            new Meeting(new Room("POSEIDON", Color.argb(100,244,67,54)),
                "Sujet",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"30/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"30/08/2020 14:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"30/08/2020 15:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com")));
        mApi.addMeeting(
            new Meeting(new Room("ARES", Color.argb(100,103,58,183)),
                "Sujet2",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 10:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 11:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com")));
        return mApi;
    }

    public static MeetingApiService addFakeValidMeetingsLongList(MeetingApiService mApi) throws MeetingApiServiceException, ParseException {

        mApi.addMeeting(
            new Meeting(new Room("POSEIDON", Color.argb(100,244,67,54)),
            "Sujet",
            convertDateTimeStringToCalendar(CST_FORMAT_DATE,"30/08/2020"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"30/08/2020 14:00:00:00"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"30/08/2020 15:00:00:00"),
            Arrays.asList("toto@gmail.com","titi@gmail.com")));

        mApi.addMeeting(
            new Meeting(new Room("ARES", Color.argb(100,103,58,183)),
            "Sujet2",
            convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 10:00:00:00"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 11:00:00:00"),
            Arrays.asList("toto@gmail.com","titi@gmail.com")));

        mApi.addMeeting(
            new Meeting(new Room("HADES", Color.argb(100,3,169,244)),
            "Sujet3",
            convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/09/2020"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/09/2020 14:00:00:00"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/09/2020 15:00:00:00"),
            Arrays.asList("toto@gmail.com","titi@gmail.com")));

        mApi.addMeeting(
            new Meeting(new Room("ZEUS", Color.argb(100,233,30,99)),
            "Sujet4",
            convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 09:00:00:00"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 10:00:00:00"),
            Arrays.asList("toto@gmail.com","titi@gmail.com")));

        mApi.addMeeting(
            new Meeting(new Room("ARES", Color.argb(100,103,58,183)),
            "Sujet5",
            convertDateTimeStringToCalendar(CST_FORMAT_DATE,"22/08/2020"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"22/08/2020 16:00:00:00"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"22/08/2020 17:00:00:00"),
            Arrays.asList("toto@gmail.com","titi@gmail.com")));

        mApi.addMeeting(
            new Meeting(new Room("PLUTON", Color.argb(100,63,81,181)),
            "Sujet6",
            convertDateTimeStringToCalendar(CST_FORMAT_DATE,"22/08/2020"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"22/08/2020 10:00:00:00"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"22/08/2020 12:00:00:00"),
            Arrays.asList("toto@gmail.com","titi@gmail.com")));

        mApi.addMeeting(
            new Meeting(new Room("VENUS", Color.argb(100,156,39,176)),
            "Sujet7",
            convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 12:00:00:00"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 14:00:00:00"),
            Arrays.asList("toto@gmail.com","titi@gmail.com")));

        mApi.addMeeting(
            new Meeting(new Room("DEMETER", Color.argb(100,76,175,80)),
            "Sujet8",
            convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/09/2020"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/09/2020 16:00:00:00"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/09/2020 18:00:00:00"),
            Arrays.asList("toto@gmail.com","titi@gmail.com")));

        mApi.addMeeting(
            new Meeting(new Room("HADES", Color.argb(100,3,169,244)),
            "Sujet9",
            convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 09:00:00:00"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 13:00:00:00"),
            Arrays.asList("toto@gmail.com","titi@gmail.com")));

        mApi.addMeeting(
            new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
            "Sujet10 reunion avec quarante caractères",
            convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 10:00:00:00"),
            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 12:00:00:00"),
            Arrays.asList("toto@gmail.com","titi@gmail.com")));
        return mApi;
    }


    public static MeetingApiService generateReferenceMeeting(MeetingApiService mApi) throws ParseException, MeetingApiServiceException {
        mApi.addMeeting( new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 06:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 08:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com")));
        return mApi;
    }

}
