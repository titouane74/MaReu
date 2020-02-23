package com.ocr.mareu.utils;

import android.content.Context;
import com.ocr.mareu.R;
import com.ocr.mareu.di.DI;
import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;
import com.ocr.mareu.service.FakeMeetingApiService;
import com.ocr.mareu.service.MeetingApiService;
import com.ocr.mareu.service.MeetingApiServiceException;
import com.ocr.mareu.utilstest.MeetingUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.ocr.mareu.utils.Validation.CST_DATETIME;
import static com.ocr.mareu.utils.Validation.CST_EMAIL;
import static com.ocr.mareu.utils.Validation.CST_ROOM;
import static com.ocr.mareu.utils.Validation.CST_TOPIC;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Florence LE BOURNOT on 20/02/2020
 */

public class ValidationTest {


    private MeetingApiService mApi;
    private List<Meeting> mMeetings;
    private List<Room> mRooms;

    @Mock
    Context contextMock;

    @BeforeEach
    public void setup() throws MeetingApiServiceException {
        initMocks(this);

        mApi = DI.getMeetingApiService();
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

    @AfterEach
    public void tearDown() {
        mApi = DI.getMeetingApiServiceNewInstance();
    }

    @Test
    public void validationTextRoomWithSuccess () {
        String lReturn = null;

        lReturn = Validation.validationText(contextMock, CST_ROOM,"NOT EMPTY KEYBOARD INPUT BLOCKED");
        assertNull(lReturn);
    }

    @Test
    public void validationTextRoomFailing () {
        String lReturn = null;

        when(contextMock.getString(R.string.err_empty_field))
                .thenReturn("Le champ ne peut pas être vide");

        lReturn = Validation.validationText(contextMock, CST_ROOM,"");
        assertEquals("Le champ ne peut pas être vide",lReturn);
    }

    @Test
    public void validationTextTopicWithSuccess () {
        String lReturn = null;

        lReturn = Validation.validationText(contextMock, CST_TOPIC,"Un texte normal");
        assertNull(lReturn);

        lReturn = Validation.validationText(contextMock, CST_TOPIC,"Réunion avec un texte avec 40 caractères");
        assertNull(lReturn);

        lReturn = Validation.validationText(contextMock, CST_TOPIC,"Texte avec > + - / @ 1 2 , ; ");
        assertNull(lReturn);

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
    public void giventDate_whenEmpty_thenFail() {
        String lReturn = null;

        when(contextMock.getString(R.string.err_empty_field))
                .thenReturn("Le champ ne peut pas être vide");

        lReturn = Validation.validationText(contextMock, CST_DATETIME,"");
        assertEquals("Le champ ne peut pas être vide",lReturn);
    }

    @Test
    public void givenDate_whenBeforeNow_thenFail() {
//        Erreur gérée dans DatePicker
//        when(contextMock.getString(R.string.err_anterior_date))
//                .thenReturn("La date ne peut pas être antérieur à aujourd\\'hui");
    }

    @Test
    public void givenTimeStart_whenEmpty_thenFail() {
        String lReturn = null;

        when(contextMock.getString(R.string.err_empty_field))
                .thenReturn("Le champ ne peut pas être vide");

        lReturn = Validation.validationText(contextMock, CST_DATETIME,"");
        assertEquals("Le champ ne peut pas être vide",lReturn);
    }

    @Test
    public void givenTimeStart_whenBeforeNow_thenFail() {
        String lReturn = null;

        Calendar lStart = Calendar.getInstance(Locale.FRANCE);
        lStart.add(Calendar.HOUR_OF_DAY, -1);
        Calendar lEnd = Calendar.getInstance(Locale.FRANCE);
        lEnd.add(Calendar.HOUR_OF_DAY,+1);

        when(contextMock.getString(R.string.err_start_before_now))
                .thenReturn("L\'heure de début ne peut pas être antérieure à maintenant");

        lReturn = Validation.validationDateTime(contextMock, lStart,lEnd);
        List<String> lError = GsonTransformer.getGsonToListString(lReturn);

        assertEquals("L\'heure de début ne peut pas être antérieure à maintenant",lError.get(0));

    }

    @Test
    public void giventStartTime_whenAfterNow_thenSuccess() {
        String lReturn = null;

        Calendar lStart = Calendar.getInstance(Locale.FRANCE);
        lStart.add(Calendar.HOUR_OF_DAY, +2);
        Calendar lEnd = Calendar.getInstance(Locale.FRANCE);
        lEnd.add(Calendar.HOUR_OF_DAY,+3);

        lReturn = Validation.validationDateTime(contextMock, lStart,lEnd);
        List<String> lError = GsonTransformer.getGsonToListString(lReturn);

        assertTrue(lError.get(0).isEmpty());

    }

    @Test
    public void givenTimeEnd_whenEmpty_thenFail() {
        String lReturn = null;

        when(contextMock.getString(R.string.err_empty_field))
                .thenReturn("Le champ ne peut pas être vide");

        lReturn = Validation.validationText(contextMock, CST_DATETIME,"");
        assertEquals("Le champ ne peut pas être vide",lReturn);
    }

    @Test
    public void givenEndTime_whenBeforeStartTime_thenFail() {
        String lReturn = null;

        Calendar lStart = Calendar.getInstance(Locale.FRANCE);
        lStart.add(Calendar.HOUR_OF_DAY, +5);
        Calendar lEnd = Calendar.getInstance(Locale.FRANCE);
        lEnd.add(Calendar.HOUR_OF_DAY,+2);

        when(contextMock.getString(R.string.err_end_before_start))
                .thenReturn("L\'heure de fin ne peut pas être antérieure à l\'heure début");

        lReturn = Validation.validationDateTime(contextMock, lStart,lEnd);
        List<String> lError = GsonTransformer.getGsonToListString(lReturn);

        assertEquals("L\'heure de fin ne peut pas être antérieure à l\'heure début",lError.get(2));

    }

    @Test
    public void givenEndTime_whenAfterStartTime_thenSuccess() {
        String lReturn = null;

        Calendar lStart = Calendar.getInstance(Locale.FRANCE);
        lStart.add(Calendar.HOUR_OF_DAY, +2);
        Calendar lEnd = Calendar.getInstance(Locale.FRANCE);
        lEnd.add(Calendar.HOUR_OF_DAY,+3);

        lReturn = Validation.validationDateTime(contextMock, lStart,lEnd);
        List<String> lError = GsonTransformer.getGsonToListString(lReturn);

        assertTrue(lError.get(1).isEmpty());

    }

    @Test
    public void validationEmailAddressWithSuccess() {
        String lReturn ;

        lReturn = Validation.validationText(contextMock, CST_EMAIL,"toto@gmail.com");
        assertNull(lReturn);
        lReturn = Validation.validationText(contextMock, CST_EMAIL,"toto12@gmail.com");
        assertNull(lReturn);
        lReturn = Validation.validationText(contextMock, CST_EMAIL,"toto-et-titi@gmail.com");
        assertNull(lReturn);
        lReturn = Validation.validationText(contextMock, CST_EMAIL,"toto*24@gmail.com");
        assertNull(lReturn);

    }

    @Test
    public void validationEmailAddressFailing() {
        String lReturn ;

        when(contextMock.getString(R.string.err_invalid_email_address))
                .thenReturn("Entrez une adresse mail valide");

        lReturn = Validation.validationText(contextMock, CST_EMAIL,"toto");
        assertEquals("Entrez une adresse mail valide",lReturn);
        lReturn = Validation.validationText(contextMock, CST_EMAIL,"toto@");
        assertEquals("Entrez une adresse mail valide",lReturn);
        lReturn = Validation.validationText(contextMock, CST_EMAIL,"toto@gmail");
        assertEquals("Entrez une adresse mail valide",lReturn);
        lReturn = Validation.validationText(contextMock, CST_EMAIL,"toto@gmail.");
        assertEquals("Entrez une adresse mail valide",lReturn);
        lReturn = Validation.validationText(contextMock, CST_EMAIL,"@.");
        assertEquals("Entrez une adresse mail valide",lReturn);
        lReturn = Validation.validationText(contextMock, CST_EMAIL,"@gmail.");
        assertEquals("Entrez une adresse mail valide",lReturn);
        lReturn = Validation.validationText(contextMock, CST_EMAIL,"@gmail.com");
        assertEquals("Entrez une adresse mail valide",lReturn);
        lReturn = Validation.validationText(contextMock, CST_EMAIL,"to&to>@gmail.com");
        assertEquals("Entrez une adresse mail valide",lReturn);

    }

    @Test
    public void validationParticipantsWithSuccess() {
        String lReturn ;

        lReturn = Validation.validationText(contextMock, CST_EMAIL,"toto@gmail.com,toto12@gmail.com," +
                "toto-et-titi@gmail.com,toto*24@gmail.com");
        assertNull(lReturn);

    }

    @Test
    public void validationParticipantsFailing() {
        String lReturn ;

        when(contextMock.getString(R.string.err_list_participants))
                .thenReturn("La liste des participants ne peut pas être vide");

        lReturn = Validation.validationParticipants(contextMock, "");
        assertEquals("La liste des participants ne peut pas être vide",lReturn);

    }

}
