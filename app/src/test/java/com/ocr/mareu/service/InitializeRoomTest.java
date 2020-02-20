package com.ocr.mareu.service;

import android.content.Context;

import com.ocr.mareu.di.DI;
import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Florence LE BOURNOT on 18/02/2020
 */
public class InitializeRoomTest {

    private MeetingApiService mApi;

    @Mock
    Context contextMock;

    @Before
    public void setup() {
        initMocks(this);
        mApi = DI.sMeetingApiService;
    }

    @Test
    public void initializeRoomsWithSuccess() {
        int nbRoomExpected = 10;

        //Avant initialisation on doit avoir 0 room
        int nbGetRooms = mApi.getRooms().size();
        assertEquals(0,nbGetRooms);

        //Après initialisation on doit avoir 10 rooms
        mApi.initializeRooms(contextMock);
        nbGetRooms = mApi.getRooms().size();

        assertEquals(nbGetRooms,nbRoomExpected);
    }
}
