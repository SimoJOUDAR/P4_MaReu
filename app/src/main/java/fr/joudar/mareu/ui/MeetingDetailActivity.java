package fr.joudar.mareu.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import fr.joudar.mareu.R;
import fr.joudar.mareu.databinding.ActivityMeetingDetailBinding;
import fr.joudar.mareu.databinding.ActivityMeetingsListBinding;
import fr.joudar.mareu.model.Meeting;

public class MeetingDetailActivity extends AppCompatActivity {

    ActivityMeetingDetailBinding binding;
    Meeting mMeeting;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMeetingDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMeeting = this.getIntent().getParcelableExtra("Meeting");
        this.updateUI();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateUI(){
        binding.meetingRoomImage.setImageResource(mMeeting.getRoom().getRoomIcon());
        binding.meetingRoomName1.setText(mMeeting.getTopic());
        binding.meetingRoomName2.setText(mMeeting.getTopic());
        binding.meetingTopic.setText(mMeeting.getTopic());
        binding.meetingDate.setText(mMeeting.getDateAsString());
        binding.meetingTime.setText(String.format("%s - %s", mMeeting.getStartTimeAsString(), mMeeting.getFinishTimeAsString()));
        binding.meetingParticipantsList.setText(mMeeting.getParticipantsAsString());
    }
}