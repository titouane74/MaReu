package com.ocr.mareu.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ocr.mareu.R;
import com.ocr.mareu.ui.fragments.AddFragment;
import com.ocr.mareu.ui.fragments.DetailFragment;
import com.ocr.mareu.ui.fragments.ListFragment;
import com.ocr.mareu.ui.fragments.RightFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ocr.mareu.di.DI.sMeetingApiService;

public class MainActivity extends AppCompatActivity implements RightFragment.OnRightListener,AddFragment.OnListenerAdd {

    @BindView(R.id.add_fab) FloatingActionButton mAddFab;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.main_layout) View mMainLayout;

    private ListFragment mListFragment;
    private RightFragment mRightFragment;
    private AddFragment mAddFragment;
    private DetailFragment mDetailFragment;
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

        configureAndShowListFragment();
        configureAndShowRightFragment();
        mAddFragment = new AddFragment();
        mDetailFragment = new DetailFragment();


        if (mMainLayout.getTag() == getString(R.string.tablet))
            mAddFab.setVisibility(View.INVISIBLE);
        mAddFab.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddActivity.class)));
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
            case android.R.id.home:
                if ((mAddFragment != null && mAddFragment.isVisible()) || (mDetailFragment != null && mDetailFragment.isVisible())) {
                    showFragment(mRightFragment);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                }
                return true;
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
//                showCalendarDialog(mContext,mRecyclerView);
                return true;
            case R.id.filter_room:
//                showDialogRooms(mContext,mRecyclerView);
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

    private void configureAndShowRightFragment() {
        mRightFragment = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.frame_right);
        if (mRightFragment == null && findViewById(R.id.frame_right) != null) {
            mRightFragment = RightFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_right, mRightFragment )
                    .commit();
        }

    }

    @Override
    public void onButtonClicked(View pView, String pActivateFragment) {

        if (mRightFragment != null && mRightFragment.isVisible()) {
            // TABLET
            if (pActivateFragment == "ADD") {
                showFragment(mAddFragment);
            } else if (pActivateFragment == "VIEW") {
                showFragment(mDetailFragment);
            }
        }
        if (getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void showFragment(final Fragment pFragment) {
        final FragmentManager lFragmentManager = getSupportFragmentManager();
        final FragmentTransaction lFragmentTransaction = lFragmentManager.beginTransaction();
        lFragmentTransaction.replace(R.id.frame_right,pFragment);
        lFragmentTransaction.commit();
    }

    @Override
    public void onButtonClickedAdd(View pView, String pActivateFragment) {
        if (mAddFragment != null && mAddFragment.isVisible()) {
            // TABLET
            if (pActivateFragment == "RIGHT") {
                showFragment(mRightFragment);
            }
        }
        if (getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }

}
