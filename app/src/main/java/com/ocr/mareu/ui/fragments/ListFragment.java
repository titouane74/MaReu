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

import static com.ocr.mareu.utils.SortOrFilter.SORT_DEFAULT;

/**
 * Created by Florence LE BOURNOT on 10/02/2020
 */
public class ListFragment extends Fragment implements MeetingRecyclerViewAdapter.OnRecyclerViewListener{

    private RecyclerView mRecyclerView;

    public ListFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View lView = inflater.inflate(R.layout.fragment_list,container,false);
        mRecyclerView = (RecyclerView) lView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        configureRecyclerView(SORT_DEFAULT);

        return lView;
    }

    /**
     * Configure le recyclerview de la liste des réunions
     * @param pOrder : string : ordrede tri ou filtre à appliquer
     */
    private void configureRecyclerView(String pOrder) {
        MeetingRecyclerViewAdapter  mListAdapter = new MeetingRecyclerViewAdapter(getActivity(), pOrder);
        mRecyclerView.setAdapter(mListAdapter);
    }

    @Override
    public void onItemClicked(View pView) { }

    /**
     * Liste des réunions à mettre à jour en fonction du tri ou filtre passé
     * @param pOrder : string : ordre de tri ou filtre passé pour afficher la liste
     */
    @Override
    public void listToUpdate(String pOrder) {
        configureRecyclerView(pOrder);
    }

    /**
     * Invalidation du menu depuis la recyclerview, sert dans la MainActivity
     */
    @Override
    public void invalidateMenuRV() { }

}
