package com.ocr.mareu.utils;

import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.ocr.mareu.di.DI.sMeetingApiService;

/**
 * Created by Florence LE BOURNOT on 27/01/2020
 */

public class SortOrFilter {

    public static final String SORT_ROOM = "SORT_ROOM";
    public static final String SORT_DATE = "SORT_DATE";
    public static final String FILTER_ROOM = "FILTER_ROOM";
    public static final String FILTER_DATE = "FILTER_DATE";
    public static final String SORT_DEFAULT = "SORT_DEFAULT";
    public static final String FILTER_EMPTY = "FILTER_EMPTY";

    private static boolean isSortAscRoom = false;
    private static boolean isSortAscDate = false;

    /**
     * Appelle la méthode adéquat en fonction de pOrder (trie ou filtre)
     * @param pMeetings : list : liste des réunions
     * @param pOrder : string : indicateur de trie ou de filtre
     * @return : list : liste des réunions triée ou filtrée
     */
    public List<Meeting> sortOrFilter(List<Meeting> pMeetings, String pOrder) {
        if (pOrder.contains("SORT")) {
            return sortMeeting(pMeetings, pOrder);
        } else if (pOrder.contains("FILTER")) {
            return filterMeeting(pMeetings, pOrder);
        } else {
            return pMeetings;
        }
    }

    /**
     * Filtre les réunions en fonction du paramètre pOrder (salle de réunion ou date)
     * @param pMeetings : list : liste des réunions
     * @param pOrder : string : indicateur de filtre
     * @return : list : liste des réunions filtrée
     */
    public List<Meeting> filterMeeting(List<Meeting> pMeetings,String pOrder) {
        List<Meeting> lMeetingRoomFiltered = new ArrayList<>();
        switch (pOrder) {
            case FILTER_ROOM:
                List<Room> lListRoomsSelected = sMeetingApiService.getRoomsSelected();
                for (Room lListRoom : lListRoomsSelected) {
                    for (int i = 0; i < pMeetings.size(); i++) {
                        if (pMeetings.get(i).getRoom().getNameRoom().equals(lListRoom.getNameRoom())) {
                            lMeetingRoomFiltered.add(pMeetings.get(i));
                        }
                    }
                }
                return lMeetingRoomFiltered;
            case FILTER_DATE:
                Calendar lDateSelected = sMeetingApiService.getDateSelected();
                String lCalSelectedFormat = new SimpleDateFormat("d/MM/yyyy").format(lDateSelected.getTime());
                String lCalFormat = "";

                for (Meeting lMeeting : pMeetings) {
                    lCalFormat = new SimpleDateFormat("d/MM/yyyy").format(lMeeting.getDate().getTime());
                    if (lCalFormat.equals(lCalSelectedFormat)) {
                        lMeetingRoomFiltered.add(lMeeting);
                    }
                }

                return lMeetingRoomFiltered;
            default :
                return pMeetings;
        }
    }

    /**
     * Trie les réunions en fonction du paramètre pOrder (salle de réunion ou date)
     * de façon ascendante ou descendante
     * @param pMeetings : list : liste des réunions
     * @param pOrder : string : indicateur de trie
     * @return : list : liste des réunions triée
     */
    public List<Meeting>sortMeeting(List<Meeting> pMeetings,String pOrder) {

        switch (pOrder) {
            case SORT_ROOM:
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
            case SORT_DATE:
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
            default :
                return pMeetings;
        }
    }
}
