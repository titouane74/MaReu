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
import com.ocr.mareu.utils.GsonTransformer;
import com.ocr.mareu.utils.SortOrFilter;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ocr.mareu.di.DI.sMeetingApiService;
import static com.ocr.mareu.utils.SortOrFilter.FILTER_EMPTY;
import static com.ocr.mareu.utils.SortOrFilter.SORT_DEFAULT;

/**
 * Created by Florence LE BOURNOT on 10/02/2020
 */
public class ParticipantRecyclerViewAdapter extends RecyclerView.Adapter<ParticipantRecyclerViewAdapter.ViewHolder>  {

    private Context mContext;
    private Meeting mMeetingSelected;
    /**
     * Constructor de l'adapter du RecyclerView
     * @param pContext : context : context
     */
    public ParticipantRecyclerViewAdapter(Context pContext) {
        mContext = pContext;
        mMeetingSelected = sMeetingApiService.getMeetingSelected();
    }

    /**
     * onCreateViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * onBindViewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mParticipants.setText(mMeetingSelected.getParticpants().toString());

    }

    /**
     * getItemCount du recyclerview
     * @return
     */

    @Override
    public int getItemCount() {
        return 0;
    }


    /**
     * Class ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_participant_rv)
        TextView mParticipants;

        /**
         * Constructor ViewHolder
         * @param pView
         */
        ViewHolder(View pView) {
            super(pView);
            ButterKnife.bind(this,pView);
        }
    }

}
