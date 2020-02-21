package com.ocr.mareu.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.ocr.mareu.R;

import butterknife.BindView;

/**
 * Created by Florence LE BOURNOT on 11/02/2020
 */
public class RightFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.btn_add) Button mBtnAdd;

    private Context mContext;

    public RightFragment() {    }

    private OnRightListener mCallback;

    /**
     * Interface permettant de gérer les callbacks vers la MainActivity
     */
    public interface OnRightListener {
        void onButtonAddMeetingClicked(View pView);
    }

    @Override
    public BaseFragment newInstance() {
        return new RightFragment();
    }

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_right; }

    @Override //onCreateView
    protected void configureDesign(View pView) {
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onButtonAddMeetingClicked(v);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
