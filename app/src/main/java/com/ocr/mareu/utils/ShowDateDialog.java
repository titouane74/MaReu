package com.ocr.mareu.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.ocr.mareu.R;
import com.ocr.mareu.ui.fragments.ListFragment;

import java.util.Calendar;

/**
 * Created by Florence LE BOURNOT on 07/02/2020
 */
public class ShowDateDialog extends DialogFragment {

    /**
     * Affichage du calendrier pour l'application du filtre par date
     * @param pContext : context : context
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
