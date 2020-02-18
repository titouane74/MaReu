package com.ocr.mareu;

import android.content.Context;

import com.ocr.mareu.di.DI;
import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;
import com.ocr.mareu.service.FakeMeetingApiService;
import com.ocr.mareu.service.MeetingApiService;
import com.ocr.mareu.utils.MeetingUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

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
        mMeetings = new ArrayList<>();
        mApi.initializeRooms(contextMock);
        mRooms = mApi.getRooms();
    }

    @Test
    public void initializeAndGetRoomsWithSuccess() {
        List<Room> lRooms = new ArrayList<>();
        int nbRoomExpected = 10;
        int initialNbRoom = lRooms.size();

        //Avant initialisation on doit avoir 0 room
        lRooms = mApi.getRooms();
        int nbGetRooms = lRooms.size();
        assertEquals(nbGetRooms,initialNbRoom);

        //Après initialisation on doit avoir 10 rooms
        mApi.initializeRooms(contextMock);
        lRooms = mApi.getRooms();
        nbGetRooms = lRooms.size();

        assertEquals(nbGetRooms,nbRoomExpected);
    }

    @Test
    public void addAndGetMeetingWithSuccess() {
        List<Meeting> lMeetingListExpected = MeetingUtils.add2FakeMeetingsForTest();
        int nbMeetingExpected = lMeetingListExpected.size();

        //Avant ajout on doit avoir 0 meeting
        int nbGetMeetings = mMeetings.size();
        assertEquals(0,nbGetMeetings);

        //Après ajout on doit avoir le nombre attendu
        mApi.addFakeMeeting();
        mMeetings = mApi.getMeetings();
        nbGetMeetings = mMeetings.size();
        assertEquals(nbMeetingExpected,nbGetMeetings);
    }



}
