package com.ocr.mareu.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ocr.mareu.R;

/**
 * Created by Florence LE BOURNOT on 11/02/2020
 */
public class RightFragment extends Fragment implements View.OnClickListener {

    private Context mContext;

    public RightFragment() {    }

    private OnRightListener mCallback;

    /**
     * Interface permettant de gérer les callbacks vers la MainActivity
     */
    public interface OnRightListener {
        void onButtonAddMeetingClicked(View pView);
    }

    public static RightFragment newInstance() {
        return new RightFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View lView = inflater.inflate(R.layout.fragment_right, container, false);
        Button mBtnAdd = lView.findViewById(R.id.btn_add);

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onButtonAddMeetingClicked(v);
            }
        });

        return lView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        try {
            mCallback = (OnRightListener) getContext();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnRightListener");
        }
    }

    @Override
    public void onClick(View v) { }

}
