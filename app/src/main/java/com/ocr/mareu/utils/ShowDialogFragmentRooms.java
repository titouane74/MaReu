package com.ocr.mareu.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ocr.mareu.R;
import com.ocr.mareu.model.Room;
import com.ocr.mareu.ui.fragments.ListFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 07/02/2020
 */
public class ShowDialogFragmentRooms extends DialogFragment {

    /**
     * Affichage de la boîte de dialogue contenant les salles de réunion pour l'application du filtre par salle
     */
    /**
     * Override to build your own custom Dialog container.  This is typically
     * used to show an AlertDialog instead of a generic Dialog; when doing so,
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} does not need
     * to be implemented since the AlertDialog takes care of its own content.
     *
     * <p>This method will be called after {@link #onCreate(Bundle)} and
     * before {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.  The
     * default implementation simply instantiates and returns a {@link Dialog}
     * class.
     *
     * <p><em>Note: DialogFragment own the {@link Dialog#setOnCancelListener
     * Dialog.setOnCancelListener} and {@link Dialog#setOnDismissListener
     * Dialog.setOnDismissListener} callbacks.  You must not set them yourself.</em>
     * To find out about these events, override {@link #onCancel(DialogInterface)}
     * and {@link #onDismiss(DialogInterface)}.</p>
     *
     * @param savedInstanceState The last saved instance state of the Fragment,
     *                           or null if this is a freshly created Fragment.
     * @return Return a new Dialog instance to be displayed by the Fragment.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder lBuilder = new AlertDialog.Builder(getActivity());
        lBuilder.setTitle(R.string.meeting_room_selected);

        List<Room> lRoomsSelected = new ArrayList<>();
        List<String> lListRooms = new ArrayList<>();

        List<Room> lRoomList =

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


        return lBuilder.create();
    }

}
