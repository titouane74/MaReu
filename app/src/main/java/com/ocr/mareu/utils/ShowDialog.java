package com.ocr.mareu.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ocr.mareu.R;
import com.ocr.mareu.model.Room;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//import static com.ocr.mareu.ui.MeetingListActivity.sCalendarSelected;
//import static com.ocr.mareu.ui.MeetingListActivity.sListRoomSelected;
import static com.ocr.mareu.di.DI.sMeetingApiService;
import static com.ocr.mareu.utils.SortOrFilter.FILTER_ROOM;

/**
 * Created by Florence LE BOURNOT on 07/02/2020
 */
public class ShowDialog {

    /**
     * Affichage de la boîte de dialogue contenant les salles de réunion pour l'application du filtre par salle
     */
    public static void showDialogRooms(Context pContext, RecyclerView pRecyclerView) {

        AlertDialog.Builder lBuilder = new AlertDialog.Builder(pContext);
        lBuilder.setTitle(R.string.meeting_room_selected);

        List<Room> lRooms = sMeetingApiService.getRooms();
        List<Room> lRoomsSelected = new ArrayList<>();
        List<String> lListRooms = new ArrayList<String>();

        for (Room lRoom : lRooms) {
            lListRooms.add(lRoom.getNameRoom());
        }

        final CharSequence[] charSequenceItems = lListRooms.toArray(new CharSequence[lListRooms.size()]);

        lBuilder.setMultiChoiceItems(charSequenceItems, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked)
                    lRoomsSelected.add(lRooms.get(which));
//                sListRoomSelected = lRoomsSelected;
//                ConfigureAdapter.configureAdapter(pContext, FILTER_ROOM, pRecyclerView);
            }
        });
        lBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        });
        lBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        });
        Dialog lDialog = lBuilder.create();
        lDialog.show();
    }

    /**
     * Affichage du calendrier pour l'application du filtre par date
     * @param pContext
     */
    public static void showCalendarDialog(Context pContext, RecyclerView pRecyclerView) {

        Calendar lCalendar = Calendar.getInstance();

        DatePickerDialog lDatePickerDialog = new DatePickerDialog(
                pContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                        sCalendarSelected = Calendar.getInstance();

                        Toast.makeText(pContext, R.string.err_anterior_date, Toast.LENGTH_SHORT).show();
/*
                        sCalendarSelected.set(year,month,dayOfMonth);
                            Toast.makeText(pContext, R.string.err_anterior_date, Toast.LENGTH_SHORT).show();
                        } else {
                            ConfigureAdapter.configureAdapter(pContext, FILTER_DATE, pRecyclerView);
                        }
*/
                    }
                },
                lCalendar.get(Calendar.YEAR),
                lCalendar.get(Calendar.MONTH),
                lCalendar.get(Calendar.DAY_OF_MONTH)
        );
        lDatePickerDialog.show();
    }
}
