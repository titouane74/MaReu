package com.ocr.mareu.ui.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.ocr.mareu.ui.activities.AddActivity;
import com.ocr.mareu.utils.Validation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ocr.mareu.di.DI.sMeetingApiService;
import static com.ocr.mareu.utils.Validation.CST_DATETIME;
import static com.ocr.mareu.utils.Validation.CST_EMAIL;
import static com.ocr.mareu.utils.Validation.CST_ROOM;
import static com.ocr.mareu.utils.Validation.CST_TOPIC;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {link AddFrag OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    @BindView(R.id.room_list_layout) TextInputLayout mListLayout;
    @BindView(R.id.room_list) AutoCompleteTextView mListRoom ;
    @BindView(R.id.email_address)TextInputLayout mEmail;
    @BindView(R.id.email_address_et) TextInputEditText mEmailEt;
    @BindView(R.id.email_group_cg) ChipGroup mEmailGroup;
    @BindView(R.id.meeting_topic) TextInputLayout mTopic;
    @BindView(R.id.meeting_topic_et) TextInputEditText mTopicEt;
    @BindView(R.id.meeting_date) TextInputLayout mDate;
    @BindView(R.id.meeting_date_et) TextInputEditText mDateEt;
    @BindView(R.id.meeting_start) TextInputLayout mTimeStart;
    @BindView(R.id.meeting_start_et) TextInputEditText mTimeStartEt;
    @BindView(R.id.meeting_end) TextInputLayout mTimeEnd;
    @BindView(R.id.meeting_end_et) TextInputEditText mTimeEndEt;

    private Calendar mNow;
    private Calendar mDateCal;
    private Calendar mTimeStartFormated;
    private Calendar mTimeEndFormated;
    private Context mContext;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNow = Calendar.getInstance();
        //Les salles
        List<Room> lRooms = sMeetingApiService.getRooms();
        System.out.println("ROOMS  :  " + lRooms.size());

        mContext = Objects.requireNonNull(getActivity()).getApplicationContext();
        System.out.println("CONTEXT   :   " + mContext);
/*
        ArrayAdapter<Room> lAdapter = new ArrayAdapter<>(mContext,R.layout.activity_room_item,lRooms);
        mListRoom.setAdapter(lAdapter);
        mListRoom.setOnClickListener((View v)-> {
            mListRoom.showDropDown();
        });
*/

        //Les participants

/*
        mEmailEt.setOnKeyListener((v,keyCode,event) -> {
            if(event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String lEmailText = Objects.requireNonNull(mEmailEt.getText()).toString().trim();

                    if (!Validation.validationTextInputLayout(getContext(), CST_EMAIL, mEmail)) {
                        return false;
                    } else {
                        final Chip lChipEmail = new Chip(getContext());
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
        });
*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View lView = inflater.inflate(R.layout.fragment_add, container, false);
        mContext = lView.getContext();
        return lView;
    }

    /**
     * OnClick du champs meeting_date_et : affiche la boîte de dialogue calendrier
     */
    @OnClick(R.id.meeting_date_et)
    void displayCalendarDialog () {
        Calendar lCalendar = Calendar.getInstance();

        DatePickerDialog lDatePickerDialog = new DatePickerDialog(
                getContext(),
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
    @OnClick({R.id.meeting_start_et, R.id.meeting_end_et})
    void displayTimeDialog(View pView) {
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
                        } else if (lId == R.id.meeting_end_et) {
                            mTimeEndEt.setText(lTimeFormated);
                            mTimeEndFormated = lTimeCal;
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
    private void addMeeting() {
        Room lRoomSelected;

        boolean isValidDateTime = false;
        boolean isValidParticipants = false ;
        boolean isValidRoom = Validation.validationTextInputLayout(getContext(), CST_ROOM, mListLayout);
        boolean isValidTopic = Validation.validationTextInputLayout(getContext(), CST_TOPIC, mTopic);

        boolean isValidDate = Validation.validationTextInputLayout(getContext(), CST_DATETIME, mDate);
        boolean isValidTimeStart = Validation.validationTextInputLayout(getContext(), CST_DATETIME, mTimeStart);
        boolean isValidTimeEnd = Validation.validationTextInputLayout(getContext(), CST_DATETIME, mTimeEnd);

        if (isValidDate && isValidTimeStart && isValidTimeEnd  ) {
            isValidDateTime = Validation.validationDateTime(getContext(), mDate, mTimeStart, mTimeEnd);
        }

        List<String> lParticipants = Validation.validationParticipants(getContext(), mEmail,mEmailGroup);

        if (lParticipants.size() > 0)
            isValidParticipants = true;

        if (!isValidTopic | !isValidParticipants | !isValidRoom | !isValidDate | !isValidTimeStart |
                !isValidTimeEnd | !isValidDateTime ) {
            Toast.makeText(getContext(),getString(R.string.action_add_meeting_missing_field), Toast.LENGTH_SHORT).show();
        } else {
            try {
                lRoomSelected = sMeetingApiService.getRoomSelected(mListRoom.getText().toString());
                sMeetingApiService.addMeeting(
                        new Meeting(lRoomSelected, mTopicEt.getText().toString(), mDateCal, mTimeStartFormated, mTimeEndFormated, lParticipants));

                Toast.makeText(getContext(),getString(R.string.action_add_meeting), Toast.LENGTH_SHORT).show();
                //TODO callback ?
//                finish();
            } catch  (MeetingApiServiceException pE)
            {
                Toast.makeText(getContext(), R.string.err_meeting_room_not_free, Toast.LENGTH_LONG).show();
            }
        }
    }

}
