package com.ocr.mareu.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
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
import com.ocr.mareu.ui.fragments.MeetingRecyclerViewAdapter;
import com.ocr.mareu.ui.fragments.RightFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ocr.mareu.di.DI.sMeetingApiService;
import static com.ocr.mareu.utils.ShowDialog.showCalendarDialog;
import static com.ocr.mareu.utils.ShowDialog.showDialogRooms;
import static com.ocr.mareu.utils.SortOrFilter.SORT_DATE;
import static com.ocr.mareu.utils.SortOrFilter.SORT_DEFAULT;
import static com.ocr.mareu.utils.SortOrFilter.SORT_ROOM;

public class MainActivity extends AppCompatActivity implements RightFragment.OnRightListener,AddFragment.OnListenerAdd,
        MeetingRecyclerViewAdapter.OnRecyclerViewListener {

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


    @SuppressLint("RestrictedApi")
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
                if ((mAddFragment != null && mAddFragment.isVisible()) || (mDetailFragment != null && mDetailFragment.isVisible()))
                    showRightFragment(mRightFragment);
                manageActionBar(false);
                return true;
            case R.id.action_remove_filter:
                mListFragment.listToUpdate(SORT_DEFAULT);
                return true;
            case R.id.action_sort:
                return true;
            case R.id.sort_date:
                mListFragment.listToUpdate(SORT_DATE);
                return true;
            case R.id.sort_room:
                mListFragment.listToUpdate(SORT_ROOM);
                return true;
            case R.id.action_filter:
                return true;
            case R.id.filter_date:
                showCalendarDialog(mContext,mListFragment);
                return true;
            case R.id.filter_room:
                showDialogRooms(mContext, mListFragment);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sMeetingApiService.resetMeetings();
        invalidateOptionsMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListFragment.listToUpdate(SORT_DEFAULT);
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

    private void showRightFragment(final Fragment pFragment) {
        final FragmentManager lFragmentManager = getSupportFragmentManager();
        final FragmentTransaction lFragmentTransaction = lFragmentManager.beginTransaction();
        lFragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right, R.anim.exit_to_right);
        lFragmentTransaction.replace(R.id.frame_right,pFragment);
        lFragmentTransaction.commit();
    }

    private void manageActionBar(boolean isEnabled) {
        if (getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isEnabled);
            getSupportActionBar().setDisplayShowHomeEnabled(isEnabled);
        }
    }

    @Override
    public void onButtonClickedClose(View pView, String pActivateFragment) {
        if (mAddFragment != null && mAddFragment.isVisible()) {
            if (pActivateFragment == "RIGHT") {
                showRightFragment(mRightFragment);
            }
        }
        manageActionBar(false);
        mListFragment.listToUpdate(SORT_DEFAULT);
    }

    @Override
    public void onButtonClicked(View pView) {
        if (mRightFragment != null && mRightFragment.isVisible()) {
            mAddFragment = AddFragment.newInstance();
            showRightFragment(mAddFragment);
        }
        manageActionBar(true);
    }

    @Override
    public void onItemClicked(View pView) {
        manageActionBar(true);
        if (mMainLayout.getTag() == getString(R.string.tablet)) {
            mDetailFragment = DetailFragment.newInstance();
            if (!mDetailFragment.isVisible())
                showRightFragment(mDetailFragment);
        } else {
            Intent lIntent = new Intent(mContext, DetailActivity.class);
            mContext.startActivity(lIntent);
        }
    }

    @Override
    public void listToUpdate(String pOrder) {
        mListFragment.listToUpdate(pOrder);
    }



}
