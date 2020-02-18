package com.ocr.mareu.service;

import android.content.Context;

import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 16/01/2020
 */
public interface MeetingApiService {

    /**
     * Sauvegarde l'indicateur de l'état du menu. Actif : true, Inactif : false
     * @param pIsMenuActive : boolean : indicateur de l'état du menu
     */
    void setIsMenuActive (boolean pIsMenuActive) ;

    /**
     * Récupère l'indicateur de l'état du menu. Actif : true, Inactif : false
     * @return : boolean : indicateur de l'état du menu
     */
    boolean getIsMenuActive () ;

    /**
     * Initialise la liste des salles de réunions disponibles
     * @param pContext : context : context
     */
    void initializeRooms(Context pContext);

    /**
     * Récupère la liste des réunions
     * @return : objet : liste des réunions
     */
    List<Meeting> getMeetings();

    /**
     * Sauvegarde la réunion sélectionnée
     * @param pMeeting : objet : réunion sélectionnée
     */
    void setMeetingSelected(Meeting pMeeting);

    /**
     * Récupère la réunion sélectionnée
     * @return : objet : réunion sélectionnée
     */
    Meeting getMeetingSelected();

    /**
     * Sauvegarde la date sélectionnée pour l'application du filtre
     * @param pDateSelected : calendar : date sélectionnée
     */
    void setDateSelected(Calendar pDateSelected);

    /**
     * Récupère la date sélectionnée pour l'application du filtre
     * @return : calendar : date sélectionnée
     */
    Calendar getDateSelected();

    /**
     * Sauvegarde les salles de réunion sélectionnées pour l'application du filtre
     * @param pRoomsSelected : object : liste des salles de réunion sélectionnées
     */
    void setRoomsSelected(List<Room> pRoomsSelected);

    /**
     * Récupère la liste des salles de réunion sélectionnées pour l'application du filtre
     * @return : objet : liste des salles de réunion sélectionnées
     */
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
     * Sauvegarde l'heure de début de la réunion pour les tests de validation avant l'enregistrement de la réunion
     * @param pStartMeeting : calendar : heure de début de la réunion
     */
    void setStartMeeting(Calendar pStartMeeting);

    /**
     * Récupère l'heure de début de la réunion
     * @return : calendar : heure de début de la réunion
     */
    Calendar getStartMeeting();

    /**
     * Sauvegarde l'heure de fin de la réunion pour les tests de validation avant l'enregistrement de la réunion
     * @param pEndMeeting : calendar : heure de fin de la réunion
     */
    void setEndMeeting(Calendar pEndMeeting) ;

    /**
     * Récupère l'heure de fin de laréunion
     * @return : calendar : heure de fin de la réunion
     */
    Calendar getEndMeeting ();

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

    /**
     * Ajout de réunions pour les tests
     */
    void addFakeMeeting();

}
