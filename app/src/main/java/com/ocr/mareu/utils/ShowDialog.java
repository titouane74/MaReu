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

/**
 * Created by Florence LE BOURNOT on 07/02/2020
 */
public class ShowDialog {

    /**
     * Affichage de la boîte de dialogue contenant les salles de réunion pour l'application du filtre par salle
     */

    public static List<Room> showDialogRooms(Context pContext, ListFragment pListFragment,List<Room> pRooms) {

        AlertDialog.Builder lBuilder = new AlertDialog.Builder(pContext);
        lBuilder.setTitle(R.string.meeting_room_selected);

        List<Room> lRoomsSelected = new ArrayList<>();
        List<String> lListRooms = new ArrayList<>();

        for (Room lRoom : pRooms) {
            lListRooms.add(lRoom.getNameRoom());
        }

        final CharSequence[] charSequenceItems = lListRooms.toArray(new CharSequence[lListRooms.size()]);

        lBuilder.setMultiChoiceItems(charSequenceItems, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    lRoomsSelected.add(pRooms.get(which));
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
                pListFragment.listToUpdate(SortOrFilterLabel.FILTER_ROOM);
            }
        });
        Dialog lDialog = lBuilder.create();
        lDialog.show();

        return lRoomsSelected;
    }

    /**
     * Affichage du calendrier pour l'application du filtre par date
     * @param pContext
     */
    public static Calendar showCalendarDialog(Context pContext, ListFragment pListFragment) {

        Calendar lCalendar = Calendar.getInstance();
        Calendar lCalendarSelected = Calendar.getInstance();

       DatePickerDialog lDatePickerDialog = new DatePickerDialog(
                pContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        lCalendarSelected.set(year,month,dayOfMonth);
                        if (lCalendarSelected .before(lCalendar)) {
                            Toast.makeText(pContext, R.string.err_anterior_date, Toast.LENGTH_SHORT).show();
                        } else {
                            pListFragment.listToUpdate(SortOrFilterLabel.FILTER_DATE);
                        }
                    }
                },
                lCalendar.get(Calendar.YEAR),
                lCalendar.get(Calendar.MONTH),
                lCalendar.get(Calendar.DAY_OF_MONTH)
        );
        lDatePickerDialog.show();

        return lCalendarSelected;
    }
}
