package com.ocr.mareu.service;

import android.content.Context;
import android.graphics.Color;

import com.ocr.mareu.R;
import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;
import com.ocr.mareu.utilstest.MeetingUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static com.ocr.mareu.service.FakeMeetingApiService.CST_FORMAT_DATE;
import static com.ocr.mareu.service.FakeMeetingApiService.CST_FORMAT_DATE_TIME;
import static com.ocr.mareu.utils.DateConverter.convertDateTimeStringToCalendar;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Florence LE BOURNOT on 20/02/2020
 */
@Tag("MeetingApiServiceAddMeeting")
//Meeting = new meeting; reference = reference meeting
public class AddMeetingTest {
    private MeetingApiService mApi;
    private List<Meeting> mMeetings;

    @Mock
    Context contextMock;

    @BeforeEach
    public void setup() throws MeetingApiServiceException, ParseException {
        initMocks(this);
        mApi = new FakeMeetingApiService();
        assertThat(mApi, notNullValue());

        mApi = MeetingUtils.generateReferenceMeeting(mApi);
        mMeetings = mApi.getMeetings();
        assertNotNull(mMeetings);
        assertEquals(1,mMeetings.size());
    }

    @AfterEach
    public void tearDown() {
        mApi = null;
    }


    @DisplayName("Case 1 : Meeting Start = Reference start")
    @Test
    public void givenNewMeeting_whenSameStart_thenFail() throws ParseException {
        String lReturn = null;

        when(contextMock.getString(R.string.err_meeting_room_not_free))
                .thenReturn("La salle de réunion n\'est pas disponible");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 06:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 07:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            lReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            lReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("La salle de réunion n\'est pas disponible",lReturn);

    }

    @DisplayName("Case 2 : Meeting end = Reference End")
    @Test
    public void givenNewMeeting_whenSameEnd_thenFail() throws ParseException {
        String lReturn = null;

        when(contextMock.getString(R.string.err_meeting_room_not_free))
                .thenReturn("La salle de réunion n\'est pas disponible");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 07:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 08:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            lReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            lReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("La salle de réunion n\'est pas disponible",lReturn);

    }

    @DisplayName("Case 3 : Meeting start before Reference start and Meeting end before Reference start")
    @Test
    public void givenNewMeeting_whenBeforeReference_thenCreateMeeting() throws ParseException {
        String lReturn = null;

        when(contextMock.getString(R.string.action_add_meeting))
                .thenReturn("Réunion ajoutée");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 04:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 05:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            lReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            lReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("Réunion ajoutée",lReturn);

    }

    @DisplayName("Case 4 : Meeting start before Reference start and Meeting end same Reference end")
    @Test
    public void givenNewMeeting_whenEndSameReferenceStart_thenCreateMeeting() throws ParseException {
        String lReturn = null;

        when(contextMock.getString(R.string.action_add_meeting))
                .thenReturn("Réunion ajoutée");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 05:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 06:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            lReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            lReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("Réunion ajoutée",lReturn);

    }

    @DisplayName("Case 5 : Meeting start before Reference start and " +
            "Meeting end before Reference end and after Reference start")
    @Test
    public void givenNewMeeting_whenEndDuringReference_thenFail() throws ParseException {
        String lReturn = null;

        when(contextMock.getString(R.string.err_meeting_room_not_free))
                .thenReturn("La salle de réunion n\'est pas disponible");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 05:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 07:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            lReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            lReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("La salle de réunion n\'est pas disponible",lReturn);
    }

    @DisplayName("Case 6 : Meeting start before Reference start and Meeting end after Reference end")
    @Test
    public void givenNewMeeting_whenReferenceDuringNewMeeting_thenFail() throws ParseException {
        String lReturn = null;

        when(contextMock.getString(R.string.err_meeting_room_not_free))
                .thenReturn("La salle de réunion n\'est pas disponible");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 05:30:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 09:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            lReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            lReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("La salle de réunion n\'est pas disponible",lReturn);

    }

    @DisplayName("Case 7 : Meeting start after Reference start and Meeting end before Reference end")
    @Test
    public void givenNewMeeting_whenNewMeetingDuringReference_thenFail() throws ParseException {
        String lReturn = null;

        when(contextMock.getString(R.string.err_meeting_room_not_free))
                .thenReturn("La salle de réunion n\'est pas disponible");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 06:30:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 07:30:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            lReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            lReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("La salle de réunion n\'est pas disponible",lReturn);

    }
    @DisplayName("Case 8 : Meeting start after Reference start and before Reference end and" +
            "Meeting end after Reference end")
    @Test
    public void givenNewMeeting_whenReferenceEndDuringNewMeeting_thenFail() throws ParseException {
        String lReturn = null;

        when(contextMock.getString(R.string.err_meeting_room_not_free))
                .thenReturn("La salle de réunion n\'est pas disponible");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 07:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 09:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            lReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            lReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("La salle de réunion n\'est pas disponible",lReturn);

    }

    @DisplayName("Case 9 : Meeting start = Reference end and Meeting end after Reference end")
    @Test
    public void givenNewMeeting_whenStartSameReferenceEnd_thenCreateMeeting() throws ParseException {
        String lReturn = null;

        when(contextMock.getString(R.string.action_add_meeting))
                .thenReturn("Réunion ajoutée");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 08:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 09:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            lReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            lReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("Réunion ajoutée",lReturn);

    }

    @DisplayName("Case 10 : Meeting start and end after Reference end")
    @Test
    public void givenNewMeeting_whenAfterReference_thenCreateMeeting() throws ParseException {
        String lReturn = null;

        when(contextMock.getString(R.string.action_add_meeting))
                .thenReturn("Réunion ajoutée");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 09:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 10:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            lReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            lReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("Réunion ajoutée",lReturn);

    }
}
