package fr.joudar.mareu.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Meeting {

    private final int id;
    @NonNull
    private final String topic;
    @NonNull
    private final LocalDate date;
    @NonNull
    private final LocalTime startTime;
    @NonNull
    private final LocalTime finishTime;
    @NonNull
    private final Room room;
    @NonNull
    private final String[] participants;

    public Meeting(int id, @NonNull String topic, @NonNull LocalDate date, @NonNull LocalTime startTime,
                   @NonNull LocalTime finishTime, @NonNull Room room, @NonNull String[] participants) {
        this.id = id;
        this.topic = topic;
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.room = room;
        this.participants = participants;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getTopic() {
        return topic;
    }

    @NonNull
    public LocalDate getDate() {
        return date;
    }

    @NonNull
    public LocalTime getStartTime() {
        return startTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    public String getStartTimeAsString(){
        // return DateFormat.getTimeInstance( DateFormat.SHORT ).format(startTime);
        return startTime.format(DateTimeFormatter.ofPattern("HH'h'mm", Locale.FRENCH));
    }

    @NonNull
    public LocalTime getFinishTime() {
        return finishTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    public String getFinishTimeAsString(){
        // return DateFormat.getTimeInstance( DateFormat.SHORT ).format(finishTime);
        return finishTime.format(DateTimeFormatter.ofPattern("HH'h'mm", Locale.FRENCH));
    }

    @NonNull
    public Room getRoom() {
        return room;
    }

    @NonNull
    public String[] getParticipantsAsStringArray() {
        return participants;
    }

    public String getParticipantsAsString() {
        String participantsAsString = "";
        for (int i=0; i<participants.length; i++){
            participantsAsString += participants[i];
            if (i!=participants.length-1){
                participantsAsString += ", ";
            }
        }
        return participantsAsString;
    }
}
