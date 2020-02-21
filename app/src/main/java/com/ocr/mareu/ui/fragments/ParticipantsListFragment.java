package com.ocr.mareu.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ocr.mareu.R;

/**
 * Created by Florence LE BOURNOT on 14/02/2020
 */
public class ParticipantsListFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public ParticipantsListFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View lView = inflater.inflate(R.layout.fragment_participant_list,container,false);
        mRecyclerView = (RecyclerView) lView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        configureRecyclerView();

        return lView;
    }

    /**
     * Configuration du recyclerview de la liste des participants
     */
    private void configureRecyclerView() {
        ParticipantRecyclerViewAdapter mListAdapter;
        mListAdapter = new ParticipantRecyclerViewAdapter(getActivity());
        mRecyclerView.setAdapter(mListAdapter);
    }
}
