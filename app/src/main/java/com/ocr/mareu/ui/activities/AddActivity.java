package com.ocr.mareu.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ocr.mareu.R;
import com.ocr.mareu.ui.fragments.AddFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Florence LE BOURNOT on 10/02/2020
 */
public class AddActivity extends AppCompatActivity implements AddFragment.OnListenerAdd {

    @BindView(R.id.toolbar_add) Toolbar mToolbarAdd;

    private AddFragment mAddFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbarAdd);

        if (getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        configureAndShowAddFragment();

    }

    private void configureAndShowAddFragment() {
        mAddFragment = (AddFragment) getSupportFragmentManager().findFragmentById(R.id.frame_add);
        if (mAddFragment == null) {
            mAddFragment = AddFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_add, mAddFragment)
                    .commit();
        }
    }

    /**
     * onCreateOptionsMenu : création des items du menu
     * @param pMenu : menu : menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        getMenuInflater().inflate(R.menu.menu_add_meeting, pMenu);
        return true;
    }

    /**
     * Sur sélection du bouton retour ou ajout de la toolbar
     * @param pItem : menuitem : retour à l'écran précédent, ajout de la réunion et retour à l'écran précédent
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        switch (pItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
//            case R.id.action_add_meeting:
//                addMeeting();
//                return true;
            default:
                return super.onOptionsItemSelected(pItem);
        }
    }

    @Override
    public void onButtonClickedClose(View pView, String pActivateFragment) {
        if (getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
        finish();
    }
}
