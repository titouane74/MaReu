package com.ocr.mareu.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ocr.mareu.R;
import com.ocr.mareu.ui.fragments.ListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ocr.mareu.di.DI.sMeetingApiService;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.add_fab) FloatingActionButton mAddFab;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    private ListFragment mListFragment;
    private long mBackPressedTime;
    private Toast mBackToast;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mContext = this;

        setSupportActionBar(mToolbar);

        this.configureAndShowListFragment();

        mAddFab.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddActivity.class) ));

    }

    /**
     * Préparation du menu option pour activer ou désactivier les menus
     * @param pMenu : menu : menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu pMenu) {
        MenuItem lActionSort = pMenu.findItem(R.id.action_sort);
        MenuItem lActionFilter = pMenu.findItem(R.id.action_filter);

/*
        if (isMenuEnabled) {
            lActionSort.setEnabled(true);
            lActionFilter.setEnabled(true);
        } else {
            lActionSort.setEnabled(false);
            lActionFilter.setEnabled(false);
        }
*/
        return true;
    }
    /**
     * onCreateOptionsMenu : création du menu
     * @param pMenu : menu : menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        getMenuInflater().inflate(R.menu.menu_meeting_list, pMenu);
        return true;
    }

    /**
     * onOptionsItemSelected : définition des actions en fonction de l'item du menu sélectionné
     * @param pItem : menuitem : indicateur de l'item sélectionné
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        switch (pItem.getItemId()) {
            case R.id.action_remove_filter:
//                ConfigureAdapter.configureAdapter(this, SORT_DEFAULT, mRecyclerView);
                return true;
            case R.id.action_sort:
                return true;
            case R.id.sort_date:
//                ConfigureAdapter.configureAdapter(this, SORT_DATE,mRecyclerView);
                return true;
            case R.id.sort_room:
//                ConfigureAdapter.configureAdapter(this, SORT_ROOM,mRecyclerView);
                return true;
            case R.id.action_filter:
                return true;
            case R.id.filter_date:
//                showCalendarDialog(mContext,mRecyclerView,mEmptyList);
                return true;
            case R.id.filter_room:
//                showDialogRooms(mContext,mRecyclerView,mEmptyList);
                return true;
            default :
                return super.onOptionsItemSelected(pItem);
        }
    }

    /**
     * onBackPressed : cliquer 2 fois sur la touche retour pour quitter l'application
     */
    @Override
    public void onBackPressed() {

        if (mBackPressedTime + 2000 > System.currentTimeMillis())
        {
            sMeetingApiService.resetMeetings();
            mBackToast.cancel();
            super.onBackPressed();
            return;
        } else {
            mBackToast = Toast.makeText(mContext, R.string.exit_app_back_pressed, Toast.LENGTH_SHORT);
            mBackToast.show();
        }
        mBackPressedTime = System.currentTimeMillis();
    }

    private void configureAndShowListFragment() {
        mListFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.frame_list);
        if (mListFragment == null) {
            mListFragment = new ListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_list, mListFragment)
                    .commit();
        }

    }
}
