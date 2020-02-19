package com.ocr.mareu.utils;

import android.content.Context;

import com.ocr.mareu.di.DI;
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

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Florence LE BOURNOT on 19/02/2020
 */
@RunWith(MockitoJUnitRunner.class)
public class SortOrFilterTest {

    private MeetingApiService mApi;
    private List<Meeting> mMeetings;
    private List<Room> mRooms;

    @Mock
    Context contextMock;

    @Before
    public void setup() throws MeetingApiServiceException {
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
    public void filterMeetingByRoomWithSuccess() {

    }

    @Test
    public void filterMeetingByDateWithSuccess() {

    }

    @Test
    public void sortMeetingByRoomAscendantWithSuccess() {

    }

    @Test
    public void sortMeetingByRoomDescendantWithSuccess() {

    }

    @Test
    public void sortMeetingByDateFromRecentToOlderWithSuccess() {

    }

    @Test
    public void sortMeetingByDateFromOlderToRecentWithSuccess() {

    }

}
