package fr.joudar.mareu.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import java.util.List;

import fr.joudar.mareu.R;
import fr.joudar.mareu.databinding.ActivityAddMeetingBinding;
import fr.joudar.mareu.di.DI;
import fr.joudar.mareu.model.Room;

public class AddMeetingActivity extends AppCompatActivity {

    ActivityAddMeetingBinding binding;

    Room mRoom;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.newMeetingToolbar);

        initRoomsListSpinner();

        this.createNewMeeting();

    }

    private void createNewMeeting(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_create_meeting, menu);
        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initRoomsListSpinner(){
        List<Room> roomsList = DI.getApiService().getRoomsList();
        RoomsListSpinnerAdapter roomsAdapter = new RoomsListSpinnerAdapter(getApplicationContext(), R.layout.rooms_list_spinner_item, roomsList);
        binding.roomAutoCompleteTextView.setAdapter(roomsAdapter);
        binding.roomAutoCompleteTextView.setOnItemClickListener(((adapterView, view, i, l) ->
                mRoom = roomsAdapter.getItem(i)));
    }
}