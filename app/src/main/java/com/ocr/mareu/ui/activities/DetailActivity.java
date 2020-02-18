package com.ocr.mareu.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ocr.mareu.R;
import com.ocr.mareu.ui.fragments.DetailFragment;

/**
 * Created by Florence LE BOURNOT on 12/02/2020
 */
public class DetailActivity extends AppCompatActivity {

    Toolbar mToolbarDetail;
    private DetailFragment mDetailFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mToolbarDetail = findViewById(R.id.toolbar_detail);
        setSupportActionBar(mToolbarDetail);

        configureAndShowDetailFragment();

        if (getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * Sur sélection du bouton retour
     * @param pItem : menuitem : retour à l'écran précédent
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        if (pItem.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(pItem);
    }

    /**
     * Méthode configurant et affichant le fragment du détail d'une réunion
     */
    private void configureAndShowDetailFragment() {
        mDetailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_detail);
        if (mDetailFragment == null) {
            mDetailFragment = DetailFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_detail, mDetailFragment)
                    .commit();
        }
    }
}