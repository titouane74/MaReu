package com.ocr.mareu.ui.activities;

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
public class RightActivity  extends AppCompatActivity implements RightFragment.OnRightListener {

    private RightFragment mRightFragment;
    private AddFragment mAddFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right);

//        configureAndShowRightFragment();
    }

/*
    private void configureAndShowRightFragment() {
        mRightFragment = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.frame_right);
        if (mRightFragment == null ) {
            mRightFragment = RightFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_right, mRightFragment )
                    .commit();
        }

    }


    public void configureAndShowAddFragment() {
        mAddFragment = (AddFragment) getSupportFragmentManager().findFragmentById(R.id.frame_right);
        if (mAddFragment == null && findViewById(R.id.frame_right) != null) {
            mAddFragment = AddFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_right, mAddFragment)
                    .commit();
        }

    }
*/
@Override
public void onButtonClicked(View pView, String pActivateFragment) {

/*
    if (mRightFragment != null && mRightFragment.isVisible()) {
        // TABLET
        configureAndShowAddFragment();
    } else {
        // SMARTPHONE
        Intent lIntent = new Intent(this, AddActivity.class);
        startActivity(lIntent);
    }
*/
}

}
