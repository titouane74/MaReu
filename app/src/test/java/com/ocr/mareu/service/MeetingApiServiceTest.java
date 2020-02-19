package com.ocr.mareu.service;

import android.content.Context;

import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;

import com.ocr.mareu.utils.MeetingUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

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
        mApi = new FakeMeetingApiService();
        //mApi = DI.sMeetingApiService;
        assertThat(mApi, notNullValue());

        mApi.initializeRooms(contextMock);
        mRooms = mApi.getRooms();
    }

    @After
    public void tearDown() {
        mApi = null;
    }

    @Test
    public void getRoomsWithSuccess() {
        int nbGetRooms = mApi.getRooms().size();
        assertEquals(10,nbGetRooms);
    }

    @Test
    public void addMeetingWithSuccess() {
        //Avant ajout on doit avoir 0 meeting
        int nbGetMeetings = mApi.getMeetings().size();
        assertEquals(0,nbGetMeetings);

        Meeting lMeetingToAdd = MeetingUtils.generate1Meeting();

        try {
            mApi.addMeeting(lMeetingToAdd);
        } catch (MeetingApiServiceException pE) {
            pE.printStackTrace();
        }

        assertEquals(nbGetMeetings+1,mApi.getMeetings().size());
    }

    @Test
    public void getMeetingsWithSuccess() throws MeetingApiServiceException {
        List<Meeting> lMeetingListExpected = MeetingUtils.add2FakeMeetingsExpected();
        int nbMeetingExpected = lMeetingListExpected.size();

        //Avant ajout on doit avoir 0 meeting
        int nbGetMeetings = mApi.getMeetings().size();
        assertEquals(0,nbGetMeetings);

        //Après ajout on doit avoir le nombre attendu
        //addFakeMeeting utilise la méthode addMeeting qui ajoute les réunions
        //mApi.addFakeMeeting();
        mApi = MeetingUtils.add2FakeMeetings(mApi);
        mMeetings = mApi.getMeetings();
        nbGetMeetings = mMeetings.size();
        assertEquals(nbMeetingExpected,nbGetMeetings);

        assertEquals(lMeetingListExpected.get(0).getRoom().getNameRoom(),mMeetings.get(0).getRoom().getNameRoom());
        assertEquals(lMeetingListExpected.get(0).getStart(),mMeetings.get(0).getStart());

        assertEquals(lMeetingListExpected.get(1).getRoom().getNameRoom(),mMeetings.get(1).getRoom().getNameRoom());
        assertEquals(lMeetingListExpected.get(1).getStart(),mMeetings.get(1).getStart());

    }

    @Test
    public void deleteMeetingWithSuccess() {
        int initialNbMeeting = mApi.getMeetings().size();
        assertEquals(0,initialNbMeeting);

        Meeting lMeetingToDelete = MeetingUtils.generate1Meeting();
        try {
            mApi.addMeeting(lMeetingToDelete);
        } catch (MeetingApiServiceException pE) {
            pE.printStackTrace();
        }

        assertEquals(initialNbMeeting + 1, mApi.getMeetings().size());

        mApi.deleteMeeting(lMeetingToDelete);
        assertEquals(initialNbMeeting,mApi.getMeetings().size());
    }

    @Test
    public void resetMeetingListWithSuccess() throws MeetingApiServiceException {

        assertEquals(0,mApi.getMeetings().size());

        mApi = MeetingUtils.addFakeValidMeetingsLongList(mApi);
        assertEquals(10,mApi.getMeetings().size());

        mApi.resetMeetings();
        assertEquals(0,mApi.getMeetings().size());
    }

    @Test
    public void extractRoomSelecteWithSuccess() {
        Room lRoomExtracted = null;

        lRoomExtracted = mApi.extractRoomSelected("POSEIDON");
        assertEquals("POSEIDON",lRoomExtracted.getNameRoom());

    }


}
