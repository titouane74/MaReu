package com.ocr.mareu.utils;

import android.content.Context;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ocr.mareu.R;
import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;
import com.ocr.mareu.service.FakeMeetingApiService;
import com.ocr.mareu.service.MeetingApiService;
import com.ocr.mareu.service.MeetingApiServiceException;
import com.ocr.mareu.utilstest.MeetingUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import static com.ocr.mareu.utils.Validation.CST_TOPIC;
import static com.ocr.mareu.utils.Validation.errorMessageToShow;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

/**
 * Created by Florence LE BOURNOT on 20/02/2020
 */
@RunWith(MockitoJUnitRunner.class)
public class ValidationTest {


    private MeetingApiService mApi;
    private List<Meeting> mMeetings;
    private List<Room> mRooms;

    @Mock
    Context contextMock;

    @Before
    public void setup() throws MeetingApiServiceException, ParseException {
        mApi = new FakeMeetingApiService();
        //mApi = DI.sMeetingApiService;
        assertThat(mApi, notNullValue());

        mApi.initializeRooms(contextMock);

        mRooms = mApi.getRooms();
        assertNotNull(mRooms);
        assertEquals(10,mRooms.size());

        mApi = MeetingUtils.addFakeValidMeetingsLongList(mApi);
        mMeetings = mApi.getMeetings();
        assertNotNull(mMeetings);
        assertEquals(10,mMeetings.size());
    }

    @After
    public void tearDown() {
        mApi = null;
    }

    @Test
    public void validationTextInputLayoutWithSuccess () {

    }

    @Test
    public void validationTextTopicFailing () {
        String lReturn = null;

        when(contextMock.getString(R.string.err_empty_field))
                .thenReturn("Le champ ne peut pas être vide");

        lReturn = Validation.validationText(contextMock, CST_TOPIC,"");
        assertEquals("Le champ ne peut pas être vide",lReturn);

        when(contextMock.getString(R.string.err_topic_length))
                .thenReturn("Libellé du sujet trop long");

        lReturn = Validation.validationText(contextMock, CST_TOPIC,
                "Réunion avec un texte avec plus de quarante caractères");
        assertEquals("Libellé du sujet trop long",lReturn);

    }

    @Test
    public void validationDateTimeWithSuccess() {

    }

    @Test
    public void validationDateTimeFailing() {

    }

    @Test
    public void validationParticipantsWithSuccess() {

    }

    @Test
    public void validationParticipantsFailing() {

    }

}
