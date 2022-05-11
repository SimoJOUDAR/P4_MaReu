package fr.joudar.mareu.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import fr.joudar.mareu.R;
import fr.joudar.mareu.databinding.ActivityMeetingDetailBinding;
import fr.joudar.mareu.model.Meeting;

/**
 * Activity to displays meeting info
 */
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
        setSupportActionBar(binding.meetingDetailToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMeeting = this.getIntent().getParcelableExtra("Meeting");
        this.updateUI();

    }

    /**********************************************************************************************
     *** UI
     *********************************************************************************************/

    /**
     * Update UI with the correct data.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateUI(){
        binding.meetingRoomImage.setImageResource(mMeeting.getRoom().getRoomIcon());
        binding.meetingRoomName1.setText(mMeeting.getRoom().getRoomName());
        binding.meetingRoomName2.setText(mMeeting.getRoom().getRoomName());
        binding.meetingTopic.setText(mMeeting.getTopic());
        binding.meetingDate.setText(mMeeting.getDateAsString());
        binding.meetingTime.setText(String.format("%s - %s", mMeeting.getStartTimeAsString(), mMeeting.getFinishTimeAsString()));
        binding.meetingParticipantsList.setText(mMeeting.getParticipantsAsString());
    }

    /**********************************************************************************************
     *** OptionMenu
     *********************************************************************************************/

    /**
     * To inflate our own custom Option Menu.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
}