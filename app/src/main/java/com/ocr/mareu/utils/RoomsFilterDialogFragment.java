package com.ocr.mareu.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ocr.mareu.R;
import com.ocr.mareu.model.Room;

import java.util.ArrayList;
import java.util.List;

import static com.ocr.mareu.ui.activities.MainActivity.sApiService;

/**
 * Created by Florence LE BOURNOT on 24/02/2020
 */
public class RoomsFilterDialogFragment extends DialogFragment {

    private OnDialogRoomsListener mCallback;

    public interface OnDialogRoomsListener {
        void onRoomsOkClicked(List<Room> lRoomsSelected);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(getActivity());
        lBuilder.setTitle(R.string.meeting_room_selected);

        List<Room> lRoomsSelected = new ArrayList<>();
        List<String> lListRooms = new ArrayList<>();

        List<Room> lRoomList = sApiService.getRooms();
        for (Room lRoom : lRoomList) {
            lListRooms.add(lRoom.getNameRoom());
        }

        final CharSequence[] charSequenceItems = lListRooms.toArray(new CharSequence[lListRooms.size()]);


        lBuilder.setMultiChoiceItems(charSequenceItems, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    lRoomsSelected.add(lRoomList.get(which));
                }
            }
        });

        lBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {  }
        });
        lBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCallback.onRoomsOkClicked(lRoomsSelected);
            }
        });
        return lBuilder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mCallback = (OnDialogRoomsListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(e.toString()
                    + " must implement OnDialogRoomsListener");
        }
    }

}
