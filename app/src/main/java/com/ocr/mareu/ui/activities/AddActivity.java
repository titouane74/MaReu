package com.ocr.mareu.ui.activities;

import android.os.Bundle;
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

    /**
     * Sur sélection du bouton retour ou ajout de la toolbar
     * @param pItem : menuitem : retour à l'écran précédent, ajout de la réunion et retour à l'écran précédent
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        if (pItem.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(pItem);
        }
    }

    /**
     * Méthode configurant et affichant le fragment d'ajout d'une réunion
     */
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
     * Ferme l'activité d'ajout d"une réunion sur le clique du bouton cancel au niveau du fragment
     * @param pView : view : vue
     * @param pActivateFragment : string : fragment à afficher, sert en mode table
     */
    @Override
    public void onButtonCancelClickedClose(View pView, String pActivateFragment) {
        if (getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
        finish();
    }
}
