package fr.joudar.mareu.ui;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.joudar.mareu.R;
import fr.joudar.mareu.databinding.MeetingItemBinding;
import fr.joudar.mareu.model.Meeting;
import fr.joudar.mareu.utils.onDeleteClickedListener;
import fr.joudar.mareu.utils.onItemClickedListener;

public class MeetingsListRecyclerViewAdapter extends RecyclerView.Adapter<MeetingsListRecyclerViewAdapter.MyViewHolder> {

    private MeetingItemBinding viewHolderBinding;
    private List<Meeting> meetingList;
    private final onDeleteClickedListener mOnDeleteClickedListener;
    private final onItemClickedListener mOnItemClickedListener;
    
    public MeetingsListRecyclerViewAdapter(List<Meeting> meetingList, onDeleteClickedListener mOnDeleteClickedListener, onItemClickedListener mOnItemClickedListener){
        this.meetingList = meetingList;
        this.mOnDeleteClickedListener = mOnDeleteClickedListener;
        this.mOnItemClickedListener = mOnItemClickedListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_item, parent, false);*/
        viewHolderBinding = MeetingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(viewHolderBinding, mOnDeleteClickedListener, mOnItemClickedListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.makeBindings(meetingList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.meetingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        MeetingItemBinding viewHolderBinding;
        Meeting mMeeting;
        onDeleteClickedListener mOnDeleteClickedListener;
        onItemClickedListener mOnItemClickedListener;

        
        public MyViewHolder(MeetingItemBinding viewHolderBinding, onDeleteClickedListener mOnDeleteClickedListener, onItemClickedListener mOnItemClickedListener) {
            super(viewHolderBinding.getRoot());
            this.viewHolderBinding = viewHolderBinding;
            this.mOnDeleteClickedListener = mOnDeleteClickedListener;
            this.mOnItemClickedListener = mOnItemClickedListener;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void makeBindings(@NonNull final Meeting meeting){
            this.mMeeting = meeting;
            viewHolderBinding.itemIcon.setImageResource(meeting.getRoom().getRoomIcon());
            viewHolderBinding.meetingInfo.setText(String.format("%s - %s - %s", meeting.getTopic(), meeting.getStartTimeAsString(), meeting.getRoom().getRoomName()));
            viewHolderBinding.meetingParticipants.setText(meeting.getParticipantsAsString());
            viewHolderBinding.deleteItemButton.setOnClickListener(View -> mOnDeleteClickedListener.onDeleteClicked(meeting));
            viewHolderBinding.viewHolder.setOnClickListener(view -> mOnItemClickedListener.onItemClicked(meeting));
        }

    }
}
