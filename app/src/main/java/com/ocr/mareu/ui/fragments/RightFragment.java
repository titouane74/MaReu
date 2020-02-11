package com.ocr.mareu.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ocr.mareu.R;

/**
 * Created by Florence LE BOURNOT on 11/02/2020
 */
public class RightFragment extends Fragment implements View.OnClickListener {

    Button mBtnAdd;

    private Context mContext;

    public RightFragment() {
    }

    private OnButtonClickedListener mCallback;

    public interface OnButtonClickedListener {
        public void onButtonClicked(View pView);
    }

    public static RightFragment newInstance() {
        RightFragment fragment = new RightFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View lView = inflater.inflate(R.layout.fragment_right, container, false);
        mBtnAdd = lView.findViewById(R.id.btn_add);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "TEST", Toast.LENGTH_SHORT).show();
            }
        });
        return lView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
/*
        try {
            mCallback = (OnButtonClickedListener) getContext();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
*/
    }

    @Override
    public void onClick(View v) {
        mCallback.onButtonClicked(v);
        Toast.makeText(mContext, "COUCOU", Toast.LENGTH_SHORT).show();
    }


}
