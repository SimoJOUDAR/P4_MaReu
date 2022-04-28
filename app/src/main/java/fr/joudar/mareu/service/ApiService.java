package fr.joudar.mareu.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import fr.joudar.mareu.model.Meeting;
import fr.joudar.mareu.model.Room;

public interface ApiService {

    Room getRoom(String roomName);

    List<Room> getRoomsList();

    String[] getRoomsListAsStrings();

    List<String> getAllParticipantsList();

    List<Meeting> getMeetings();

    void deleteMeeting(Meeting meeting);

    void addMeeting(Meeting meeting);

    boolean isRoomAvailable(Room room, LocalDate date, LocalTime startTime, LocalTime finishTime);

    List<Meeting> MeetingsListFilteredByRoom(List<String> selectedRooms);

    List<Meeting> MeetingsListFilteredByDate(LocalDate date);

}
