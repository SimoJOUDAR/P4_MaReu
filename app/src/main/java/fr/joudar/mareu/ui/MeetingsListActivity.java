package fr.joudar.mareu.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import java.util.List;

import fr.joudar.mareu.R;
import fr.joudar.mareu.di.DI;
import fr.joudar.mareu.model.Meeting;
import fr.joudar.mareu.utils.onDeleteClickedListener;
import fr.joudar.mareu.utils.onItemClickedListener;

public class MeetingsListActivity extends AppCompatActivity implements onDeleteClickedListener, onItemClickedListener{

    List<Meeting> meetingsList;
    onDeleteClickedListener mOnDeleteClickedListener;
    onItemClickedListener mOnItemClickedListener;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_list);

        meetingsList = DI.getApiService().getMeetings();

        //FragmentManager fragmentManager = getSupportFragmentManager();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MeetingsListFragment.newInstance(meetingsList,this, this)).commit();
    }

    @Override
    public void onDeleteClicked(Meeting meeting) {

    }

    @Override
    public void onItemClicked(Meeting meeting) {

    }
}