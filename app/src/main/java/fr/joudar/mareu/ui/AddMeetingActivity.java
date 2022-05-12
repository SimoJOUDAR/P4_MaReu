package fr.joudar.mareu.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
        getMenuInflater().inflate(R.menu.menu_create_meeting, menu);
        return true;
    }

    /**
     * Configures the Listener for our custom Option Menu.
     * @param item
     * @return true if a new Meeting is successfully created and finishes the Activity.
     */
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

    /**********************************************************************************************
     *** Id
     *********************************************************************************************/

    /**
     * To auto-generate an Id for the new Meeting being created.
     * @return true after assigning the Id value to the mId field.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean createNewMeetingId(){
        int lastIterator = DI.getApiService().getMeetings().size()-1;
        mId = DI.getApiService().getMeetings().get(lastIterator).getId()+1;
        return true;
    }

    /**********************************************************************************************
     *** Topic
     *********************************************************************************************/

    /**
     * Captures the topic Input and assigns it the mTopic field if the input is valid.
     * @return true if the mTopic field contains a valid value. Otherwise, false and shows an error.
     */
    private boolean validateTopic() {
        String topicInput = binding.topicEditText.getText().toString().trim();
        onInputChange(binding.topicInputLayout, binding.topicEditText);

        if (topicInput.isEmpty()) {
            binding.topicInputLayout.setError("Field can't be empty");
            return false;
        }
        else {
            binding.topicInputLayout.setError(null);
            mTopic = topicInput;
            return true;
        }
    }

    /**********************************************************************************************
     *** Rooms
     *********************************************************************************************/

    /**
     * Initializes the spinner for the Room list and configures the Listener to pick up the chosen Room and assigns it to the mRoom field.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initRoomsListSpinner(){
        List<Room> roomsList = DI.getApiService().getRoomsList();
        RoomsListSpinnerAdapter roomsAdapter = new RoomsListSpinnerAdapter(getApplicationContext(), R.layout.rooms_list_spinner_item, roomsList);
        binding.roomAutoCompleteTextView.setAdapter(roomsAdapter);
        binding.roomAutoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
                mRoom = roomsAdapter.getItem(i);
                binding.roomAutoCompleteTextView.setText(mRoom.getRoomName());
                binding.roomInputLayout.setError(null);
        });
    }

    /**
     * Checks if the mRoom field has been assigned correctly.
     * @return true if the mRoom field contains a valid value. Otherwise, false and shows an error.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validateRoom(){
        // TODO: onTextChange => Remove error
        if (DI.getApiService().getRoomsList().contains(mRoom)){
            binding.roomInputLayout.setError(null);
            return true;
        }
        else
            binding.roomInputLayout.setError("Field can't be empty");
            return false;
    }

    /**********************************************************************************************
     *** Date
     *********************************************************************************************/

    /**
     * Initializes the DatePicker and configures the Listener to pick up the chosen date and assigns it to the mDate field.
     */
    private void initDatePicker(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mDate = LocalDate.of(i, i1+1, i2);
                binding.dateEditText.setText(String.format("%d/%d/%d", i2, i1+1, i));
            }
        }, year, month, day);

        binding.dateEditText.setOnClickListener(view -> {
            picker.show();
        });
    }

    /**
     * Checks if the mDate field has been assigned correctly.
     * @return true if the mDate field contains a valid value. Otherwise, false and shows an error.
     */
    private boolean validateDate(){
        onInputChange(binding.dateInputLayout, binding.dateEditText);
        if (mDate == null) {
            binding.dateInputLayout.setError("Field can't be empty");
            return false;
        }
        else {
            binding.dateInputLayout.setError(null);
            return true;
        }
    }

    /**********************************************************************************************
     *** Time
     *********************************************************************************************/

    /**
     * Initializes the TimePickers and configures the Listeners to pick up the chosen times and assigns them to the mStartTime and mFinishTime fields.
     */
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

    /**
     * Checks if the mStartTime field has been assigned correctly.
     * @return true if the mStartTime field contains a valid value. Otherwise, false and shows an error.
     */
    private boolean validateStartTime(){
        onInputChange(binding.startTimeInputLayout, binding.startTimeEditText);
        if (mStartTime == null) {
            binding.startTimeInputLayout.setError("Field can't be empty");
            return false;
        }
        else {
            binding.startTimeInputLayout.setError(null);
            return true;
        }
    }

    /**
     * Checks if the mFinishTime field has been assigned correctly.
     * @return true if the mFinishTime field contains a valid value. Otherwise, false and shows an error.
     */
    private boolean validateFinishTime(){
        onInputChange(binding.finishTimeInputLayout, binding.finishTimeEditText);
        if (mFinishTime == null) {
            binding.finishTimeInputLayout.setError("Field can't be empty");
            return false;
        }
        else {
            binding.finishTimeInputLayout.setError(null);
            return true;
        }
    }

    /**********************************************************************************************
     *** Participants
     *********************************************************************************************/

    /**
     * Configures the participants "add button" Listener to pick up only valid inputs and assign them to new chips.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initParticipantsChipGroup(){
        initAutoCompleteAdapter();
        binding.participantsInputLayout.setEndIconOnClickListener(view -> {
            autoCompleteDropDrownItemClicked();
        });
    }

    /**
     * Initializes the Participants AutoComplete with the correct data.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initAutoCompleteAdapter(){
        List<String> data = DI.getApiService().getAllParticipantsList();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, data);
        binding.participantsAutoCompleteTextView.setAdapter(adapter);

        binding.participantsAutoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            autoCompleteDropDrownItemClicked();
        });
    }

    private void autoCompleteDropDrownItemClicked(){
        String email = binding.participantsAutoCompleteTextView.getText().toString().trim();
        if (checkEmailValidity(email)) {
            binding.participantsInputLayout.setError(null);
            Chip chip = (Chip) LayoutInflater.from(this).inflate(R.layout.chip, null, false);
            chip.setText(email);
            chip.setOnCloseIconClickListener(view1 -> {
                binding.participantsChipGroup.removeView(view1);
            });
            binding.participantsChipGroup.addView(chip);
            binding.participantsAutoCompleteTextView.setText("");
        }
    }

    /**
     * Checks email validity.
     * @param email input.
     * @return true if the email is valid. Otherwise, false and shows an error.
     */
    private boolean checkEmailValidity(String email){
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && checkEmailUniqueness(email)){
            return true;
        }

        else {
            binding.participantsInputLayout.setError("Email unavailable");
            binding.participantsInputLayout.setErrorIconDrawable(null);
            return false;
        }
    }

    /**
     * Checks if the new email input doesn't already exist in the ChipGroup.
     * @return true if the email is new and doesn't exist in the ChipGroup. Otherwise, false.
     */
    private boolean checkEmailUniqueness(String email){
        boolean isUnique = true;
        int chipCount = binding.participantsChipGroup.getChildCount();
        String chipEmail;
        if (chipCount>0) {
            for (int i = 0; i < chipCount; i++) {
                chipEmail = ((Chip) binding.participantsChipGroup.getChildAt(i)).getText().toString().trim();
                if (chipEmail.equals(email)) {
                    isUnique = false;
                }
            }
        }
        return isUnique;
    }

    /**
     * Adds the new email input to the Participants List if it doesn't already contains it. To keep email suggestions updated.
     * @param email
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void validateAndSaveNewParticipants(String email) {
        if (!DI.getApiService().getAllParticipantsList().contains(email)){
            DI.getApiService().addNewParticipant(email);
        }
    }

    /**
     * Assigns chip emails to the mParticipants field.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initParticipants(){
        int chipCount = binding.participantsChipGroup.getChildCount();
        if (chipCount>0) {
            mParticipants = new String[chipCount];
            for (int i = 0; i < chipCount; i++) {
                String email = ((Chip) binding.participantsChipGroup.getChildAt(i)).getText().toString();
                mParticipants[i] = email;
                validateAndSaveNewParticipants(email);
            }
        }
    }

    /**
     * Checks if the mParticipants field has been assigned correctly.
     * @return true if mParticipants field contains a valid value. Otherwise, false and shows an error.
     */
    private boolean validateParticipants(){
        if (mParticipants == null) {
            binding.participantsInputLayout.setError("Field can't be empty");
            binding.participantsInputLayout.setErrorIconDrawable(null);
            return false;
        }
        else {
            binding.finishTimeInputLayout.setError(null);
            return true;
        }
    }

    /**********************************************************************************************
     *** finish Activity
     *********************************************************************************************/

    /**
     * To finish the current activity and return back to the main Activity.
     */
    private void ExitActivity() {
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        finish();
    }

    /**********************************************************************************************
     *** Meeting
     *********************************************************************************************/

    /**
     * Checks if all fields are assigned correctly to create a new Meeting and add it to the Meeting list.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNewMeeting(){
        if (confirmationInput()) {
            mMeeting = new Meeting(mId, mTopic, mDate, mStartTime, mFinishTime, mRoom, mParticipants);
            DI.getApiService().addMeeting(mMeeting);
            Toast.makeText(this, "Meeting created", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checks if all inputs and assignments are valid.
     * @return true if all inputs and assignments are valid. Otherwise, false.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean confirmationInput(){
        initParticipants();
        boolean validInput = createNewMeetingId();
        validInput &= validateTopic();
        validInput &= validateRoom();
        validInput &= validateDate();
        validInput &= validateStartTime();
        validInput &= validateFinishTime();
        validInput &= validateParticipants();
        if (validInput)
            return true;
        else {
            return false;
        }
    }

    /**********************************************************************************************
     *** Tools
     *********************************************************************************************/

    /**
     * Configure the afterTextChanged Listener to remove errors during new inputs.
     * @param viewLayout
     * @param view
     */
    private void onInputChange(TextInputLayout viewLayout, TextInputEditText view){
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewLayout.setError(null);
            }
        });
    }

    /**
     * Initializes all fields to null.
     */
    private void initVariables() {
        mTopic = null;
        mDate = null;
        mStartTime = null;
        mFinishTime = null;
        mRoom = null;
        mParticipants = null;
        mMeeting = null;
    }

}