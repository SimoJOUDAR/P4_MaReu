package fr.joudar.mareu.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.joudar.mareu.model.Meeting;
import fr.joudar.mareu.model.Room;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DummyApiService implements ApiService{

    private List<Room> roomsList = DummySampleGenerator.generateRoomsList();
    private List<String> participantsList = DummySampleGenerator.generateParticipantsList();
    private List<Meeting> meetingsList = DummySampleGenerator.generateMeetingsList();

    @Override
    public Room getRoom(String roomName) {
        Room room = null;
        for (Room iterationRoom:roomsList){
            if (iterationRoom.getRoomName().equals(roomName)){
                room = iterationRoom;
            }
        }
        return room;
    }

    @Override
    public List<Room> getRoomsList() {
        return roomsList;
    }

    @Override
    public String[] getRoomsListAsStrings() {
        int listSize = roomsList.size();
        String[] roomsListAsString = new String[listSize];
        for (int i=0; i<listSize; i++){
            roomsListAsString[i] = roomsList.get(i).getRoomName();
        }
        return roomsListAsString;
    }

    @Override
    public List<String> getAllParticipantsList() {
        return participantsList;
    }

    @Override
    public void addNewParticipant(String email) {
        participantsList.add(email);
    }

    @Override
    public List<Meeting> getMeetings() {
        return meetingsList;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetingsList.remove(meeting);

    }

    @Override
    public void addMeeting(Meeting meeting) {
        meetingsList.add(meeting);
    }

    @Override
    public boolean isRoomAvailable(Room room, LocalDate date, LocalTime startTime, LocalTime finishTime) {
        //filter by room + Filter by date + compare time
        return false;
    }

    @Override
    public List<Meeting> MeetingsListFilteredByRoom(Room room) {
        List<Meeting> meetings = new ArrayList<>();
        for (Meeting meeting : meetingsList){
            if (meeting.getRoom().equals(room)){
                meetings.add(meeting);
            }
        }
        return meetings;
    }

    @Override
    public List<Meeting> MeetingsListFilteredByDate(LocalDate date) {
        List<Meeting> meetings = new ArrayList<>();
        for (Meeting meeting : meetingsList){
            if (meeting.getDate().isEqual(date)){
                meetings.add(meeting);
            }
        }
        return meetings;
    }
}
