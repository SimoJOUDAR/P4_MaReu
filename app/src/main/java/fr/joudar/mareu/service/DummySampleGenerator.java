package fr.joudar.mareu.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.joudar.mareu.R;
import fr.joudar.mareu.model.Meeting;
import fr.joudar.mareu.model.Room;

/**
 * Data sample generator for demonstrations
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public abstract class DummySampleGenerator {

    public static List<Room> DUMMY_ROOMS_LIST = Arrays.asList(
            new Room("Mercury", R.drawable.ic_room_mercury),
            new Room("Venus", R.drawable.ic_room_venus),
            new Room("Earth", R.drawable.ic_room_earth),
            new Room("Mars", R.drawable.ic_room_mars),
            new Room("Jupiter", R.drawable.ic_room_jupiter),
            new Room("Saturn", R.drawable.ic_room_saturn),
            new Room("Uranus", R.drawable.ic_room_uranus),
            new Room("Neptune", R.drawable.ic_room_neptune)
    );

    public static List<String> DUMMY_PARTICIPANTS_LIST = Arrays.asList(
            "michaeljackson@mareu.fr", "eltonjohn@mareu.fr", "freddiemercury@mareu.fr",
            "arethafranklin@mareu.fr", "raycharles@mareu.fr", "elvispresley@mareu.fr",
            "samcooke@mareu.fr", "johnlennon@mareu.fr", "marvingaye@mareu.fr",
            "bobdylan@mareu.fr", "steviewonder@mareu.fr", "jamesbrown@mareu.fr",
            "littlerichard@mareu.fr", "charlesaznavour@mareu.fr"
    );

    public static List<Meeting> DUMMY_MEETINGS_LIST = Arrays.asList(
            new Meeting(1,
                    "Réunion A",
                    LocalDate.now(),
                    LocalTime.of(9,00),
                    LocalTime.of(10,00),
                    DUMMY_ROOMS_LIST.get(0),
                    new String[]{DUMMY_PARTICIPANTS_LIST.get(0), DUMMY_PARTICIPANTS_LIST.get(1), DUMMY_PARTICIPANTS_LIST.get(2)}),
            new Meeting(2,
                    "Réunion B",
                    LocalDate.now(),
                    LocalTime.of(11,00),
                    LocalTime.of(11,30),
                    DUMMY_ROOMS_LIST.get(1),
                    new String[]{DUMMY_PARTICIPANTS_LIST.get(3), DUMMY_PARTICIPANTS_LIST.get(4)}),
            new Meeting(3,
                    "Réunion C",
                    LocalDate.now().plusDays(1),
                    LocalTime.of(10,00),
                    LocalTime.of(10,15),
                    DUMMY_ROOMS_LIST.get(2),
                    new String[]{DUMMY_PARTICIPANTS_LIST.get(5), DUMMY_PARTICIPANTS_LIST.get(6)}),
            new Meeting(4,
                    "Réunion D",
                    LocalDate.now().plusDays(1),
                    LocalTime.of(14,10),
                    LocalTime.of(14,45),
                    DUMMY_ROOMS_LIST.get(3),
                    new String[]{DUMMY_PARTICIPANTS_LIST.get(7), DUMMY_PARTICIPANTS_LIST.get(8), DUMMY_PARTICIPANTS_LIST.get(9), DUMMY_PARTICIPANTS_LIST.get(10)}),
            new Meeting(5,
                    "Réunion E",
                    LocalDate.now().plusDays(2),
                    LocalTime.of(8,10),
                    LocalTime.of(8,45),
                    DUMMY_ROOMS_LIST.get(4),
                    new String[]{DUMMY_PARTICIPANTS_LIST.get(9), DUMMY_PARTICIPANTS_LIST.get(10)}),
            new Meeting(6,
                    "Réunion F",
                    LocalDate.now().plusDays(2),
                    LocalTime.of(17,10),
                    LocalTime.of(17,45),
                    DUMMY_ROOMS_LIST.get(5),
                    new String[]{DUMMY_PARTICIPANTS_LIST.get(11), DUMMY_PARTICIPANTS_LIST.get(12), DUMMY_PARTICIPANTS_LIST.get(13)}),
            new Meeting(7,
                    "Réunion G",
                    LocalDate.now().plusDays(3),
                    LocalTime.of(11,00),
                    LocalTime.of(11,30),
                    DUMMY_ROOMS_LIST.get(6),
                    new String[]{DUMMY_PARTICIPANTS_LIST.get(0), DUMMY_PARTICIPANTS_LIST.get(1), DUMMY_PARTICIPANTS_LIST.get(2)}),
            new Meeting(8,
                    "Réunion H",
                    LocalDate.now().plusDays(3),
                    LocalTime.of(17,30),
                    LocalTime.of(18,00),
                    DUMMY_ROOMS_LIST.get(7),
                    new String[]{DUMMY_PARTICIPANTS_LIST.get(5), DUMMY_PARTICIPANTS_LIST.get(1), DUMMY_PARTICIPANTS_LIST.get(7)})
    );

    static List<Room> generateRoomsList() {
        return new ArrayList<>(DUMMY_ROOMS_LIST);
    }

    static List<String> generateParticipantsList() {
        return new ArrayList<>(DUMMY_PARTICIPANTS_LIST);
    }

    static List<Meeting> generateMeetingsList() {
        return new ArrayList<>(DUMMY_MEETINGS_LIST);
    }

}
