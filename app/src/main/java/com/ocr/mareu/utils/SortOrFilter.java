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
     * Filtre les réunions en fonction du paramètre pOrder (salle de réunion ou date)
     * @param pMeetings : list : liste des réunions
     * @return : list : liste des réunions filtrée
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
     * Trie les réunions en fonction du paramètre pOrder (salle de réunion ou date)
     * de façon ascendante ou descendante
     * @param pMeetings : list : liste des réunions
     * @return : list : liste des réunions triée
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

    public static List<Meeting>sortMeetingRoomDesc(List<Meeting> pMeetings) {

        Collections.reverse(pMeetings);

        return pMeetings;
    }


    public static List<Meeting> sortMeetingDateAsc(List<Meeting> pMeetings) {

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

    public static List<Meeting> sortMeetingDateDesc(List<Meeting> pMeetings) {

        Collections.reverse(pMeetings);

        return pMeetings;
    }

}
