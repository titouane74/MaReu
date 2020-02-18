package com.ocr.mareu.service;

import android.content.Context;
import android.graphics.Color;

import com.ocr.mareu.di.DI;
import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;

import com.ocr.mareu.utils.MeetingUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ocr.mareu.service.FakeMeetingApiService.CST_FORMAT_DATE;
import static com.ocr.mareu.service.FakeMeetingApiService.CST_FORMAT_DATE_TIME;
import static com.ocr.mareu.utils.DateConverter.convertDateTimeStringToCalendar;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Florence LE BOURNOT on 18/02/2020
 */
@RunWith(MockitoJUnitRunner.class)
public class MeetingApiServiceTest {

    private MeetingApiService mApi;
    private List<Meeting> mMeetings;
    private List<Room> mRooms;

    @Mock
    Context contextMock;

    @Before
    public void setup() {
        mApi = DI.sMeetingApiService;
        assertThat(mApi, notNullValue());

        mMeetings = new ArrayList<>();

        mApi.initializeRooms(contextMock);
        mRooms = mApi.getRooms();
    }

    @Test
    public void getRoomsWithSuccess() {
        int nbGetRooms = mApi.getRooms().size();
        assertEquals(10,nbGetRooms);
    }


    @Test
    public void addMeetingWithSuccess() {
        List<Meeting> lMeetingListExpected = MeetingUtils.add2FakeMeetingsForTest();
        int nbMeetingExpected = lMeetingListExpected.size();

        //Avant ajout on doit avoir 0 meeting
        int nbGetMeetings = mMeetings.size();
        assertEquals(0,nbGetMeetings);

        //Après ajout on doit avoir le nombre attendu
        //addFakeMeeting utilise la méthode addMeeting qui ajoute les réunions
        mApi.addFakeMeeting();
        mMeetings = mApi.getMeetings();
        nbGetMeetings = mMeetings.size();
        assertEquals(nbMeetingExpected,nbGetMeetings);

        try {
            mApi.addMeeting(
                    new Meeting(new Room("HADES", Color.argb(100,3,169,244)),
                            "Sujet3",
                            convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),
                            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 14:00:00:00"),
                            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 15:00:00:00"),
                            Arrays.asList("toto@gmail.com","titi@gmail.com")));
        } catch (ParseException pE) {
            pE.printStackTrace();
        } catch (MeetingApiServiceException pE) {
            pE.printStackTrace();
        }

        nbMeetingExpected ++;
        nbGetMeetings = mMeetings.size();
        assertEquals(nbMeetingExpected,nbGetMeetings);

    }

    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> lMeetingListExpected = MeetingUtils.add2FakeMeetingsForTest();
        int nbMeetingExpected = lMeetingListExpected.size();

        //Avant ajout on doit avoir 0 meeting
        int nbGetMeetings = mMeetings.size();
        assertEquals(0,nbGetMeetings);

        //Après ajout on doit avoir le nombre attendu
        //addFakeMeeting utilise la méthode addMeeting qui ajoute les réunions
        mApi.addFakeMeeting();
        mMeetings = mApi.getMeetings();
        nbGetMeetings = mMeetings.size();
        assertEquals(nbMeetingExpected,nbGetMeetings);

        Assert.assertThat(mMeetings, IsIterableContainingInAnyOrder.containsInAnyOrder(lMeetingListExpected));



    }

}
