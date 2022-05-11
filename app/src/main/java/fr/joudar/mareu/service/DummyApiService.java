package fr.joudar.mareu.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.joudar.mareu.model.Meeting;
import fr.joudar.mareu.model.Room;

/**
 * Dummy mock of the Api
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class DummyApiService implements ApiService{

    private List<Room> roomsList = DummySampleGenerator.generateRoomsList();
    private List<String> participantsList = DummySampleGenerator.generateParticipantsList();
    private List<Meeting> meetingsList = DummySampleGenerator.generateMeetingsList();

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Room> getRoomsList() {
        return roomsList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getRoomsListAsStrings() {
        int listSize = roomsList.size();
        String[] roomsListAsString = new String[listSize];
        for (int i=0; i<listSize; i++){
            roomsListAsString[i] = roomsList.get(i).getRoomName();
        }
        return roomsListAsString;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllParticipantsList() {
        return participantsList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewParticipant(String email) {
        participantsList.add(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetings() {
        return meetingsList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        meetingsList.remove(meeting);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMeeting(Meeting meeting) {
        meetingsList.add(meeting);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRoomAvailable(Room room, LocalDate date, LocalTime startTime, LocalTime finishTime) {
        //filter by room + Filter by date + compare time
        return false;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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
