package com.ocr.mareu.ui.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ocr.mareu.R;
import com.ocr.mareu.utils.SortOrFilterLabel;


/**
 * Created by Florence LE BOURNOT on 10/02/2020
 */
public class ListFragment extends BaseFragment implements MeetingRecyclerViewAdapter.OnRecyclerViewListener{

    private RecyclerView mRecyclerView;

    public ListFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Retourne le layout à utilisr pour le fragment pour la création de la view
     * @return : layout : layout à utiliser
     */
    @Override
    protected int getFragmentLayout() { return R.layout.fragment_list; }

    @Override
    protected void configureDesign(View pView) {
        mRecyclerView = (RecyclerView) pView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        configureRecyclerView(SortOrFilterLabel.SORT_DEFAULT);
    }

    /**
     * Configure le recyclerview de la liste des réunions
     * @param pOrder : string : ordrede tri ou filtre à appliquer
     */
    private void configureRecyclerView(Enum pOrder) {
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
    public void listToUpdate(Enum pOrder) {
        configureRecyclerView(pOrder);
    }

}
