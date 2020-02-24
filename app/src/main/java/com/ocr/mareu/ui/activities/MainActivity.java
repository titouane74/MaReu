package com.ocr.mareu.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ocr.mareu.R;
import com.ocr.mareu.di.DI;
import com.ocr.mareu.model.Room;
import com.ocr.mareu.service.MeetingApiService;
import com.ocr.mareu.ui.fragments.AddFragment;
import com.ocr.mareu.ui.fragments.DetailFragment;
import com.ocr.mareu.ui.fragments.ListFragment;
import com.ocr.mareu.ui.fragments.MeetingRecyclerViewAdapter;
import com.ocr.mareu.ui.fragments.RightFragment;
import com.ocr.mareu.utils.RoomsFilterDialogFragment;
import com.ocr.mareu.utils.SortOrFilterLabel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ocr.mareu.utils.ShowDateDialog.showCalendarDialog;


public class MainActivity extends AppCompatActivity implements RightFragment.OnRightListener,AddFragment.OnListenerAdd,
        MeetingRecyclerViewAdapter.OnRecyclerViewListener, RoomsFilterDialogFragment.OnDialogRoomsListener {

    public static MeetingApiService sApiService;

    @BindView(R.id.add_fab)
    FloatingActionButton mAddFab;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_layout)
    View mMainLayout;

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

        sApiService = DI.getMeetingApiService();
        sApiService.initializeRooms(mContext);

        configureAndShowListFragment();
        mAddFragment = new AddFragment();
        mDetailFragment = new DetailFragment();

        if (mMainLayout.getTag() == getString(R.string.tablet)) {
            configureAndShowRightFragment();
            mAddFab.hide();
        }
        mAddFab.setOnClickListener(v -> {
            mAddFragment = new AddFragment();
            replaceFragment(mAddFragment);
            mAddFab.hide();
        });
    }

    /**
     * Préparation du menu option pour activer ou désactivier les menus
     * @param pMenu : menu : menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu pMenu) {
        MenuItem lSortAscRoom = pMenu.findItem(R.id.sort_room_asc);
        MenuItem lSortDescRoom = pMenu.findItem(R.id.sort_room_desc);
        MenuItem lSortRecentDate = pMenu.findItem(R.id.sort_date_recent);
        MenuItem lSortOlderDate = pMenu.findItem(R.id.sort_date_older);
        MenuItem lFilterRoom = pMenu.findItem(R.id.filter_room);
        MenuItem lFilterDate = pMenu.findItem(R.id.filter_date);

        boolean isActive = sApiService.getIsMenuActive();

        lSortAscRoom.setEnabled(isActive);
        lSortDescRoom.setEnabled(isActive);
        lSortRecentDate.setEnabled(isActive);
        lSortOlderDate.setEnabled(isActive);
        lFilterRoom.setEnabled(isActive);
        lFilterDate.setEnabled(isActive);

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
                    if (mMainLayout.getTag() == getString(R.string.tablet)) {
                        replaceFragment(mRightFragment);
                    } else {
                        mAddFab.show();
                        replaceFragment(mListFragment);
                    }
                }
                manageActionBar(false);
                return true;
            case R.id.action_remove_filter:
                mListFragment.listToUpdate(SortOrFilterLabel.SORT_DEFAULT);
                return true;
            case R.id.sort_date_recent:
                mListFragment.listToUpdate(SortOrFilterLabel.SORT_DATE_RECENT);
                return true;
            case R.id.sort_date_older:
                mListFragment.listToUpdate(SortOrFilterLabel.SORT_DATE_OLDER);
                return true;
            case R.id.sort_room_asc:
                mListFragment.listToUpdate(SortOrFilterLabel.SORT_ROOM_ASC);
                return true;
            case R.id.sort_room_desc:
                mListFragment.listToUpdate(SortOrFilterLabel.SORT_ROOM_DESC);
                return true;
            case R.id.filter_date:
                sApiService.setDateSelected(showCalendarDialog(mContext, mListFragment));
                return true;
            case R.id.filter_room:
                DialogFragment lDialogRooms = new RoomsFilterDialogFragment();
                lDialogRooms.show(getSupportFragmentManager(),"RoomsFilterDialogFragment");
                return true;
            default:
                return super.onOptionsItemSelected(pItem);
        }
    }

    /**
     * onBackPressed : cliquer 2 fois sur la touche retour pour quitter l'application
     */
    @Override
    public void onBackPressed() {

        if (mBackPressedTime + 2000 > System.currentTimeMillis()) {
            sApiService.resetMeetings();
            mBackToast.cancel();
            super.onBackPressed();
            return;
        } else {
            mBackToast = Toast.makeText(mContext, R.string.exit_app_back_pressed, Toast.LENGTH_SHORT);
            mBackToast.show();
        }
        mBackPressedTime = System.currentTimeMillis();
    }

    /**
     * A la rotation de l'écran, on purge la liste des réunions et on réinitialise le menu
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sApiService.resetMeetings();
        invalidateOptionsMenu();
    }

    /**
     * A la rotation de l'écran, pour le mode tablette, on ferme tous les fragments ouverts
     * exceptés celui de la liste des réunions et de celui du bouton d'ajout d'une réunion
     * @param outState : bundle : on ne sauvegarde rien
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (mMainLayout.getTag() == getString(R.string.tablet)) {
            List<Fragment> lFragmentList = getSupportFragmentManager().getFragments();
            for (Fragment lFragment : lFragmentList) {
                if (lFragment.getTag() != getString(R.string.fragment_right) || lFragment.getTag() != getString(R.string.fragment_list)) {
                    if (!lFragment.isDetached()) {
                        lFragment.onDetach();
                        getSupportFragmentManager().beginTransaction().remove(lFragment).commit();
                    }
                }
            }
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Sur le retour sur le Mainactivity, on rafraîchi la liste des réunions
     */
    @Override
    protected void onResume() {
        super.onResume();
        mListFragment.listToUpdate(SortOrFilterLabel.SORT_DEFAULT);
    }

    /**
     * On initialise et on affiche le fragment contenant la liste des réunions
     */
    private void configureAndShowListFragment() {
        mListFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.frame_list);
        if (mListFragment == null) {
            mListFragment = new ListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_list, mListFragment)
                    .commit();
        }
    }

    /**
     * On initialise et on affiche le fragment de la partie droite en mode tablette
     * qui affiche par défaut le bouton d'ajout d'une réunion
     */
    private void configureAndShowRightFragment() {
        mRightFragment = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.frame_right);
        if (mRightFragment == null && findViewById(R.id.frame_right) != null) {
            mRightFragment = new RightFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_right, mRightFragment)
                    .commit();
        }
    }

    /**
     * Méthode remplacement de fragmentne fonction du mode tablette ou non
     * @param pFragment : fragment : fragment à afficher
     */

    private void replaceFragment(final Fragment pFragment) {
        final FragmentManager lFragmentManager = getSupportFragmentManager();
        final FragmentTransaction lFragmentTransaction = lFragmentManager.beginTransaction();
        lFragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        if (mMainLayout.getTag() == getString(R.string.tablet)) {
            lFragmentTransaction.replace(R.id.frame_right, pFragment);
        } else {
            lFragmentTransaction.replace(R.id.frame_list, pFragment);
        }
        lFragmentTransaction.commit();
    }

    /**
     * Gère l'affichage du bouton de retour à l'écran précédent en fonction de l'indicateur
     * @param isEnabled : boolean : indicateur de la barre de menu
     */
    private void manageActionBar(boolean isEnabled) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isEnabled);
            getSupportActionBar().setDisplayShowHomeEnabled(isEnabled);
        }
    }

    /**
     * Fermeture du fragment d'ajout d'une réunion lors du clique sur le bouton cancel
     * et réaffichage de l'écran de droite avec le bouton d'ajout de réunion
     * fonctionnement pour le mode tablette
     * @param pView : view : vue
     */
    @Override
    public void onButtonCancelClickedClose(View pView) {
        if (mAddFragment != null && mAddFragment.isVisible()) {
            if (mMainLayout.getTag() == getString(R.string.tablet)) {
               replaceFragment(mRightFragment);
            } else {
               replaceFragment(mListFragment);
                mAddFab.show();
            }
        }
        manageActionBar(false);
        mListFragment.listToUpdate(SortOrFilterLabel.SORT_DEFAULT);
        invalidateOptionsMenu();
    }

    /**
     * En mode tablette, ouverture du fragment d'ajout d'une réunion suite au clic
     * sur la bouton Ajouter un réunion du fragment_right
     * @param pView : view : vue
     */
    @Override
    public void onButtonAddMeetingClicked(View pView) {
        manageActionBar(true);
        if (mRightFragment != null && mRightFragment.isVisible()) {
            mAddFragment = new AddFragment();
            replaceFragment(mAddFragment);
            mAddFab.hide();
        }
    }

    /**
     * Sur le clic d'un item de la liste des réunions on ouvre l'écran de détail
     * En mode tablette, on ouvre le fragment de détail à droite sinon on ouvre à la place de ListFragment
     * @param pView : view : vue
     */
    @Override
    public void onItemClicked(View pView) {
        manageActionBar(true);
        mDetailFragment = new DetailFragment();
        mAddFab.hide();
        if (mMainLayout.getTag() == getString(R.string.tablet)) {
            replaceFragment(mDetailFragment);
        } else {
            replaceFragment(mDetailFragment);
        }
    }

    /**
     * Liste des réunions à mettre à jour en fonction du tri ou filtre passé
     * En mode tablette, si le détail d'une réunion est affichée et qu'elle est supprimée
     * de la liste alors on ferme le détail pour réactiver le fragment de droite
     * @param pOrder : string : ordre de tri ou filtre passé pour afficher la liste
     */
    @Override
    public void listToUpdate(Enum pOrder) {
        mListFragment.listToUpdate(pOrder);
        if (sApiService.getMeetingSelected() == sApiService.getMeetingDeleted()) {
            if (mMainLayout.getTag() == getString(R.string.tablet)) {
                if (mDetailFragment.isVisible()) {
                    mRightFragment = new RightFragment();
                    replaceFragment(mRightFragment);
                }
            }
        }
        invalidateOptionsMenu();
    }

    @Override
    public void onRoomsOkClicked(List<Room> pRoomsSelected) {
        sApiService.setRoomsSelected(pRoomsSelected);
        mListFragment.listToUpdate(SortOrFilterLabel.FILTER_ROOM);
    }
}