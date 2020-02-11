package com.ocr.mareu.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ocr.mareu.R;
import com.ocr.mareu.ui.fragments.AddFragment;
import com.ocr.mareu.ui.fragments.RightFragment;

/**
 * Created by Florence LE BOURNOT on 11/02/2020
 */
public class RightActivity  extends AppCompatActivity implements RightFragment.OnButtonClickedListener{

    private RightFragment mRightFragment;
    private AddFragment mAddFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right);

        configureAndShowRightFragment();
    }

    private void configureAndShowRightFragment() {
        mRightFragment = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.frame_right);
        if (mRightFragment == null ) {
            mRightFragment = RightFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_right, mRightFragment )
                    .commit();
        }

    }

    @Override
    public void onButtonClicked(View pView) {

        if (mRightFragment != null && mRightFragment.isVisible()) {
            // TABLET
            configureAndShowAddFragment();
        } else {
            // SMARTPHONE
            Intent lIntent = new Intent(this, AddActivity.class);
            startActivity(lIntent);
        }
    }

    public void configureAndShowAddFragment() {
        mAddFragment = (AddFragment) getSupportFragmentManager().findFragmentById(R.id.frame_add);
        if (mAddFragment == null && findViewById(R.id.frame_add) != null) {
            mAddFragment = AddFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_add, mAddFragment)
                    .commit();
        }

    }
}
