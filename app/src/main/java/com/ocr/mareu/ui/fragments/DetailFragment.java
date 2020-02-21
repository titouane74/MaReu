package com.ocr.mareu.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ocr.mareu.R;
import com.ocr.mareu.model.Meeting;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;

import static com.ocr.mareu.di.DI.sMeetingApiService;

/**
 * Created by Florence LE BOURNOT on 12/02/2020
 */
public class DetailFragment  extends BaseFragment {

    @BindView(R.id.room_name) TextView mRoomName ;
    @BindView(R.id.info_date_time) TextView mDateTime ;
    @BindView(R.id.info_topic) TextView mTopic ;
    @BindView(R.id.imgParticipants) ImageView mImgParts ;
    @BindView(R.id.imgTime) ImageView mImgTime ;
    @BindView(R.id.imgTopic) ImageView mImgTopic ;
    @BindView(R.id.recycler_part) RecyclerView mRecyclerView ;

    private Meeting mMeeting ;

    public DetailFragment() {}

    @Override
    public BaseFragment newInstance() { return new DetailFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_detail; }

    @Override //onCreateView
    protected void configureDesign(View pView) {
        mMeeting = sMeetingApiService.getMeetingSelected();
        setInfoMeeting();
        configureRecyclerViewParts();
    }

    /**
     * Affiche les informations de la réunion
     */
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

    /**
     * Configure le recyclerview de la liste des participants
     */
    private void configureRecyclerViewParts( ) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ParticipantRecyclerViewAdapter  mListAdapter = new ParticipantRecyclerViewAdapter(getActivity());
        mRecyclerView.setAdapter(mListAdapter);
    }

}
