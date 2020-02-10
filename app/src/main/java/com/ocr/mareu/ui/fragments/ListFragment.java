package com.ocr.mareu.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ocr.mareu.R;

/**
 * Created by Florence LE BOURNOT on 10/02/2020
 */
public class ListFragment extends Fragment {

    public ListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_list,container,false);

        result.findViewById(R.id.text_list);
        return result;

    }
}
