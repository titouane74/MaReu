package com.ocr.mareu.utils;

import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 27/01/2020
 */

public class SortOrFilter {

    /**
     * Filtre les réunions en fonctions des salles de réunion sélectionnées
     * @param pMeetings : objet : liste des réunions
     * @param pListRoomsSelected : objet : liste des salles sélectionnées
     * @return : objet : liste des réunions filtrée
     */
    public static List<Meeting> filterMeetingRoom(List<Meeting> pMeetings,List<Room> pListRoomsSelected ) {
        List<Meeting> lMeetingRoomFiltered = new ArrayList<>();
        for (Room lListRoom : pListRoomsSelected) {
            for (int i = 0; i < pMeetings.size(); i++) {
                if (pMeetings.get(i).getRoom().getNameRoom().equals(lListRoom.getNameRoom())) {
                    lMeetingRoomFiltered.add(pMeetings.get(i));
                }
            }
        }
        return lMeetingRoomFiltered;
    }

    /**
     * Filtre les réunions en fonction de la date sélectionnée
     * @param pMeetings : objet : liste des réunions
     * @param pDateSelected : calendar : date sélectionnée
     * @return : objet : liste des réunions filtrée
     */
    public static  List<Meeting> filterMeetingDate(List<Meeting> pMeetings, Calendar pDateSelected) {
        List<Meeting> lMeetingDateFiltered = new ArrayList<>();
        String lCalSelectedFormat = new SimpleDateFormat("dd/MM/yyyy").format(pDateSelected.getTime());
        String lCalFormat;

        for (Meeting lMeeting : pMeetings) {
            lCalFormat = new SimpleDateFormat("dd/MM/yyyy").format(lMeeting.getDate().getTime());
            if (lCalFormat.equals(lCalSelectedFormat)) {
                lMeetingDateFiltered.add(lMeeting);
            }
        }
        return lMeetingDateFiltered;
    }

    /**
     * Tri la liste des réunions par ordre alphabétique ascendant
     * @param pMeetings : obet : liste des réunions
     * @return : objet : liste des réunions triée
     */
    public static List<Meeting>sortMeetingRoomAsc(List<Meeting> pMeetings) {

        Collections.sort(pMeetings, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting o1, Meeting o2) {
                return o1.getRoom().getNameRoom().compareTo(o2.getRoom().getNameRoom());
            }
        });

        return pMeetings;
    }

    /**
     * Tri la liste des réunions par ordre alphabétique descendant
     * @param pMeetings : obet : liste des réunions
     * @return : objet : liste des réunions triée
     */
    public static List<Meeting>sortMeetingRoomDesc(List<Meeting> pMeetings) {

        Collections.sort(pMeetings, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting o1, Meeting o2) {
                return o2.getRoom().getNameRoom().compareTo(o1.getRoom().getNameRoom());
            }
        });

        return pMeetings;
    }


    /**
     * Tri la liste des réunions par date de la plus ancienne à la plus récente
     * @param pMeetings : obet : liste des réunions
     * @return : objet : liste des réunions triée
     */
    public static List<Meeting> sortMeetingDateOlderToRecent(List<Meeting> pMeetings) {

        Collections.sort(pMeetings, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting o1, Meeting o2) {
                if (o1.getStart().compareTo(o2.getStart()) == 0) {
                    return o1.getEnd().compareTo(o2.getEnd());
                } else {
                    return o1.getStart().compareTo(o2.getStart());
                }
            }
        });

        return pMeetings;
    }

    /**
     * Tri la liste des réunions par date de la plus récente à la plus ancienne
     * @param pMeetings : obet : liste des réunions
     * @return : objet : liste des réunions triée
     */
    public static List<Meeting> sortMeetingDateRecentToOlder(List<Meeting> pMeetings) {

        Collections.sort(pMeetings, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting o1, Meeting o2) {
                if (o2.getStart().compareTo(o1.getStart()) == 0) {
                    return o2.getEnd().compareTo(o1.getEnd());
                } else {
                    return o2.getStart().compareTo(o1.getStart());
                }
            }
        });

        return pMeetings;
    }
}
