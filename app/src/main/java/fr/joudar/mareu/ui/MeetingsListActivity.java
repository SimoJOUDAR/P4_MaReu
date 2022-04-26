package fr.joudar.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import fr.joudar.mareu.R;

public class MeetingsListActivity extends AppCompatActivity {

    MeetingsListFragment mMeetingsListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_list);

        mMeetingsListFragment = MeetingsListFragment.newInstance();
        //FragmentManager fragmentManager = getSupportFragmentManager();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mMeetingsListFragment).commit();
    }
}