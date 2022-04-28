package fr.joudar.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import fr.joudar.mareu.R;
import fr.joudar.mareu.databinding.ActivityMeetingDetailBinding;
import fr.joudar.mareu.databinding.ActivityMeetingsListBinding;

public class MeetingDetailActivity extends AppCompatActivity {

    ActivityMeetingDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMeetingDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}