package com.ocr.mareu.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.Toast;

import com.ocr.mareu.R;
import com.ocr.mareu.model.Room;
import com.ocr.mareu.ui.fragments.ListFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.ocr.mareu.di.DI.sMeetingApiService;
import static com.ocr.mareu.utils.SortOrFilter.FILTER_DATE;
import static com.ocr.mareu.utils.SortOrFilter.FILTER_ROOM;

/**
 * Created by Florence LE BOURNOT on 07/02/2020
 */
public class ShowDialog {

    /**
     * Affichage de la boîte de dialogue contenant les salles de réunion pour l'application du filtre par salle
     */

    public static void showDialogRooms(Context pContext, ListFragment pListFragment) {

        AlertDialog.Builder lBuilder = new AlertDialog.Builder(pContext);
        lBuilder.setTitle(R.string.meeting_room_selected);

        List<Room> lRooms = sMeetingApiService.getRooms();
        List<Room> lRoomsSelected = new ArrayList<>();
        List<String> lListRooms = new ArrayList<>();

        for (Room lRoom : lRooms) {
            lListRooms.add(lRoom.getNameRoom());
        }

        final CharSequence[] charSequenceItems = lListRooms.toArray(new CharSequence[lListRooms.size()]);

        lBuilder.setMultiChoiceItems(charSequenceItems, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    lRoomsSelected.add(lRooms.get(which));
                }
            }
        });
        lBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        });
        lBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sMeetingApiService.setRoomsSelected(lRoomsSelected);
                pListFragment.listToUpdate(FILTER_ROOM);
            }
        });
        Dialog lDialog = lBuilder.create();
        lDialog.show();
    }

    /**
     * Affichage du calendrier pour l'application du filtre par date
     * @param pContext
     */
    public static void showCalendarDialog(Context pContext, ListFragment pListFragment) {

        Calendar lCalendar = Calendar.getInstance();

       DatePickerDialog lDatePickerDialog = new DatePickerDialog(
                pContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar lCalendarSelected = Calendar.getInstance();

                        lCalendarSelected.set(year,month,dayOfMonth);
                        if (lCalendarSelected .before(lCalendar)) {
                            Toast.makeText(pContext, R.string.err_anterior_date, Toast.LENGTH_SHORT).show();
                        } else {
                            sMeetingApiService.setDateSelected(lCalendarSelected);
                            pListFragment.listToUpdate(FILTER_DATE);
                        }
                    }
                },
                lCalendar.get(Calendar.YEAR),
                lCalendar.get(Calendar.MONTH),
                lCalendar.get(Calendar.DAY_OF_MONTH)
        );
        lDatePickerDialog.show();
    }
}
