package com.ocr.mareu.di;

import com.ocr.mareu.service.FakeMeetingApiService;
import com.ocr.mareu.service.MeetingApiService;

/**
 * Created by Florence LE BOURNOT on 16/01/2020
 */
public class DI {

    /**
     * Constructor du Dependencies Injection
     */
    private DI(){}

    /**
     * Déclaration d'une nouvelle instance d'Api Service
     */
    private static MeetingApiService sApiService = new FakeMeetingApiService();

    /**
     * Récupère l'ApiService en cours
     * @return : objet : api service
     */
    public static MeetingApiService getMeetingApiService() { return sApiService; }

    /**
     * Récupère une nouvelle instance d'Api Service
     * @return : objet : api service
     */
    public static MeetingApiService getMeetingApiServiceNewInstance() {
        sApiService = new FakeMeetingApiService();
    return sApiService; }

}
