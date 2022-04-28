package fr.joudar.mareu.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.joudar.mareu.R;
import fr.joudar.mareu.model.Meeting;
import fr.joudar.mareu.utils.onDeleteClickedListener;
import fr.joudar.mareu.utils.onItemClickedListener;

public class MeetingsListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    List<Meeting> meetingsList;
    private onDeleteClickedListener mOnDeleteClickedListener;
    private onItemClickedListener mOnItemClickedListener;

    public static MeetingsListFragment newInstance(List<Meeting> meetingsList, onDeleteClickedListener mOnDeleteClickedListener, onItemClickedListener mOnItemClickedListener) {
        MeetingsListFragment fragment = new MeetingsListFragment();
        fragment.meetingsList = meetingsList;
        fragment.mOnDeleteClickedListener = mOnDeleteClickedListener;
        fragment.mOnItemClickedListener = mOnItemClickedListener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meetings_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(new MeetingsListRecyclerViewAdapter(meetingsList, mOnDeleteClickedListener, mOnItemClickedListener));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //mRecyclerView.setAdapter(new MeetingsListRecyclerViewAdapter(meetingsList, deleteListener, detailListener));
    }
}
