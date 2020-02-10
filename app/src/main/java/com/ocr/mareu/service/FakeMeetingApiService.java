package com.ocr.mareu.service;

import android.graphics.Color;

import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ocr.mareu.utils.SortFilters.sortOrFilter;

/**
 * Created by Florence LE BOURNOT on 16/01/2020
 */
public class FakeMeetingApiService implements MeetingApiService {

    private List<Meeting> mMeetings ;
    private final List<Room> mRooms;

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

    /**
     * Récupère la liste des réunions triées ou filtrées
     * @param pOrder : string : ordre de trie ou de filtre
     * @return : objet : liste des réunions triées ou filtrées
     */
    @Override
    public List<Meeting> getMeetingsSortOrFilter(String pOrder) {

        return sortOrFilter(mMeetings,pOrder);
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
    public Room getRoomSelected(String pRoom) {
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
}
