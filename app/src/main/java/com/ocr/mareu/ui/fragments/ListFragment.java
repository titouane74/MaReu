package com.ocr.mareu.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ocr.mareu.R;
import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.utils.ItemClickSupport;

import static com.ocr.mareu.utils.SortOrFilter.SORT_DEFAULT;

/**
 * Created by Florence LE BOURNOT on 10/02/2020
 */
public class ListFragment extends Fragment implements MeetingRecyclerViewAdapter.OnRecyclerViewListener {

    private RecyclerView mRecyclerView;
    private MeetingRecyclerViewAdapter mListAdapter;

    public ListFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View lView = inflater.inflate(R.layout.fragment_list,container,false);
        mRecyclerView = (RecyclerView) lView;
        configurerecyclerView();

        return lView;
    }

    private void configurerecyclerView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListAdapter = new MeetingRecyclerViewAdapter(getActivity(), SORT_DEFAULT);
        mRecyclerView.setAdapter(mListAdapter);
    }

    @Override
    public void onItemClicked(View pView, String pMeeting) { }

    @Override
    public void onItemChangeListToUpdate() {
        mListAdapter.notifyDataSetChanged();
    }

}
