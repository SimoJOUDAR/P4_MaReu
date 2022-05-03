package fr.joudar.mareu.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

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
        addMeetingLauncher();

        mApiService = DI.getApiService();
        mRecyclerView = binding.recyclerViewList;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        refreshRecyclerView();
    }

    private void refreshRecyclerView() {
        this.mRecyclerView.setAdapter(new MeetingsListRecyclerViewAdapter(mApiService.getMeetings(), this));
    }

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
                    Meeting newMeeting = (Meeting) result.getData().getExtras().get("newMeeting");
                    mApiService.addMeeting(newMeeting);
                    refreshRecyclerView();
                }
            }
        });

        binding.buttonAddMeeting.setOnClickListener(view -> {
            Intent i = new Intent(this, AddMeetingActivity.class);
            mStartAddMeetingForResult.launch(i);
        });
    }
}