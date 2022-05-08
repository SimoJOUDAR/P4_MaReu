package fr.joudar.mareu.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import fr.joudar.mareu.R;
import fr.joudar.mareu.databinding.ActivityMeetingsListBinding;
import fr.joudar.mareu.di.DI;
import fr.joudar.mareu.model.Meeting;
import fr.joudar.mareu.service.ApiService;
import fr.joudar.mareu.utils.onItemClickedListener;

public class MeetingsListActivity extends AppCompatActivity implements onItemClickedListener{


    ActivityMeetingsListBinding binding;
    ApiService mApiService;
    RecyclerView mRecyclerView;
    ActivityResultLauncher<Intent> mStartAddMeetingForResult;

    @RequiresApi(api = Build.VERSION_CODES.O)
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
    private void refreshRecyclerView() {
        this.mRecyclerView.setAdapter(new MeetingsListRecyclerViewAdapter(mApiService.getMeetings(), this));
    }

    /**********************************************************************************************
     *** Listeners
     *********************************************************************************************/
    @Override
    public void onItemDetailClicked(Meeting meeting) {
        Intent i = new Intent(this, MeetingDetailActivity.class);
        i.putExtra("Meeting", (Parcelable) meeting);
        startActivity(i);
    }

    @Override
    public void onItemDeleteClicked(Meeting meeting) {
        mApiService.deleteMeeting(meeting);
        refreshRecyclerView();
    }

    private void addMeetingLauncher(){
        mStartAddMeetingForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    //Meeting newMeeting = (Meeting) result.getData().getExtras().get("newMeeting");
                    //mApiService.addMeeting(newMeeting);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_room_filter:
                Toast.makeText(this, "Room filter checked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_date_filter:
                Toast.makeText(this, "Date filter checked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.submenu_room_filter_off:
                Toast.makeText(this, "Disable room filter checked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.submenu_date_filter_off:
                Toast.makeText(this, "Disable date filter checked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.submenu_all_filters_off:
                Toast.makeText(this, "Disable all filters checked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}