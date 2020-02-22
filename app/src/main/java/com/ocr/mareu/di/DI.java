package com.ocr.mareu.di;

import com.ocr.mareu.service.FakeMeetingApiService;
import com.ocr.mareu.service.MeetingApiService;

/**
 * Created by Florence LE BOURNOT on 16/01/2020
 */
public class DI {

    private DI(){}
    /**
     * Création d'une nouvelle instance de l'API Service
     */
    public static MeetingApiService sMeetingApiService = new FakeMeetingApiService();

    public static MeetingApiService getMeetingApiService() { return sMeetingApiService; }

    public static boolean sIsExecutedOneTimeForTest = false ;

}
