package fr.joudar.mareu.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import java.time.LocalDate;
import java.util.Calendar;

import fr.joudar.mareu.R;
import fr.joudar.mareu.databinding.ActivityMeetingsListBinding;
import fr.joudar.mareu.di.DI;
import fr.joudar.mareu.model.Meeting;
import fr.joudar.mareu.model.Room;
import fr.joudar.mareu.service.ApiService;
import fr.joudar.mareu.utils.onItemClickedListener;

/**
 * Main Activity to display the list of all meetings and show options
 */
public class MeetingsListActivity extends AppCompatActivity implements onItemClickedListener{


    ActivityMeetingsListBinding binding;
    ApiService mApiService;
    RecyclerView mRecyclerView;
    ActivityResultLauncher<Intent> mStartAddMeetingForResult;
    LocalDate mFilterDate;
    Room mFilterRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMeetingsListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.meetingListToolbar);

        addMeetingLauncher();

        mApiService = DI.getApiService();
        mRecyclerView = binding.recyclerViewList;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        refreshRecyclerView();
    }

    /**********************************************************************************************
     *** RecyclerView
     *********************************************************************************************/
    /**
     * Refresh the RecyclerView with unfiltered data.
     */
    private void refreshRecyclerView() {
        this.mRecyclerView.setAdapter(new MeetingsListRecyclerViewAdapter(mApiService.getMeetings(), this));
    }

    /**
     * Refresh the RecyclerView with room filtered data.
     * @param room
     */
    private void refreshRecyclerViewWithRoomFilter(Room room) {
        this.mRecyclerView.setAdapter(new MeetingsListRecyclerViewAdapter(mApiService.MeetingsListFilteredByRoom(room), this));
    }

    /**
     * Refresh the RecyclerView with date filtered data.
     * @param date
     */
    private void refreshRecyclerViewWithDateFilter(LocalDate date) {
        this.mRecyclerView.setAdapter(new MeetingsListRecyclerViewAdapter(mApiService.MeetingsListFilteredByDate(date), this));
    }

    /**********************************************************************************************
     *** Listeners
     *********************************************************************************************/

    /**
     * Launches the MeetingDetailActivity to display the meeting's info
     * @param meeting
     */
    @Override
    public void onItemDetailClicked(Meeting meeting) {
        Intent i = new Intent(this, MeetingDetailActivity.class);
        i.putExtra("Meeting", (Parcelable) meeting);
        startActivity(i);
    }

    /**
     * Deletes a meeting from the list.
     * @param meeting
     */
    @Override
    public void onItemDeleteClicked(Meeting meeting) {
        mApiService.deleteMeeting(meeting);
        refreshRecyclerView();
    }

    /**
     * Configures the "add meeting" button to launch the AddMeetingActivity.
     */
    private void addMeetingLauncher(){
        mStartAddMeetingForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    refreshRecyclerView();
                }
            }
        });

        binding.buttonAddMeeting.setOnClickListener(view -> {
            Intent i = new Intent(this, AddMeetingActivity.class);
            mStartAddMeetingForResult.launch(i);
        });
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Configures the Listeners for our custom Option Menu.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_room_filter:
                startRoomFilter();
                return true;
            case R.id.menu_date_filter:
                startDateFilter();
                return true;
            case R.id.menu_all_filters_off:
                removeAllFilters();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Launches a room picker dialog that allows to choose a Room to filter the meeting list with.
     */
    private void startRoomFilter() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.room_filter_dialog);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialog_button);
        dialogButton.setOnClickListener(view -> dialog.dismiss());

        ListView dialogListView = (ListView) dialog.findViewById(R.id.dialog_listview);
        RoomsListSpinnerAdapter dialogAdapter = new RoomsListSpinnerAdapter(this, R.layout.rooms_list_spinner_item, mApiService.getRoomsList());
        dialogListView.setAdapter(dialogAdapter);
        dialogListView.setOnItemClickListener((adapterView, view, i, l) -> {
            mFilterRoom = mApiService.getRoomsList().get(i);
            refreshRecyclerViewWithRoomFilter(mFilterRoom);
        });
        dialog.show();
    }

    /**
     * Launches a DatePicker dialog that allows to choose a date to filter the meeting list with.
     */
    private void startDateFilter(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mFilterDate = LocalDate.of(i, i1+1, i2);
                refreshRecyclerViewWithDateFilter(mFilterDate);
            }
        }, year, month, day);
        picker.show();
    }

    /**
     * Removes all filters
     */
    private void removeAllFilters() {
        refreshRecyclerView();
    }

}