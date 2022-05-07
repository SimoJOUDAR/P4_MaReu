package fr.joudar.mareu.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import fr.joudar.mareu.R;
import fr.joudar.mareu.databinding.ActivityAddMeetingBinding;
import fr.joudar.mareu.di.DI;
import fr.joudar.mareu.model.Meeting;
import fr.joudar.mareu.model.Room;

public class AddMeetingActivity extends AppCompatActivity {

    private int mId;
    private String mTopic;
    private LocalDate mDate;
    private LocalTime mStartTime;
    private LocalTime mFinishTime;
    private Room mRoom;
    private String[] mParticipants;
    private Meeting mMeeting;

    ActivityAddMeetingBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.newMeetingToolbar);

        initVariables();
        initRoomsListSpinner();
        initDatePicker();
        initTimePicker();
        initParticipantsChipGroup();
    }

    private void initVariables() {
        mTopic = null;
        mDate = null;
        mStartTime = null;
        mFinishTime = null;
        mRoom = null;
        mParticipants = null;
        mMeeting = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNewMeeting(){
        createNewMeetingId();
        getTopicFromEditText();
        initParticipants();
        if (mTopic != null && mDate != null && mStartTime != null & mFinishTime != null && mRoom != null && mParticipants != null) {
            mMeeting = new Meeting(mId, mTopic, mDate, mStartTime, mFinishTime, mRoom, mParticipants);
            DI.getApiService().addMeeting(mMeeting);
            Toast.makeText(this, "Meeting created", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_create_meeting, menu);
        return true;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNewMeetingId(){
        mId = DI.getApiService().getMeetings().size();
    }

    private void getTopicFromEditText() {
        if (!binding.topicEditText.getText().toString().isEmpty()) {
            mTopic = binding.topicEditText.getText().toString();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initRoomsListSpinner(){
        List<Room> roomsList = DI.getApiService().getRoomsList();
        RoomsListSpinnerAdapter roomsAdapter = new RoomsListSpinnerAdapter(getApplicationContext(), R.layout.rooms_list_spinner_item, roomsList);
        binding.roomAutoCompleteTextView.setAdapter(roomsAdapter);
        binding.roomAutoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
                mRoom = roomsAdapter.getItem(i);
                binding.roomAutoCompleteTextView.setText(mRoom.getRoomName());
        });
    }

    private void initDatePicker(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        DatePickerDialog picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mDate = LocalDate.of(i, i1, i2);
                binding.dateEditText.setText(String.format("%d/%d/%d", i2, i1+1, i));
            }
        }, year, month, day);

        binding.dateEditText.setOnClickListener(view -> {
            picker.show();
        });
    }

    private void initTimePicker(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog startTimepicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                mStartTime = LocalTime.of(i, i1);
                binding.startTimeEditText.setText(String.format("%d:%d", mStartTime.getHour(), mStartTime.getMinute()));

                if (mFinishTime == null){
                    mFinishTime = LocalTime.of(i, i1).plusMinutes(15);
                    binding.finishTimeEditText.setText(String.format("%d:%d", mFinishTime.getHour(), mFinishTime.getMinute()));
                }

            }
        }, hour, minute, android.text.format.DateFormat.is24HourFormat(this));

        binding.startTimeEditText.setOnClickListener(view -> {
            startTimepicker.show();
        });

        TimePickerDialog finishTimepicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                mFinishTime = LocalTime.of(i, i1);
                binding.finishTimeEditText.setText(String.format("%d:%d", mFinishTime.getHour(), mFinishTime.getMinute()));

            }
        }, hour, minute, android.text.format.DateFormat.is24HourFormat(this));

        binding.finishTimeEditText.setOnClickListener(view -> {
            finishTimepicker.show();
        });
    }

    private void initParticipantsChipGroup(){
        binding.participantsInputLayout.setEndIconOnClickListener(view -> {
            String email = binding.participantsEditText.getText().toString();
            if (checkEmailValidity(email)) {
                Chip chip = (Chip) LayoutInflater.from(this).inflate(R.layout.chip, null, false);
                chip.setText(email);
                chip.setOnCloseIconClickListener(view1 -> {
                    binding.participantsChipGroup.removeView(view1);
                });
                binding.participantsChipGroup.addView(chip);
                binding.participantsEditText.setText("");
            }
        });
    }

    private boolean checkEmailValidity(String email){
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }

        else {
            //TODO : change to show alert below view
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void initParticipants(){
        int chipCount = binding.participantsChipGroup.getChildCount();
        if (chipCount>0) {
            mParticipants = new String[chipCount];
            for (int i = 0; i < chipCount; i++) {
                mParticipants[i] = ((Chip) binding.participantsChipGroup.getChildAt(i)).getText().toString();
            }
        }
    }

    private void ExitActivity() {
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.create_new_meeting){
            createNewMeeting();
            if (mMeeting != null){
                ExitActivity();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}