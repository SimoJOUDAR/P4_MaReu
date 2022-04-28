package fr.joudar.mareu.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import fr.joudar.mareu.R;
import fr.joudar.mareu.databinding.ActivityMeetingsListBinding;
import fr.joudar.mareu.di.DI;
import fr.joudar.mareu.model.Meeting;
import fr.joudar.mareu.service.ApiService;
import fr.joudar.mareu.utils.onItemClickedListener;

public class MeetingsListActivity extends AppCompatActivity implements onItemClickedListener{

    ActivityMeetingsListBinding binding;
    public ApiService mApiService;
    RecyclerView mRecyclerView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMeetingsListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mApiService = DI.getApiService();
        mRecyclerView = binding.recyclerViewList;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        refreshRecyclerView(mApiService.getMeetings());
    }

    private void refreshRecyclerView(List<Meeting> items) {
        this.mRecyclerView.setAdapter(new MeetingsListRecyclerViewAdapter(items, this));
    }


    @Override
    public void onItemDetailClicked(int id) {

    }

    @Override
    public void onItemDeleteClicked(int id) {

    }
}