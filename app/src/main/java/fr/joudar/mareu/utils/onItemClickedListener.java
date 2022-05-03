package fr.joudar.mareu.utils;

import fr.joudar.mareu.model.Meeting;

public interface onItemClickedListener {
    void onItemDetailClicked(Meeting meeting);
    void onItemDeleteClicked(Meeting meeting);
}
