package com.ocr.mareu.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ocr.mareu.R;
import com.ocr.mareu.ui.fragments.DetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Florence LE BOURNOT on 12/02/2020
 */
public class DetailActivites extends AppCompatActivity {

    @BindView(R.id.toolbar_detail) Toolbar mToolbarDetail;
    private DetailFragment mDetailFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbarDetail);

        if (getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        configureAndShowDetailFragment();
    }

    private void configureAndShowDetailFragment() {
        mDetailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_right);
        if (mDetailFragment == null) {
            mDetailFragment = DetailFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_right, mDetailFragment)
                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        if (pItem.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(pItem);
    }
}
