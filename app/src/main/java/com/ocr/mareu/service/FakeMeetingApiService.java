package com.ocr.mareu.service;

import android.content.Context;
import android.graphics.Color;

import com.ocr.mareu.R;
import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.ocr.mareu.utils.DateConverter.convertDateTimeStringToCalendar;

/**
 * Created by Florence LE BOURNOT on 16/01/2020
 */
public class FakeMeetingApiService implements MeetingApiService {

    private List<Meeting> mMeetings ;
    private List<Room> mRooms = new ArrayList<>();
    private Meeting mMeetingSelected;
    private Meeting mMeetingDeleted;
    private Calendar mDateSelected;
    private List<Room> mRoomsSelected;
    private Calendar mStartMeeting;
    private Calendar mEndMeeting;
    private boolean isMenuActive = false;

    public boolean sIsExecutedOneTimeForTest = false ;

    public static final String CST_FORMAT_DATE = "dd/MM/yyyy";
    public static final String CST_FORMAT_DATE_TIME = "dd/MM/yyyy HH:mm:ss:SS";

    /**
     * Constructor initialisant les listes de réunion et de salle
     */
    public FakeMeetingApiService() {
        mMeetings = new ArrayList<>();
    }

    /**
     * Sauvegarde l'indicateur de l'état du menu. Actif : true, Inactif : false
     * @param pIsMenuActive : boolean : indicateur de l'état du menu
     */
    public void setIsMenuActive (boolean pIsMenuActive) { isMenuActive = pIsMenuActive; }

    /**
     * Récupère l'indicateur de l'état du menu. Actif : true, Inactif : false
     * @return : boolean : indicateur de l'état du menu
     */
    public boolean getIsMenuActive () { return isMenuActive; }

    /**
     * Initialise la liste des salles de réunions disponibles
     * @param pContext : context : context
     */
    @Override
    public void initializeRooms(Context pContext) {

        mRooms = new ArrayList<>(Arrays.asList(
                new Room ("ARES", Color.parseColor(pContext.getString(R.string.s_color_room1))),
                new Room ("ATHENA", Color.parseColor(pContext.getString(R.string.s_color_room2))),
                new Room ("CRONOS", Color.parseColor(pContext.getString(R.string.s_color_room3))),
                new Room ("DEMETER", Color.parseColor(pContext.getString(R.string.s_color_room4))),
                new Room ("GAIA", Color.parseColor(pContext.getString(R.string.s_color_room5))),
                new Room ("HADES", Color.parseColor(pContext.getString(R.string.s_color_room6))),
                new Room ("PLUTON", Color.parseColor(pContext.getString(R.string.s_color_room7))),
                new Room ("POSEIDON", Color.parseColor(pContext.getString(R.string.s_color_room8))),
                new Room ("VENUS", Color.parseColor(pContext.getString(R.string.s_color_room9))),
                new Room ("ZEUS", Color.parseColor(pContext.getString(R.string.s_color_room10)))
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

    /**
     * Sauvegarde la réunion sélectionnée
     * @param pMeeting : objet : réunion sélectionnée
     */
    @Override
    public void setMeetingSelected(Meeting pMeeting) { mMeetingSelected = pMeeting; }

    /**
     * Récupère la réunion sélectionnée
     * @return : objet : réunion sélectionnée
     */
    @Override
    public Meeting getMeetingSelected() { return mMeetingSelected; }

    /**
     * Sauvegarde la date sélectionnée pour l'application du filtre
     * @param pDateSelected : calendar : date sélectionnée
     */
    @Override
    public void setDateSelected(Calendar pDateSelected) { mDateSelected = pDateSelected; }

    /**
     * Récupère la date sélectionnée pour l'application du filtre
     * @return : calendar : date sélectionnée
     */
    @Override
    public Calendar getDateSelected() { return mDateSelected; }

    /**
     * Sauvegarde les salles de réunion sélectionnées pour l'application du filtre
     * @param pRoomsSelected : object : liste des salles de réunion sélectionnées
     */
    @Override
    public void setRoomsSelected(List<Room> pRoomsSelected) { mRoomsSelected = pRoomsSelected; }

    /**
     * Récupère la liste des salles de réunion sélectionnées pour l'application du filtre
     * @return : objet : liste des salles de réunion sélectionnées
     */
    @Override
    public List<Room> getRoomsSelected() { return mRoomsSelected; }

    /**
     * Récupère la liste des salles de réunion
     * @return : objet : liste des salles de réunion
     */
    @Override
    public List<Room> getRooms() {
        return mRooms;
    }

    /**
     * Recherche et récupère la salle de réunion sélectionnée pour l'enregistrement de la réunion
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
     * Sauvegarde l'heure de début de la réunion pour les tests de validation avant l'enregistrement de la réunion
     * @param pStartMeeting : calendar : heure de début de la réunion
     */
    public void setStartMeeting(Calendar pStartMeeting) { mStartMeeting = pStartMeeting; }

    /**
     * Récupère l'heure de début de la réunion
     * @return : calendar : heure de début de la réunion
     */
    public Calendar getStartMeeting() { return mStartMeeting; }

    /**
     * Sauvegarde l'heure de fin de la réunion pour les tests de validation avant l'enregistrement de la réunion
     * @param pEndMeeting : calendar : heure de fin de la réunion
     */
    public void setEndMeeting(Calendar pEndMeeting) { mEndMeeting = pEndMeeting; }

    /**
     * Récupère l'heure de fin de laréunion
     * @return : calendar : heure de fin de la réunion
     */
    public Calendar getEndMeeting () { return mEndMeeting; }

    /**
     * Sauvegarde la réunion qui a été supprimée pour fermer l'écran de détail si celui-ci
     * contient l'élément supprimé
     * @param pMeeting : meeting : réunion supprimée
     */
    @Override
    public void setMeetingDeleted (Meeting pMeeting) { mMeetingDeleted = pMeeting; }

    /**
     * Récupère la réunion supprimée
     * @return : meeting : réunion supprimée
     */
    @Override
    public Meeting getMeetingDeleted () {
        return mMeetingDeleted;
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

    /**
     * Sauvegarde l'indicateur d'exécution pour le chargement des réunions exemples pour la démo
     * Indicateur qui permet de ne charger la liste qu'une seule fois à l'ouverture
     * @param pIsExecutedOneTimeForTest
     */
    public void setIsExecutedOneTimeForTest(boolean pIsExecutedOneTimeForTest) {
        sIsExecutedOneTimeForTest = pIsExecutedOneTimeForTest;
    }

    /**
     * Récupère l'indicateur d'exécution du chargement des réunions exemples pour la démo
     * @return : boolean : indicateur d'exécution de chargement des données
     */
    public boolean getIsExecutedOneTimeForTest() {
        return sIsExecutedOneTimeForTest;
    }

    /**
     * Ajout de réunions pour les tests manuels longue liste
     */
    public void addFakeValidMeetingsLongList()  {

        try {
            addMeeting(
                new Meeting(new Room("POSEIDON", Color.argb(100,244,67,54)),
                    "Sujet",
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE,"30/08/2020"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"30/08/2020 14:00:00:00"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"30/08/2020 15:00:00:00"),
                        Arrays.asList("loki@marvel.com","thor@marvel.com","captainamerica@marvel.com","wintersoldier@marvel.com")));
        } catch (MeetingApiServiceException pE) {
            pE.printStackTrace();
        }
        try {
            addMeeting(
                new Meeting(new Room("ARES", Color.argb(100,103,58,183)),
                    "Sujet2",
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 10:00:00:00"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 11:00:00:00"),
                    Arrays.asList("toto@gmail.com","titi@gmail.com")));
        } catch (MeetingApiServiceException pE) {
            pE.printStackTrace();
        }
        try {
            addMeeting(
                new Meeting(new Room("HADES", Color.argb(100,3,169,244)),
                    "Sujet3",
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/09/2020"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/09/2020 14:00:00:00"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/09/2020 15:00:00:00"),
                    Arrays.asList("toto@gmail.com","titi@gmail.com")));
        } catch (MeetingApiServiceException pE) {
            pE.printStackTrace();
        }
        try {
            addMeeting(
                new Meeting(new Room("ZEUS", Color.argb(100,233,30,99)),
                    "Sujet4",
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 09:00:00:00"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/08/2020 10:00:00:00"),
                    Arrays.asList("toto@gmail.com","titi@gmail.com")));
        } catch (MeetingApiServiceException pE) {
            pE.printStackTrace();
        }
        try {
            addMeeting(
                new Meeting(new Room("ARES", Color.argb(100,103,58,183)),
                    "Sujet5",
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE,"22/08/2020"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"22/08/2020 16:00:00:00"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"22/08/2020 17:00:00:00"),
                    Arrays.asList("toto@gmail.com","titi@gmail.com")));
        } catch (MeetingApiServiceException pE) {
            pE.printStackTrace();
        }
        try {
            addMeeting(
                new Meeting(new Room("PLUTON", Color.argb(100,63,81,181)),
                    "Sujet6",
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE,"22/08/2020"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"22/08/2020 10:00:00:00"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"22/08/2020 12:00:00:00"),
                    Arrays.asList("toto@gmail.com","titi@gmail.com")));
        } catch (MeetingApiServiceException pE) {
            pE.printStackTrace();
        }
        try {
            addMeeting(
                new Meeting(new Room("VENUS", Color.argb(100,156,39,176)),
                    "Sujet7",
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 12:00:00:00"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 14:00:00:00"),
                    Arrays.asList("toto@gmail.com","titi@gmail.com")));
        } catch (MeetingApiServiceException pE) {
            pE.printStackTrace();
        }
        try {
            addMeeting(
                new Meeting(new Room("DEMETER", Color.argb(100,76,175,80)),
                    "Sujet8",
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/09/2020"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/09/2020 16:00:00:00"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"15/09/2020 18:00:00:00"),
                    Arrays.asList("toto@gmail.com","titi@gmail.com")));
        } catch (MeetingApiServiceException pE) {
            pE.printStackTrace();
        }
        try {
            addMeeting(
                new Meeting(new Room("HADES", Color.argb(100,3,169,244)),
                    "Sujet9",
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 09:00:00:00"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 13:00:00:00"),
                    Arrays.asList("toto@gmail.com","titi@gmail.com")));
        } catch (MeetingApiServiceException pE) {
            pE.printStackTrace();
        }
        try {
            addMeeting(
                new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                    "Sujet10 reunion avec quarante caractères",
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 10:00:00:00"),
                    convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 12:00:00:00"),
                    Arrays.asList("toto@gmail.com","titi@gmail.com")));
        } catch (MeetingApiServiceException pE) {
            pE.printStackTrace();
        }
    }
}
