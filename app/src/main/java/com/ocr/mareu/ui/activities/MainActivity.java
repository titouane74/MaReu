package com.ocr.mareu.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ocr.mareu.R;
import com.ocr.mareu.ui.fragments.ListFragment;

public class MainActivity extends AppCompatActivity {

    private ListFragment mListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureAndShowMeetingListFragment();
    }

    private void configureAndShowMeetingListFragment() {
        mListFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.frame_list);
        if (mListFragment == null) {
            mListFragment = new ListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_list, mListFragment)
                    .commit();
        }

    }
}
