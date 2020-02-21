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
import com.ocr.mareu.utils.GsonTransformer;
import com.ocr.mareu.utils.Validation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;

import static com.ocr.mareu.di.DI.sMeetingApiService;
import static com.ocr.mareu.utils.Validation.CST_DATETIME;
import static com.ocr.mareu.utils.Validation.CST_EMAIL;
import static com.ocr.mareu.utils.Validation.CST_ROOM;
import static com.ocr.mareu.utils.Validation.CST_TOPIC;
import static com.ocr.mareu.utils.Validation.errorMessageDateTimeToShow;
import static com.ocr.mareu.utils.Validation.errorMessageToShow;
import static com.ocr.mareu.utils.Validation.transformChipGroupInString;

public class AddFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.room_list) AutoCompleteTextView mListRoom;
    @BindView(R.id.room_list_layout) TextInputLayout mListLayout;
    @BindView(R.id.email_address) TextInputLayout mEmail;
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
    @BindView(R.id.btn_cancel) Button mBtnCancel;
    @BindView(R.id.btn_save) Button mBtnSave;

    private Calendar mDateCal;
    private Context mContext;
    private List<String> mParticipants;
    private OnListenerAdd mCallback;

    /**
     * Inteface permettant de gérer le bouton d'annulation dans l'AddActivity ou le MainActivity
     * en focntion de l'appelant
     */
    public interface OnListenerAdd {
        void onButtonCancelClickedClose(View pView, String pActivateFragment);
    }

    public AddFragment() { }

    @Override
    public BaseFragment newInstance()  { return new AddFragment(); }

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_scroll_add; }

    @Override //onCreateView
    protected void configureDesign(View pView) {
        mContext = pView.getContext();

        List<Room> lRooms = sMeetingApiService.getRooms();

        ArrayAdapter<Room> lAdapter = new ArrayAdapter<>(mContext,R.layout.activity_room_item,lRooms);
        mListRoom.setAdapter(lAdapter);
        mListRoom.setShowSoftInputOnFocus(false);
        mListRoom.setOnClickListener(v -> mListRoom.showDropDown() );
        mListRoom.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode != KeyEvent.KEYCODE_DPAD_DOWN || keyCode != KeyEvent.KEYCODE_DPAD_UP) {
                        mListRoom.showDropDown();
                    }
                }
                return true;
            }
        });

        mEmailEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        String lEmailText = Objects.requireNonNull(mEmailEt.getText()).toString().trim();
                        String lReturn = Validation.validationText(getContext(), CST_EMAIL, lEmailText);
                        if (!errorMessageToShow(mEmail, lReturn)){
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
        mDateEt.setOnClickListener(v -> displayCalendarDialog ());
        mTimeStartEt.setOnClickListener(v -> displayTimeDialog(v));
        mTimeEndEt.setOnClickListener(v -> displayTimeDialog(v));
        mBtnCancel.setOnClickListener(v -> mCallback.onButtonCancelClickedClose(v, getString(R.string.fragment_right)));
        mBtnSave.setOnClickListener(v -> {
            if (addMeeting(sMeetingApiService.getStartMeeting(), sMeetingApiService.getEndMeeting(),
                    sMeetingApiService.extractRoomSelected(mListRoom.getText().toString()))) {
                mCallback.onButtonCancelClickedClose(v, getString(R.string.fragment_right)); }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                            sMeetingApiService.setStartMeeting(lTimeCal);
                        } else if (lId == R.id.meeting_end_et) {
                            mTimeEndEt.setText(lTimeFormated);
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
    public boolean addMeeting(Calendar pStart, Calendar pEnd, Room pRoomSelected)  {
       if (testFieldsValidity(pStart, pEnd)) {
            try {
                sMeetingApiService.addMeeting(
                        new Meeting(pRoomSelected, mTopicEt.getText().toString(), mDateCal, pStart, pEnd, mParticipants));

                Toast.makeText(getContext(), getString(R.string.action_add_meeting), Toast.LENGTH_SHORT).show();
                return true;
            } catch (MeetingApiServiceException pE) {
                Toast.makeText(getContext(), R.string.err_meeting_room_not_free, Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.action_add_meeting_missing_field), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean testFieldsValidity(Calendar pStart, Calendar pEnd) {
        String  lReturn;
        boolean isValidDateTime = true;
        boolean isValidParticipants = true ;

        lReturn = Validation.validationText(getContext(), CST_ROOM,Objects.requireNonNull(mListLayout.getEditText()).getText().toString().trim());
        boolean isValidRoom = errorMessageToShow(mListLayout,lReturn);

        lReturn = Validation.validationText(getContext(), CST_TOPIC,Objects.requireNonNull(mTopic.getEditText()).getText().toString().trim());
        boolean isValidTopic = errorMessageToShow(mTopic,lReturn);

        lReturn = Validation.validationText(getContext(), CST_DATETIME,Objects.requireNonNull(mDate.getEditText()).getText().toString().trim());
        boolean isValidDate = errorMessageToShow(mDate,lReturn);

        lReturn = Validation.validationText(getContext(), CST_DATETIME,Objects.requireNonNull(mTimeStart.getEditText()).getText().toString().trim());
        boolean isValidTimeStart = errorMessageToShow(mTimeStart, lReturn);

        lReturn = Validation.validationText(getContext(), CST_DATETIME,Objects.requireNonNull(mTimeEnd.getEditText()).getText().toString().trim());
        boolean isValidTimeEnd = errorMessageToShow(mTimeEnd, lReturn);

        if (isValidDate && isValidTimeStart && isValidTimeEnd  ) {
            lReturn = Validation.validationDateTime(getContext(), pStart, pEnd);
            if (lReturn.contains("/")) {
                List<String> lError = GsonTransformer.getGsonToListString(lReturn);
                isValidDateTime = errorMessageDateTimeToShow(mTimeStart, lError.get(0), mTimeEnd,lError.get(2));
            }
        }
        String lParts = transformChipGroupInString(mEmailGroup);
        lReturn = Validation.validationParticipants(getContext(), lParts);
        if (lReturn.contains("@")) {
            mParticipants = GsonTransformer.getGsonToListString(lReturn);
        } else {
            isValidParticipants = errorMessageToShow(mEmail, lReturn);
        }

        if (!isValidTopic | !isValidParticipants | !isValidRoom | !isValidDate | !isValidTimeStart | !isValidTimeEnd | !isValidDateTime ) {
            return false;
        } else {
            return true;
        }
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