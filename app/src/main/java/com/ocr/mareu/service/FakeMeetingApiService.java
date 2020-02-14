package com.ocr.mareu.service;

import android.graphics.Color;

import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.ocr.mareu.di.DI.sMeetingApiService;
import static com.ocr.mareu.utils.DateConverter.convertDateTimeStringToCalendar;

/**
 * Created by Florence LE BOURNOT on 16/01/2020
 */
public class FakeMeetingApiService implements MeetingApiService {

    private List<Meeting> mMeetings ;
    private final List<Room> mRooms;
    private Meeting mMeetingSelected;
    private Calendar mDateSelected;
    private List<Room> mRoomsSelected;


    public static final String CST_FORMAT_DATE = "dd/MM/yyyy";
    public static final String CST_FORMAT_DATE_TIME = "dd/MM/yyyy HH:mm:ss:SS";
    public static final String CST_FORMAT_TIME = "HH:mm:ss:SS";

    /**
     * Constructor initialisant les listes de réunion et de salle
     */
    public FakeMeetingApiService() {
        mMeetings = new ArrayList<>();
        mRooms = new ArrayList<>(Arrays.asList(
            new Room ("ARES", Color.parseColor("#F44336")),
            new Room ("ATHENA", Color.parseColor("#F57555")),
            new Room ("CRONOS", Color.parseColor("#FFEB3B")),
            new Room ("DEMETER", Color.parseColor("#8BC34A")),
            new Room ("GAIA", Color.parseColor("#009688")),
            new Room ("HADES", Color.parseColor("#00BCD4")),
            new Room ("PLUTON", Color.parseColor("#3F51B5")),
            new Room ("POSEIDON", Color.parseColor("#673AB7")),
            new Room ("VENUS", Color.parseColor("#9C27B0")),
            new Room ("ZEUS", Color.parseColor("#E91E63"))
        )) ;
    }



    /**
     * Récupère la liste des réunions
     * @return : objet : liste des réunions
     */
    @Override
    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    @Override
    public void setMeetingSelected(Meeting pMeeting) {
        mMeetingSelected = pMeeting;
    }

    @Override
    public Meeting getMeetingSelected() {
        return mMeetingSelected;
    }

    @Override
    public void setDateSelected(Calendar pDateSelected) {
        mDateSelected = pDateSelected;
    }

    @Override
    public Calendar getDateSelected() {
        return mDateSelected;
    }

    @Override
    public void setRoomsSelected(List<Room> pRoomsSelected) {
        mRoomsSelected = pRoomsSelected;
    }

    @Override
    public List<Room> getRoomsSelected() {
        return mRoomsSelected;
    }


    /**
     * Récupère la liste des salles de réunion
     * @return : objet : liste des salles de réunion
     */
    @Override
    public List<Room> getRooms() {
        return mRooms;
    }

    /**
     * Recherche et récupère la salle de réunion sélectionnée
     * @return : objet : salle de réunion sélectionnée
     */
    @Override
    public Room extractRoomSelected(String pRoom) {
        Room lRoom = null;
        for (Room lRooms : mRooms) {
            if (lRooms.getNameRoom().equals(pRoom))
                lRoom = lRooms;
        }
        return lRoom;
    }


    /**
     * Suppression d'une réunion
     * @param pMeeting : objet : réunion à supprimer
     */
    @Override
    public void deleteMeeting(Meeting pMeeting) {
        mMeetings.remove(pMeeting);
    }

    /**
     * Ajout d'une réunion en validant que la salle est disponible pour le créneau horaire
     * @param pMeeting : objet : réunion à ajouter
     * @throws MeetingApiServiceException
     */
    @Override
    public void addMeeting(Meeting pMeeting) throws MeetingApiServiceException {

        //pMeeting = réunion à valider
        //lMeetings = meeting déjà validé
        for (Meeting lMeetings : mMeetings) {
            if (pMeeting.getRoom().getNameRoom().equals(lMeetings.getRoom().getNameRoom())) {
                //Réunion début = Meeting début || Réunion fin = Meeting fin => pas d'ajout
                if (pMeeting.getStart().equals(lMeetings.getStart())
                        || pMeeting.getEnd().equals(lMeetings.getEnd()))
                    throw new MeetingApiServiceException();

                //Réunion début before Meeting début && (Réunion fin after Meeting début || Réunion fin after Meeting fin) => pas d'ajout
                if (pMeeting.getStart().before(lMeetings.getStart())
                        && (pMeeting.getEnd().after(lMeetings.getStart()) || pMeeting.getEnd().after(lMeetings.getEnd())))
                    throw new MeetingApiServiceException();

                //Réunion début after Meeting début && Réunion début before Meeting fin
                // && (Réunion fin before Meeting fin || Réunion fin after Meeting fin) => pas d'ajout
                if (pMeeting.getStart().after(lMeetings.getStart()) && pMeeting.getStart().before(lMeetings.getEnd())
                        && (pMeeting.getEnd().before(lMeetings.getEnd()) || pMeeting.getEnd().after(lMeetings.getEnd())))
                    throw new MeetingApiServiceException();

            }
        }
        mMeetings.add(pMeeting);
    }

    /**
     * Purge de la liste des réunions suite à rotation de l'écran ou sortie de l'application
     */
    @Override
    public void resetMeetings() {
        mMeetings = new ArrayList<>();
    }


    public void addFakeMeeting()  {
        List<String> lStringList = Arrays.asList("toto@gmail.com","titi@gmail.com");
        Room lRoomTest = new Room("POSEIDON", Color.argb(100,103,58,183));
        List<String> lStringList2 = Arrays.asList("toto@gmail.com","titi@gmail.com");
        Room lRoomTest2 = new Room("ARES", Color.argb(100,244,67,54));

        try {
            sMeetingApiService.addMeeting(
                    new Meeting(lRoomTest,
                            "Sujet",
                            convertDateTimeStringToCalendar(CST_FORMAT_DATE,"30/08/2020"),
                            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"30/08/2020 14:00:00:00"),
                            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"30/08/2020 15:00:00:00"),
                            lStringList));
            sMeetingApiService.addMeeting(
                    new Meeting(lRoomTest2,
                            "Sujet2",
                            convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),
                            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 10:00:00:00"),
                            convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 11:00:00:00"),
                            lStringList2));
        } catch (MeetingApiServiceException | ParseException pE) {
            pE.printStackTrace();
        }
    }

}
