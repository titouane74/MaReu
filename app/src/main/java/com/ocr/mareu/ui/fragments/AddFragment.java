package com.ocr.mareu.ui.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ocr.mareu.R;
import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.model.Room;
import com.ocr.mareu.service.MeetingApiServiceException;
import com.ocr.mareu.utils.Validation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.ocr.mareu.di.DI.sMeetingApiService;
import static com.ocr.mareu.utils.Validation.CST_DATETIME;
import static com.ocr.mareu.utils.Validation.CST_EMAIL;
import static com.ocr.mareu.utils.Validation.CST_ROOM;
import static com.ocr.mareu.utils.Validation.CST_TOPIC;

public class AddFragment extends Fragment implements View.OnClickListener {

    private AutoCompleteTextView mListRoom;
    private ChipGroup mEmailGroup ;
    private TextInputLayout mListLayout, mEmail, mTopic, mDate, mTimeStart, mTimeEnd ;
    private TextInputEditText mEmailEt, mTopicEt, mDateEt, mTimeStartEt, mTimeEndEt ;
    private Button mBtnCancel, mBtnSave;

    private Calendar mDateCal;
    private Calendar mTimeStartFormated;
    private Calendar mTimeEndFormated;
    private Context mContext;

    private OnListenerAdd mCallback;

    public interface OnListenerAdd {
        void onButtonClickedClose(View pView, String pActivateFragment);
    }

    public AddFragment() { }

    public static AddFragment newInstance() {
        return new AddFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View lView = inflater.inflate(R.layout.fragment_scroll_add, container, false);
        mContext = lView.getContext();

        bindObjectToCode(lView);

        List<Room> lRooms = sMeetingApiService.getRooms();

        ArrayAdapter<Room> lAdapter = new ArrayAdapter<>(mContext,R.layout.activity_room_item,lRooms);
        mListRoom.setAdapter(lAdapter);
        mListRoom.setShowSoftInputOnFocus(false);
        mListRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListRoom.showDropDown();
            }
        });

        mEmailEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        String lEmailText = Objects.requireNonNull(mEmailEt.getText()).toString().trim();

                        if (!Validation.validationTextInputLayout(getContext(), CST_EMAIL, mEmail)) {
                            return false;
                        } else {
                            final Chip lChipEmail = new Chip(mContext);
                            lChipEmail.setText(lEmailText);
                            lChipEmail.setCloseIconVisible(true);
                            lChipEmail.setOnCloseIconClickListener(v1 -> mEmailGroup.removeView(lChipEmail));

                            mEmailGroup.addView(lChipEmail);
                            mEmailGroup.setVisibility(View.VISIBLE);
                            mEmailEt.setText("");
                            mEmail.setError(null);
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        mDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { displayCalendarDialog (); }
        });
        mTimeStartEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { displayTimeDialog(v); }
        });
        mTimeEndEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { displayTimeDialog(v); }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { mCallback.onButtonClickedClose(v, "RIGHT"); }
        });
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addMeeting())
                    mCallback.onButtonClickedClose(v, "RIGHT");
            }
        });
        return lView;
    }

    /**
     * OnClick du champs meeting_date_et : affiche la boîte de dialogue calendrier
     */
    private void displayCalendarDialog () {
        Calendar lCalendar = Calendar.getInstance();

        DatePickerDialog lDatePickerDialog = new DatePickerDialog(
                mContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        mDateCal = Calendar.getInstance();

                        mDateCal.set(year,month,dayOfMonth);
                        String lDate = lDateFormat.format(mDateCal.getTime());
                        if (mDateCal.before(lCalendar)) {
                            mDate.setError(getString(R.string.err_anterior_date));
                        } else {
                            mDateEt.setText(lDate);
                            mDate.setError(null);
                            mTimeStart.setEnabled(true);
                            mTimeEnd.setEnabled(true);
                        }
                    }
                },
                lCalendar.get(Calendar.YEAR),
                lCalendar.get(Calendar.MONTH),
                lCalendar.get(Calendar.DAY_OF_MONTH)
        );
        lDatePickerDialog.show();
    }

    /**
     * OnClick des champs meeting_start_et et meeting_end_et : affiche de la boîte de dialogue
     * de heures
     * @param pView : view : vue
     */
    private void displayTimeDialog(View pView) {
        final int lId = pView.getId();
        Calendar lTime = Calendar.getInstance();

        TimePickerDialog lTimePickerDialog = new TimePickerDialog(
                getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.FRENCH);
                        Calendar lTimeCal = Calendar.getInstance();

                        lTimeCal.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        lTimeCal.set(Calendar.MINUTE,minute);
                        lTimeCal.set(Calendar.SECOND,0);
                        lTimeCal.set(Calendar.MILLISECOND,0);
                        try {
                            lTimeCal.set(Calendar.DAY_OF_MONTH,mDateCal.get(Calendar.DAY_OF_MONTH));
                            lTimeCal.set(Calendar.MONTH,mDateCal.get(Calendar.MONTH));
                            lTimeCal.set(Calendar.YEAR,mDateCal.get(Calendar.YEAR));
                        } catch (NullPointerException e) {
                            Toast.makeText(getContext(), R.string.err_meeting_date_empty, Toast.LENGTH_SHORT).show();
                        }

                        String lTimeFormated = sdf.format(lTimeCal.getTime());

                        if (lId == R.id.meeting_start_et) {
                            mTimeStartEt.setText(lTimeFormated);
                            mTimeStartFormated = lTimeCal;
                            sMeetingApiService.setStartMeeting(lTimeCal);
                        } else if (lId == R.id.meeting_end_et) {
                            mTimeEndEt.setText(lTimeFormated);
                            mTimeEndFormated = lTimeCal;
                            sMeetingApiService.setEndMeeting(lTimeCal);
                        }
                    }
                },
                lTime.get(Calendar.HOUR_OF_DAY),
                lTime.get(Calendar.MINUTE),
                true
        );
        lTimePickerDialog.show();
    }

    /**
     * Ajout d'une réunion
     * @return : boolean : indicateur si la réunion a été ajouté ou non
     */
    private boolean addMeeting()  {
        Room lRoomSelected;

        boolean isValidDateTime = false;
        boolean isValidParticipants = false ;
        boolean isValidRoom = Validation.validationTextInputLayout(getContext(), CST_ROOM, mListLayout);
        boolean isValidTopic = Validation.validationTextInputLayout(getContext(), CST_TOPIC, mTopic);

        boolean isValidDate = Validation.validationTextInputLayout(getContext(), CST_DATETIME, mDate);
        boolean isValidTimeStart = Validation.validationTextInputLayout(getContext(), CST_DATETIME, mTimeStart);
        boolean isValidTimeEnd = Validation.validationTextInputLayout(getContext(), CST_DATETIME, mTimeEnd);

        if (isValidDate && isValidTimeStart && isValidTimeEnd  )
            isValidDateTime = Validation.validationDateTime(getContext(), mTimeStart, mTimeEnd);

        List<String> lParticipants = Validation.validationParticipants(getContext(), mEmail,mEmailGroup);

        if (lParticipants.size() > 0)
            isValidParticipants = true;

        if (!isValidTopic | !isValidParticipants | !isValidRoom | !isValidDate | !isValidTimeStart | !isValidTimeEnd | !isValidDateTime ) {
            Toast.makeText(getContext(),getString(R.string.action_add_meeting_missing_field), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            try {
                lRoomSelected = sMeetingApiService.extractRoomSelected(mListRoom.getText().toString());
                sMeetingApiService.addMeeting(
                        new Meeting(lRoomSelected, mTopicEt.getText().toString(), mDateCal, mTimeStartFormated, mTimeEndFormated, lParticipants));

                Toast.makeText(getContext(),getString(R.string.action_add_meeting), Toast.LENGTH_SHORT).show();
                return true;
            } catch  (MeetingApiServiceException pE)
            {
                Toast.makeText(getContext(), R.string.err_meeting_room_not_free, Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }

    private void bindObjectToCode(View pView) {
        mListRoom = pView.findViewById(R.id.room_list) ;
        mListLayout = pView.findViewById(R.id.room_list_layout) ;
        mEmail = pView.findViewById(R.id.email_address);
        mEmailEt = pView.findViewById(R.id.email_address_et) ;
        mEmailGroup = pView.findViewById(R.id.email_group_cg) ;
        mTopic = pView.findViewById(R.id.meeting_topic) ;
        mTopicEt = pView.findViewById(R.id.meeting_topic_et) ;
        mDate = pView.findViewById(R.id.meeting_date) ;
        mDateEt = pView.findViewById(R.id.meeting_date_et) ;
        mTimeStart = pView.findViewById(R.id.meeting_start) ;
        mTimeStartEt = pView.findViewById(R.id.meeting_start_et) ;
        mTimeEnd = pView.findViewById(R.id.meeting_end) ;
        mTimeEndEt = pView.findViewById(R.id.meeting_end_et) ;
        mBtnCancel = pView.findViewById(R.id.btn_cancel);
        mBtnSave = pView.findViewById(R.id.btn_save);
    }

    @Override
    public void onClick(View v) { }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        try {
            mCallback = (AddFragment.OnListenerAdd) mContext;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListenerAdd");
        }
    }
}
