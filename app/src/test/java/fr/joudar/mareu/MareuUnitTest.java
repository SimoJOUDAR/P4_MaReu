package fr.joudar.mareu;

import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import fr.joudar.mareu.di.DI;
import fr.joudar.mareu.model.Meeting;
import fr.joudar.mareu.model.Room;
import fr.joudar.mareu.service.ApiService;
import fr.joudar.mareu.service.DummyApiService;
import fr.joudar.mareu.service.DummySampleGenerator;


public class MareuUnitTest {

    private ApiService apiService;


    @Test
    public void getMeetingsWithSuccess() {
        apiService = DI.getNewInstanceApiService();
        List<Meeting> generatedMeetingList = apiService.getMeetings();
        List<Meeting> expectedMeetingList = DummySampleGenerator.DUMMY_MEETINGS_LIST;
        assertEquals(generatedMeetingList, expectedMeetingList);
    }

    @Test
    public void addNewMeetingWithSuccess() {
        apiService = DI.getNewInstanceApiService();
        int meetingListSizeBefore = apiService.getMeetings().size();
        Meeting newMeeting = new Meeting(9,
                "Réunion I",
                LocalDate.now().plusDays(4),
                LocalTime.of(17,30),
                LocalTime.of(18,00),
                new Room("Earth", R.drawable.ic_room_earth),
                new String[]{"samcooke@mareu.fr", "johnlennon@mareu.fr", "marvingaye@mareu.fr"});
        apiService.addMeeting(newMeeting);
        int meetingListSizeAfter = apiService.getMeetings().size();
        assertTrue(apiService.getMeetings().contains(newMeeting));
        assertEquals(meetingListSizeAfter, meetingListSizeBefore + 1);
    }

    @Test
    public void deleteMeetingWithSuccess() {
        apiService = DI.getNewInstanceApiService();
        int meetingListSizeBefore = apiService.getMeetings().size();
        Meeting meetingToBeDeleted = apiService.getMeetings().get(0);

        apiService.deleteMeeting(meetingToBeDeleted);

        int meetingListSizeAfter = apiService.getMeetings().size();
        assertFalse(apiService.getMeetings().contains(meetingToBeDeleted));
        assertEquals(meetingListSizeAfter, meetingListSizeBefore-1);
    }

    @Test
    public void getMeetingsFilteredByDateWithSuccess() {
        apiService = DI.getNewInstanceApiService();
        List<Meeting> dateFilteredList = apiService.MeetingsListFilteredByDate(LocalDate.now());
        List<Meeting> expectedList = Arrays.asList(apiService.getMeetings().get(0), apiService.getMeetings().get(1));
        assertEquals(dateFilteredList, expectedList);
    }

    @Test
    public void getMeetingFilteredByRoomWithSuccess() {
        apiService = DI.getNewInstanceApiService();
        List<Meeting> roomFilteredList = apiService.MeetingsListFilteredByRoom(DummySampleGenerator.DUMMY_ROOMS_LIST.get(2));
        List<Meeting> expectedList = Arrays.asList(apiService.getMeetings().get(2));
        assertEquals(roomFilteredList, expectedList);
    }

    @Test
    public void addNewParticipantWithSuccess() {
        apiService = DI.getNewInstanceApiService();
        int participantsListSizeBefore = apiService.getAllParticipantsList().size();
        apiService.addNewParticipant("Hello@test.fr");
        int participantsListSizeAfter = apiService.getAllParticipantsList().size();

        assertTrue(apiService.getAllParticipantsList().contains("Hello@test.fr"));
        assertEquals(participantsListSizeAfter, participantsListSizeBefore+1);
    }
}
