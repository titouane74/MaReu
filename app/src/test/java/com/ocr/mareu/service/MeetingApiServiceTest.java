package com.ocr.mareu.service;

import android.content.Context;

import com.ocr.mareu.R;
import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;

import com.ocr.mareu.utilstest.MeetingUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Calendar;
import java.util.List;

import static com.ocr.mareu.service.FakeMeetingApiService.CST_FORMAT_DATE;
import static com.ocr.mareu.service.FakeMeetingApiService.CST_FORMAT_DATE_TIME;
import static com.ocr.mareu.utils.DateConverter.convertDateTimeStringToCalendar;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * Created by Florence LE BOURNOT on 18/02/2020
 */

public class MeetingApiServiceTest {

    private MeetingApiService mApi;
    private List<Meeting> mMeetings;
    private List<Room> mRooms;

    @Mock
    Context contextMock;

    @BeforeEach
    public void setup() {
        initMocks(this);
        mApi = new FakeMeetingApiService();
        //mApi = DI.sMeetingApiService;
        assertThat(mApi, notNullValue());

        mApi.initializeRooms(contextMock);
        mRooms = mApi.getRooms();
    }

    @AfterEach
    public void tearDown() {
        mApi = null;
    }

    @Test
    public void getRoomsWithSuccess() {
        int nbGetRooms = mApi.getRooms().size();
        assertEquals(10,nbGetRooms);
    }

    @Test
    public void addMeetingWithSuccess() throws MeetingApiServiceException {
        //Avant ajout on doit avoir 0 meeting
        int nbGetMeetings = mApi.getMeetings().size();
        assertEquals(0,nbGetMeetings);

        Meeting lMeetingToAdd = MeetingUtils.generate1Meeting();

        mApi.addMeeting(lMeetingToAdd);

        assertEquals(nbGetMeetings+1,mApi.getMeetings().size());

        List<Meeting> lMeetingsAdded = mApi.getMeetings();
        assertEquals("HADES",lMeetingsAdded.get(nbGetMeetings).getRoom().getNameRoom());
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

    @Test
    public void setAndGetMeetingSelectedWithSuccess() {

        Meeting lMeetingSelectedInitial = mApi.getMeetingSelected();
        assertNull(lMeetingSelectedInitial);

        Meeting lMeetingSelected = MeetingUtils.generate1Meeting();
        mApi.setMeetingSelected(lMeetingSelected);

        Meeting lMeetingGet = mApi.getMeetingSelected();
        assertNotNull(lMeetingGet);
        assertEquals("HADES",lMeetingGet.getRoom().getNameRoom());
        assertEquals(lMeetingSelected,lMeetingGet);
    }

    @Test
    public void setAndGetRoomsSelectedWithSuccess() {

        List<Room> lRoomsSelectedInitial = mApi.getRoomsSelected();
        assertNull(lRoomsSelectedInitial);

        List<Room> lRoomsSelected = MeetingUtils.generate3Rooms();
        mApi.setRoomsSelected(lRoomsSelected);

        List<Room> lRoomsGet = mApi.getRoomsSelected();
        assertNotNull(lRoomsGet);
        assertEquals("HADES",lRoomsGet.get(0).getNameRoom());
        assertEquals("ARES",lRoomsGet.get(1).getNameRoom());
        assertEquals("POSEIDON",lRoomsGet.get(2).getNameRoom());
        assertEquals(lRoomsSelected,lRoomsGet);

    }

    @Test
    public void setAndGetDateSelectedWithSuccess() {

        Calendar lDateInitial = mApi.getDateSelected();
        assertNull(lDateInitial);

        Calendar lDateSelected = convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020");
        mApi.setDateSelected(lDateSelected);

        Calendar lDateGet = mApi.getDateSelected();
        assertNotNull(lDateGet);
        assertEquals(lDateSelected,lDateGet);

    }

    @Test
    public void setAndGetStartMeetingWithSuccess() {

        Calendar lDateTimeInitial = mApi.getStartMeeting();
        assertNull(lDateTimeInitial);

        Calendar lDateTimeToSet = convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 14:00:00:00");
        mApi.setStartMeeting(lDateTimeToSet);

        Calendar lDateTimeGet = mApi.getStartMeeting();
        assertNotNull(lDateTimeGet);
        assertEquals(lDateTimeToSet,lDateTimeGet);

    }

    @Test
    public void setAndGetEndMeetingWithSuccess()  {

        Calendar lDateTimeInitial = mApi.getEndMeeting();
        assertNull(lDateTimeInitial);

        Calendar lDateTimeToSet = convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 15:00:00:00");
        mApi.setEndMeeting(lDateTimeToSet);

        Calendar lDateTimeGet = mApi.getEndMeeting();
        assertNotNull(lDateTimeGet);
        assertEquals(lDateTimeToSet,lDateTimeGet);

    }

    @Test
    public void setAndGetIsMenuActiveWithSuccess() {

        boolean isMenuActiveInitial = mApi.getIsMenuActive();
        assertFalse(isMenuActiveInitial);

        mApi.setIsMenuActive(true);

        boolean isMenuActiveGet = mApi.getIsMenuActive();
        assertTrue(isMenuActiveGet);

    }

}
