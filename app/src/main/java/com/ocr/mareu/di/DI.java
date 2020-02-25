package com.ocr.mareu.di;

import com.ocr.mareu.service.FakeMeetingApiService;
import com.ocr.mareu.service.MeetingApiService;

/**
 * Created by Florence LE BOURNOT on 16/01/2020
 */
public class DI {

    //private DI(){}
    /**
     * Création d'une nouvelle instance de l'API Service
     */
    private static MeetingApiService sApiService = new FakeMeetingApiService();

    public static MeetingApiService getMeetingApiService() { return sApiService; }

    public static MeetingApiService getMeetingApiServiceNewInstance() {
        sApiService = new FakeMeetingApiService();
    return sApiService; }

}
