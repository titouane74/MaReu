package com.ocr.mareu.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ocr.mareu.R;
import com.ocr.mareu.model.Meeting;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.ocr.mareu.di.DI.sMeetingApiService;


/**
 * Created by Florence LE BOURNOT on 12/02/2020
 */
public class DetailFragment  extends Fragment {

    private TextView mRoomName, mDateTime, mTopic;
    private ImageView mImgParts, mImgTime, mImgTopic;
    private Meeting mMeeting ;
    private RecyclerView mRecyclerView;

    public DetailFragment() {}

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View lView = inflater.inflate(R.layout.fragment_detail, container, false);

        mMeeting = sMeetingApiService.getMeetingSelected();

        bindObjectToCode(lView);

        setInfoMeeting();
        configureRecyclerViewParts();


        return lView;
    }

    private void setInfoMeeting() {
        int lColor = mMeeting.getRoom().getColorRoom();

        mRoomName.setText(mMeeting.getRoom().getNameRoom());
        mRoomName.setBackgroundColor(lColor);
        mTopic.setText(mMeeting.getTopic());

        @SuppressLint("SimpleDateFormat")
        String lDateTime =
                new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.FRENCH).format(mMeeting.getDate().getTime()) + "\n" +
                        new SimpleDateFormat("HH:mm").format(mMeeting.getStart().getTime()) + " - " +
                        new SimpleDateFormat("HH:mm").format(mMeeting.getEnd().getTime()) ;
        mDateTime.setText(lDateTime);

        mImgTopic.setColorFilter(lColor);
        mImgTime.setColorFilter(lColor);
        mImgParts.setColorFilter(lColor);


    }

    private void configureRecyclerViewParts( ) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ParticipantRecyclerViewAdapter  mListAdapter = new ParticipantRecyclerViewAdapter(getActivity());
        mRecyclerView.setAdapter(mListAdapter);
    }

    private void bindObjectToCode(View pView){
        mRoomName = pView.findViewById(R.id.room_name);
        mDateTime = pView.findViewById(R.id.info_date_time);
        mTopic = pView.findViewById(R.id.info_topic);
        mImgParts = pView.findViewById(R.id.imgParticipants);
        mImgTime = pView.findViewById(R.id.imgTime);
        mImgTopic = pView.findViewById(R.id.imgTopic);
        mRecyclerView = pView.findViewById(R.id.recycler_part);
    }
}
