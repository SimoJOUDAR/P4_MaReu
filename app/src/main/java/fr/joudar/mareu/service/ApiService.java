package fr.joudar.mareu.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import fr.joudar.mareu.model.Meeting;
import fr.joudar.mareu.model.Room;

/**
 * Meeting API client
 */
public interface ApiService {

    /**
     * Get a Room by name
     * @param roomName
     * @return {@link Room}
     */
    Room getRoom(String roomName);

    /**
     * Get all Rooms
     * @return {@link List}
     */
    List<Room> getRoomsList();

    /**
     * Get all Rooms
     * @return {@link String}
     */
    String[] getRoomsListAsStrings();

    /**
     * Get all Participants
     * @return {@link List}
     */
    List<String> getAllParticipantsList();

    /**
     * Add a Participant to Participant List
     * @param email
     */
    void addNewParticipant(String email);

    /**
     * Get all Meetings
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * Delete a Meeting
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Add a Meeting
     * @param meeting
     */
    void addMeeting(Meeting meeting);

    /**
     * Returns a boolean value of Room's available during the appointed date and time.
     * @param (room, date, startTime, finishTime) the location, date and time the room is intended to be used.
     * @return true if the Room is available and false otherwise
     */
    boolean isRoomAvailable(Room room, LocalDate date, LocalTime startTime, LocalTime finishTime);

    List<Meeting> MeetingsListFilteredByRoom(Room room);

    List<Meeting> MeetingsListFilteredByDate(LocalDate date);

}
