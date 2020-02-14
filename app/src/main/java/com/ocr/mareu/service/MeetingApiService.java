package com.ocr.mareu.service;

import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 16/01/2020
 */
public interface MeetingApiService {

    /**
     * Récupère la liste des réunions
     * @return : objet : liste des réunions
     */
    List<Meeting> getMeetings();

    void setMeetingSelected(Meeting pMeeting);

    Meeting getMeetingSelected();

    void setDateSelected(Calendar pDateSelected);

    Calendar getDateSelected();

    void setRoomsSelected(List<Room> pRoomsSelected);

    List<Room> getRoomsSelected();


    /**
     * Récupère la liste des salles de réunion
     * @return : objet : liste des salles de réunion
     */
    List<Room> getRooms();

    /**
     * Recherche et récupère la salle de réunion sélectionnée
     * @return : objet : salle de réunion sélectionnée
     */
    Room extractRoomSelected(String pRoom);

    /**
     * Suppression d'une réunion
     * @param pMeeting : objet : réunion à supprimer
     */
    void deleteMeeting (Meeting pMeeting);

    /**
     * Ajout d'une réunion en validant que la salle est disponible pour le créneau horaire
     * @param pMeeting : objet : réunion à ajouter
     * @throws MeetingApiServiceException
     */
    void addMeeting (Meeting pMeeting) throws MeetingApiServiceException;

    /**
     * Purge de la liste des réunions suite à rotation de l'écran ou sortie de l'application
     */
    void resetMeetings();

    void addFakeMeeting();
}
