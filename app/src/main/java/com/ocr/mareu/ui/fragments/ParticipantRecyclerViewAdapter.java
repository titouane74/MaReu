package com.ocr.mareu.ui.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ocr.mareu.R;
import com.ocr.mareu.model.Meeting;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ocr.mareu.ui.activities.MainActivity.sApiService;

/**
 * Created by Florence LE BOURNOT on 10/02/2020
 */
public class ParticipantRecyclerViewAdapter extends RecyclerView.Adapter<ParticipantRecyclerViewAdapter.ViewHolder>  {

    private Meeting mMeetingSelected;

    /**
     * Constructor de l'adapter du RecyclerView
     */
    public ParticipantRecyclerViewAdapter() {
        mMeetingSelected = sApiService.getMeetingSelected();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_participant_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mParticipants.setText(mMeetingSelected.getParticpants().get(position));

    }

    @Override
    public int getItemCount() {
        return mMeetingSelected.getParticpants().size();
    }


    /**
     * Class ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_participant_rv)
        TextView mParticipants;

        ViewHolder(View pView) {
            super(pView);
            ButterKnife.bind(this,pView);
        }
    }
}
