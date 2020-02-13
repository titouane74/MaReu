package com.ocr.mareu.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ocr.mareu.R;
import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.utils.GsonTransformer;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;

import static android.content.Intent.getIntent;

/**
 * Created by Florence LE BOURNOT on 12/02/2020
 */
public class DetailFragment  extends Fragment {

    private TextView mRoomName, mDateTime, mListParts, mTopic;
    private ImageView mImgParts, mImgTime, mImgTopic;

    private Context mContext;
    private Meeting mMeeting ;

    private int mId;

    public DetailFragment() {}

    public static DetailFragment newInstance() {
        DetailFragment lFragment = new DetailFragment();
        return lFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View lView = inflater.inflate(R.layout.fragment_detail, container, false);
        mContext = lView.getContext();

        bindObjectToCode(lView);
        readBundle(getArguments());

        return lView;
    }

/*    //TODO faire un bundle
    private void getIncomingIntent() {
        if (getIntent().hasExtra("position") ) {
            mMeeting = mMeetings.get(getIntent().getIntExtra("position", 0));
            setInfoMeeting();
        }
        mMeeting = GsonTransformer.getGsonToMeeting(pMeeting);
        setInfoMeeting();
    }*/

    private void setInfoMeeting() {
        int lColor = mMeeting.getRoom().getColorRoom();

        mRoomName.setText(mMeeting.getRoom().getNameRoom());
        mRoomName.setBackgroundColor(lColor);
        mTopic.setText(mMeeting.getTopic());

        mListParts.setText(TextUtils.join(",\n ", mMeeting.getParticpants()));

        @SuppressLint("SimpleDateFormat")
        String lDateTime =
                new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.FRENCH).format(mMeeting.getDate().getTime()) + "\n" +
                        new SimpleDateFormat("HH:mm").format(mMeeting.getStart().getTime()) + " - " +
                        new SimpleDateFormat("HH:mm").format(mMeeting.getEnd().getTime()) ;
        mDateTime.setText(lDateTime);

//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(lColor));

        mImgTopic.setColorFilter(lColor);
        mImgTime.setColorFilter(lColor);
        mImgParts.setColorFilter(lColor);
    }

    private void bindObjectToCode(View pView){
        mRoomName = pView.findViewById(R.id.room_name);
        mDateTime = pView.findViewById(R.id.info_date_time);
        mListParts = pView.findViewById(R.id.info_list_part);
        mTopic = pView.findViewById(R.id.info_topic);
        mImgParts = pView.findViewById(R.id.imgParticipants);
        mImgTime = pView.findViewById(R.id.imgTime);
        mImgTopic = pView.findViewById(R.id.imgTopic);

    }

    private void readBundle(Bundle pBundle) {
        if (pBundle != null) {
            mMeeting = GsonTransformer.getGsonToMeeting(pBundle.getString("meeting"));
            setInfoMeeting();
        }
    }
}
