package fr.joudar.mareu.ui;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.joudar.mareu.databinding.MeetingItemBinding;
import fr.joudar.mareu.model.Meeting;
import fr.joudar.mareu.utils.onItemClickedListener;

public class MeetingsListRecyclerViewAdapter extends RecyclerView.Adapter<MeetingsListRecyclerViewAdapter.MyViewHolder> {

    private MeetingItemBinding viewHolderBinding;
    private List<Meeting> mMeetingList;
    private final onItemClickedListener mOnItemClickedListener;
    
    public MeetingsListRecyclerViewAdapter(List<Meeting> meetingList, onItemClickedListener mOnItemClickedListener){
        this.mMeetingList = meetingList;
        this.mOnItemClickedListener = mOnItemClickedListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewHolderBinding = MeetingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(viewHolderBinding);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.makeBindings(mMeetingList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mMeetingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(MeetingItemBinding viewHolderBinding) {
            super(viewHolderBinding.getRoot());
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void makeBindings(@NonNull final Meeting meeting){
            viewHolderBinding.itemIcon.setImageResource(meeting.getRoom().getRoomIcon());
            viewHolderBinding.meetingInfo.setText(String.format("%s - %s - %s", meeting.getTopic(), meeting.getStartTimeAsString(), meeting.getRoom().getRoomName()));
            viewHolderBinding.meetingParticipants.setText(meeting.getParticipantsAsString());
            viewHolderBinding.deleteItemButton.setOnClickListener(View -> mOnItemClickedListener.onItemDeleteClicked(meeting));
            viewHolderBinding.viewHolder.setOnClickListener(view -> mOnItemClickedListener.onItemDetailClicked(meeting.getId()));
        }

    }
}
