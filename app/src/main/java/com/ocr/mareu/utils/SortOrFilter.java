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

    private static boolean isSortAscRoom = false;
    private static boolean isSortAscDate = false;


    /**
     * Filtre les réunions en fonction du paramètre pOrder (salle de réunion ou date)
     * @param pMeetings : list : liste des réunions
     * @return : list : liste des réunions filtrée
     */
    public List<Meeting> filterMeetingRoom(List<Meeting> pMeetings,List<Room> pListRoomsSelected ) {
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

    public List<Meeting> filterMeetingDate(List<Meeting> pMeetings, Calendar pDateSelected) {
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
     * Trie les réunions en fonction du paramètre pOrder (salle de réunion ou date)
     * de façon ascendante ou descendante
     * @param pMeetings : list : liste des réunions
     * @return : list : liste des réunions triée
     */
    public List<Meeting>sortMeetingRoom(List<Meeting> pMeetings) {

        if (!isSortAscRoom) {
            Collections.sort(pMeetings, new Comparator<Meeting>() {
                @Override
                public int compare(Meeting o1, Meeting o2) {
                    return o1.getRoom().getNameRoom().compareTo(o2.getRoom().getNameRoom());
                }
            });
            isSortAscRoom = true;
        } else {
            Collections.reverse(pMeetings);
            isSortAscRoom = false;
        }
        return pMeetings;
    }

    public List<Meeting> sortMeetingDate(List<Meeting> pMeetings) {

        if (!isSortAscDate) {
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
            isSortAscDate = true;
        } else {
            Collections.reverse(pMeetings);
            isSortAscDate = false;
        }
        return pMeetings;
    }

}
