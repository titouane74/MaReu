package com.ocr.mareu.ui.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ocr.mareu.R;
import com.ocr.mareu.model.Meeting;
import com.ocr.mareu.service.MeetingApiServiceException;
import com.ocr.mareu.utils.SortOrFilter;
import com.ocr.mareu.utils.SortOrFilterLabel;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ocr.mareu.di.DI.sMeetingApiService;


/**
 * Created by Florence LE BOURNOT on 10/02/2020
 */
public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private OnRecyclerViewListener mCallback;

    /**
     * Inteface permettant de gérer les callbacks vers la MainActivity
     */
    public interface OnRecyclerViewListener {
        void onItemClicked(View pView);
        void listToUpdate(Enum pOrder);
    }

    private Context mContext;
    private List<Meeting> mMeetings;

    /**
     * Constructor de l'adapter du RecyclerView
     * @param pContext : context : context
     * @param pOrder : string : indicateur de trie ou de filtre
     */
    public MeetingRecyclerViewAdapter(Context pContext, Enum pOrder) {
        mContext = pContext;
//        sMeetingApiService.addFakeMeeting();
//        sMeetingApiService.addFakeValidMeetingsLongList();
        SortOrFilter lSortOrFilter = new SortOrFilter();

        mMeetings = sMeetingApiService.getMeetings();
        if (pOrder == SortOrFilterLabel.SORT_ROOM_ASC ) {
            mMeetings = lSortOrFilter.sortMeetingRoomAsc(mMeetings);
        } else if (pOrder == SortOrFilterLabel.SORT_ROOM_DESC ) {
            mMeetings = lSortOrFilter.sortMeetingRoomDesc(mMeetings);
        } else if (pOrder == SortOrFilterLabel.SORT_DATE_OLDER ) {
            mMeetings = lSortOrFilter.sortMeetingDateOlderToRecent(mMeetings);
        } else if (pOrder == SortOrFilterLabel.SORT_DATE_RECENT ) {
            mMeetings = lSortOrFilter.sortMeetingDateRecentToOlder(mMeetings);
        } else if (pOrder == SortOrFilterLabel.FILTER_ROOM ) {
            mMeetings = lSortOrFilter.filterMeetingRoom(mMeetings, sMeetingApiService.getRoomsSelected());
        } else if (pOrder == SortOrFilterLabel.FILTER_DATE ) {
            mMeetings = lSortOrFilter.filterMeetingDate(mMeetings, sMeetingApiService.getDateSelected());
        }

        if (mMeetings.size() > 0) {
            sMeetingApiService.setIsMenuActive(true);
        } else {
            sMeetingApiService.setIsMenuActive(false);
        }
        mCallback = (OnRecyclerViewListener) mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        mCallback = (OnRecyclerViewListener) mContext;

        final Meeting lMeeting = mMeetings.get(position);

        @SuppressLint("SimpleDateFormat")
        String lDescription = TextUtils.join(" - ", Arrays.asList(
                lMeeting.getRoom().getNameRoom(),
                new SimpleDateFormat("HH:mm").format(lMeeting.getStart().getTime()),
                lMeeting.getTopic()));

        holder.mDescription.setText(lDescription);
        holder.mParticipants.setText(
                TextUtils.join(", ",
                        lMeeting.getParticpants()));

        ((GradientDrawable)holder.mRoomColor.getBackground()).setColor(lMeeting.getRoom().getColorRoom());

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder lAlertDialogBuiler = new AlertDialog.Builder(mContext);
                lAlertDialogBuiler .setTitle(mContext.getString(R.string.app_name));
                lAlertDialogBuiler
                        .setMessage(mContext.getString(R.string.msg_delete_meeting))
                        .setCancelable(false)
                        .setPositiveButton(mContext.getString(R.string.btn_yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sMeetingApiService.deleteMeeting(lMeeting);
                                mCallback.listToUpdate(SortOrFilterLabel.SORT_DEFAULT);
                            }
                        })
                        .setNegativeButton(mContext.getString(R.string.btn_no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog lAlertDialog = lAlertDialogBuiler.create();
                lAlertDialog.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sMeetingApiService.setMeetingSelected(lMeeting);
                mCallback.onItemClicked(v);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    /**
     * Class ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_location_img)
        ImageView mRoomColor;
        @BindView(R.id.item_description)
        TextView mDescription ;
        @BindView(R.id.item_participant) TextView mParticipants;
        @BindView(R.id.item_delete_img)
        ImageButton mDelete;

        ViewHolder(View pView) {
            super(pView);
            ButterKnife.bind(this,pView);
        }
    }
    @Override
    public void onClick(View v) {}
}
